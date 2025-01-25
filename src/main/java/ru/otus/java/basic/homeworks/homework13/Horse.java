package ru.otus.java.basic.homeworks.homework13;

/**
 * Лошадь тратит силу из расчета на 100 км, не может перемещаться по болоту
 * power - сила
 * powerConsumption - расход силы в шт. на 100 км
 */
public class Horse extends Transport {
    private final int powerConsumption = 10;
    private float power = 50f;

    @Override
    public String toString() {
        return String.format("Horse{powerConsumption=%d, power=%.2f}", powerConsumption, power);
    }

    @Override
    public boolean move(Area area, int distance) {
        if (area == Area.SWAMP) {
            System.out.println("Лошадь не ездит по " + area);
            return false;
        } else if (power < (float) distance * powerConsumption / 100) {
            System.out.println("У лошади не хватает сил для поездки на расстояние " + distance);
            return false;
        }
        power -= (float) distance * powerConsumption / 100;
        System.out.printf("Поездка на лошади по %s на расстояние %d\n", area, distance);
        return true;
    }
}
