package ru.otus.java.basic.homeworks.homework20.server;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Server implements Closeable {
    private final List<String> allowOperations = Arrays.asList("+", "-", "*", "/");
    private final ServerSocket serverSocket;
    private final SocketAddress socketAddress;
    private Socket clientSocket = null;
    private OutputStream outputStream = null;
    private BufferedWriter bufferedWriter = null;
    private InputStream streamReader = null;
    private BufferedReader bufferedReader = null;

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
            if (operandTwo == 0 && operation.equals("/")) {
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
            stringBuilder.append(res);
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
        int sizeReadBuffer = 10;
        int sizeWriteBuffer = 10;

        while (true) {
            boundingAndListen(soTimeout, numberConnections);
            try {
                clientSocket = serverSocket.accept();
                outputStream = clientSocket.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream), sizeReadBuffer);
                streamReader = clientSocket.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(streamReader), sizeWriteBuffer);
                while (true) {
                    try {
                        String request = bufferedReader.readLine();
                        if (request == null) {
                            System.out.println("Lost client connection");
                            closeClient();
                            break;
                        }
                        System.out.println("Request received '" + request + "'");
                        if (request.equals("shutdown server")) {
                            System.out.println("!!!!!!!!!!!! shutdown server start !!!!!!!!!!!!!!!!!");
                            break;
                        }
                        String responce = handleMessage(request);
                        System.out.println("Calculation result " + responce);
                        bufferedWriter.write(responce);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    } catch (IOException e) {
                        System.out.println("Failed to receive message from client");
                        closeClient();
                        break;
                    }
                }
            } catch (IOException e) {
                try {
                    closeClient();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            } finally {
                System.out.println("Completed communication with the client");
            }
        }
    }

    private void closeClient() throws IOException {
        if (bufferedReader != null) bufferedReader.close();
        if (streamReader != null) streamReader.close();
        if (bufferedWriter != null) bufferedWriter.close();
        if (outputStream != null) outputStream.close();
        if (clientSocket != null) clientSocket.close();
    }

    @Override
    public void close() throws IOException {
        closeClient();

        serverSocket.close();
        if (serverSocket.isClosed()) {
            System.out.println("Server has shutdown");
        } else {
            System.out.println("Server has not shutdown");
        }
    }
}

