package ru.otus.java.basic.homeworks.homework13;


/**
 * Человек тратит силу только при перемещении пешком или на велосипеде из расчета на 100 км
 * power - сила
 * powerConsumption - расход силы в шт. на 100 км
 * <p>
 * Уместно ли делать implements Moveable для класса Human, без неё тоже все работает ? Если делать то для чего ?
 */
public class Human {
    private final int powerConsumption = 20;
    private final TransportFactory transportFactory;
    private final String name;
    private float power = 20f;
    private Transport currentTransport;

    public Human(String name) {
        this.name = name;
        this.transportFactory = new TransportFactory();
    }

    /**
     * Какой из 3-х вариантов реализации оптимальнее ?
     * Подумал что 3-й, т.к. в нем минимальное количество преобразований
     *
     */
    @Override
    public String toString() {
//вариант 1
//        StringBuilder message = new StringBuilder();
//        if (currentTransport == null) {
//            message.append("NULL");
//        } else {
//            message.append(currentTransport);
//        }
//        return String.format("Human{name=%s, powerConsumption=%d, power=%.2f, currentTransport=%s}\n", name, powerConsumption, power, message);
//
//вариант 2
//        StringBuilder message = new StringBuilder();
//        message.append(String.format("Human{name=%s, powerConsumption=%d, power=%.2f", name, powerConsumption, power));
//        message.append(String.format(", currentTransport=%s}", Objects.requireNonNullElse(currentTransport, "NULL")));
//        return message.toString();

//вариант 3
        String curTransport = currentTransport == null ? "NULL" : currentTransport.toString();
        return String.format("Human{name=%s, powerConsumption=%d, power=%.2f, currentTransport=%s}\n", name, powerConsumption, power, curTransport);
    }

    public boolean sitDown(TransportType transportType) {
        if (this.currentTransport == null) {
            currentTransport = transportFactory.createTransport(transportType);
            return true;
        } else {
            return false;
        }
    }

    public boolean getUp() {
        if (currentTransport != null) {
            currentTransport = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Усложнил задачу относительно дз, чтобы если сил/топлива не хватало на указанное расстояние, то чтобы передвижение не выполнялось.
     * Так как велосипед тратит силы ездока, то чтобы реализовать вышесказанное
     * велосипед должен знать и учитывать силы ездока, это нарушение инкапсуляции ?
     * <p>
     * Можно ли как то обойтись без currentTransport instanceof Bicycle ?
     * <p>
     * Можно сделать поле в классе Human currentTransportType типа TransportType и при изменении currentTransport
     * запоминать в нем текущий тип транспорта при посадки и вставании с транспорта, это не лучше ?
     *
     * @param area     тип местности по которой нужно выполнить передвижение
     * @param distance расстояние на которое нужно выполнить передвижение
     * @return true если передвижение состоялось, false - в противном случае
     */
    public boolean move(Area area, int distance) {
        if (currentTransport instanceof Bicycle) {
            if (power < (float) distance * powerConsumption / 100) {
                System.out.printf("У человека не хватает сил чтобы проехать на велосипеде расстояние  %d\n", distance);
                return false;
            }
            if (currentTransport.move(area, distance)) {
                power -= (float) distance * powerConsumption / 100;
                return true;
            }
            return false;
        } else if (currentTransport == null) {
            if (power < (float) distance * powerConsumption / 100) {
                System.out.printf("У человека не хватает сил чтобы пройти пешком расстояние %d\n", distance);
                return false;
            }
            power -= (float) distance * powerConsumption / 100;
            System.out.printf("Пешая прогулка по %s на расстояние %d\n", area, distance);
            return true;

        } else {
            return currentTransport.move(area, distance);
        }
    }
}
