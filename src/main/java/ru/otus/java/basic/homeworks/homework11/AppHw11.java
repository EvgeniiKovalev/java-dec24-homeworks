package ru.otus.java.basic.homeworks.homework11;

public class AppHw11 {
    public static void main(String[] args) {

    /*

    Домашнее задание
    ООП: Наследование и полиморфизм

    Цель:
    научиться работать с наследованием и полиморфизмом.


    Описание/Пошаговая инструкция выполнения домашнего задания:
    Создайте классы Cat, Dog и Horse с наследованием от класса Animal
    У каждого животного есть имя, скорость бега и плавания (м/с), и выносливость (измеряется в условных единицах)
    Затраты выносливости:
    Все животные на 1 метр бега тратят 1 ед выносливости,
    Собаки на 1 метр плавания - 2 ед.
    Лошади на 1 метр плавания тратят 4 единицы
    Кот плавать не умеет.
    Реализуйте методы run(int distance) и swim(int distance), которые должны возвращать время, затраченное на указанное действие,
    и “понижать выносливость” животного. Если выносливости не хватает, то возвращаем время -1 и указываем что
    у животного появилось состояние усталости. При выполнении действий пишем сообщения в консоль.
    Добавляем метод info(), который выводит в консоль состояние животного.
    */

        System.out.println("---------------тест cat");
        Cat cat = new Cat("barsic", 3, 2000);
        cat.info();
        System.out.println(cat);
        //обычный бег
        Tester.testRun(TestAction.RUN, 10, cat);
        cat.info();
        System.out.println(cat);
        //тест усталости при беге
        Tester.testRun(TestAction.RUN, 100000, cat);
        cat.info();
        System.out.println(cat);

        System.out.println("\n---------------тест Dog");
        Dog dog = new Dog("reks", 20, 2,20000);
        dog.info();
        System.out.println(dog);
        Tester.testRun(TestAction.RUN, 30, dog);
        dog.info();
        System.out.println(dog);
        Tester.testRun(TestAction.SWIM, 10, dog);
        dog.info();
        System.out.println(dog);

        System.out.println("\n---------------тест Horse");
        Horse horse = new Horse("gamlet", 20, 2,50000);
        horse.info();
        System.out.println(horse);
        Tester.testRun(TestAction.RUN, 30, horse);
        horse.info();
        System.out.println(horse);
        Tester.testRun(TestAction.SWIM, 10, horse);
        horse.info();
        System.out.println(horse);
    }
}
