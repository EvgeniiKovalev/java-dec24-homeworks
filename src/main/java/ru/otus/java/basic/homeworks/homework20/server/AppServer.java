package ru.otus.java.basic.homeworks.homework20.server;

import java.io.IOException;

public class AppServer {
    public static void main(String[] args) {
        try (Server server = new Server(8080)){
            server.run(3000000, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
