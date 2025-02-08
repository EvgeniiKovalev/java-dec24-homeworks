package ru.otus.java.basic.homeworks.homework13;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Transport {
    protected final ArrayList<Area> rejectedArea;
    protected Human driver;

    public Transport(Area...array) {
        rejectedArea = new ArrayList<>();
        rejectedArea.addAll(Arrays.asList(array));
    }

    boolean isRejectedAreaType(Area area) {
        return rejectedArea.contains(area);
    }

    public String listRejectedArea() {
        return rejectedArea.toString();
    }

    public void setDriver(Human driver) {
        this.driver = driver;
    }

    public abstract boolean move(Area area, int distance);
}
