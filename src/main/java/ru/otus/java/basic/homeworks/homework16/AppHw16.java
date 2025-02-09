package ru.otus.java.basic.homeworks.homework16;

import java.util.ArrayList;

public class AppHw16 {
    public static void main(String[] args) {
        System.out.println("ТЕСТ 1 getArrayList");
        ArrayList<Integer> intList = getArrayList(-40, 10);
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
        for (int i : arrayList) {
            if (i > 5 ) result += i;
        }
        return result;
    }

    public static void fillArrayList(ArrayList<Integer> arrayList, int number) {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.set(i, number);
        }
    }

    public static void incArrayList(ArrayList<Integer> arrayList, int number) {
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.set(i, arrayList.get(i) + number);
        }
    }

    public static ArrayList<String> printNameEmployee(ArrayList<Employee> listEmployee) {
        ArrayList<String> result = new ArrayList<>();
        for (Employee e : listEmployee) {
            result.add(e.getName());
        }
        return result;
    }

    public static ArrayList<Employee> olderEmployee(ArrayList<Employee> listEmployee, int minAge) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee e : listEmployee) {
            if (minAge <= e.getAge()) {
                result.add(e);
            }
        }
        return result;
    }

    public static boolean checkAverageAgerEmployee(ArrayList<Employee> listEmployee, int minAge, int[] avgAge) {
        for (Employee e : listEmployee) {
            avgAge[0] += e.getAge();
        }
        avgAge[0] = avgAge[0] / listEmployee.size();
        return minAge < avgAge[0];
    }


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
