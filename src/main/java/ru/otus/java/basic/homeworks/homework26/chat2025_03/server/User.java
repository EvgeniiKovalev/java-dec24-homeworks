import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class User {
    private final String login;
    private final String password;
    private final String username;
    private final List<Role> roles;

    public User(String login, String password, String username) {
        this.login = login;
        this.password = password;
        this.username = username;
        roles = new ArrayList<>(); //потокобезопасность не нужна

    }

    public void setDefaultRoles(){
        roles.add(Role.USER);
        if (username.equalsIgnoreCase("admin") || username.equalsIgnoreCase("root")) {
            roles.add(Role.ADMIN);
        }
    }

    public boolean checkRole(Role role) {
        return roles.contains(role);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

}
