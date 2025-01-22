package ru.otus.java.basic.homeworks.homework11;

public class Horse extends Animal implements Swimable{
    private final int swimSpeed;
    private final int staminaForSwim;

    public Horse(String name, int runSpeed, int swimSpeed, int stamina) {
        super(name, runSpeed, stamina);
        this.swimSpeed = swimSpeed;
        this.staminaForSwim = 4;
    }

    @Override
    public int swim(int distance) {
        System.out.println("'" + getName() + "' плывет  дистанцию " + distance);
        if (getStamina() - staminaForSwim*distance < 0) {
            System.out.println("у '" + getName() + "' появилось состояние усталости");
            return -1;
        }
        setStamina(staminaForSwim*distance);
        return Math.round(((float)distance)/swimSpeed);
    }

    @Override
    public String toString() {
        return  String.format("Horse{swimSpeed=%d, staminaForSwim=%d, name='%s', runSpeed=%d, stamina=%d, staminaForRun=%d}",
                swimSpeed, staminaForSwim, getName(), getRunSpeed(), getStamina(), getStaminaForRun());
    }
}
