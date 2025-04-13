package ru.otus.java.basic.homeworks.homework27;

import java.util.ArrayList;
import java.util.List;

public class Study_generics {
    public void test1() {
        Apple apple1 = new Apple(1);
        Apple apple2 = new Apple(1);
        Apple apple3 = new Apple(4);
        Orange orange1 = new Orange(1);
        Fruit fruit1 = new Fruit(1);
        Fruit fruit2 = new Fruit(1);

        Box<Fruit> fruitBox1 = new Box<>();
        Box<Fruit> fruitBox2 = new Box<>();
        Box<Apple> appleBox1 = new Box<>();
        Box<Apple> appleBox2 = new Box<>();


        System.out.println("тест: фруктовая коробка может содержать все типы фруктов");
        fruitBox1.add(fruit1);
        fruitBox1.add(apple1);
        fruitBox1.add(orange1);
        System.out.println("fruitBox size = " + fruitBox1.size());
        System.out.println();

//        System.out.println("тест: яблочная коробка не может содержать ничего кроме яблок");
//        /*      ошибка синтаксиса  при добавлении в яблочную коробку фруктов и апельсин
//                'add(ru.otus.java.basic.homeworks.homework27.Study_generics.Apple)'
//                in 'ru.otus.java.basic.homeworks.homework27.Study_generics.Box'
//                cannot be applied to '(ru.otus.java.basic.homeworks.homework27.Study_generics.Fruit)'
//        */
//        appleBox1.add(fruit1); //error
//        appleBox1.add(orange1); //error

        System.out.println("тест: можно перекладывать между коробками одного типа");
        appleBox1.add(apple2);
        System.out.println("до переноса appleBox1 -> appleBox2");
        System.out.println("appleBox1 size = " + appleBox1.size());
        System.out.println("appleBox2 size = " + appleBox2.size());
        System.out.println("appleBox1 -> appleBox2");
        appleBox1.transferTo(appleBox2);
        System.out.println("после переноса appleBox1 -> appleBox2");
        System.out.println("appleBox1 size = " + appleBox1.size());
        System.out.println("appleBox2 size = " + appleBox2.size());
        System.out.println();

        fruitBox2.add(fruit2);
        System.out.println("до переноса fruitBox1 -> fruitBox2");
        System.out.println("fruitBox1 size = " + fruitBox1.size());
        System.out.println("fruitBox2 size = " + fruitBox2.size());
        System.out.println("fruitBox1 -> fruitBox2");
        fruitBox1.transferTo(fruitBox2);
        System.out.println("после переноса fruitBox1 -> fruitBox2");
        System.out.println("fruitBox1 size = " + fruitBox1.size());
        System.out.println("fruitBox2 size = " + fruitBox2.size());
        System.out.println();


        System.out.println("тест: можно перекладывать из коробки с яблоками в коробку с фруктами");
        System.out.println("до переноса appleBox2 -> fruitBox1");
        System.out.println("appleBox2 size = " + appleBox2.size());
        System.out.println("fruitBox1 size = " + fruitBox1.size());
        System.out.println("appleBox2 -> fruitBox1");
        appleBox2.transferTo(fruitBox1);
        System.out.println("после переноса appleBox2 -> fruitBox1");
        System.out.println("appleBox2 size = " + appleBox2.size());
        System.out.println("fruitBox1 size = " + fruitBox1.size());
        System.out.println();


//        System.out.println("тест: нельзя перекладывать из коробки с  фруктами в коробку с яблоками");
//        System.out.println("до переноса fruitBox1 -> appleBox2");
//        System.out.println("fruitBox1 size = " + fruitBox1.size());
//        System.out.println("appleBox2 size = " + appleBox2.size());
//        System.out.println("fruitBox1 -> appleBox2");
//
//        /* ошибка синтаксиса в строке ниже
//        'transferTo(ru.otus.java.basic.homeworks.homework27.Study_generics.Box
//        <? super ru.otus.java.basic.homeworks.homework27.Study_generics.Fruit>)'
//        in 'ru.otus.java.basic.homeworks.homework27.Study_generics.Box'
//        cannot be applied to '(ru.otus.java.basic.homeworks.homework27.Study_generics.Box
//        <ru.otus.java.basic.homeworks.homework27.Study_generics.Apple>)'
//         */
//        fruitBox1.transferTo(appleBox2);
//
//        System.out.println("после переноса fruitBox1 -> appleBox2");
//        System.out.println("fruitBox1 size = " + fruitBox1.size());
//        System.out.println("appleBox2 size = " + appleBox2.size());


        appleBox1.add(apple1);
        Box<Orange> orangeBox1 = new Box<>();
        orangeBox1.add(new Orange(4));
        System.out.println("тест: выводим вес всех коробок");
        System.out.println("fruitBox1 weight = " + fruitBox1.weight());
        System.out.println("fruitBox2 weight = " + fruitBox2.weight());
        System.out.println("appleBox1 weight = " + appleBox1.weight());
        System.out.println("appleBox2 weight = " + appleBox2.weight());
        System.out.println("orangeBox1 weight = " + orangeBox1.weight());
        System.out.println();

        System.out.println("тест: сравниваем вес двух коробок");
        System.out.println("fruitBox1.compare(fruitBox2) = " + fruitBox1.compare(fruitBox2));
        System.out.println("fruitBox2.compare(fruitBox1) = " + fruitBox2.compare(fruitBox1));
        System.out.println("appleBox1.compare(fruitBox1) = " + appleBox1.compare(fruitBox1));
        System.out.println("orangeBox1.compare(fruitBox2) = " + orangeBox1.compare(fruitBox2));
    }

    public class Fruit {
        protected int weight; //

        public Fruit(int weight) {
            this.weight = weight;
        }
    }

    public class Apple extends Fruit {
        public Apple(int weight) {
            super(weight);
        }
    }

    public class Orange extends Fruit {
        public Orange(int weight) {
            super(weight);
        }
    }

    public class Box<T extends Fruit> {
        private final List<T> box = new ArrayList<>();

        public int weight() {
            int result = 0;
            for (T item : box) {
                result += item.weight;
            }
            return result;
        }

        public boolean compare(Box<?> another) {
            return this.weight() == another.weight();
        }

        public void add(T fruit) {
            box.add(fruit);
        }

        int size() {
            return box.size();
        }

        public void transferTo(Box<? super T> targetBox) {
            for (T item : box) {
                targetBox.add(item);
            }
            box.clear();
        }
    }
}