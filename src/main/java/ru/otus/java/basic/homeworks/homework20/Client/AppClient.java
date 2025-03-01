package ru.otus.java.basic.homeworks.homework20.Client;

import java.io.IOException;
import java.util.Scanner;

public class AppClient {
    public static void main(String[] args) {
        try (Client client = new Client()) {
            StringBuilder inputFromConsole = new StringBuilder();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                if (!client.ActiveState()) {
                    if (!client.connect(8080)) {
                        break;
                    }
                }
                inputFromConsole.setLength(0);
                System.out.println("Input two real numbers (type double) and arithmetic operation, separate them with a " +
                        "space character, type shutdown server to exit:");
                inputFromConsole.append(scanner.nextLine());
                client.send(inputFromConsole.toString());
                if (inputFromConsole.toString().equals("quit")) {
                    //break;
                }
                inputFromConsole.setLength(0);

                String result = client.receive();
                System.out.println(result);
                inputFromConsole.append(result);
                System.out.println("Calculation result: " + inputFromConsole);

                client.close();
            }
        }
    }
}
