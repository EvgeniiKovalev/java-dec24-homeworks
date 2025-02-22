package ru.otus.java.basic.homeworks.homework12;

public class Cat {
    private final String name;
    private boolean satiety = false;
    private int appetite;

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    public void eat(Plate plate) throws IllegalArgumentException {
        if (plate.reduceFood(appetite)) {
            satiety = true;
            appetite = 0;
            System.out.println("кот " + name + " наелся");
        }

    }

    @Override
    public String toString() {
        return String.format("Cat{name='%s', satiety=%b, appetite=%d}", name, satiety, appetite);
    }
}
