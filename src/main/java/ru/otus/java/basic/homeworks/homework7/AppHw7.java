package ru.otus.java.basic.homeworks.homework7;

import static ru.otus.java.basic.homeworks.homework7.Diagonal.*;

public class AppHw7 {

    public static void main(String[] args) {

        System.out.println("-------------------");
        System.out.println("метод sumOfPositiveElements вернет сумму всех положительных элементов двумерного массива");
        System.out.println("исходный массив:");
        int[][] arrInt1 = createTwoDimensionalArrayOfInt(4, 3, 0, 9);
        printTwoDimensionalArrayOfInt(arrInt1);
        System.out.println("cумма всех элементов массива: " + sumOfPositiveElements(arrInt1));

        System.out.println("-------------------");
        System.out.println("метод DrawSquare рисует звездочками(*)  квадрат с указанной длиной стороны");
        System.out.println("size = 1");
        DrawSquare(1);
        System.out.println("size = 2");
        DrawSquare(2);
        System.out.println("size = 4");
        DrawSquare(4);

        System.out.println("-------------------");
        System.out.println("метод fillDiagonal зануляет диаг. элементы двумерного массива");
        int[][] arrInt2 = createTwoDimensionalArrayOfInt(3, 3, 0, 9);
        System.out.println("исходный массив:");
        printTwoDimensionalArrayOfInt(arrInt2);
        fillDiagonal(arrInt2, FROMLEFT);
        System.out.println("массив после зануления  диагонали нач-ся слева внизу:");
        printTwoDimensionalArrayOfInt(arrInt2);

        arrInt2 = createTwoDimensionalArrayOfInt(2, 2, 0, 9);
        System.out.println("исходный массив:");
        printTwoDimensionalArrayOfInt(arrInt2);
        fillDiagonal(arrInt2, FROMRIGHT);
        System.out.println("массив после зануления  диагонали нач-ся справа внизу:");
        printTwoDimensionalArrayOfInt(arrInt2);

        arrInt2 = createTwoDimensionalArrayOfInt(3, 3, 0, 9);
        System.out.println("исходный массив:");
        printTwoDimensionalArrayOfInt(arrInt2);
        fillDiagonal(arrInt2, ALL);
        System.out.println("массив после зануления обеих диагоналей нач-ся справа и слева внизу:");
        printTwoDimensionalArrayOfInt(arrInt2);

        System.out.println("тест обработки \"неизвестного\" значения параметра diagonal: " + UNKNOWN);
        fillDiagonal(arrInt2, UNKNOWN);

        System.out.println("-------------------");
        System.out.println("метод findMax вернет максимальный элемент двумерного массива");
        arrInt2 = createTwoDimensionalArrayOfInt(3, 3, 0, 9);
        System.out.println("исходный массив:");
        printTwoDimensionalArrayOfInt(arrInt2);
        System.out.println("Макс. элемент = " + findMax(arrInt2));


        System.out.println("-------------------");
        System.out.println("метод sumSecondRow вернет сумму элементов второй строки двумерного массива");
        arrInt2 = createTwoDimensionalArrayOfInt(1, 1, 0, 9);
        System.out.println("исходный массив:");
        printTwoDimensionalArrayOfInt(arrInt2);
        System.out.println("сумма элементов второй строки = " + sumSecondRow(arrInt2));

        arrInt2 = createTwoDimensionalArrayOfInt(2, 2, 0, 9);
        System.out.println("исходный массив:");
        printTwoDimensionalArrayOfInt(arrInt2);
        System.out.println("сумма элементов второй строки = " + sumSecondRow(arrInt2));

        arrInt2 = createTwoDimensionalArrayOfInt(4, 4, 0, 9);
        System.out.println("исходный массив:");
        printTwoDimensionalArrayOfInt(arrInt2);
        System.out.println("сумма элементов второй строки = " + sumSecondRow(arrInt2));


        System.out.println("------------------- тест1 работы с параметрами");
        Diagonal[] arrParam = {Diagonal.FROMLEFT,Diagonal.FROMRIGHT};
        testParam1(1, arrParam);

        System.out.println("------------------- тест2 работы с параметрами");
        testParam2(2, Diagonal.FROMLEFT, Diagonal.FROMRIGHT);
    }

    public static void testParam1(int a, Diagonal[] args) {
        System.out.println("a=" + a);
        for (int i = 0; i < args.length; i++) {
            System.out.println("args["+i+"]="+args[i]);
        }
    }

    public static void testParam2(int a, Diagonal ... args) {
        System.out.println("a=" + a);
        for (int i = 0; i < args.length; i++) {
            System.out.println("args["+i+"]="+args[i]);
        }
    }

    //Реализуйте метод, который считает сумму элементов второй строки двумерного массива,
    // если второй строки не существует, то в качестве результата необходимо вернуть -1
    public static int sumSecondRow(int[][] arr) {
        if (arr.length < 2) {
            return -1;
        }
        int result = 0;
        for (int j = 0; j < arr[1].length; j++) {
            result += arr[1][j];
        }
        return result;
    }

    //Реализовать метод findMax(int[][] array) который должен найти и вернуть максимальный элемент массива
    public static int findMax(int[][] array) {
        int result = array[0][0];
        for (int[] ints : array) {
            for (int anInt : ints) {
                if (anInt > result) {
                    result = anInt;
                }
            }
        }
        return result;
    }

    //Реализовать метод, принимающий в качестве аргумента двумерный целочисленный массив,
    // и зануляющий его диагональные элементы (можете выбрать любую из диагоналей, или занулить обе);
    public static void fillDiagonal(int[][] arrNumbers, Diagonal diagonal) {
        for (int i = 0; i < arrNumbers.length ; i++) {
            for (int j = 0; j < arrNumbers.length; j++) {
                switch (diagonal) {
                    case FROMRIGHT:
                        if (i == j) {
                            arrNumbers[i][j] = 0;
                        }
                        break;
                    case FROMLEFT:
                        if (i + j == arrNumbers[i].length-1)  {
                            arrNumbers[i][j] = 0;
                        }
                        break;
                    case ALL:
                        if (i == j || i + j == arrNumbers[i].length - 1) {
                            arrNumbers[i][j] = 0;
                        }
                        break;
                    default:
                        System.out.println("Неизвестное значение параметра diagonal: " + diagonal);
                        return;
                }
            }
        }
    }

    public static int[][] createTwoDimensionalArrayOfInt(int width, int height, int minValue, int maxValue) {
        int[][] result = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = (int) (Math.random() * (maxValue - minValue));
            }
        }
        return result;
    }

    public static void printTwoDimensionalArrayOfInt(int[][] arr) {
        for (int[] ints : arr) {
            System.out.print(ints[0]);
            for (int j = 1; j < ints.length - 1; j++) {
                System.out.print(", " + ints[j]);
            }
            System.out.println(", " + ints[ints.length - 1]);
        }
    }

    //Реализовать метод, который принимает в качестве аргумента int size и
    // печатает в консоль квадрат из символов * со сторонами соответствующей длины
    public static void DrawSquare(int size) {
        for (int height = 0; height < size; height++) {
            System.out.print("*");
            for (int width = 1; width < size - 1; width++) {
                if (height == 0 || height == size - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            if (size > 1) {
                System.out.println("*");
            } else {
                System.out.println();
            }
        }
    }

    //метод sumOfPositiveElements(..), принимающий в качестве аргумента целочисленный двумерный массив,
    //метод должен посчитать и вернуть сумму всех элементов массива, которые больше 0
    public static int sumOfPositiveElements(int[][] arrNumbers) {
        int result = 0;
        for (int[] arrNumber : arrNumbers) {
            for (int i : arrNumber) {
                if (i > 0) {
                    result += i;
                }
            }
        }
        return result;
    }

}
