package ru.otus.java.basic.homeworks.homework15;

import java.util.Random;

/**
 * Домашнее задание
 * Работа с исключениями
 *<p>
 * Цель: научиться работать с исключениями.
 * <p>
 * Описание/Пошаговая инструкция выполнения домашнего задания:
 * Реализуйте метод, аргументом которого является двумерный строковый массив размером 4х4. Если передан массив другого
 * размера необходимо бросить исключение AppArraySizeException.
 * Метод должен обойти все элементы массива, преобразовать в int и просуммировать. Если в каком-то элементе массива
 * преобразование не удалось (например, в ячейке лежит текст вместо числа), должно быть брошено исключение
 * AppArrayDataException с детализацией, в какой именно ячейке лежат неверные данные.
 * В методе main() необходимо вызвать полученный метод, обработать возможные исключения AppArraySizeException и
 * AppArrayDataException и вывести результат расчета (сумму элементов, при условии что подали на вход корректный массив).
 */
public class AppHw15 {

    public static void main(String[] args) throws AppArraySizeException, AppArrayDataException {

        System.out.println("\nТЕСТ 1 работоспособность метода sumArray при размерности массива 4Х4 и значениями в виде строк являющихся числами");
        String[][] array = generateRandomArray(4,4);
        System.out.println("Исходный массив");
        printArray(array);
        System.out.println("сумма элементов массива = " +sumArray(array));

        try {
            System.out.println("\nТЕСТ 2 AppArraySizeException");
            array = generateRandomArray(4,5);
            System.out.println("Исходный массив");
            printArray(array);
            System.out.println("сумма элементов массива = " +sumArray(array));
        } catch (AppArraySizeException e) {
            System.out.println("поймано исключение " + e.getClass().getName());
            System.out.printf("%s \"%s\"\n", "Сообщение исключения",e.getMessage());
        }

        try {
            System.out.println("\nТЕСТ 3 AppArrayDataException");
            System.out.println("Исходный массив");
            printArray(array);
            array = generateRandomArray(4,4);
            array[1][1] = "строка";
            System.out.println("Измененный массив");
            printArray(array);
            System.out.println("сумма элементов массива = " +sumArray(array));

        } catch (AppArrayDataException e) {
            System.out.println("поймано исключение " + e.getClass().getName());
            System.out.printf("%s \"%s\"\n", "Сообщение исключения",e.getMessage());
            System.out.printf("детализированная информация о ячейке в которой возникла ошибка: номер строки= %d, номер колонки %d\n", e.getRowNum(), e.getColNum());
        }
    }

    public static String[][] generateRandomArray(int m, int n) {
        String[][] result = new String[m][n];
        Random random = new Random();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = String.valueOf(random.nextInt(10));
            }
        }
        return result;
    }

    public static int sumArray(String[][] arr) throws AppArraySizeException, AppArrayDataException{
        if (arr.length != 4 || arr[0].length != 4) {
            throw new AppArraySizeException("Размерность массива не соответствует требованию 4х4");
        }
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length ; j++) {
                    try {
                        result += Integer.parseInt(arr[i][j]);
                    }
                    catch (NumberFormatException e) {
                        throw new AppArrayDataException("неверные данные в ячейке ", i, j);
                    }
            }
        }
        return result;
    }
    public static void printArray(String[][] arr) {
        for (String[] strArr : arr) {
            for (int j = 0; j < strArr.length - 1; j++) {
                System.out.print((j > 0 ? ", ":"") + strArr[j]);
            }
            System.out.println(", " + strArr[strArr.length - 1]);
        }
    }    
    
}
