package ru.otus.java.basic.homeworks.homework20.server;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static ru.otus.java.basic.homeworks.homework20.server.Common.safetyClose;

public class Server implements Closeable {
    private final List<String> allowOperations = Arrays.asList("+", "-", "*", "/");
    private final ServerSocket serverSocket;
    private final SocketAddress socketAddress;

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

    private String handleRequest(String msg) {
        if (msg.equals("SendListMathOperations")) {
            return "Available mathematical operations: " + allowOperations;
        }

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
                throw new Exception("Invalid math operation entered, allowed list: \"+ - * /\"");
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

    private HashMap<NameFieldResult, Object> handleClientConnection(ClientConnection clientConnection, int sizeBuffer) throws IOException {
        Socket clientSocket = clientConnection.getClientSocket();

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()), sizeBuffer);
        clientConnection.setBufferedWriter(bufferedWriter);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()), sizeBuffer);
        clientConnection.setBufferedReader(bufferedReader);

        HashMap<NameFieldResult, Object> result = new HashMap<>();
        while (true) {
            try {
                String request = bufferedReader.readLine();
                if (request == null) {
                    result.put(NameFieldResult.MESSAGE, "Lost client connection with " + clientConnection.getConnectionInfo());
                    result.put(NameFieldResult.EXCLUSIVE_ACTION_SERVER, ExclusiveActionServer.CLOSE_CLIENT);
                    break;
                }
                System.out.println("Request received '" + request + "'");
                if (request.equals("shutdown server")) {
                    result.put(NameFieldResult.MESSAGE, "!!!!!!!!!!!! shutdown server start !!!!!!!!!!!!!!!!!");
                    result.put(NameFieldResult.EXCLUSIVE_ACTION_SERVER, ExclusiveActionServer.SHUTDOWN_SERVER);
                    clientSocket.shutdownInput();
                    break;
                }
                String responce = handleRequest(request);
                System.out.println("Calculation result " + responce);
                bufferedWriter.write(responce);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                result.put(NameFieldResult.MESSAGE, "Failed to receive message from client");
                result.put(NameFieldResult.EXCLUSIVE_ACTION_SERVER, ExclusiveActionServer.CLOSE_CLIENT);
                break;
            }
        }
        return result;
    }

    public void run(int soTimeout, int numberConnections) {
        System.out.println("Server is trying to start");
        int sizeBuffer = 10;

        while (true) {
            boundingAndListen(soTimeout, numberConnections);
            ClientConnection clientConnection = new ClientConnection();
            try {
                ExclusiveActionServer exclusiveAction = null;
                clientConnection.setClientSocket(serverSocket.accept());
                System.out.println("Connected client " + clientConnection);
                HashMap<NameFieldResult, Object> resultHandleClientConnection = handleClientConnection(clientConnection, sizeBuffer);

                String message = (String) resultHandleClientConnection.get(NameFieldResult.MESSAGE);
                if (!message.isEmpty()) {
                    System.out.println(message);
                }
                exclusiveAction = (ExclusiveActionServer) resultHandleClientConnection.get(NameFieldResult.EXCLUSIVE_ACTION_SERVER);
                if (exclusiveAction == ExclusiveActionServer.CLOSE_CLIENT || exclusiveAction == ExclusiveActionServer.SHUTDOWN_SERVER) {
                    safetyClose(clientConnection);
                    if (exclusiveAction == ExclusiveActionServer.SHUTDOWN_SERVER) {
                        break;
                    }
                }
            } catch (IOException ex) {
                try {
                    safetyClose(clientConnection);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                System.out.println("Completed communication with the client " + clientConnection.getConnectionInfo());
            }
        }
    }



    @Override
    public void close() throws IOException {
        safetyClose(serverSocket);
        System.out.println("Server has shutdown");
    }
}

