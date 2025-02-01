package ru.otus.java.basic.homeworks.homework16;

public class Employee {
    private final String name;
    private final int age;

    public Employee(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("Employee{name='%s', age=%d}", name, age);
    }
}
