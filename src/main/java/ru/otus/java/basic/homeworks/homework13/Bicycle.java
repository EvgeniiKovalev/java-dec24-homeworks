package ru.otus.java.basic.homeworks.homework13;

/**
 * Велосипед тратит для поездки силы человека, не может перемещаться болоту
 */
public class Bicycle extends Transport {
    public Bicycle() {
        super(Area.SWAMP);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Bicycle other = (Bicycle) obj;
        return other.driver == driver;
    }

    @Override
    public String toString() {
        if (driver != null) {
            return String.format("Bicycle{driver=%s, rejectedArea=%s}", driver.getName(), listRejectedArea());
        }
        return String.format("Bicycle{driver=%s, rejectedArea=%s}", "NULL", listRejectedArea());
    }

    @Override
    public boolean move(Area area, int distance) {
        if (isRejectedAreaType(area)) {
            System.out.printf("Велосипед не ездит по %s\n", area);
            return false;
        }
        if (driver.getPower() < (float) distance * driver.getPowerConsumption() / 100) {
            System.out.printf("У человека не хватает сил чтобы проехать на велосипеде расстояние  %d\n", distance);
            return false;
        }
        driver.setPower((float) distance * driver.getPowerConsumption() / 100);
        System.out.printf("Прогулка на велосипеде по %s на расстояние %d\n", area, distance);
        return true;
    }
}
