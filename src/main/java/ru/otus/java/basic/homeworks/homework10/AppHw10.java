package ru.otus.java.basic.homeworks.homework10;

import ru.otus.java.basic.homeworks.homework7.User;
import ru.otus.java.basic.homeworks.homework7.Box;
import ru.otus.java.basic.homeworks.homework7.Color;

import java.time.Year;

public class AppHw10 {
    public static void main(String[] args) throws Exception {
        /*
        Создайте класс Пользователь (User) с полями: фамилия, имя, отчество, год рождения, email;
        Реализуйте у класса конструктор, позволяющий заполнять эти поля при создании объекта;
        В классе Пользователь реализуйте метод, выводящий в консоль информацию о пользователе в виде:
        ФИО: фамилия имя отчество
        Год рождения: год рождения
        e-mail: email
        В методе main() Main класса создайте массив из 10 пользователей и заполните его объектами и с
        помощью цикла выведите информацию только о пользователях старше 40 лет.
         */
        System.out.println("------------------тест класса User\n");
        User[] array = new User[10];
        int yearOfBirthRandom;
        int currentYear = Year.now().getValue();
        for (int i = 0; i < 10; i++) {
            yearOfBirthRandom = 1910 + (int) (Math.random() * 110);
            array[i] = new User(
                    "Фамилия_" + i,
                    "Имя_" + i,
                    "Отчество_" + i,
                    yearOfBirthRandom,
                    "user_" + yearOfBirthRandom + "@gmail.com"
            );

            if ( currentYear - array[i].yearOfBirth >= 40) {
                System.out.println(array[i]+ "\n");
            }
        }

        /*
        Попробуйте реализовать класс по его описания: объекты класса Коробка должны иметь размеры и цвет.
        Коробку можно открывать и закрывать. Коробку можно перекрашивать. Изменить размер коробки после создания нельзя.
        У коробки должен быть метод, печатающий информацию о ней в консоль.
        В коробку можно складывать предмет (если в ней нет предмета), или выкидывать его оттуда
        (только если предмет в ней есть), только при условии что коробка открыта (предметом читаем просто строку).
        Выполнение методов должно сопровождаться выводом сообщений в консоль.
        */
        System.out.println("------------------тесты класса Box\n");
        System.out.println("тест 0 создаем");
        Box box = new Box(101, 102, 103, Color.WHITE);
        System.out.println("тест 1 открываем");
        box.setIsOpened(true);System.out.println(box);
        System.out.println("тест 2 заполняем");
        box.setIsEmpty(false);System.out.println(box);
        System.out.println("тест 3 закрываем");
        box.setIsOpened(false);System.out.println(box);
        System.out.println("тест 4 открываем");
        box.setIsOpened(true);System.out.println(box);
        System.out.println("тест 5 освобождаем");
        box.setIsEmpty(true);System.out.println(box);
        System.out.println("тест 6 закрываем");
        box.setIsOpened(false);System.out.println(box);
        System.out.println("тест 7 перекрашиваем");
        box.color = Color.GREEN;System.out.println(box);
        System.out.println("тест 8 закрываем закрытую коробку");
        //box.setOpened(false); //получаем исключение - Коробка уже закрыта!!!
        System.out.println("тест 9 открываем");
        box.setIsOpened(true);System.out.println(box);
        System.out.println("тест 10 открываем открытую коробку");
        //box.setOpened(true); //получаем исключение - Коробка уже открыта!!!
        System.out.println("тест 11 закрываем коробку");
        box.setIsOpened(false);System.out.println(box);
        System.out.println("тест 12 заполняем закрытую коробку");
        //box.setIsEmpty(false); //получаем исключение - Коробка закрыта, чтобы освободить/заполнить коробку сначала откройте коробку !!!
    }
}
