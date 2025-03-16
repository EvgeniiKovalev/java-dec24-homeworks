package ru.otus.java.basic.homeworks.homework20.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Closeable {
    private Socket clientSocket;
    private PrintWriter printerWriter;
    private BufferedReader bufferedReader;

    public String getServerInfo() {
        return serverInfo;
    }

    private String serverInfo = null;

    public boolean isConnected() {
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
            printerWriter = new PrintWriter(clientSocket.getOutputStream(), false);
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            serverInfo = String.format("%s:%d", InetAddress.getByName(name), port);
            msg.append(String.format("Connected to server %s. ", serverInfo));
            res = true;
        } catch (IllegalArgumentException | IOException e) {
            msg.append(String.format("Failed connected to server  %s:%d, error='%s'\n", name, port, e.getMessage()));
        }
        System.out.print(msg);
        return res;
    }

    public int send(String msg) {
        printerWriter.println(msg);
        printerWriter.flush();
        if (printerWriter.checkError()) {
            return -1;
        }
        return 0;
    }

    public String receive() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (printerWriter != null) printerWriter.close();

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
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
