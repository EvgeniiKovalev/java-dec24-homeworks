package ru.otus.java.basic.homeworks.homework20.exercisemachine;

import java.util.Arrays;

public class ExceptionTaskDivide {
    private ExceptionTaskDivide() {
    }

    /**
     * Задание 2 из 5: Java синтаксис: обработка ошибок - коды возврата, исключения (иерархия), ДЗ (54)
     * Реализуйте метод, который принимает чётное количество целочисленных элементов и попарно делит их друг на друга.
     * Если в качестве делителя встречается ноль, то нужно отловить ArithmeticException и
     * записать в качестве результата 0. Если общее количество элементов не является чётным,
     * метод должен бросить IllegalArgumentException Метод должен вернуть массив с результатами деления
     * (использовать обычное деление).
     */
    public static int[] divide(int... numbers) {
        if (numbers.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        int[] result = new int[numbers.length / 2];
        for (int i = 0; i < numbers.length; i += 2) {
            try {
                result[i / 2] = numbers[i] / numbers[i + 1];
            } catch (ArithmeticException e) {
                result[i / 2] = 0;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Делим попарно 4, 2, 4, 1: " + Arrays.toString(divide(4, 2, 4, 1)));
        System.out.println("Делим попарно 0, 2, 0, 0: " + Arrays.toString(divide(0, 2, 0, 0, 2)));
    }
}