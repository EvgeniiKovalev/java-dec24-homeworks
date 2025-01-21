package ru.otus.java.basic.homeworks.homework11;

public abstract class Animal {
    protected String name;
    protected int runSpeed;
    protected int stamina;
    protected final int staminaForRun = 1;

    protected int run(int distance) {
        System.out.println("'" + name + "' бежит дистанцию " + distance);
        if (stamina - staminaForRun*distance < 0) {
            System.out.println("у '" + name + "' появилось состояние усталости");
            return -1;
        }
        stamina -= staminaForRun*distance;
        return Math.round(((float)distance)/runSpeed);
    }

    protected void info() {
        System.out.println("состояние '" + name + "' " + stamina);
    }

    public Animal(String name, int runSpeed, int stamina) {
        this.name = name;
        this.runSpeed = runSpeed;
        this.stamina = stamina;
    }
}
