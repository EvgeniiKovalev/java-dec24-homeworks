import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) {
        try (Client client = new Client()) {
            new Thread(client).start();
            client.handleInputUser();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}