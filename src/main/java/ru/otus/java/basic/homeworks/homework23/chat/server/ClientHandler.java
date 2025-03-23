package ru.otus.java.basic.homeworks.homework23.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private final List<String> listAllowedCommandsBeforeAuth = List.of("/exit", "/auth", "/reg");
    private final List<String> listAllowedCommandsAfterAuth = List.of("/exit", "/w");

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean closeHandleMessageAuth(int lenParts, String login, String password) {
        if (lenParts != 3) {
            sendMessage("Неверный формат команды /auth ");
            return false;
        }
        return server.getAuthenticatedProvider().authenticate(this, login, password);
    }

    public boolean closeHandleMessageReg(int lenParts, String login, String password, String username) {
        if (lenParts != 4) {
            sendMessage("Неверный формат команды /reg ");
            return false;
        }
        return server.getAuthenticatedProvider().registration(this,  login, password, username);
    }

    /**
     * @return true - success authentication with client, else - false
     */
    public boolean authentificateClient() throws IOException {
        while (true) {
            String message = in.readUTF();
            if (message.isEmpty()) {continue;}
            String[] parts = message.split(" ");
            String command = parts[0];
            if (!listAllowedCommandsBeforeAuth.contains(command)) {
                sendMessage("Перед работой необходимо пройти аутентификацию командой " +
                        "/auth login password или регистрацию командой /reg login password username");
                continue;
            }
            boolean authSuccess = false;
            switch (command) {
                case "/exit":
                    sendMessage("/exitok");
                    return false;
                // /auth login password
                case "/auth":
                    authSuccess = closeHandleMessageAuth(parts.length, parts[1], parts[2]);
                    break;
                // /reg login password username
                case "/reg":
                    authSuccess = closeHandleMessageReg(parts.length, parts[1], parts[2], parts[3]);
                    break;
            }
            if (authSuccess) {
                return true;
            }
        }
    }

    public void handleCommandsClient() throws IOException {
        while (true) {
            String message = in.readUTF();
            if (message.isEmpty()) {continue;}

            String[] parts = message.split(" ");
            String command = parts[0];
            if (!listAllowedCommandsAfterAuth.contains(command) && command.charAt(0) == '/') {
                sendMessage("Неподдерживаемая команда");
                continue;
            }
            switch (command) {
                case "/exit":
                    sendMessage("/exitok");
                    return;
                // /w username your long message
                case "/w":
                    server.sendMessageToUsername(this, parts);
                    break;
                default:
                    server.broadcastMessage(username + " : " + message);
            }
        }
    }

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
            try {
                System.out.println("Клиент подключился ");
                if (authentificateClient()) {
                    handleCommandsClient();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        server.unsubscribe(this);
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

