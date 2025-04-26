package ru.otus.java.basic.homeworks.homework32.http.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer implements AutoCloseable {
    private final int port;
    private final int numThead;
    private final Dispatcher dispatcher;
    private final ExecutorService pool;
    private volatile boolean isRunning;
    private ServerSocket serverSocket;

    public HttpServer(int port, int numThead) {
        this.port = port;
        this.numThead = numThead;
        this.dispatcher = new Dispatcher();
        this.pool = Executors.newFixedThreadPool(numThead);
        this.isRunning = true;
    }

    private static void safetyClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии ресурса: " + e.getMessage());
            }
        }
    }

    public void start() {
        createHookStopServer();
        try {
            serverSocket = new ServerSocket(port);
            System.out.printf("Сервер запущен, порт: %d, количество одновременно обрабатываемых запросов: " +
                    "%d\n", port, numThead);
            while (isRunning) {
                Socket socket = serverSocket.accept();
                pool.submit(() -> handleClientSocket(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!pool.isShutdown()) {
                pool.shutdown();
            }
        }
    }

    private void createHookStopServer() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nПолучен сигнал завершения (Ctrl+C). Останавливаем сервер...");
            isRunning = false;
            if (!pool.isShutdown()) {
                pool.shutdown();
            }
            System.out.println("Ждем 3 секунды для завершения обработки начатых, но не завершенных запросов");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    safetyClose(serverSocket);
                }
            } catch (Exception e) {
                System.err.println("Ошибка при остановке сервера: " + e.getMessage());
            }
        }));
    }

    private void handleClientSocket(Socket socket) {
        try (socket) {
            byte[] buffer = new byte[8192];
            int n = socket.getInputStream().read(buffer);
            if (n < 0) {
                System.out.println("Получено битое сообщение");
                return;
            }
            String rawRequest = new String(buffer, 0, n);
            HttpRequest request = new HttpRequest(rawRequest);
            request.info(true);
            dispatcher.execute(request, socket.getOutputStream());
        } catch (Exception e) {
            System.err.println("Ошибка обработки соединения: " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        boolean success = true;
        if (!pool.isShutdown()) {
            pool.shutdown();
        }
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
                success = false;
            }
        }
        if (!success) {
            throw new IOException("Возникли ошибки при закрытии сервера");
        }
        System.out.println("Сервер успешно остановлен");
    }
}