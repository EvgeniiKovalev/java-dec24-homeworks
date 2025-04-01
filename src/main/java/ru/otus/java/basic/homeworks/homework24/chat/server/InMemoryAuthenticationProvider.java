package ru.otus.java.basic.homeworks.homework24.chat.server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryAuthenticationProvider implements AuthenticatedProvider {
    private final Server server;
    private final List<User> users;

    public InMemoryAuthenticationProvider(Server server) {
        this.server = server;

        this.users = new CopyOnWriteArrayList<>();
        this.users.add(new User("login1", "password1", "username1"));
        this.users.add(new User("admin", "admin", "admin"));
        this.users.add(new User("root", "root", "root"));
        this.users.add(new User("qwe", "qwe", "qwe1"));
        this.users.add(new User("asd", "asd", "asd1"));
        this.users.add(new User("zxc", "zxc", "zxc1"));
    }

    @Override
    public void initialize() {
        System.out.println("Сервис аутентификации запущен: In memory режим");
    }

    private User getUserByLoginAndPassword(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private String getUsernameByLoginAndPassword(String login, String password) {
        User user = getUserByLoginAndPassword(login, password);
        return user != null ? user.getUsername() : null;
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

        authUser.setDefaultRoles();
        clientHandler.setUser(authUser);
        clientHandler.setUsername(authName);

        server.subscribe(clientHandler);

        clientHandler.sendMessage("/authok " + authName);
        return true;
    }

    private boolean isLoginAlreadyExist(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUsernameAlreadyExist(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
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
        user.setDefaultRoles();
        users.add(user);
        clientHandler.setUser(user);
        clientHandler.setUsername(username);

        server.subscribe(clientHandler);
        clientHandler.sendMessage("/regok " + username);

        return true;
    }
}
