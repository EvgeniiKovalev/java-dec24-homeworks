package ru.otus.java.basic.homeworks.homework13;
/**
 * Домашнее задание
 * Работа с интерфейсами и перечислениями
 * <p>
 * Цель:
 * практика в задачах применения интерфейсов и перечислений.
 * <p>
 * Описание/Пошаговая инструкция выполнения домашнего задания:
 * Создайте класс Человек с полями name (имя) и currentTransport (текущий транспорт)
 * Реализуйте в вашем приложении классы
 *     Машина,
 *     Лошадь,
 *     Велосипед,
 *     Вездеход
 * Каждый из классов должен предоставлять возможность перемещаться на определенное расстояние с указанием типа местности
 * В приложении должны быть типы местности:
 *     густой лес,
 *     равнина,
 *     болото
 * Человек должен иметь возможность сесть на любой из этих видов транспорта, встать с него, или переместиться на некоторое
 * расстояние (при условии что он находится на каком-либо транспорте)
 * При попытке выполнить перемещение у человека, не использующего транспорт, считаем что он просто идет указанное расстояние
 * пешком
 * При перемещении Машина и Вездеход тратят бензин, который у них ограничен.
 * Лошадь тратит силы.
 * Велосипед может использоваться без ограничений (можете для усложнения велосипедом тратить силы “водителя”).
 * При выполнении действия результат должен быть отпечатан в консоль
 * У каждого вида транспорта есть местности по которым он не может перемещаться:
 *     машина - густой лес и болото,
 *     лошадь и велосипед - болото,
 *     вездеход - нет ограничений
 * При попытке переместиться должен быть возвращен результат true/false - удалось ли выполнить действие
 */

public class AppHw13 {
    public static void main(String[] args) {
        Human human = new Human("Петр");
        System.out.println(human);

        System.out.println("ТЕСТ 1: машина едет по равнине и топливо машины частично потрачено");
        human.sitDown(TransportType.CAR);
        boolean resMove = human.move(Area.PLAIN, 10);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);

        System.out.println("ТЕСТ 2: машина не едет, если топлива не хватит на поездку и топливо машины не потрачено");
        resMove = human.move(Area.PLAIN, 10000000);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);

        System.out.println("ТЕСТ 3: человек встает с транспорта");
        human.getUp();
        System.out.println(human);

        System.out.println("ТЕСТ 4: машина не едет по болоту и топливо машины не потрачено");
        human.sitDown(TransportType.CAR);
        resMove = human.move(Area.SWAMP, 10);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);

        System.out.println("ТЕСТ 5: машина не едет по лесу и топливо машины не потрачено");
        resMove = human.move(Area.FOREST, 10);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);

        System.out.println("ТЕСТ 6: человек встает с транспорта");
        human.getUp();
        resMove = human.move(Area.FOREST, 15);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);

        System.out.println("ТЕСТ 7: человек садится на лошадь и едет по лесу, силы лошади уменьшены");
        human.sitDown(TransportType.HORSE);
        resMove = human.move(Area.FOREST, 15);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);

        System.out.println("ТЕСТ 7: лошадь не едет по лесу, если не хватает сил для поездки на указанное расстояние");
        resMove = human.move(Area.FOREST, 1500000000);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);

        System.out.println("ТЕСТ 8: человек садится на велосипед и едет по лесу, силы человека уменьшены");
        human.getUp();
        human.sitDown(TransportType.BICYCLE);
        resMove = human.move(Area.FOREST, 15);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);

        System.out.println("ТЕСТ 9: велосипед не едет по болоту");
        resMove = human.move(Area.SWAMP, 22);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);

        System.out.println("ТЕСТ 10: человек садится на вездеход и едет по болоту, топливо вездехода уменьшено");
        human.getUp();
        human.sitDown(TransportType.OFFROADER);
        resMove = human.move(Area.SWAMP, 10);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);

        System.out.println("ТЕСТ 11: вездеход и едет по равнине, топливо вездехода уменьшено");
        resMove = human.move(Area.PLAIN, 10);
        System.out.println("результат human.move " + resMove);
        System.out.println(human);
        human.getUp();
    }
}
