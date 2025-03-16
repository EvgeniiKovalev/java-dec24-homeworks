package ru.otus.java.basic.homeworks.homework20.server;

import java.io.*;
import java.net.Socket;

import static ru.otus.java.basic.homeworks.homework20.server.Common.safetyClose;

public class ClientConnection implements Closeable, Comparable {
    private String connectionInfo;
    private BufferedWriter bufferedWriter = null;
    private BufferedReader bufferedReader = null;
    private Socket clientSocket = null;

    public String getConnectionInfo() {
        return connectionInfo;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(@org.jetbrains.annotations.NotNull Socket clientSocket) {
        this.clientSocket = clientSocket;
        connectionInfo = String.format("%s:%d", clientSocket.getInetAddress().getHostName(), clientSocket.getLocalPort());
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public String toString() {
        return String.format("%s%d", clientSocket.getInetAddress().getHostName(), clientSocket.getLocalPort());
    }


    @Override
    public void close() throws IOException {
        safetyClose(bufferedReader);
        safetyClose(bufferedWriter);
        safetyClose(clientSocket);
    }

    @Override
    public int compareTo(Object o) {
        if (!o.getClass().getName().equals("ClientConnection")) {
            throw new ClassCastException("Cannot compare  with " + o.getClass().getName());
        }
        return CharSequence.compare(toString(), o.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return 31 * toString().hashCode();
    }
}

