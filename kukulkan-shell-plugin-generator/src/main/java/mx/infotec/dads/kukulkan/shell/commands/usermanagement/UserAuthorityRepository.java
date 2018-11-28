package mx.infotec.dads.kukulkan.shell.commands.usermanagement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserAuthorityRepository {

    private Map<Long, User> users;

    private Set<Authority> authorities;

    private Set<UserAuthority> usersAuthorities;

    private Path usersCsvPath;

    private Path authoritiesCsvPath;

    private Path usersAuthoritiesCsvPath;

    public UserAuthorityRepository(Path usersCsvPath, Path authoritiesCsvPath, Path usersAuthoritiesCsvPath) {
        this.usersCsvPath = usersCsvPath;
        this.authoritiesCsvPath = authoritiesCsvPath;
        this.usersAuthoritiesCsvPath = usersAuthoritiesCsvPath;
        readFiles(usersCsvPath, authoritiesCsvPath, usersAuthoritiesCsvPath);
    }

    public void delete(Long id) {
        users.remove(id);
        removeUserAuthoritiesWithUserId(id);
    }

    public boolean exists(Long id) {
        return users.get(id) != null;
    }

    public List<User> findAll() {
        return users.values().stream().collect(Collectors.toList());
    }

    public User save(User user) throws LoginAlreadyUsed, EmailAlreadyUsed, AuthorityNotExists {
        Long id = user.getId();
        if (findOneByLogin(user.getLogin()).isPresent()) {
            throw new LoginAlreadyUsed();
        }
        if (findOneByEmailIgnoreCase(user.getEmail()).isPresent()) {
            throw new EmailAlreadyUsed();
        }
        if (id == null) {
            user.setId(nextId());
        }
        User saved = users.put(id, user);
        addUserAuthorities(user);
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

    public boolean exists(Authority authority) {
        for (Authority auth : authorities) {
            if (auth.getName().equalsIgnoreCase(authority.getName())) {
                return true;
            }
        }
        return false;
    }

    public void delete(Authority authority) throws AuthorityHasAssociatedUsers {
        // Verify authority it is not associated to users
        for (UserAuthority ua : usersAuthorities) {
            if (ua.getAuthority().getName().equalsIgnoreCase(authority.getName())) {
                throw new AuthorityHasAssociatedUsers();
            }
        }
        authorities.removeIf(auth -> auth.getName().equalsIgnoreCase(authority.getName()));
    }

    public List<Authority> findAllAuthorities() {
        return authorities.stream().collect(Collectors.toList());
    }

    public Authority save(Authority authority) {
        authorities.add(authority);
        return authority;
    }

    private void removeUserAuthoritiesWithUserId(Long id) {
        usersAuthorities.removeIf(ua -> ua.getId() == id);
    }

    private void addUserAuthorities(User user) throws AuthorityNotExists {
        removeUserAuthoritiesWithUserId(user.getId());
        for (Authority auth : user.getAuthorities()) {
            if (!exists(auth)) {
                throw new AuthorityNotExists(auth.getName());
            }
            usersAuthorities.add(new UserAuthority(user.getId(), auth));
        }
    }

    private Long nextId() {
        Optional<Long> maybeMax = users.keySet().stream().max(Comparator.naturalOrder());
        if (maybeMax.isPresent())
            return maybeMax.get() + 1;
        else
            return System.currentTimeMillis();
    }

    public void flush() {
        CsvUtils.saveCsv((users.values().stream().collect(Collectors.toList())), User.class, usersCsvPath.toFile());
        CsvUtils.saveCsv(authorities.stream().collect(Collectors.toList()), Authority.class,
                authoritiesCsvPath.toFile());
        CsvUtils.saveCsv(usersAuthorities.stream().collect(Collectors.toList()), UserAuthority.class,
                usersAuthoritiesCsvPath.toFile());
    }

    private void readFiles(Path usersCsvPath, Path authoritiesCsvPath, Path usersAuthoritiesCsvPath) {
        users = new HashMap<>();
        authorities = new HashSet<>();
        usersAuthorities = new HashSet<>();

        ObjectMapper mapper = new ObjectMapper();
        List<String> lines;
        List<Map<String, String>> mappedLines;

        // Reading users csv
        try {
            lines = Files.readAllLines(usersCsvPath);

            mappedLines = CsvUtils.parseCsv(lines);
            for (Map<String, String> map : mappedLines) {
                User user = mapper.convertValue(map, User.class);
                users.put(user.getId(), user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reading authorities
        try {
            lines = Files.readAllLines(authoritiesCsvPath);

            mappedLines = CsvUtils.parseCsv(lines);
            for (Map<String, String> map : mappedLines) {
                Authority auth = mapper.convertValue(map, Authority.class);
                authorities.add(auth);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Reading users-authorities
        try {
            lines = Files.readAllLines(usersAuthoritiesCsvPath);

            mappedLines = CsvUtils.parseCsv(lines);
            for (Map<String, String> map : mappedLines) {
                UserAuthority userAuthority = mapper.convertValue(map, UserAuthority.class);
                usersAuthorities.add(userAuthority);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Adding authorities to users
        for (UserAuthority userAuthority : usersAuthorities) {
            users.get(userAuthority.getId()).addAuthority(userAuthority.getAuthority());
        }
    }

}
