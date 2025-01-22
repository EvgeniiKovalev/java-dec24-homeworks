package ru.otus.java.basic.homeworks.homework11;

public class Cat extends Animal {

    public Cat(String name, int runSpeed, int stamina) {
        super(name, runSpeed, stamina);
    }

    @Override
    public String toString() {
        return String.format("Cat{name='%s', runSpeed=%d, stamina=%d, staminaForRun=%d}",
                getName(), getRunSpeed(), getStamina(), getStaminaForRun());
    }
}
