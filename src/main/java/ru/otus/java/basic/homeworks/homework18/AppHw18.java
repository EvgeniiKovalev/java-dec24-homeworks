package ru.otus.java.basic.homeworks.homework18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * <pre>
 * Построение простейшего двоичного дерева и поиск по нему
 *
 * Цель:
 * развитие навыков работы со сложными объектами;
 * практическое применение рекурсии и сортировки, выбора классов для реализации.
 *
 *
 * Описание/Пошаговая инструкция выполнения домашнего задания:
 * Основноее ДЗ
 *
 * Даны два класса
 * public class Person {
 *    String name;
 *    Position position;
 *    Long id;
 * }
 * public enum Position {
 *  MANAGER, DIRECTOR, DRIVER, ENGINEER, SENIOR_MANAGER, DEVELOPER, QA,
 *  JANITOR, PLUMBER, BRANCH_DIRECTOR, JUNIOR_DEVELOPER
 * }
 *
 * Написать класс PersonDataBase, содержащий список Person, и имеющий следующие методы, со следующей асимптотической
 * сложностью (методы и конструктор класса Person реализовать самостоятельно)
 *
 * Person findById(Long id) - найти Person по id - O(1)
 * void add(Person person) - добавить Person - O(1)
 * isManager(Person person) - O(1) - true если Position : MANAGER, DIRECTOR, BRANCH_DIRECTOR или SENIOR_MANAGER
 * isEmployee(Long id) - O(1) - true если Employee имеет любой другой Position
 * </pre>
 */
public class AppHw18 {
    public static void main(String[] args) {
        List<Task> taskListForRun = new ArrayList<>();
        taskListForRun.add(Task.TASK1);
        taskListForRun.add(Task.TASK2);
        taskListForRun.add(Task.TASK3);
        taskListForRun.add(Task.TASKJENERICS);


        if (taskListForRun.contains(Task.TASK1)) {
            System.out.println("Task1 -- двоичное дерево с методами поиска с асимптотической сложность O(1)");
            PersonDataBase binaryTree = new PersonDataBase();
            System.out.println("тест add");
            binaryTree.add(new Person("1", Position.MANAGER, 1L));
            binaryTree.add(new Person("12", Position.MANAGER, 1L));
            binaryTree.add(new Person("2", Position.DEVELOPER, 2L));
            binaryTree.add(new Person("2", Position.DEVELOPER, 2L));
            System.out.println("размер коллекции = " + binaryTree.size());
            System.out.println();

            System.out.println("тест findNode");
            Node node1 = binaryTree.findNode(1L);
            System.out.println(node1);
            Node node2 = binaryTree.findNode(2L);
            System.out.println(node2);
            System.out.println();

            System.out.println("тест findById");
            System.out.println("нашли элемент: " + binaryTree.findById(1L));
            System.out.println("Не нашли элемент который не должен был вставлен как повторяющийся: " + binaryTree.findById(12L));
            System.out.println();

            System.out.println("тест isManager");
            System.out.println("позиция = " + binaryTree.findNode(1L).getPerson().getPosition() + " isManager = " + binaryTree.isManager(binaryTree.findById(1L)));
            System.out.println("позиция = " + binaryTree.findNode(2L).getPerson().getPosition() + " isManager = " + binaryTree.isManager(binaryTree.findById(2L)));
            System.out.println();

            System.out.println("тест isEmployee");
            System.out.println("позиция = " + binaryTree.findNode(1L).getPerson().getPosition() + " isEmployee = " + binaryTree.isEmployee(1L));
            System.out.println("позиция = " + binaryTree.findNode(2L).getPerson().getPosition() + " isEmployee = " + binaryTree.isEmployee(2L));
            System.out.println();
        }

        if (taskListForRun.contains(Task.TASK2)) {
            System.out.println("Task2 -- СОРТИРОВКИ");

            System.out.println("Сортировка пузырьком на массиве случайных чисел");
            Sorting.randomFillArray(10);
            System.out.println("    до сортировки     " + Arrays.toString(Sorting.testData));
            Sorting.bubbleSort(Sorting.testData);
            System.out.println("    после сортировки  " + Arrays.toString(Sorting.testData));
            System.out.println();

            System.out.println("Быстрая сортировка на массиве с гарантированно повторяющимися элементами");
            System.out.println("    до сортировки     " + Arrays.toString(Sorting.testData2));
            Sorting.quickSort(Sorting.testData2, 0, Sorting.testData2.length - 1);
            System.out.println("    после сортировки  " + Arrays.toString(Sorting.testData2));
            System.out.println();

            System.out.println("Быстрая сортировка на массиве случайных чисел");
            Sorting.randomFillArray(10);
            System.out.println("    до сортировки     " + Arrays.toString(Sorting.testData));
            Sorting.quickSort(Sorting.testData, 0, Sorting.testData.length - 1);
            System.out.println("    после сортировки  " + Arrays.toString(Sorting.testData));
            System.out.println();
        }

        if (taskListForRun.contains(Task.TASK3)) {
            System.out.println("Task3 -- задача о бинарном поиске");

            SearchTreeNode searchTreeNode = new SearchTreeNode();
            searchTreeNode.buildBinaryTree(10);
            System.out.println("Корень дерева: " + searchTreeNode.getRoot());
            System.out.println("--тест find");

            Node node = new Node(
                    new Person("name 2000", Position.MANAGER, 2000L)
            );
            //"результат поиска " +
            System.out.println("результат поиска: " + searchTreeNode.find(node));
            System.out.println();


            System.out.println("--тест getSortedList");
            List<Node> list = searchTreeNode.getSortedList();
            for (int i = 0; i < list.size() ; i++) {
                System.out.println("   " + list.get(i));
            }
            System.out.println();
        }

        if (taskListForRun.contains(Task.TASKJENERICS)) {
            System.out.println("TASKJENERICS -- задача по практике с дженериками");
            JenericsPractice testJeneric = new JenericsPractice(10);

            System.out.println("Вывод четных элементов");
            testJeneric.printList(true);
            System.out.println();

            System.out.println("Вывод нечетных элементов");
            testJeneric.printList(false);
            System.out.println();
        }
    }

    public static class Sorting {
        static int[] testData;
        static int[] testData2 = {2, -1, 2};
        static Random rand = new Random();

        public static void randomFillArray(int size) {
            testData = new int[size];

            for (int i = 0; i < size; i++) {
                testData[i] = rand.nextInt(size) * (1 - 2*rand.nextInt(2));
            }
        }

        public static void bubbleSort(int[] array) {
            for (int j = 0; j < array.length - 1; j++) {
                for (int i = 0; i < array.length - 1 - j; i++) {
                    if (array[i] > array[i + 1]) {
                        int swap = array[i];
                        array[i] = array[i + 1];
                        array[i + 1] = swap;
                    }
                }
            }
        }

        /**
         * Описание алгоритма
         * идем навстречу с концов интервала, левый вперед, правый назад
         * пока не встретятся индексы, и обмениваем элементы,
         * (условие обмена для левых  - левые меньше опорного,
         * условие обмена для правых - правые больше опорного)
         * возвращаем правый индекс, на котором цикл остановится
         */
        public static int partition(int[] array, int leftIndex, int rightIndex) {
            int supportElement = array[leftIndex + rand.nextInt(rightIndex - leftIndex)];
            int i = leftIndex;
            int j = rightIndex;

            while (i <= j) {
                while (array[i] < supportElement) {
                    i++;
                }
                while (array[j] > supportElement) {
                    j--;
                }
                if (i >= j) {
                    break;
                }
                int swap = array[i];
                array[i] = array[j];
                array[j] = swap;
                i++;
                j--;
            }
            return j;
        }


        public static void quickSort(int[] array, int leftIndex, int rightIndex) {
            if (array == null || (rightIndex - leftIndex) == 0) {
                return;
            }

            int supportIndex = partition(array, leftIndex, rightIndex);
            quickSort(array, leftIndex, supportIndex);
            quickSort(array, supportIndex + 1, rightIndex);

        }
    }

}

