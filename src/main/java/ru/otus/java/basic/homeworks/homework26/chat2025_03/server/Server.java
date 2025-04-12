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
        //authenticatedProvider = new InMemoryAuthenticationProvider(this);
        authenticatedProvider = new PostgresAuthenticationProvider(this);
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

    boolean kickUsername(ClientHandler authorCommand, String[] parts) {
        if (parts.length != 2) {
            return false;
        }

        try {
            if (authorCommand.getUser().checkRole(authenticatedProvider.getRole("admin"))) {
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
                    return true;
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
        } catch (IOException e) {
            System.err.println("Ошибка при отключении клиента: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     *
     * @param clientFrom
     * @param parts
     * @return false if command is not valid and not sended message, true - if sended message
     */
    boolean sendMessageToUsername(ClientHandler clientFrom, String[] parts){
        if (parts.length < 3) {
            return false;
        }

        String username = parts[1];
        ClientHandler recepientClient = clientByUsername(username);
        StringBuilder message = new StringBuilder();
        if (recepientClient == null) {
            //отправка автору сообщения, что не найден пользователь которому предназначалось сообщение
            recepientClient = clientFrom;
            message.append("cреди подключенных пользователей не найден с username = \"").append(username).append("\"");
            System.out.println(message);
        }
        if (message.length() == 0) {
            message.append(clientFrom.getUsername()).append(" :");
            for (int i = 2; i < parts.length; i++) {
                message.append(" ").append(parts[i]);
            }
        }
        recepientClient.sendMessage(message.toString());
        return true;
    }

    void printClients(ClientHandler authorCommand){
        if (authorCommand.getUser().checkRole(authenticatedProvider.getRole("admin"))) {
            String username = authorCommand.getUsername();
            StringBuilder message = new StringBuilder("Подключенные клиенты:");
            for (ClientHandler client : clients) {
                message.append("\r\n").append(client.getUsername());
            }
            sendMessageToUsername(authorCommand, new String[]{"/w", username, message.toString()});
            System.out.println(message);
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
        return clientByUsername(username) != null;
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
