package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.infotec.dads.kukulkan.shell.commands.AbstractCommand;
import mx.infotec.dads.kukulkan.shell.services.PrintService;

@ShellComponent
public class UsersManagement extends AbstractCommand {

    @Autowired
    private PrintService printer;

    @ShellMethod("Add a user to project")
    public void createUser(String firstName, String lastName, String email, String login, String password,
            @ShellOption(defaultValue = "es") String langKey) throws IOException, LoginAlreadyUsed, EmailAlreadyUsed {
        UserRepository repo = createUserRepository();
        printer.info("Creating new user...");
        User user = getUser(firstName, lastName, email, login, password, langKey);
        repo.save(user);
    }

    @ShellMethod("Update user with given info")
    public void updateUser(@ShellOption(valueProvider = UserValueProvider.class) Long id, String firstName,
            String lastName, String email, String login, String password,
            @ShellOption(defaultValue = "es") String langKey) throws IOException, LoginAlreadyUsed, EmailAlreadyUsed {
        UserRepository repo = createUserRepository();
        printer.info("Updating user {}", id);
        User user = repo.findOne(id);
        if (user != null) {
            repo.save(user);
        } else {
            printer.error("No user found with id " + id);
        }
    }

    @ShellMethod("List all users registered for this project")
    public void listUsers() throws IOException {
        UserRepository repo = createUserRepository();
        printer.info("Users:");
        List<User> users = repo.findAll();
        for (User user : users) {
            printer.info(user.toString());
            printer.info(System.lineSeparator());
        }
    }

    @ShellMethod("Delete user with given id")
    public void deleteUser(@ShellOption(valueProvider = UserValueProvider.class) Long id) throws IOException {
        UserRepository repo = createUserRepository();
        printer.info("Deleting user with id " + id);
        if (!repo.exists(id)) {
            printer.warning("User with id " + id + " does not exists");
        } else {
            repo.delete(id);
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

    public UserRepository createUserRepository() throws IOException {
        Path usersCsvPath = navigator.getCurrentPath().resolve("src/main/resources/config/liquibase/users.csv");
        List<String> lines = Files.readAllLines(usersCsvPath);
        List<Map<String, String>> mappedLines = CsvUtils.parseCsv(lines);
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = new ArrayList<>();
        for (Map<String, String> map : mappedLines) {
            users.add(mapper.convertValue(map, User.class));
        }
        return new UserRepository(users, usersCsvPath.toFile());
    }

}
