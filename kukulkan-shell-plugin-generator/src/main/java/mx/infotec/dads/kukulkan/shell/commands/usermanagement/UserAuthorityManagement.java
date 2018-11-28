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

import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.services.PrintService;

@ShellComponent
public class UserAuthorityManagement extends AbstractCommand {

    private static final String USERS_CSV = "src/main/resources/config/liquibase/users.csv";

    private static final String AUTHORITIES_CSV = "src/main/resources/config/liquibase/authorities.csv";

    private static final String USERS_AUTHORITIES_CSV = "src/main/resources/config/liquibase/users_authorities.csv";

    @Autowired
    private PrintService printer;

    public Availability userAuthorityManagementAvailability() {
        File usersCsv = navigator.getCurrentPath().resolve(USERS_CSV).toFile();
        File authoritiesCsv = navigator.getCurrentPath().resolve(AUTHORITIES_CSV).toFile();
        File usersAuthoritiesCsv = navigator.getCurrentPath().resolve(USERS_AUTHORITIES_CSV).toFile();

        if (!usersCsv.exists()) {
            return Availability
                    .unavailable("Users file was not found at " + System.lineSeparator() + usersCsv.getAbsolutePath());
        } else if (!authoritiesCsv.exists()) {
            return Availability
                    .unavailable("Authorities file was not found at " + System.lineSeparator() + AUTHORITIES_CSV);
        } else if (!usersAuthoritiesCsv.exists()) {
            return Availability.unavailable("Users-Authorities file was not found at " + AUTHORITIES_CSV);
        } else {
            return Availability.available();
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Add a user to project")
    public void addUser(String firstName, String lastName, String email, String login, String password,
            @ShellOption(defaultValue = "es") String langKey,
            @ShellOption(valueProvider = CommaSeparatedAuthoritiesValuesProvider.class) String authoritiesString) {
        UserAuthorityRepository repo = createRepo();
        printer.info("Creating new user...");
        User user = getUser(firstName, lastName, email, login, password, langKey);
        try {
            user.setAuthorities(Arrays.asList(authoritiesString.split("\\s*,\\s*")).stream().map(Authority::new)
                    .collect(Collectors.toSet()));
            repo.save(user);
            repo.flush();
        } catch (AuthorityNotExists | LoginAlreadyUsed | EmailAlreadyUsed e) {
            printer.error(e.getMessage());
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Update user with given info")
    public void updateUser(@ShellOption(valueProvider = UserValueProvider.class) Long id, String firstName,
            String lastName, String email, String login, String password,
            @ShellOption(defaultValue = "es") String langKey) {
        UserAuthorityRepository repo = createRepo();
        printer.info("Updating user {}", id);
        User user = repo.findOne(id);
        if (user != null) {
            try {
                if (firstName != null)
                    user.setFirstName(firstName);
                if (lastName != null)
                    user.setLastName(lastName);
                if (email != null)
                    user.setEmail(email);
                if (login != null)
                    user.setLogin(login);
                if (password != null)
                    user.setPassword(password);
                if (langKey != null)
                    user.setLangKey(langKey);

                repo.save(user);
                repo.flush();
            } catch (AuthorityNotExists | LoginAlreadyUsed | EmailAlreadyUsed e) {
                printer.error(e.getMessage());
            }
        } else {
            printer.error("No user found with id " + id);
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("List all users registered for this project")
    public void listUsers() {
        UserAuthorityRepository repo = createRepo();
        printer.info("Users:");
        List<User> users = repo.findAll();
        for (User user : users) {
            printer.info(user.toString());
            printer.info(System.lineSeparator());
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Delete user with given id")
    public void deleteUser(@ShellOption(valueProvider = UserValueProvider.class) Long id) {
        UserAuthorityRepository repo = createRepo();
        printer.info("Deleting user with id " + id);
        if (!repo.exists(id)) {
            printer.warning("User with id " + id + " does not exists");
        } else {
            repo.delete(id);
            repo.flush();
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("List all authorities registered for this project")
    public void listAuthorities() {
        UserAuthorityRepository repo = createRepo();
        printer.info("Authorities:");
        for (Authority authority : repo.findAllAuthorities()) {
            printer.info(authority.getName());
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Delete an authority")
    public void deleteAuthority(@ShellOption(valueProvider = AuthorityValueProvider.class) String name) {
        printer.info("Deleting authority: " + name);
        UserAuthorityRepository repo = createRepo();
        try {
            repo.delete(new Authority(name));
            repo.flush();
        } catch (AuthorityHasAssociatedUsers e) {
            printer.error(e.getMessage());
        }
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Add an authority")
    public void addAuthority(String name) {
        UserAuthorityRepository repo = createRepo();
        repo.save(new Authority(name.toUpperCase()));
        repo.flush();
    }

    @ShellMethodAvailability("userAuthorityManagementAvailability")
    @ShellMethod("Add an authority to user")
    public void addAuthorityToUser(@ShellOption(valueProvider = UserValueProvider.class) Long userId,
            @ShellOption(valueProvider = AuthorityValueProvider.class) String authorityName) {
        UserAuthorityRepository repo = createRepo();
        Authority authority = new Authority(authorityName);
        if (!repo.exists(authority)) {
            printer.error("Authority " + authorityName + " does not exists");
            printer.info("Add a new authority with the 'add-authority' command first");
            return;
        }
        if (!repo.exists(userId)) {
            printer.error("User with Id " + userId + " does not exists");
            return;
        }
        User user = repo.findOne(userId);
        user.addAuthority(authority);
        try {
            repo.save(user);
            repo.flush();
        } catch (AuthorityNotExists | LoginAlreadyUsed | EmailAlreadyUsed e) {
            printer.error("Failed to add authority to user");
        }
    }

    private User getUser(String firstName, String lastName, String email, String login, String password,
            String langKey) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setLogin(login);
        user.setImageUrl(null);
        user.setLangKey(langKey);
        user.setPassword(encoder.encode(password));
        return user;
    }

    public UserAuthorityRepository createRepo() {
        return new UserAuthorityRepository(navigator.getCurrentPath().resolve(USERS_CSV),
                navigator.getCurrentPath().resolve(AUTHORITIES_CSV),
                navigator.getCurrentPath().resolve(USERS_AUTHORITIES_CSV));
    }

}
