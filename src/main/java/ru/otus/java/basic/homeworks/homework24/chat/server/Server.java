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
                                    String.format("В списке пользователй чата не найден пользователь \"%s\"", kickUsername)
                            }
                    );
                    return;
                }
                sendMessageToUsername(
                        authorCommand,
                        new String[]{
                                "/w",
                                kickUsername,
                                "kickok"
                        }
                );
                System.out.println(String.format("%s:/kick %s", authorKick, kickUsername));
                kickClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendMessageToUsername(ClientHandler clientFrom, String[] parts){
        String username = parts[1];
        ClientHandler recepientClient = clientByUsername(username);
        if (recepientClient == null) {
            System.out.println("Не найден username \"" + username + "\" среди подключенных пользователей");
            return;
        }
        StringBuilder messageBuilder = new StringBuilder(clientFrom.getUsername()).append(" :");
        for (int i = 2; i < parts.length; i++) {
            messageBuilder.append(" ").append(parts[i]);
        }
        System.out.println("sendMessageToUsername: "  + messageBuilder.toString());
        recepientClient.sendMessage(messageBuilder.toString());
    }

    void printClients(ClientHandler authorCommand){
        if (authorCommand.checkRole(Role.ADMIN)) {
            System.out.println("Подключенные клиенты:");
            String username = authorCommand.getUsername();
            sendMessageToUsername(authorCommand, new String[]{"/w", username, "Подключенные клиенты:"});
            for (ClientHandler client : clients) {
                System.out.println(client.getUsername());
                sendMessageToUsername(authorCommand, new String[]{"/w", username, client.getUsername()});
            }
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
