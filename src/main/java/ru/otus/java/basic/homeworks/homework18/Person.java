package ru.otus.java.basic.homeworks.homework18;

public class Person {
    private String name;
    private Position position;
    private Long id;

    public Person(String name, Position position, Long id) {
        this.name = name;
        this.position = position;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Person{ name=%s, position=%s, id=%d }", name, position, id);
    }

    public Position getPosition() {
        return position;
    }

    public Long getId() {
        return id;
    }
}