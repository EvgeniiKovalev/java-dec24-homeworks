package ru.otus.java.basic.homeworks.homework13;

/**
 * Машина тратит топливо из расчета на 100 км, не может перемещаться по лесу и болоту
 * fuel - топливо
 * fuelConsumption - расход топлива в литрах на 100 км
 */
public class Car extends Transport {
    private final int fuelConsumption = 1;
    private float fuel = 100.0f;

    @Override
    public String toString() {
        return String.format("Car{fuelConsumption=%d, fuel=%.2f}", fuelConsumption, fuel);
    }

    @Override
    public boolean move(Area area, int distance) {
        if (area == Area.FOREST || area == Area.SWAMP) {
            System.out.println("Машина не ездит по " + area);
            return false;
        } else if (fuel < (float) distance * fuelConsumption / 100) {
            System.out.println("У машины не хватает топлива для поездки на расстояние " + distance);
            return false;
        }
        fuel -= (float) distance * fuelConsumption / 100;
        System.out.printf("Поездка на машине по %s на расстояние %d\n", area, distance);
        return true;
    }
}
