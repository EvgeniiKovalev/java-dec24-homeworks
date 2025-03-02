package ru.otus.java.basic.homeworks.homework21;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * <pre>
 * Домашнее задание
 * Работа с многопоточностью
 *
 * Цель:
 * научиться работать с многопоточностью в Java;
 * посмотреть, как многопоточность позволяет ускорить выполнение задач.
 *
 *
 * Описание/Пошаговая инструкция выполнения домашнего задания:
 * Необходимо выполнить две реализации задачи заполнения массива
 *
 * Реализация №1
 *
 * Реализуйте метод, который создает double массив длиной 100_000_000 элементов
 * Метод должен должен циклом for пройти по каждому элементу и посчитать его значение по формуле:
 * array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
 * Засеките время выполнения цикла и выведите его в консоль.
 * Реализация №2:
 *
 * Сделайте то же самое что и в реализации один, только чтобы массив заполняли 4 потока одновременно.
 * То есть первый поток заполняет первую четверть массива, второй - вторую и т.д.
 * И посмотрите насколько быстрее выполнится работа по сравнению с одним потоком.
 * </pre>
 */
public class AppHw21 {
    static int length_array = 100_000_000;
    static double[] array1 = new double[length_array];
    static double[] array2 = new double[length_array];

    public static void calculate(double[] array, int from, int to, String name) {
        System.out.println("Start " + name);
        Date start = new Date();
        for (int i = from; i < to; i++) {
            array[i] = 1.14 * Math.cos(i) * Math.sin(i * 0.2) * Math.cos(i / 1.2);
        }
        Date end = new Date();
        Duration duration = Duration.between(start.toInstant(), end.toInstant());
        long diffInSeconds = duration.getSeconds();
        long diffInMinutes = duration.toMinutes();
        System.out.printf("finish %s: %d minute %d second\n", name, diffInMinutes, diffInSeconds - diffInMinutes * 60);
    }

    public static void calc1() {
        calculate(array2, 0, 25_000_000, "thread_1");
    }

    public static void calc2() {
        calculate(array2, 25_000_000, 50_000_000, "thread_2");
    }

    public static void calc3() {
        calculate(array2, 50_000_000, 75_000_000, "thread_3");
    }

    public static void calc4() {
        calculate(array2, 75_000_000, 100_000_000, "thread_4");
    }


    public static void main(String[] args) {
        List<TaskName> testTask = List.of(TaskName.ONETHREAD, TaskName.MULTITHREAD);
        Date start;
        Date end;
        Duration duration;
        long diffInSeconds;
        long diffInMinutes;

        if (testTask.contains(TaskName.ONETHREAD)) {
            //single stream test
            calculate(array1, 0, length_array, "test one thread");
            System.out.println();
        }

        if (testTask.contains(TaskName.MULTITHREAD)) {
            //4-thread test

            //variant with pool
            System.out.println("option with pool and static procedures, without Thread class");
            start = new Date();
            ExecutorService pool = Executors.newFixedThreadPool(4);

            pool.submit(AppHw21::calc1);
            pool.submit(AppHw21::calc2);
            pool.submit(AppHw21::calc3);
            pool.submit(AppHw21::calc4);
            pool.shutdown();

            //wait for execution not more than 5 minutes
            boolean allTasksCompleted = false;
            try {
                allTasksCompleted = pool.awaitTermination(5, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                System.out.println("Execution was interrupted");
                Thread.currentThread().interrupt();
                return;
            }

            end = new Date();
            duration = Duration.between(start.toInstant(), end.toInstant());
            diffInSeconds = duration.getSeconds();
            diffInMinutes = duration.toMinutes();

            if (allTasksCompleted) {
                System.out.println("All tasks completed successfully");
                System.out.printf("%d minute %d second\n", diffInMinutes, diffInSeconds - diffInMinutes * 60);
            } else {
                System.out.println("Timeout exceeded: some tasks might not be completed");
            }
            System.out.println();


            //option with join and thread class, no pool
            System.out.println("option with join and Thread class, no pool");
            List<Thread> threads = new ArrayList<>();
            start = new Date();
            for (int i = 0; i < 4; i++) {
                var myThread = new MyThread("thread_" + i, i * 25_000_000, (i + 1) * 25_000_000, array2);
                threads.add(myThread);
                myThread.start();
            }

            //waiting for execution as much as necessary
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

            end = new Date();
            duration = Duration.between(start.toInstant(), end.toInstant());
            diffInSeconds = duration.getSeconds();
            diffInMinutes = duration.toMinutes();
            System.out.printf("%d minute %d second\n", diffInMinutes, diffInSeconds - diffInMinutes * 60);
        }
    }
}
