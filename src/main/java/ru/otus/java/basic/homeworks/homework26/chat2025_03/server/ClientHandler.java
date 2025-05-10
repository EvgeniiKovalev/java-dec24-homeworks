import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Closeable {
    @SuppressWarnings("checkstyle:ConstantName")
    private static final List<String> allowedCommandsBeforeAuth = List.of("/exit", "/auth", "/reg");
    @SuppressWarnings("checkstyle:ConstantName")
    private static final List<String> allowedCommandsAfterAuth = List.of("/exit", "/w", "/kick", "/clients");
    private final Server server;
    private final Socket client;
    private final DataInputStream in;
    private final DataOutputStream out;
    private User user;
    private String username;

    @Override
    public String toString() {
        return username;
    }

    public ClientHandler(Server server, Socket client) throws IOException {
        this.server = server;
        this.client = client;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
        new Thread(() -> {
            try {
                System.out.println("Клиент подключился");
                if (authenticateClient()) {
                    System.out.println("подключился " + username);
                    handleCommandsClient();
                }
            } catch (IOException e) {
                System.out.printf("Ошибка при коммуникации с клиентом \"%s\": %s%n",
                        username, e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public User getUser() {
        return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /** Performs client authentication.
     *
     * @return true - success authentication with client, else - false.
     */
    @SuppressWarnings("checkstyle:MissingSwitchDefault")
    public boolean authenticateClient() throws IOException {
        System.out.println("authenticateClient()");
        while (true) {
            String message = in.readUTF();
            if (message.isEmpty()) {
                continue;
            }
            String[] parts = message.split(" ");
            String command = parts[0];
            if (!allowedCommandsBeforeAuth.contains(command)) {
                sendMessage("Перед работой необходимо пройти аутентификацию командой " +
                        "/auth login password или регистрацию командой /reg login password username");
                continue;
            }
            int lenParts = parts.length;
            switch (command) {
                case "/exit":
                    sendMessage("/exitok");
                    return false;
                case "/auth":
                    if (lenParts != 3) {
                        sendMessage("Не указаны параметры команды: /auth login password");
                        continue;
                    }
                    if (!server.getAuthenticatedProvider().authenticate(this, parts[1], parts[2])) {
                        continue;
                    }
                    return true;
                case "/reg":
                    if (lenParts != 4) {
                        sendMessage("Не указаны параметры команды: /reg login password username");
                        continue;
                    }
                    if (!server.getAuthenticatedProvider().registration(this, parts[1], parts[2], parts[3])) {
                        continue;
                    }
                    return true;
            }
        }
    }

    public void handleCommandsClient() throws IOException {
        System.out.println("handleCommandsClient()");
        while (true) {
            String message = in.readUTF();
            if (message.isEmpty()) {
                continue;
            }

            String[] parts = message.split(" ");
            String command = parts[0];
            if (!allowedCommandsAfterAuth.contains(command) && command.charAt(0) == '/') {
                sendMessage("Неподдерживаемая команда");
                continue;
            }
            switch (command) {
                case "/w":
                    if (!server.sendMessageToUsername(this, parts)) {
                        sendMessage("Не указаны параметры команды: /w username message");
                        continue;
                    }
                    break;
                case "/kick":
                    if (!server.kickUsername(this, parts)) {
                        sendMessage("Не указаны параметры команды: /kick username");
                        continue;
                    }
                    break;
                case "/clients":
                    server.printClients(this);
                    break;
                case "/exit":
                    sendMessage("/exitok");
                    return;
                default:
                    server.broadcastMessage(username + " : " + message);
            }
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void safetyClose(Closeable closeObject) {
        if (closeObject != null) {
            try {
                closeObject.close();
            } catch (IOException e) {
                System.out.println("--- НАЧАЛО ОШИБКИ ---");
                System.out.println("Тип ресурса: " + closeObject.getClass().getSimpleName());
                System.out.println("Сообщение об ошибке: " + e.getMessage());
                e.printStackTrace();
                System.out.println("--- КОНЕЦ ОШИБКИ ---");
            }
        }
    }

    @Override
    public void close() throws IOException {
        server.unsubscribe(this);
        safetyClose(in);
        safetyClose(out);
        safetyClose(client);
    }
}

