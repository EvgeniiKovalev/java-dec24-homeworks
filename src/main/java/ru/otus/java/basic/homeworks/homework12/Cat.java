package ru.otus.java.basic.homeworks.homework12;

public class Cat {
    private final String name;
    private boolean satiety = false;
    private int appetite;

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    public void eat(Plate plate) throws Exception {
// какой из вариантов кормления еды лучше с точки зрения дизайна приложения?
//      вариант кормления 1
//        if (plate.getCurrentVolume() >= appetite) {
//            plate.reduceFood(appetite);
//            satiety = true;
//            appetite = 0;
//        }

//      вариант кормления 2
        if (plate.reduceFood(appetite)) {
            satiety = true;
            appetite = 0;
            System.out.println("кот "+ name + " наелся");
        }

    }

    @Override
    public String toString() {
        return "Cat{ name='" + name + '\'' +", satiety=" + satiety +", appetite=" + appetite +'}';
    }
}
