package ru.otus.java.basic.homeworks.homework11;

public class Dog extends Animal implements  Swimable{
    private final int swimSpeed;
    private final int staminaForSwim;

    public Dog(String name, int runSpeed, int swimSpeed, int stamina) {
        super(name, runSpeed, stamina);
        this.swimSpeed = swimSpeed;
        this.staminaForSwim = 2;
    }

    @Override
    public int swim(int distance) {
        System.out.println("'" + name + "' плывет  дистанцию " + distance);
        if (stamina - staminaForSwim*distance < 0) {
            System.out.println("у '" + name + "' появилось состояние усталости");
            return -1;
        }
        stamina -= staminaForSwim*distance;
        return Math.round(((float)distance)/swimSpeed);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "swimSpeed=" + swimSpeed +
                ", staminaForSwim=" + staminaForSwim +
                ", name='" + name + '\'' +
                ", runSpeed=" + runSpeed +
                ", stamina=" + stamina +
                ", staminaForRun=" + staminaForRun +
                '}';
    }
}
