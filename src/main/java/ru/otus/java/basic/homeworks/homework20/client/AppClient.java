package ru.otus.java.basic.homeworks.homework20.client;

import java.util.Scanner;

public class AppClient {
    public static void main(String[] args) {
        try (Client client = new Client()) {
            StringBuilder inputFromConsole = new StringBuilder();
            Scanner scanner = new Scanner(System.in);
            System.out.println("------------------------");
            System.out.println("Input two real numbers (type double) and arithmetic operation, separate them with a space character");
            System.out.println("type 'shutdown server' to shutdown server exit");
            System.out.println("type 'stop client' to shutdown client");
            System.out.println("------------------------");
            while (true) {
                if (!client.ActiveState()) {
                    if (!client.connect(8080)) {
                        break;
                    }
                }
                inputFromConsole.setLength(0);
                System.out.print("Your request:");
                inputFromConsole.append(scanner.nextLine());
                if (inputFromConsole.toString().equals("stop client")) {
                    break;
                }
                client.send(inputFromConsole.toString());
                if (inputFromConsole.toString().equals("shutdown server")) {
                    break;
                }

                inputFromConsole.setLength(0);
                inputFromConsole.append(client.receive());
                System.out.println(inputFromConsole);
                System.out.println("Calculation result: " + inputFromConsole);
            }
        }
    }
}
