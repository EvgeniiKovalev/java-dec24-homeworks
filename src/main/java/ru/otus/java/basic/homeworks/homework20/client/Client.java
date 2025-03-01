package ru.otus.java.basic.homeworks.homework20.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Closeable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public boolean ActiveState() {
        try {
            return clientSocket.isConnected() && !clientSocket.isClosed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean connect(int port) {
        return connect("localhost", port);
    }

    public boolean connect(String name, int port) {
        boolean res = false;
        StringBuilder msg = new StringBuilder();
        try {
            if (port < 1024 || port > 49151) {
                throw new IllegalArgumentException("Port is invalid");
            }
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Name is invalid(null)");
            }
            clientSocket = new Socket(InetAddress.getByName(name), port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            msg.append(String.format("Connected to server %s:%d\n", InetAddress.getByName(name), port));
            res = true;
        } catch (IllegalArgumentException | IOException e) {
            msg.append(String.format("Failed connected to server  %s:%d, error='%s'\n", name, port, e.getMessage()));
        }
        System.out.print(msg);
        return res;
    }

    public void send(String msg) {
        out.println(msg);
    }

    public String receive() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (out != null) out.close();

        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
