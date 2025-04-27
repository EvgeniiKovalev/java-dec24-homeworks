package ru.otus.java.basic.homeworks.homework32.http.server;

/**
 * Домашнее задание
 * Доработка простого “веб” приложения
 * Цель:
 * научиться вносить доработки в существующие проекты.
 * Описание/Пошаговая инструкция выполнения домашнего задания:
 * Реализуйте возможность обработки более одного потока за запуск сервера
 * Реализуйте обработку запросов в отдельных потоках
 */
public class Application {
    public static void main(String[] args) {
        try (HttpServer server = new HttpServer(8189, 10)) {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
