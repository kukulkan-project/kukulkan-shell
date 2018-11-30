package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.services.PrintService;

@ShellComponent
public class UserAuthorityManagementCommands extends AbstractCommand {

    private static final String USERS_CSV = "src/main/resources/config/liquibase/users.csv";

    private static final String AUTHORITIES_CSV = "src/main/resources/config/liquibase/authorities.csv";

    private static final String USERS_AUTHORITIES_CSV = "src/main/resources/config/liquibase/users_authorities.csv";

    private static final String USERS_TABLE_FORMAT = "%1s %20s %30s %30s %20s %20s %10s %10s %10s %10s %10s";

    @Autowired
    private PrintService printer;

    public Availability userAuthorityManagementAvailability() {
        File usersCsv = navigator.getCurrentPath().resolve(USERS_CSV).toFile();
        File authoritiesCsv = navigator.getCurrentPath().resolve(AUTHORITIES_CSV).toFile();
        File usersAuthoritiesCsv = navigator.getCurrentPath().resolve(USERS_AUTHORITIES_CSV).toFile();

        if (!usersCsv.exists()) {
            return Availability
                    .unavailable(String.format("Users file was not found at %s", usersCsv.getAbsolutePath()));
        } else if (!authoritiesCsv.exists()) {
            return Availability.unavailable(String.format("Authorities file was not found at %s", AUTHORITIES_CSV));
        } else if (!usersAuthoritiesCsv.exists()) {
            return Availability
                    .unavailable(String.format("Users-Authorities file was not found at %s", AUTHORITIES_CSV));
        } else {
            return Availability.available();
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Add a user to project")
    public void addUser(String firstName, String lastName, String email, String login, String password,
            @ShellOption(defaultValue = "es") String langKey,
            @ShellOption(valueProvider = CommaSeparatedAuthoritiesValuesProvider.class, defaultValue = "ROLE_USER") String authorities) {
        UserAuthorityRepository repo = createRepo();
        User user = getUser(firstName, lastName, email, login, password, langKey);
        try {
            if (!"DEFAULT".equalsIgnoreCase(authorities))
                setUserAuthoritiesFromCsvString(user, authorities);
            if (repo.findOneByLogin(user.getLogin()).isPresent()) {
                printer.error("Login already in use");
                return;
            }
            if (repo.findOneByEmailIgnoreCase(user.getEmail()).isPresent()) {
                printer.error("Email already in use");
                return;
            }
            repo.save(user);
            repo.flush();
            printer.info(String.format("User %s added with id %s", user.getLogin(), user.getId()));
        } catch (AuthorityNotExists e) {
            printer.error(e.getMessage());
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Update user with given info")
    public void updateUser(@ShellOption(valueProvider = UserValueProvider.class) Long id,
            @ShellOption(defaultValue = "", help = "Comma separated authorities") String authorities,
            @ShellOption(defaultValue = "") String firstName, @ShellOption(defaultValue = "") String lastName,
            @ShellOption(defaultValue = "") String email, @ShellOption(defaultValue = "") String login,
            @ShellOption(defaultValue = "") String password, @ShellOption(defaultValue = "es") String langKey) {
        UserAuthorityRepository repo = createRepo();
        User user = repo.findOne(id);
        if (user != null) {
            try {
                if (!StringUtils.isEmpty(authorities))
                    setUserAuthoritiesFromCsvString(user, authorities);
                if (!StringUtils.isEmpty(firstName))
                    user.setFirstName(firstName);
                if (!StringUtils.isEmpty(lastName))
                    user.setLastName(lastName);
                if (!StringUtils.isEmpty(email))
                    user.setEmail(email);
                if (!StringUtils.isEmpty(login))
                    user.setLogin(login);
                if (!StringUtils.isEmpty(password))
                    user.setPassword(encodePassword(password));
                if (!StringUtils.isEmpty(langKey))
                    user.setLangKey(langKey);

                repo.save(user);
                repo.flush();
                printer.info(String.format("User %s updated", user.getLogin()));
            } catch (AuthorityNotExists e) {
                printer.error(e.getMessage());
            }
        } else {
            printer.error(String.format("No user found with id %s", id));
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("List all users registered for this project")
    public void listUsers() {
        UserAuthorityRepository repo = createRepo();
        List<User> users = repo.findAll();
        printer.info(String.format(USERS_TABLE_FORMAT, "ID", "LOGIN", "EMAIL", "AUTHORITIES", "FIRST NAME", "LAST NAME",
                "ACTIVATED", "LANG KEY", "IMAGE URL", "CREATED BY", "LAST MODIFIED BY"));
        for (User user : users) {
            printer.info(String.format(USERS_TABLE_FORMAT, user.getId(), user.getLogin(), user.getEmail(),
                    user.getAuthorities(), user.getFirstName(), user.getLastName(), user.isActivated(),
                    user.getLangKey(), user.getImageUrl(), user.getCreatedBy(), user.getLastModifiedBy()));
            printer.info(System.lineSeparator());
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Delete user with given id")
    public void deleteUser(@ShellOption(valueProvider = UserValueProvider.class) Long id) {
        UserAuthorityRepository repo = createRepo();
        if (!repo.exists(id)) {
            printer.error(String.format("User with id %s does not exists", id));
        } else {
            String login = repo.findOne(id).getLogin();
            repo.delete(id);
            repo.flush();
            printer.info("Deleted user %s", login);
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("List all authorities registered for this project")
    public void listAuthorities() {
        UserAuthorityRepository repo = createRepo();
        printer.info("AUTHORITY");
        for (Authority authority : repo.findAllAuthorities()) {
            printer.info(authority.getName());
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Delete an authority")
    public void deleteAuthority(@ShellOption(valueProvider = AuthorityValueProvider.class) String name) {
        UserAuthorityRepository repo = createRepo();
        if (!repo.exists(new Authority(name))) {
            printer.error(String.format("Authority %s does not exists", name));
        }
        try {
            repo.delete(new Authority(name));
            repo.flush();
            printer.info(String.format("Authority %s deleted", name));
        } catch (AuthorityHasAssociatedUsers e) {
            printer.error(e.getMessage());
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Add an authority")
    public void addAuthority(String name) {
        UserAuthorityRepository repo = createRepo();
        if (repo.exists(new Authority(name))) {
            printer.error(String.format("Authority %s already exists", name));
        } else {
            repo.save(new Authority(name.toUpperCase()));
            repo.flush();
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Add an authority to user")
    public void addAuthorityToUser(@ShellOption(valueProvider = UserValueProvider.class) Long userId,
            @ShellOption(valueProvider = AuthorityValueProvider.class) String authorityName) {
        UserAuthorityRepository repo = createRepo();
        Authority authority = new Authority(authorityName);
        if (!repo.exists(authority)) {
            printer.error(String.format("Authority %s does not exists", authorityName));
            printer.info("Add a new authority with the 'add-authority' command first");
            return;
        }
        if (!repo.exists(userId)) {
            printer.error(String.format("User with id %s does not exists", userId));
            return;
        }
        User user = repo.findOne(userId);
        user.addAuthority(authority);
        try {
            printer.info(String.format("Adding authority %s to user %s", authorityName, user.getLogin()));
            repo.save(user);
            repo.flush();
        } catch (AuthorityNotExists e) {
            printer.error("Failed to add authority to user");
            printer.error(e.getMessage());
        }
    }

    private String encodePassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    private User getUser(String firstName, String lastName, String email, String login, String password,
            String langKey) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setLogin(login);
        user.setImageUrl(null);
        user.setLangKey(langKey);
        user.setPassword(encodePassword(password));
        return user;
    }

    private void setUserAuthoritiesFromCsvString(User user, String authoritiesString) {
        user.setAuthorities(Arrays.asList(authoritiesString.split("\\s*,\\s*")).stream().map(Authority::new)
                .collect(Collectors.toSet()));
    }

    public UserAuthorityRepository createRepo() {
        return new UserAuthorityRepository(navigator.getCurrentPath().resolve(USERS_CSV),
                navigator.getCurrentPath().resolve(AUTHORITIES_CSV),
                navigator.getCurrentPath().resolve(USERS_AUTHORITIES_CSV));
    }

}
