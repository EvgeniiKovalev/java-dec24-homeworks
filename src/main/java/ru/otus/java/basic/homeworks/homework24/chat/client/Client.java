package ru.otus.java.basic.homeworks.homework24.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Closeable, Runnable {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private volatile boolean needClose = false; // может измениться в разных потоках методами одного и того же экземпляра этого класса
    private String username;

    public Client() throws IOException {
        socket = new Socket("localhost", 8189);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        needClose = false;
    }

    @Override
    public void run() {
        try {
            StringBuilder message = new StringBuilder();
            while (!needClose) {
                System.out.println("run()");
                String[] parts;
                message.setLength(0);
                try {
                    message.append(in.readUTF());
                } catch (EOFException e) {
                    break;
                }
                parts = message.toString().split(" ");
                String command = parts[0];
                switch (command) {
                    case "/exitok":
                        needClose = true;
                        break;
                    case "/kickok":
                        needClose = true;
                        System.out.println(message);
                        break;
                    case "/authok":
                        username = parts[1];
                        System.out.println("Аутентификация прошла успешно с именем пользователя: " + username);
                        break;
                    case "/regok":
                        username = parts[1];
                        System.out.println("Регистрация прошла успешно с именем пользователя: " + username);
                        break;
                    default:
                        System.out.println(message);
                        break;
                }

                if (needClose) {
                    break;
                }
            }
        } catch (IOException e) {
            needClose = true;
            e.printStackTrace();
        } finally {
            try {
                if (needClose) {
                    close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void handleInputUser() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        while (!needClose) {
            int countTryes = 1;
            String message = scanner.nextLine();
            if (needClose) {break;}
            while (true) {
                if (needClose) {break;}
                try {
                    System.out.println("needClose = " + needClose);
                    out.writeUTF(message);
                    break;
                } catch (IOException e) {
                    Thread.sleep(1000);
                    if (countTryes == 3) {
                        throw new IOException("Не удалось отправить сообщение серверу за три попытки");
                    }
                    countTryes++;
                }
            }
            System.out.println("цикл завершен, needClose = " + needClose);
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
        System.out.printf("Отключение пользователя \"%s\"", username);
        safetyClose(in);
        safetyClose(out);
        safetyClose(socket);
    }
}
