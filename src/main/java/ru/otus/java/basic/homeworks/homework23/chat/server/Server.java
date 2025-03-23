package ru.otus.java.basic.homeworks.homework23.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ClientHandler> clients;
    private AuthenticatedProvider authenticatedProvider;

    public Server(int port) {
        this.port = port;
        clients = new ArrayList<>();
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

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void sendMessageToUsername(ClientHandler clientFrom, String[] parts){
        int lenParts = parts.length;
        if (lenParts < 3) {
            throw new IllegalArgumentException("Неверная структура сообщения,  структура д. быть \"/w username your long message\"");
        }
        String username = parts[1];
        ClientHandler recepientClient = null;
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                recepientClient = client;
            }
        }
        if (recepientClient == null) {
            System.out.println("Не найден username \"" + username + "\" среди подключенных клиентов ");
            return;
        }
        StringBuilder messageBuilder = new StringBuilder(clientFrom.getUsername());
        messageBuilder.append(" :");
        for (int i = 2; i < lenParts; i++) {
            messageBuilder.append(" ").append(parts[i]);
        }
        recepientClient.sendMessage(messageBuilder.toString());
    }

    public boolean isUsernameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
