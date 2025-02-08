package ru.otus.java.basic.homeworks.homework13;

/**
 * Вездеход тратит топливо из расчета на 100 км, может перемещаться везде
 * fuel - топливо
 * fuelConsumption - расход топлива в литрах на 100 км
 */
public class Offroader extends Transport {
    private final int fuelConsumption = 4;
    private float fuel = 100.0f;

    public Offroader() {
        super((Area) null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Offroader other = (Offroader) obj;
        return other.fuel == fuel && other.driver == driver;
    }

    @Override
    public String toString() {
        if (driver != null) {
            return String.format("Offroader{driver=%s, rejectedArea=%s, fuelConsumption=%d, fuel=%.2f}", driver.getName(), listRejectedArea(), fuelConsumption, fuel);
        }
        return String.format("Offroader{driver=%s, rejectedArea=%s, fuelConsumption=%d, fuel=%.2f}", "NULL", listRejectedArea(), fuelConsumption, fuel);
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
