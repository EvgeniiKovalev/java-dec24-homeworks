package ru.otus.java.basic.homeworks.homework17;


/**
 * <pre>
 * Цели занятия
 * познакомиться с реализациями Map и Set.
 *
 * Краткое содержание
 * принципы работы HashMap, LinkedHashMap, TreeMap;
 * принципы работы HashSet, LinkedHashSet, TreeSet;
 * практические задачи на использование Set и Map.
 *
 * Результаты
 * умение использовать структуры данных для решения задач.
 *  Описание/Пошаговая инструкция выполнения домашнего задания:
 *  Реализуйте класс PhoneBook, который хранит в себе список имен (фио) и телефонных номеров;
 *     Метод add() должен позволять добавлять запись имя-телефон;
 *     Метод find() выполнять поиск номер(-а, -ов) телефона по имени;
 *         Под одним именем может быть несколько телефонов (в случае однофамильцев,
 *         или наличии у одного человека нескольких номеров), тогда при запросе такой фамилии должны выводиться все телефоны;
 *     Метод containsPhoneNumber должен проверять наличие телефона в справочнике.
 * </pre>
 */
public class AppHw17 {

    public static void main(String[] args) {

        PhoneBook phoneBook = new PhoneBook(10);
        System.out.println("ТЕСТ 1 add");
        phoneBook.add("fio1", "+7-922-232-21-21");
        phoneBook.add("fio2", "+7-922-232-22-22");
        phoneBook.add("fi3", "+7-922-232-23-23");
        System.out.println(phoneBook);

        System.out.println("ТЕСТ 2 find");
        System.out.println("найден номер телефона по фио: " + phoneBook.find("fio1"));

        System.out.println("ТЕСТ 3 containsPhoneNumber");
        phoneBook.containsPhoneNumber("+7-922-232-21-21");

        System.out.println("ТЕСТ 4 containsFio");
        phoneBook.containsFio("fio1");

        System.out.println("ТЕСТ 5 getEntryLikeByFio");
        phoneBook.getEntryLikeByFio("fio");
    }
}
