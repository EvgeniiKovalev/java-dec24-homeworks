package ru.otus.java.basic.http.server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Домашнее задание:
 * 1. Добавить логирование
 * 2. В CalculatorProcessor обработайте ситуации: параметры a, b не являются целыми числами (кинуть ошибку 400)
 * 3. В CreateItemProcessor нельзя передавать пустое название продукта или отрицательную цену (кинуть ошибку 400)
 * 4. Если в любом из обработчиков вылетело любое необработанное исключение, то сервер должен вернуть ответ 500
 */
public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        try (HttpServer server = new HttpServer(8189, 10)) {
            server.start();
        } catch (Exception e) {
            logger.error("Ошибка: {}, завершение работы сервера с кодом {}", e.getMessage(), 1, e);
            System.exit(1);
        } catch (OutOfMemoryError | StackOverflowError e) {
            logger.fatal("Критическая ошибка JVM: {}, завершение работы сервера с кодом {}", e.getMessage(), 2, e);
            System.exit(2);
        } catch (Throwable e) {
            logger.error("Неизвестная ошибка: {}, завершение работы сервера с кодом {}", e.getMessage(), 3, e);
            System.exit(3);
        }
    }
}
