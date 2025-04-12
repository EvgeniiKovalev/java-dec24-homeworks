public class Constants {
    private Constants() {
    }

    public static final String DATABASE_URL =
            "jdbc:postgresql://localhost:5432/otus-db";

    public static final String USER_BY_LOGIN_PASSWORD_QUERY =
            "select id, login, \"password\", username \n" +
            "from public.users where login = ? and \"password\" = ?";

    public static final String ROLES_BY_USERNAME_QUERY =
            "select r.id, r.name \n" +
            "from public.users_to_roles ur \n" +
            "    join public.roles r on r.id = ur.role_id \n" +
            "    join public.users u on u.id = ur.user_id \n" +
            "where u.username = ?";

    public static final String ROLE_BY_NAME_QUERY = "select id, name from public.roles where name = ?";

    public static final String USER_BY_USERNAME_QUERY =
            "select id, login, \"password\", username \n" +
            "from public.users where username = ?";

    public static final String USER_BY_LOGIN_QUERY =
            "select id, login, \"password\", username \n" +
            "from public.users where login = ?";

    public static final String SAVE_NEW_USER_QUERY =
            "insert into public.users(login, \"password\", username) \n" +
            "values(?, ?, ?) returning id";

    public static final String SAVE_EXIST_USER_QUERY =
            "update public.users \n" +
            "  set login = ?, \"password\" = ?, username = ? \n" +
            "where id = ?";

    public static final String SAVE_USER_ROLE_QUERY =
            "insert into public.users_to_roles(user_id, role_id) values(?, ?)";
}
