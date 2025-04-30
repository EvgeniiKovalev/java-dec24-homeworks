package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpServer implements AutoCloseable {
    private static final Logger logger = LogManager.getLogger(HttpServer.class);
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
                logger.error("Ошибка при закрытии ресурса: {}", e.getMessage());
            }
        }
    }

    public void start() throws Exception {
        createHookStopServer();
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Сервер запущен, порт: {}, количество одновременно обрабатываемых запросов: " +
                    "{}", port, numThead);
            while (isRunning) {
                Socket socket = serverSocket.accept();
                pool.submit(() -> handleClientSocket(socket));
            }
        } finally {
            if (!pool.isShutdown()) {
                pool.shutdown();
            }
        }
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
                    logger.error("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void createHookStopServer() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Получен сигнал завершения (Ctrl+C). Останавливаем сервер...");
            isRunning = false;
            if (!pool.isShutdown()) {
                shutdownAndAwaitTermination(pool);
            }
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    safetyClose(serverSocket);
                }
            } catch (Exception e) {
                logger.error("Ошибка при остановке сервера: {}", e.getMessage());
            }
        }));
    }

    private void handleClientSocket(Socket socket) {
        try (socket) {
            byte[] buffer = new byte[8192];
            int n = socket.getInputStream().read(buffer);
            if (n < 0) {
                logger.warn("Получено битое сообщение");
                return;
            }
            String rawRequest = new String(buffer, 0, n);
            HttpRequest request = new HttpRequest(rawRequest);
            request.info(true);
            dispatcher.execute(request, socket.getOutputStream());
        } catch (Exception e) {
            logger.error("Ошибка обработки соединения: {}", e.getMessage());
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
                logger.error("Ошибка закрытия serverSocket", e);
                success = false;
            }
        }
        if (!success) {
            throw new IOException("Возникли ошибки при закрытии сервера");
        }
        logger.info("Сервер успешно остановлен");
    }

}
