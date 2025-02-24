package ru.otus.java.basic.homeworks.homework18;


import java.util.*;

/**
 * по собственной инициативе, в ДЗ нет такой задачи.
 * Создать класс со списком объектов произвольной природы (любой класс).
 * Класс включает в себя метод, который называется PrintList с логическим параметром.
 * Метод выводит на консоль нечетные или четные элементы списка, в зависимости от величины параметра (true или false)
 */
public class JenericsPractice {
    final List<Object> listObj = new ArrayList<>();

    public JenericsPractice(int n) {
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                listObj.add(i);
            }
            else {
                listObj.add("Element " + i);
            }
        }
    }

    /**
     *
     * @param evenElement == true -> вывод четных, иначе нечетных
     * элемент является четным, если его индекс делится на 2 без остатка
     */
    public void printList(boolean evenElement) {
        for (int i = evenElement ? 0 : 1; i < listObj.size(); i += 2 ) {
            System.out.println(listObj.get(i));
        }
    }
}
