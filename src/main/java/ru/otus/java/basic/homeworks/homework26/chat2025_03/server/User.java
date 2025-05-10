import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {
    private int id; // -1 означает что это новый пользователь, который ещё не сохранен в случае JDBC
    private String login;
    private String password;
    private String username;
    private final List<Role> roles = new ArrayList<>();

    public User(String login, String password, String username) {
        this(-1, login, password, username);
    }

    public User(int id, String login, String password, String username) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.username = username;
    }

    public Iterator<Role> getRolesIterator() {
        return roles.iterator();
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void loadUserRoles(List<Role> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
    }

    public boolean checkRole(Role role) {
        return roles.contains(role);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
