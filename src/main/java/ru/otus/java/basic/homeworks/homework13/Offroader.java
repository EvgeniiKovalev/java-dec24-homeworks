package ru.otus.java.basic.homeworks.homework13;

public class Offroader extends Transport {
    private final int fuelConsumption = 4;
    private float fuel = 100.0f;

    @Override
    public String toString() {
        return String.format("Offroader{fuelConsumption=%d, fuel=%.2f}", fuelConsumption, fuel);
    }

    @Override
    public boolean move(Area area, int distance) {
        if (fuel < (float) distance * fuelConsumption / 100) {
            System.out.println("У вездехода не хватает топлива для поездки на расстояние " + distance);
            return false;
        }
        fuel -= (float) distance * fuelConsumption / 100;
        System.out.printf("Поездка на вездеходе по %s на расстояние %d\n", area, distance);
        return true;
    }

}
