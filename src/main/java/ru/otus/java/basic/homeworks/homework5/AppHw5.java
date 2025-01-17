package ru.otus.java.basic.homeworks.homework5;

import java.util.Arrays;

public class AppHw5 {
    public static void main(String[] args) {
        printWord("Java", 3);

        int[] arrayOfInt = {1, 7, 4, 10};
        sumAndPrint(arrayOfInt);

        int[] arrayOfInteger = new int[5];
        setOfArray(arrayOfInteger, 10);
        increaseOfElementArray(arrayOfInteger, 1);
        comparingArrayHalves(arrayOfInteger);
        printArray(arrayOfInteger);//проверка
    }

    public static void printWord(String str, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(str);
        }
    }

    public static void sumAndPrint(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > 5) {
                sum += array[i];
            }
        }
        System.out.println(sum);
    }

    public static void setOfArray(int[] array, int decimalNumber) {
        //вариант без цикла
        //Arrays.fill(array, decimalNumber);

        // вариант c циклом for
        for (int i = 0; i < array.length; i++) {
            array[i] = decimalNumber;
        }
    }

    public static void increaseOfElementArray(int[] array, int decimalNumber) {
        //цикл for
        for (int i = 0; i < array.length; i++) {
            array[i] += decimalNumber;
        }

        //аналог цикла foreach, элементы перебирает, но изменить в таком цикле их не получается, изменения не сохраняются в массиве, скорее всего потому что item лок. переменная
//        for (int item : array) {
//            System.out.println("до увеличения" + item);
//            item += decimalNumber;
//            System.out.println("после увеличения" + item);
//        }
    }

    public static void comparingArrayHalves(int[] array/*ссылка на массив*/) {
        int sumFirstHalves = 0;
        int sumSecondHalves = 0;
        for (int i = 0; i < array.length; i++) {
            if (i <= array.length / 2 + 1) {
                //для нечетного количества элементов в первую половину попадет на 1 элемент больше чем во вторую
                sumFirstHalves += array[i];
            } else {
                sumSecondHalves += array[i];
            }
        }
        //System.out.printf("sumFirstHalves: %d, sumSecondHalves: %d\n", sumFirstHalves, sumSecondHalves); //отладка

        if (sumFirstHalves > sumSecondHalves) {
            System.out.println("сумма элементов первой половины массива больше");
        } else if (sumFirstHalves < sumSecondHalves) {
            System.out.println("сумма элементов второй половины массива больше");
        } else {
            System.out.println("сумма элементов первой и второй половин массива равны");
        }
    }

    public static void printArray(int[] array) {
        //вариант без цикла
        //System.out.println("печать массива: " + Arrays.toString(array));

        //вариант с циклом
        for (int i = 0; i < array.length; i++) {
            System.out.print((i > 0 ? "" : "печать массива: [") + array[i] + (i == array.length - 1 ? "]" : ", "));
        }
    }

}
