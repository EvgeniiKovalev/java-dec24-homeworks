package ru.otus.java.basic.homeworks.homework24.chat.client;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    private volatile boolean needDisconnect = false;
    String username;

    public Client() throws IOException {
        Scanner scanner = new Scanner(System.in);
        socket = new Socket("localhost", 8189);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
                handleAnswerServer();

            } catch (IOException e) {
                needDisconnect = true;
                e.printStackTrace();
            } finally {
                try {
                    if (needDisconnect) {
                        disconnect();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        handleInputUser(scanner);
    }

    private void handleInputUser(Scanner scanner) throws IOException {
        while (!needDisconnect) {
            String message = scanner.nextLine();
            out.writeUTF(message);
        }
    }

    private void handleAnswerServer() throws IOException {
        while (!needDisconnect) {
            System.out.println("handleAnswerServer()");
            String message = in.readUTF();
            String[] parts = message.split(" ");
            String command = parts[0];
            switch (command) {
                case "/exitok":
                    needDisconnect = true;
                    break;
                case "/authok":
                    username = parts[1];
                    System.out.println("Аутентификация прошла успешно с именем пользователя: " + username);
                    break;
                case "/regok":
                    username = parts[1];
                    System.out.println("Регистрация прошла успешно с именем пользователя: " + username);
                    break;
                case "/kickok":
                    needDisconnect = true;
                    break;
                default:
                    System.out.println(message);
                    break;
            }

            if (needDisconnect) {break;}
        }
    }

    public void safetyClose(Closeable closeObject) throws IOException{
        if (closeObject != null) {
            try {
                closeObject.close();
            } catch (IOException e) {
                throw new IOException("Ошибка в disconnect", e);
            }
        }
    }

    public void disconnect() throws IOException {
        System.out.printf("Отключение пользователя \"%s\"", username);
        safetyClose(in);
        safetyClose(out);
        safetyClose(socket);
    }
}
