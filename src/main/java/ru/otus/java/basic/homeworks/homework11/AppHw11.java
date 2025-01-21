package ru.otus.java.basic.homeworks.homework11;

public class AppHw11 {
    public static void main(String[] args) {
        //Tester test = new Tester(); //класс для удобства вывода результатов при запуске методов run, swim при тестирования, не меняя самих методов run, swim

        System.out.println("---------------тест cat");
        Cat cat = new Cat("barsic", 3, 2000);
        cat.info();
        System.out.println(cat);
        //System.out.println(cat.run(10));
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
        //System.out.println(dog.run(30));
        Tester.testRun(TestAction.RUN, 30, dog);
        dog.info();
        System.out.println(dog);
        //System.out.println(dog.swim(10));
        Tester.testRun(TestAction.SWIM, 10, dog);
        dog.info();
        System.out.println(dog);

        System.out.println("\n---------------тест Horse");
        Horse horse = new Horse("gamlet", 20, 2,50000);
        horse.info();
        System.out.println(horse);
        //System.out.println(horse.run(30));
        Tester.testRun(TestAction.RUN, 30, horse);
        horse.info();
        System.out.println(horse);
        //System.out.println(horse.swim(10));
        Tester.testRun(TestAction.SWIM, 10, horse);
        horse.info();
        System.out.println(horse);
    }
}
