import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Client implements Closeable, Runnable {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final CountDownLatch latch = new CountDownLatch(1);
    private String username;

    public Client() throws IOException {
        socket = new Socket("localhost", 8189);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            StringBuilder message = new StringBuilder();
            while (!latch.await(100, TimeUnit.MILLISECONDS)) {
                String[] parts;
                message.setLength(0);
                try {
                    message.append(in.readUTF());
                } catch (EOFException e) {
                    System.out.println("latch.getCount() = " + latch.getCount());
                    break;
                }
                parts = message.toString().split(" ");
                String command = parts[0];
                switch (command) {
                    case "/exitok":
                        latch.countDown();
                        break;
                    case "/kickok":
                        latch.countDown();
                        System.out.println(message);
                        break;
                    case "/authok":
                        username = parts[1];
                        System.out.println("Аутентификация прошла успешно с именем пользователя: " +
                                username);
                        break;
                    case "/regok":
                        username = parts[1];
                        System.out.println("Регистрация прошла успешно с именем пользователя: " + username);
                        break;
                    default:
                        System.out.println(message);
                        break;
                }
                if (latch.await(100, TimeUnit.MILLISECONDS)) {
                    System.out.println("Команда с закрытием клиента");
                    break;
                }
            }
        } catch (InterruptedException e) {
            latch.countDown();
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            latch.countDown();
            e.printStackTrace();
        } finally {
            latch.countDown();
        }
    }

    void handleInputUser() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        while (!latch.await(100, TimeUnit.MILLISECONDS)) {
            int countTryes = 1;
            String message = scanner.nextLine();
            if (latch.await(100, TimeUnit.MILLISECONDS)) {
                System.out.println("Пришла команда закрыть клинта, закрываем ввод");
                break;
            }
            while (true) {
                try {
                    out.writeUTF(message);
                    break;
                } catch (IOException e) {
                    Thread.sleep(1000);
                    if (countTryes >= 3) {
                        throw new IOException("Не удалось отправить сообщение серверу за три попытки");
                    }
                    countTryes++;
                }
            }
        }
    }

    public void safetyClose(Closeable closeObject) throws IOException {
        if (closeObject != null) {
            try {
                closeObject.close();
            } catch (IOException e) {
                throw new IOException("Ошибка в disconnect", e);
            }
        }
    }

    @Override
    public void close() throws IOException {
        System.out.printf("Отключение пользователя \"%s\", latch.getCount() = %d",
                username, latch.getCount());
        safetyClose(in);
        safetyClose(out);
        safetyClose(socket);
    }
}
