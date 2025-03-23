package ru.otus.java.basic.homeworks.homework23.chat.server;

public class ServerApplication {
    public static void main(String[] args) {
        new Server(8189).start();
    }
}