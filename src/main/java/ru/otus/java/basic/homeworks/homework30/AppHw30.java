package ru.otus.java.basic.homeworks.homework30;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Домашнее задание
 * Продвинутая работа с многопоточностью
 * Цель:
 * научиться работать с многопоточностью в Java.
 * Описание/Пошаговая инструкция выполнения домашнего задания:
 * Создайте пул потоков
 * Задайте пулу три задачи: первая должна 5 раз напечатать букву A, вторая - B, третья - C
 * Синхронизируйте потоки в пуле таким образом, чтобы в любой ситуации в консоль
 * печаталась последовательность ABCABCABCABCABC
 * После печати всех символов программа должна завершиться
 */
public class AppHw30 {
    static final Object monitor = new Object();
    static int executionOrder = 1;

    private static void waitForYourTurn(int expectedOrder) throws InterruptedException {
        while (executionOrder != expectedOrder) {
            monitor.wait();
        }
    }

    public static void printC() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    waitForYourTurn(3);
                    System.out.print("C");
                    executionOrder = 1;
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.submit(AppHw30::printC);
        pool.submit(new Runnable() {
            @Override
            public void run() {
                synchronized (monitor) {
                    try {
                        for (int i = 0; i < 5; i++) {
                            waitForYourTurn(2);
                            System.out.print("B");
                            executionOrder = 3;
                            monitor.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        pool.execute(() -> {
            synchronized (monitor) {
                try {
                    for (int i = 0; i < 5; i++) {
                        waitForYourTurn(1);
                        System.out.print("A");
                        executionOrder = 2;
                        monitor.notifyAll();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pool.shutdown();
    }
}
