package ru.otus.java.basic.homeworks.homework3;

import java.util.Random;
import java.util.Scanner;

public class AppHw3 {
    public static void main(String[] args) {
        greetings();
        checkSign(-2, 1, -1);
        selectColor();
        compareNumbers();
        addOrSubtractAndPrint(1, 1, true);

        Scanner scanner = new Scanner(System.in);
        int num_func;
        System.out.print("Введите число от 1 до 5: ");
        num_func = scanner.nextInt();
        switch (num_func) {
            case 1:
                greetings();
                break;
            case 2:
                checkSign(new Random().nextInt(), new Random().nextInt(), new Random().nextInt());
                break;
            case 3:
                selectColor();
                break;
            case 4:
                compareNumbers();
                break;
            case 5:
                addOrSubtractAndPrint(new Random().nextInt(), new Random().nextInt(), Math.random() > 0.5);
                break;
            default:
                System.out.printf("нет функции соответствущей числу %d", num_func);
                break;
        }
    }

    public static void greetings() {
        System.out.println("Hello\nWorld\nfrom\nJava");
    }

    public static void checkSign(int a, int b, int c) {
        //System.out.printf("input variables:\n  a = %d\n  b = %d  \n  c = %d\n", a, b, c);
        System.out.println(a + b + c >= 0 ? "Сумма положительная" : "Сумма отрицательная");
    }

    public static void selectColor() {
        int data = 21;
        if (data <= 10) {
            System.out.println("Красный");
        } else if (data > 10 && data <= 20) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }

    public static void compareNumbers() {
        int a = 2;
        int b = 1;
        System.out.println(a >= b ? "a >= b" : "a < b");
    }

    // Если increment = true, то метод должен к initValue прибавить delta и отпечатать в консоль результат, иначе - вычесть;
    public static void addOrSubtractAndPrint(int initValue, int delta, boolean increment) {
        //System.out.printf("input variables:\n  initValue = %d\n  delta = %d  \n  increment = %b\n", initValue, delta, increment);
        System.out.println(increment ? initValue + delta : initValue - delta);
    }

}
