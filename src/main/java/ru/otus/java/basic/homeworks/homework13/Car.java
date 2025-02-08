package ru.otus.java.basic.homeworks.homework13;

/**
 * Машина тратит топливо из расчета на 100 км, не может перемещаться по лесу и болоту
 * fuel - топливо
 * fuelConsumption - расход топлива в литрах на 100 км
 */
public class Car extends Transport {
    private final int fuelConsumption = 1;
    private float fuel = 100.0f;

    public Car() {
        super(Area.SWAMP, Area.FOREST);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Car other = (Car) obj;
        return other.fuel == fuel && other.driver == driver;
    }

    @Override
    public String toString() {
        if (driver != null) {
            return String.format("Car{driver=%s, rejectedArea=%s, fuelConsumption=%d, fuel=%.2f}", driver.getName(), listRejectedArea(), fuelConsumption, fuel);
        }
        return String.format("Car{driver=%s, rejectedArea=%s, fuelConsumption=%d, fuel=%.2f}", "NULL", listRejectedArea(), fuelConsumption, fuel);
    }

    @Override
    public boolean move(Area area, int distance) {
        if (isRejectedAreaType(area)) {
            System.out.println("Машина не ездит по " + area);
            return false;
        }
        if (fuel < (float) distance * fuelConsumption / 100) {
            System.out.println("У машины не хватает топлива для поездки на расстояние " + distance);
            return false;
        }
        fuel -= (float) distance * fuelConsumption / 100;
        System.out.printf("Поездка на машине по %s на расстояние %d\n", area, distance);
        return true;
    }
}
