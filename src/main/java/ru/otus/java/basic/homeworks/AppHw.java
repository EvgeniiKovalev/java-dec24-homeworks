package ru.otus.java.basic.homeworks;

public class AppHw {
    public static void main(String[] args) {
        System.out.println("AppHw");

        String str = "122";
        String[] arr;
        arr = new String[3];
        arr = str.split(" ", 2);

        for (int i = 0; i < arr.length; i++) {
            System.out.println(" i = " + i + " : " + arr[i]);
        }
        System.out.println(arr[1]);

    }
}
