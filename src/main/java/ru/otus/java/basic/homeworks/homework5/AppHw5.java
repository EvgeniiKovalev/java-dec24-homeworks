package ru.otus.java.basic.homeworks.homework5;

import java.util.Arrays;

public class AppHw5 {
    public static void main(String[] args) {
        System.out.println("---------------Обычные задачи");
        printWord("Java", 3);
        System.out.println("\n");

        int[] arrayOfInt1 = {1, 7, 4, 10};
        sumAndPrint(arrayOfInt1);
        int[] arrayOfInt2 = new int[5];
        setOfArray(arrayOfInt2, 10);
        increaseOfElementArray(arrayOfInt2, 1);
        comparingArrayHalves(arrayOfInt2);
        printArray(arrayOfInt2);//проверка

        System.out.print("\n\n---------------Задачи со звездочкой");
        System.out.println("\nметод, принимающий на вход набор целочисленных массивов, и получающий новый массив равный сумме входящих");
        int[][] arrayOfArrayInt = {{1, 2, 3}, {2, 2}, {1, 1, 1, 1, 1}};
        System.out.print("Результат - ");
        printArray(sumArrays(arrayOfArrayInt));


        System.out.println("\n\nметод, проверяющий что есть \"точка\" в массиве, в которой сумма левой и правой части равны");
        int[] arrInt1 = {1, 1, 1, 1, 1, 5};
        int[] arrInt2 = {5, 3, 4, -2};
        int[] arrInt3 = {7, 2, 2, 2};
        int[] arrInt4 = {9, 4};
        int[] arrInt5 = {1, 1, 1, 2};
        int[] arrInt6 = {0, 0, 0};
        FindDotArray(arrInt1);
        FindDotArray(arrInt2);
        FindDotArray(arrInt3);
        FindDotArray(arrInt4);
        FindDotArray(arrInt5);
        FindDotArray(arrInt6);

        System.out.println("\nметод проверяющий что массив упорядочен по выбору пользователя");
        int[] arrInt8 = {-2, 1, 5};
        int[] arrInt9 = {2, 1, -5};
        int[] arrInt10 = {1, 1};
        int[] arrInt11 = {2};

        printArray(arrInt8);
        System.out.println(arrayIsOrdered(arrInt8, false));
        printArray(arrInt8);
        System.out.println(arrayIsOrdered(arrInt8, true));

        printArray(arrInt9);
        System.out.println(arrayIsOrdered(arrInt9, false));
        printArray(arrInt9);
        System.out.println(arrayIsOrdered(arrInt9, true));

        printArray(arrInt10);
        System.out.println(arrayIsOrdered(arrInt10, false));
        printArray(arrInt10);
        System.out.println(arrayIsOrdered(arrInt10, true));

        printArray(arrInt11);
        System.out.println(arrayIsOrdered(arrInt11, false));
        printArray(arrInt11);
        System.out.println(arrayIsOrdered(arrInt11, true));


        System.out.println("\nметод переворачивающий массив");
        System.out.print("исходный массив ");
        int[] arrInt12 = {-2, 1, 5};
        printArray(arrInt12);
        arrayReverse(arrInt12);
        System.out.print("\nперевернутый массив ");
        printArray(arrInt12);

    }

    //    Реализуйте метод, “переворачивающий" входящий массив
//    Пример: { 1 2 3 4 } => { 4 3 2 1 }
    public static void arrayReverse(int[] arr) {
        int tempNumber;
        for (int i = arr.length - 1; i > 0; i--) {
            tempNumber = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = arr[i];
            arr[i] = tempNumber;
        }
    }

    //    Реализуйте метод, проверяющий что все элементы массива идут в порядке убывания или
//    возрастания (по выбору пользователя)
    public static String arrayIsOrdered(int[] arr, boolean direction) {
        // direction = true - проверяем возрастание, false - убывание
        int arrIndex = direction ? 0 : arr.length - 1;
        int itemProcessed = 0;
        int itemCurrent;
        int itemPrevious = 0;
        String res = direction ? "возрастание" : "убывание";

        while (itemProcessed < arr.length) {
            itemCurrent = arr[arrIndex];

            if ((itemProcessed > 0 && itemPrevious >= itemCurrent) || arr.length < 2) {
                return "Нарушено " + res;
            }

            if (direction) {
                arrIndex++;
            } else {
                arrIndex--;
            }
            itemPrevious = itemCurrent;
            itemProcessed++;
        }
        return "Соблюдено " + res;
    }

    //    Реализуйте метод, проверяющий что есть "точка" в массиве, в которой сумма левой и правой части равны.
//    Точка находится между элементами.
//    Пример: { 1, 1, 1, 1, 1, | 5 }, { 5, | 3, 4, -2 }, { 7, 2, 2, 2 }, { 9, 4 }
    public static void FindDotArray(int[] arr) {
        String res = "";
        for (int dotIndex = 1; dotIndex < arr.length; dotIndex++) {
            int sumBeforeDot = 0;
            int sumAfterDot = 0;
            for (int i = 0; i < dotIndex; i++) {
                sumBeforeDot += arr[i];
            }

            for (int i = dotIndex; i < arr.length; i++) {
                sumAfterDot += arr[i];
            }

            if (sumBeforeDot == sumAfterDot) {
                res = "{ ";
                for (int i = 0; i < arr.length; i++) {
                    if (i == dotIndex) {
                        res += ", | ";
                    } else if (i > 0) {
                        res += ", ";
                    }
                    res += arr[i];
                }
                res += " }";
                break;
            }
        }
        if (res.isEmpty()) {
            printArray(arr);
            System.out.print("\n");
        } else {
            System.out.println(res);
        }

    }


    //    Реализуйте метод, принимающий на вход набор целочисленных массивов, и получающий новый
//    массив равный сумме входящих;
//    Пример:
//       { 1, 2, 3 }
//     + { 2, 2 }
//     + { 1, 1, 1, 1, 1}
//     = { 4, 5, 4, 1, 1 }
    public static int[] sumArrays(int[][] arr) {
        int maxLengthArrays = 0;
        for (int[] i : arr) {
            maxLengthArrays = Math.max(maxLengthArrays, i.length);
        }
        int[] resArr = new int[maxLengthArrays]; //инициализирован нулями
        for (int i = 0; i < maxLengthArrays; i++) {
            for (int[] ints : arr) {
                try {
                    resArr[i] += ints[i];
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        return resArr;
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

    public static void comparingArrayHalves(int[] array) {
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
        //System.out.println(Arrays.toString(array));

        //вариант с циклом
        System.out.print("{ ");
        for (int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.print(array[array.length - 1] + " }");

    }

}
