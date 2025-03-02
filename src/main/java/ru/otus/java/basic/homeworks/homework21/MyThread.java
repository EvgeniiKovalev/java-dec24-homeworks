package ru.otus.java.basic.homeworks.homework21;

import static ru.otus.java.basic.homeworks.homework21.AppHw21.calculate;

public class MyThread extends Thread {
    private final int from;
    private final int to;
    private final double[] array;

    public MyThread(String name, int from, int to, double[] array) {
        super(name);
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        calculate(array, from, to, getName());
    }
}
