package ru.otus.java.basic.homeworks.homework20.client;

import java.util.Scanner;

public class AppClient {
    public static void main(String[] args) {
        try (Client client = new Client()) {
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(System.in);
            System.out.println("------------------------");
            System.out.println("Input two real numbers (type double) and arithmetic operation, separate them with a space character");
            System.out.println("type 'shutdown server' to shutdown server exit");
            System.out.println("type 'stop client' to shutdown client");
            System.out.println("------------------------");

            boolean receivedMathOperations = false;
            while (true) {
                if (!client.isConnected()) {
                    if (!client.connect(8080)) {
                        break;
                    }
                }
                stringBuilder.setLength(0);
                if (receivedMathOperations) {
                    System.out.print("Your request:");
                    stringBuilder.append(scanner.nextLine());
                } else {
                    stringBuilder.append("SendListMathOperations");
                }

                if (stringBuilder.toString().equals("stop client")) {
                    break;
                }

                if (client.send(stringBuilder.toString()) == -1) {
                    System.out.println("Произошла ошибка записи в сокет");
                    break;
                }

                if (stringBuilder.toString().equals("shutdown server")) {
                    break;
                }

                stringBuilder.setLength(0);
                stringBuilder.append(client.receive());

                if (receivedMathOperations) {
                    System.out.println("Calculation result: " + stringBuilder);
                }
                else {
                    System.out.println(stringBuilder);
                    receivedMathOperations = true;
                }
            }
            if (client.getServerInfo() != null && !client.getServerInfo().isEmpty()) {
                System.out.printf("Клиент завершает работу c сервером %s,", client.getServerInfo());
            }
        }
    }
}
