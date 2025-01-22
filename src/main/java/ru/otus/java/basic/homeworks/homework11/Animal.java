package ru.otus.java.basic.homeworks.homework11;

public abstract class Animal {
    private final String name;
    private final int runSpeed;
    private int stamina;
    private final int staminaForRun = 1;

    public String getName() {
        return this.name;
    }

    public int getRunSpeed() {
        return this.runSpeed;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina -= stamina;
    }

    public int getStaminaForRun() {
        return staminaForRun;
    }

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
        System.out.printf("состояние '%s' %d\n", name, stamina);
    }

    public Animal(String name, int runSpeed, int stamina) {
        this.name = name;
        this.runSpeed = runSpeed;
        this.stamina = stamina;
    }
}
