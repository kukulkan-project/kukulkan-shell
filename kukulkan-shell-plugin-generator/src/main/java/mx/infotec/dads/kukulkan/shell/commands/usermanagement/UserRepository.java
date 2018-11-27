package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository {

    private Map<Long, User> users;

    private File file;

    public UserRepository(List<User> users, File file) {
        this.users = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        this.file = file;
    }

    public void delete(Long id) {
        users.remove(id);
        saveCsv();
    }

    public boolean exists(Long id) {
        return users.get(id) != null;
    }

    public List<User> findAll() {
        return users.values().stream().collect(Collectors.toList());
    }

    public User save(User user) throws LoginAlreadyUsed, EmailAlreadyUsed {
        Long id = user.getId();
        if (id == null) {
            user.setId(nextId());
        }
        if (findOneByLogin(user.getLogin()).isPresent()) {
            throw new LoginAlreadyUsed();
        }
        if (findOneByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new EmailAlreadyUsed();
        }
        User saved = users.put(id, user);
        saveCsv();
        return saved;

    }

    public Optional<User> findOneByLogin(String login) {
        return users.values().stream().filter(user -> login.equals(user.getLogin())).findFirst();
    }

    public Optional<User> findOneByEmailIgnoreCase(String email) {
        if (email == null)
            return Optional.empty();
        return users.values().stream().filter(user -> email.equalsIgnoreCase(user.getEmail())).findFirst();
    }

    public User findOne(Long id) {
        return users.get(id);
    }

    private Long nextId() {
        Optional<Long> maybeMax = users.keySet().stream().max(Comparator.naturalOrder());
        if (maybeMax.isPresent())
            return maybeMax.get() + 1;
        else
            return System.currentTimeMillis();
    }

    private void saveCsv() {
        CsvUtils.saveCsv((users.values().stream().collect(Collectors.toList())), User.class, file);
    }

}
