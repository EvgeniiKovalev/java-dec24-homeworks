package ru.otus.java.basic.homeworks.homework10;

/*
Создайте класс Пользователь (User) с полями: фамилия, имя, отчество, год рождения, email;
Реализуйте у класса конструктор, позволяющий заполнять эти поля при создании объекта;
В классе Пользователь реализуйте метод, выводящий в консоль информацию о пользователе в виде:
ФИО: фамилия имя отчество
Год рождения: год рождения
e-mail: email
В методе main() Main класса создайте массив из 10 пользователей и заполните его объектами и с
помощью цикла выведите информацию только о пользователях старше 40 лет.
 */
public class User {
    private int yearOfBirth;
    private String surname;
    private String name;
    private String middleName;
    private String email;

    public User(String surname, String name, String middleName, int yearOfBirth, String email) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.yearOfBirth = yearOfBirth;
        this.email = email;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    @Override
    public String toString() {
        return "ФИО: " + surname + " " + name + " " + middleName + "\nГод рождения: " + yearOfBirth + "\ne-mail: " + email;
    }

}

