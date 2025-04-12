import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PostgresAuthenticationProvider implements AuthenticatedProvider {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/otus-db";
    private final Connection connection;
    private final Server server;
    private static String USER_BY_LOGIN_PASSWORD_QUERY;
    private static String USER_BY_USERNAME_QUERY;
    private static String USER_BY_LOGIN_QUERY;
    private static String SAVE_NEW_USER_QUERY;
    private static String SAVE_USER_ROLE_QUERY;
    private static String ROLES_BY_USERNAME_QUERY;
    private static String ROLE_BY_NAME_QUERY;
    private static String SAVE_EXIST_USER_QUERY;
    private static Role adminRole;
    private static Role userRole;

    public PostgresAuthenticationProvider(Server server) {
        this.server = server;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            System.out.println("Не удалось подключиться к Postgres");
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, "admin", "password");
    }

    @Override
    public void initialize() {
        USER_BY_LOGIN_PASSWORD_QUERY =
                "select id, login, \"password\", username \n"
                        + "from public.users where login = ? and \"password\" = ?";

        ROLES_BY_USERNAME_QUERY =
                "select r.id, r.name \n"
                        + "from public.users_to_roles ur \n"
                        + "    join public.roles r on r.id = ur.role_id \n"
                        + "    join public.users u on u.id = ur.user_id \n"
                        + "where u.username = ?";

        ROLE_BY_NAME_QUERY = "select id, name from public.roles where name = ?";

        USER_BY_USERNAME_QUERY =
                "select id, login, \"password\", username \n"
                        + "from public.users where username = ?";

        USER_BY_LOGIN_QUERY =
                "select id, login, \"password\", username \n"
                        + "from public.users where login = ?";

        SAVE_NEW_USER_QUERY =
                "insert into public.users(login, \"password\", username) \n"
                        + "values(?, ?, ?) returning id";

        SAVE_EXIST_USER_QUERY =
                "update public.users \n"
                        + "  set login = ?, \"password\" = ?, username = ? \n"
                        + "where id = ?";

        SAVE_USER_ROLE_QUERY =
                "insert into public.users_to_roles(user_id, role_id) values(?, ?)";

        adminRole = readRole("admin");
        userRole = readRole( "user");

        System.out.println("Сервис аутентификации запущен: JDBC режим");
    }

    @Override
    public Role getRole(String nameRole) {
        switch (nameRole) {
            case "admin":
                return adminRole;
            case "user":
                return userRole;
            default:
                return null;
        }
    }

    private Role readRole(String nameRole) {
        try (PreparedStatement ps = connection.prepareStatement(ROLE_BY_NAME_QUERY)) {
            ps.setString(1, nameRole);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    return new Role(id, name);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDefaultRoles(User user){
        user.addRole(userRole);
        if (user.getUsername().equalsIgnoreCase("admin") || user.getUsername().equalsIgnoreCase("root")) {
            user.addRole(adminRole);
        }
    }

    int saveUserTable(User user, Connection connection) throws SQLException {
        int user_id  = user.getId();
        if (user_id == -1) {
            try (PreparedStatement ps = connection.prepareStatement(SAVE_NEW_USER_QUERY)){
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getUsername());
                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        user_id = rs.getInt("id");
                        if (user_id <= 0) {
                            System.out.println("Не удалось сохранить нового пользователя в бд, login = " + user.getLogin());
                            return -2;
                        }
                        user.setId(user_id);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException(e);
            }
        } else {
            try (PreparedStatement ps = connection.prepareStatement(SAVE_EXIST_USER_QUERY)){
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getUsername());
                ps.setInt(4, user_id);
                if (ps.executeUpdate() == 0) {
                    System.out.println("Не удалось сохранить существующего пользователя в бд, id = " + user_id);
                    return -2;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException(e);
            }
        }
        return user_id;
    }

    public void saveUser(User user){
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            int user_id;
            try {
                user_id = saveUserTable(user, connection);
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
                throw new RuntimeException(e);
            }

            try (PreparedStatement ps = connection.prepareStatement(SAVE_USER_ROLE_QUERY)){
                List<Role> roles = readUserRoles(user);
                Iterator<Role> iteratorRole = user.getRolesIterator();
                while (iteratorRole.hasNext()) {
                    Role role = iteratorRole.next();
                    if (roles != null && roles.contains(role)) {
                        continue;
                    }
                    ps.setInt(1, user_id);
                    ps.setInt(2, role.getId());
                    if (ps.executeUpdate() == 0) {
                        System.out.println("Не удалось сохранить роль пользователя в бд, id = " + user_id);
                        return;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
                throw new RuntimeException(e);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Role> readUserRoles(User user) {
        if (user == null) return null;

        String username = user.getUsername();
        try (PreparedStatement ps = connection.prepareStatement(ROLES_BY_USERNAME_QUERY)) {
            ps.setString(1, username);
            List<Role> result = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    result.add(new Role(id, name));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUserByLoginAndPassword(String login, String password) {
        try (PreparedStatement ps = connection.prepareStatement(USER_BY_LOGIN_PASSWORD_QUERY)) {
            ps.setString(1, login);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    String username = rs.getString(4);
                    return new User(id, login, password, username);
                }
                return null;
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }


    @Override
    public boolean authenticate(ClientHandler clientHandler, String login, String password) {
        User authUser = getUserByLoginAndPassword(login, password);
        if (authUser == null) {
            clientHandler.sendMessage("Некорректный логин/пароль");
            return false;
        }

        String authName = authUser.getUsername();
        if (server.isUsernameBusy(authName)) {
            clientHandler.sendMessage("Под учетной записью уже залогинились");
            return false;
        }

        authUser.loadUserRoles(readUserRoles(authUser));
        clientHandler.setUser(authUser);
        clientHandler.setUsername(authName);

        server.subscribe(clientHandler);

        clientHandler.sendMessage("/authok " + authName);
        return true;
    }

    private User userByLogin(String login) {
        try (PreparedStatement ps = connection.prepareStatement(USER_BY_LOGIN_QUERY)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                return !rs.next() ? null : new User(
                                                    rs.getInt("id"),
                                                    login,
                                                    rs.getString("password"),
                                                    rs.getString("username")
                                                    );
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private boolean isLoginAlreadyExist(String login) {
        return userByLogin(login) != null;
    }

    private User userByUsername(String username) {
        try (PreparedStatement ps = connection.prepareStatement(USER_BY_USERNAME_QUERY)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return !rs.next() ? null : new User(
                                                    rs.getInt("id"),
                                                    rs.getString("login"),
                                                    rs.getString("password"),
                                                    username
                                                    );
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private boolean isUsernameAlreadyExist(String username) {
        return userByUsername(username) != null;
    }

    @Override
    public boolean registration(ClientHandler clientHandler, String login, String password, String username) {
        if (login.trim().length() < 3 || password.trim().length() < 3 || username.trim().length() < 3) {
            clientHandler.sendMessage("Требования логин 3+ символа, пароль 3+ символа," + "имя пользователя 3+ символа не выполнены");
            return false;
        }
        if (isLoginAlreadyExist(login)) {
            clientHandler.sendMessage("Указанный логин уже занят");
            return false;
        }
        if (isUsernameAlreadyExist(username)) {
            clientHandler.sendMessage("Указанное имя пользователя уже занято");
            return false;
        }

        User user = new User(login, password, username);
        setDefaultRoles(user);
        saveUser(user);
        clientHandler.setUser(user);
        clientHandler.setUsername(username);

        server.subscribe(clientHandler);
        clientHandler.sendMessage("/regok " + username);

        return true;
    }
}
