package ru.otus.java.basic.homeworks.homework24.chat.server;

public class ServerApplication {
    public static void main(String[] args) {
        try (Server server = new Server(8189)) {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}