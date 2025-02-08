package ru.otus.java.basic.homeworks.homework13;

/**
 * Человек тратит силу только при перемещении пешком или на велосипеде из расчета на 100 км
 * power - сила
 * powerConsumption - расход силы в шт. на 100 км
 */
public class Human {
    private final int powerConsumption = 20;
    private final String name;
    private float power = 20f;
    private Transport currentTransport;

    String getName() {
        return name;
    }

    public Human(String name) {
        this.name = name;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power -= power;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    @Override
    public String toString() {
        return String.format("Human{name=%s, powerConsumption=%d, power=%.2f, currentTransport=%s}\n", name, powerConsumption, power, currentTransport);

    }

    public boolean sitDown(Transport transport) {
        if (currentTransport != null || transport.driver != null) {
            return false;
        }
        currentTransport = transport;
        transport.setDriver(this);
        return true;
    }

    public boolean getUp() {
        if (currentTransport != null) {
            currentTransport.setDriver(null);
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

        if (currentTransport != null) {
            return currentTransport.move(area, distance);
        }

        if (power < (float) distance * powerConsumption / 100) {
            System.out.printf("У человека не хватает сил чтобы пройти пешком расстояние %d\n", distance);
            return false;
        }
        power -= (float) distance * powerConsumption / 100;
        System.out.printf("Пешая прогулка по %s на расстояние %d\n", area, distance);
        return true;
    }
}
