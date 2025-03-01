package ru.otus.java.basic.homeworks.homework20.server;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Server implements Closeable {
    private final List<String> allowOperations = Arrays.asList("+", "-", "*", "/");
    private ServerSocket serverSocket;
    private SocketAddress socketAddress;


    public Server(int port) {
        this(port, "localhost");
    }

    public Server(int port, String name) {
        try {
            serverSocket = new ServerSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            InetAddress inetAddress = InetAddress.getByName(name);
            socketAddress = new InetSocketAddress(inetAddress, port);
            serverSocket.setReuseAddress(true);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private String handleMessage(String msg) {
        List<String> items = Stream.of(msg.split(" ")).map(String::trim).collect(Collectors.toList());
        if (msg.isEmpty() || items.size() != 3) {
            return "The string entered does not match the format \"<real number> <real number> <math operation>\"";
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            double operandOne = Double.parseDouble(items.get(0));
            double operandTwo = Double.parseDouble(items.get(1));
            String operation = items.get(2);
            if (!allowOperations.contains(operation)) {
                return "Invalid math operation entered, allowed list: \"+ - * /\"";
            }
            if (operandTwo == 0 && operation.equals("/")){
                throw new ArithmeticException("Invalid divide-by-zero operation");
            }

            Double res = 0.0;
            switch (operation) {
                case "+":
                    res = operandOne + operandTwo;
                    break;
                case "-":
                    res = operandOne - operandTwo;
                    break;
                case "*":
                    res = operandOne * operandTwo;
                    break;
                case "/":
                    res = operandOne / operandTwo;
                    break;
            }
            stringBuilder.append(res.toString());
        } catch (ArithmeticException e) {
            stringBuilder.append(e.getMessage());
        } catch (NumberFormatException e) {
            stringBuilder.append("The first and/or second operand is not a real number");
        } catch (NullPointerException e) {
            stringBuilder.append(e.getMessage());
        } catch (Exception e) {
            stringBuilder.append(e.getMessage());
        } finally {
            return stringBuilder.length() == 0 ? "Calculation failed" : stringBuilder.toString();
        }
    }

    private void boundingAndListen(int soTimeout, int numberConnections) {
        if (!serverSocket.isBound()) {
            try {
                serverSocket.bind(socketAddress, numberConnections);
                serverSocket.setSoTimeout(soTimeout);
                if (serverSocket.isBound()) {
                    System.out.println("!!!!!!!!!!!! Server started and listening started, " + serverSocket.getLocalSocketAddress() + " !!!!!!!!!!!!");
                } else {
                    throw new Exception("Failed to start Server and start listening");
                }
            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void run(int soTimeout, int numberConnections) {
        System.out.println("Server is trying to start");
        StringBuilder stringBuilder = new StringBuilder();
        int sizeReadBuffer = 10;
        int sizeWriteBuffer = 10;
        while (true) {
            boundingAndListen(soTimeout, numberConnections);
            try (Socket clientSocket = serverSocket.accept();
                 OutputStream outputStream = clientSocket.getOutputStream();
                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream), sizeWriteBuffer);
                 InputStream streamReader = clientSocket.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streamReader), sizeWriteBuffer)) {
                try {
                    stringBuilder.setLength(0);
                    stringBuilder.append(bufferedReader.readLine());
                } catch (IOException e) {
                    System.out.println("Failed to receive message from client");
                }
                System.out.println("Request received '" + stringBuilder.toString() + "'");
                if (stringBuilder.toString().equals("shutdown server")) {
                    System.out.println("!!!!!!!!!!!! shutdown server start !!!!!!!!!!!!!!!!!");
                    break;
                }
                String result = handleMessage(stringBuilder.toString());
                System.out.println("Calculation result " + result);
                bufferedWriter.write(result);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("Completed communication with the client");
            }
        }
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
        if (serverSocket.isClosed()) {
            System.out.println("Server has shutdown");
        } else {
            System.out.println("Server has not shutdown");
        }

    }
}

