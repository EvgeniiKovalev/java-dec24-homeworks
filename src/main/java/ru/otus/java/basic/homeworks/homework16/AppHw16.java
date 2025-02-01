package ru.otus.java.basic.homeworks.homework16;

import java.util.ArrayList;

/**
 * <pre>
 * Домашнее задание
 * Работа со списками
 *
 * Цель: научиться работать со списками.
 *
 * Описание/Пошаговая инструкция выполнения домашнего задания:
 *
 * Реализуйте метод, принимающий в качестве аргументов числа min и max, и возвращающий ArrayList с набором
 * последовательных значений в указанном диапазоне (min и max включительно, шаг - 1);
 *
 * Реализуйте метод, принимающий в качестве аргумента список целых чисел, суммирующий все элементы,
 * значение которых больше 5, и возвращающий сумму;
 *
 * Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на список,
 * метод должен переписать каждую заполненную ячейку списка указанным числом;
 *
 * Реализуйте метод, принимающий в качестве аргументов целое число и ссылку на список,
 * увеличивающий каждый элемент списка на указанное число;
 *
 * Создайте класс Сотрудник с полями: имя, возраст;
 *    - Реализуйте метод, принимающий в качестве аргумента список сотрудников, и возвращающий список их имен;
 *
 *    - Реализуйте метод, принимающий в качестве аргумента список сотрудников и минимальный возраст,
 *      и возвращающий список сотрудников, возраст которых больше либо равен указанному аргументу;
 *
 *    - Реализуйте метод, принимающий в качестве аргумента список сотрудников и минимальный средний возраст,
 *      и проверяющий что средний возраст сотрудников превышает указанный аргумент;
 *
 *    - Реализуйте метод, принимающий в качестве аргумента список сотрудников,
 *      и возвращающий ссылку на самого молодого сотрудника.
 * </pre>
 */
public class AppHw16 {
    public static void main(String[] args) {
        System.out.println("ТЕСТ 1 getArrayList");
        ArrayList<Integer> intList = getArrayList(-4, 10);
        System.out.println("список целых чисел:" + intList);

        System.out.println("ТЕСТ 2 sumMoreFiveInArrayList");
        System.out.println("сумма элементов больше 5 = " + sumMoreFiveInArrayList(intList));

        System.out.println("ТЕСТ 3 fillArrayList");
        fillArrayList(intList, 22);
        System.out.println("список: " + intList);

        System.out.println("ТЕСТ 4 incArrayList");
        incArrayList(intList, 100);
        System.out.println("список: " + intList);

        System.out.println("ТЕСТ 5 printNameEmployee");
        ArrayList<Employee> empList = new ArrayList<>();
        empList.add(new Employee("Ustas", 45));
        empList.add(new Employee("Alex", 55));
        empList.add(new Employee("Muller", 45));
        System.out.println("Имена сотрудников: " + printNameEmployee(empList));

        System.out.println("ТЕСТ 6 olderEmployee");
        int minAge = 67;
        System.out.printf("Сотрудники возраст которых > %d : %s\n", minAge, olderEmployee(empList, minAge));

        System.out.println("ТЕСТ 7 checkAverageAgerEmployee");
        int[] avgAge = {0};
        boolean ch = checkAverageAgerEmployee(empList, minAge, avgAge);
        System.out.printf("Средний возраст сотрудников: %d лет, %s заданный возраст %d лет\n", avgAge[0], (ch ? "" : "не ") + "превышает ", minAge);

        System.out.println("ТЕСТ 8 youngEmployee");
        System.out.printf("Самый молодой сотрудник %s", youngEmployee(empList));


    }

    public static ArrayList<Integer> getArrayList(int min, int max) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            result.add(i);
        }
        return result;
    }

    public static int sumMoreFiveInArrayList(ArrayList<Integer> arrayList) {
        int result = 0;
        //вар1
        for (Integer integer : arrayList) {
            result += integer;
        }

        //вар2
//        for (int i: arrayList) {
//            result += i;
//        }
        return result;
    }

    public static void fillArrayList(ArrayList<Integer> arrayList, int number) {
        //вар1
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.set(i, number);
        }

        //вар2
        //arrayList.replaceAll(n -> number);
    }

    public static void incArrayList(ArrayList<Integer> arrayList, int number) {
        //вар1
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.set(i, arrayList.get(i) + number);
        }

        //вар2
        //arrayList.replaceAll(n -> n + number);
    }

    /**
     * Реализуйте метод, принимающий в качестве аргумента список сотрудников, и возвращающий список их имен;
     */
    public static ArrayList<String> printNameEmployee(ArrayList<Employee> listEmployee) {
        ArrayList<String> result = new ArrayList<>();
        for (Employee e : listEmployee) {
            result.add(e.getName());
        }
        return result;
    }

    /**
     * Реализуйте метод, принимающий в качестве аргумента список сотрудников и минимальный возраст,
     * и возвращающий список сотрудников, возраст которых больше либо равен указанному аргументу;
     */
    public static ArrayList<Employee> olderEmployee(ArrayList<Employee> listEmployee, int minAge) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee e : listEmployee) {
            if (minAge < e.getAge()) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Реализуйте метод, принимающий в качестве аргумента список сотрудников и минимальный средний возраст,
     * и проверяющий что средний возраст сотрудников превышает указанный аргумент;
     *
     * @param avgAge -ссылка на массив из одного элемента, значение в котороем после завершения работы метода будет средний возраст
     */
    public static boolean checkAverageAgerEmployee(ArrayList<Employee> listEmployee, int minAge, int[] avgAge) {
        for (Employee e : listEmployee) {
            avgAge[0] += e.getAge();
        }
        avgAge[0] = avgAge[0] / listEmployee.size();
        return minAge < avgAge[0];
    }


    /**
     * Реализуйте метод, принимающий в качестве аргумента список сотрудников,
     * и возвращающий ссылку на самого молодого сотрудника.
     */
    public static Employee youngEmployee(ArrayList<Employee> listEmployee) {
        Employee res = listEmployee.get(0);
        for (Employee employee : listEmployee) {
            if (res.getAge() > employee.getAge()) {
                res = employee;
            }
        }
        return res;
    }
}
