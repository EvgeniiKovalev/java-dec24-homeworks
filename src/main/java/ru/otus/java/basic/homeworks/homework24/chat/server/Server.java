package ru.otus.java.basic.homeworks.homework24.chat.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server implements Closeable {
    private final int port;
    private final List<ClientHandler> clients;
    private final AuthenticatedProvider authenticatedProvider;

    public Server(int port) {
        this.port = port;
        clients = new CopyOnWriteArrayList<>();
        authenticatedProvider = new InMemoryAuthenticationProvider(this);
        authenticatedProvider.initialize();
    }

    public AuthenticatedProvider getAuthenticatedProvider() {
        return authenticatedProvider;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(this, clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    void kickUsername(ClientHandler authorCommand, String[] parts) throws IOException {
        try {
            if (authorCommand.checkRole(Role.ADMIN)) {
                String kickUsername = parts[1];
                String authorKick = authorCommand.getUsername();
                ClientHandler kickClient = clientByUsername(kickUsername);
                if (kickClient == null) {
                    sendMessageToUsername(
                            authorCommand,
                            new String[]{
                                    "/w",
                                    authorKick,
                                    "cреди подключенных пользователей не найден с username = \"" + kickUsername + "\""
                            }
                    );
                    return;
                }
                sendMessageToUsername(
                        authorCommand,
                        new String[]{
                                "/kickok",
                                kickUsername,
                                "вы отключены от чата"
                        }
                );
                kickClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendMessageToUsername(ClientHandler clientFrom, String[] parts){
        String username = parts[1];
        ClientHandler recepientClient = clientByUsername(username);
        StringBuilder message = new StringBuilder();
        if (recepientClient == null) {
            //отправка автору сообщения, что не найден пользователь которому предназначалось сообщение
            recepientClient = clientFrom;
            message.append("cреди подключенных пользователей не найден с username = \"" + username + "\"");
            System.out.println(message.toString());
        }
        if (message.length() == 0) {
            message.append(clientFrom.getUsername()).append(" :");
            for (int i = 2; i < parts.length; i++) {
                message.append(" ").append(parts[i]);
            }
        }
        recepientClient.sendMessage(message.toString());
    }

    void printClients(ClientHandler authorCommand){
        if (authorCommand.checkRole(Role.ADMIN)) {
            String username = authorCommand.getUsername();
            StringBuilder message = new StringBuilder("Подключенные клиенты:");
            for (ClientHandler client : clients) {
                message.append("\r\n").append(client.getUsername());
            }
            sendMessageToUsername(authorCommand, new String[]{"/w", username, message.toString()});
            System.out.println(message.toString());
        }
    }

    public ClientHandler clientByUsername(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return client;
            }
        }
        return null;
    }

    public boolean isUsernameBusy(String username) {
        return clientByUsername(username) != null ? true : false;
    }

    @Override
    public void close() throws IOException {
        if (clients != null) {
            boolean Success = true;
            for (ClientHandler clientHandler : clients) {
                try {
                    clientHandler.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Success = false;
                }
            }
            if (!Success) {
                throw new IOException("Возникли ошибки при закрытии клиентов");
            }
        }
    }
}
