package ru.otus.java.basic.homeworks.homework11;

public class Cat extends Animal {

    public Cat(String name, int runSpeed, int stamina) {
        super(name, runSpeed, stamina);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", runSpeed=" + runSpeed +
                ", stamina=" + stamina +
                ", staminaForRun=" + staminaForRun +
                '}';
    }
}
