package ru.otus.java.basic.homeworks.homework13;

/**
 * Лошадь тратит силу из расчета на 100 км, не может перемещаться по болоту
 * power - сила
 * powerConsumption - расход силы в шт. на 100 км
 */
public class Horse extends Transport {
    private final int powerConsumption = 10;
    private float power = 50f;

    public Horse() {
        super(Area.SWAMP);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Horse other = (Horse) obj;
        return other.power == power && other.driver == driver;
    }

    @Override
    public String toString() {
        if (driver != null) {
            return String.format("Horse{driver=%s, rejectedArea=%s, powerConsumption=%d, power=%.2f}", driver.getName(), listRejectedArea(), powerConsumption, power);
        }
        return String.format("Horse{driver=%s, rejectedArea=%s, powerConsumption=%d, power=%.2f}", "NULL", listRejectedArea(), powerConsumption, power);
    }

    @Override
    public boolean move(Area area, int distance) {
        if (isRejectedAreaType(area)) {
            System.out.println("Лошадь не ездит по " + area);
            return false;
        }
        if (power < (float) distance * powerConsumption / 100) {
            System.out.println("У лошади не хватает сил для поездки на расстояние " + distance);
            return false;
        }
        power -= (float) distance * powerConsumption / 100;
        System.out.printf("Поездка на лошади по %s на расстояние %d\n", area, distance);
        return true;
    }
}
