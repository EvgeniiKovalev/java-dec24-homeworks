package ru.otus.java.basic.homeworks.homework13;

/**
 * Велосипед тратит для поездки силы человека, не может перемещаться болоту
 */
public class Bicycle extends Transport {
    @Override
    public String toString() {
        return "Bicycle{}";
    }

    @Override
    public boolean move(Area area, int distance) {
        if (area == Area.SWAMP) {
            System.out.printf("Велосипед не ездит по %s\n", area);
            return false;
        }
        System.out.printf("Прогулка на велосипеде по %s на расстояние %d\n", area, distance);
        return true;
    }
}
