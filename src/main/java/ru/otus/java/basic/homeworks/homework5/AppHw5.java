package ru.otus.java.basic.homeworks.homework5;

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
        findDotArray(arrInt1);
        findDotArray(arrInt2);
        findDotArray(arrInt3);
        findDotArray(arrInt4);
        findDotArray(arrInt5);
        findDotArray(arrInt6);

        System.out.println("\nметод проверяющий что массив упорядочен по выбору пользователя");
        int[] arrInt8 = {-2, 1, 5};
        int[] arrInt9 = {2, 1, -5};
        int[] arrInt10 = {1, 1};
        int[] arrInt11 = {2};

        printArray(arrInt8);
        printResultArrayIsOrdered(false, arrayIsOrdered(arrInt8, false));
        printArray(arrInt8);
        printResultArrayIsOrdered(true, arrayIsOrdered(arrInt8, true));

        printArray(arrInt9);
        printResultArrayIsOrdered(false, arrayIsOrdered(arrInt9, false));
        printArray(arrInt9);
        printResultArrayIsOrdered(true, arrayIsOrdered(arrInt9, true));

        printArray(arrInt10);
        printResultArrayIsOrdered(false, arrayIsOrdered(arrInt10, false));
        printArray(arrInt10);
        printResultArrayIsOrdered(true, arrayIsOrdered(arrInt10, true));

        printArray(arrInt11);
        printResultArrayIsOrdered(false, arrayIsOrdered(arrInt11, false));
        printArray(arrInt11);
        printResultArrayIsOrdered(true, arrayIsOrdered(arrInt11, true));


        System.out.println("\nметод переворачивающий массив");
        System.out.print("исходный массив ");
        int[] arrInt12 = {-2, 1, 5};
        printArray(arrInt12);
        arrayReverse(arrInt12);
        System.out.print("\nперевернутый массив ");
        printArray(arrInt12);

        System.out.print("\nисходный массив ");
        int[] arrInt13 = {1, 2, 3, 4};
        printArray(arrInt13);
        arrayReverse(arrInt13);
        System.out.print("\nперевернутый массив ");
        printArray(arrInt13);

        System.out.print("\nисходный массив ");
        int[] arrInt14 = {1, 2};
        printArray(arrInt14);
        arrayReverse(arrInt14);
        System.out.print("\nперевернутый массив ");
        printArray(arrInt14);

        System.out.print("\nисходный массив ");
        int[] arrInt15 = {1};
        printArray(arrInt15);
        arrayReverse(arrInt15);
        System.out.print("\nперевернутый массив ");
        printArray(arrInt15);


    }

    //    Реализуйте метод, “переворачивающий" входящий массив
//    Пример: { 1 2 3 4 } => { 4 3 2 1 }
    public static void arrayReverse(int[] arr) {
        int tempNumber;
        int medianaIndex;
        if (arr.length % 2 == 0) {
            medianaIndex = arr.length/2;
        } else {
            medianaIndex = arr.length / 2 + 1;
        }
        for (int i = arr.length - 1; i >= medianaIndex; i--) {
            tempNumber = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = arr[i];
            arr[i] = tempNumber;
        }
    }

    public static void printResultArrayIsOrdered(boolean checkIsAsc, boolean ResultArrayIsOrdered) {
        System.out.println("\t" + (checkIsAsc ? "возрастает" : "убывает") + " " + ResultArrayIsOrdered);
    }

    //    Реализуйте метод, проверяющий что все элементы массива идут в порядке убывания или
//    возрастания (по выбору пользователя)
    public static boolean arrayIsOrdered(int[] arr, boolean checkIsAsc) {
        int arrIndex = checkIsAsc ? 0 : arr.length - 1;
        int itemCurrent;
        int itemPrevious = 0;

        for (int i = 0; i < arr.length; i++) {
            itemCurrent = arr[arrIndex];
            if ((i > 0 && itemPrevious >= itemCurrent) || arr.length < 2) {
                return false;
            }

            if (checkIsAsc) {
                arrIndex++;
            } else {
                arrIndex--;
            }
            itemPrevious = itemCurrent;
        }
        return true;
    }

    //    Реализуйте метод, проверяющий что есть "точка" в массиве, в которой сумма левой и правой части равны.
//    Точка находится между элементами.
//    Пример: { 1, 1, 1, 1, 1, | 5 }, { 5, | 3, 4, -2 }, { 7, 2, 2, 2 }, { 9, 4 }
    public static void findDotArray(int[] arr) {
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
        for (int[] ints : arr) {
            for (int i = 0; i < ints.length; i++) {
                resArr[i] += ints[i];
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
