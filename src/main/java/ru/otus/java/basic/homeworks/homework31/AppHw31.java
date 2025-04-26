package ru.otus.java.basic.homeworks.homework31;

/**
 * Домашнее задание
 * Тестирование Java кода
 * Цель:
 * научиться писать тесты с использованием JUnit.
 * Описание/Пошаговая инструкция выполнения домашнего задания:
 * Реализовать метод, принимающий в качестве аргумента одномерный целочисленный массив, и возвращающий
 * новый массив, который содержит элементы исходного массива, идущие после последней единицы.
 * Если входной массив не содержит единиц, то должно быть брошено RuntimeException.
 * Примеры:
 * Входной массив: [ 1 2 1 2 2 ] => Результат: [ 2 2 ]
 * Входной массив: [ 2 2 2 2 ] => RuntimeException
 * Реализовать метод, проверяющий входной массив, что он состоит только из чисел 1 и 2. Если в массиве
 * присутствуют числа кроме 1 и 2, или нет хотя бы одной единицы или двойки,
 * то результат должен быть равен false
 * Примеры:
 * [ 1 2 ] => true
 * [ 1 1 ] => false
 * [ 1 3 ] => false
 * [ 1 2 2 1 ] => true
 * Реализовать набор тестов для методов.
 */
public class AppHw31 {
    public static int[] methodOne(int[] source) {
        int indexLastOne = -1;
        for (int i = source.length - 1; i >= 0; i--) {
            if (source[i] == 1) {
                indexLastOne = i;
                break;
            }
        }
        if (indexLastOne == -1) {
            throw new RuntimeException("Не содержит единиц");
        }

        int[] result = new int[source.length - indexLastOne - 1];
        int j = 0;
        for (int i = indexLastOne + 1; i < source.length; i++) {
            result[j++] = source[i];
        }
        return result;
    }

    public static boolean methodTwo(int[] source) {
        int presentOne = 0;
        int presentTwo = 0;
        for (int item : source) {
            if (item == 1) {
                presentOne = 1;
                continue;
            }
            if (item == 2) {
                presentTwo = 1;
                continue;
            }
            return false;
        }
        return presentOne + presentTwo == 2;
    }

    public static void main(String[] args) {
        System.out.println("AppHw31");
    }
}
