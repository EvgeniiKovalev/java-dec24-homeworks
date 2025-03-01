package ru.otus.java.basic.homeworks.homework20.exercisemachine;

public class ExceptionTaskMain {

    public static void main(String[] args) {
        System.out.println("Этот вызов бросает нужное исключение: " + ExceptionTask.invokesException(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException();
        }));
        System.out.println("Этот вызов не бросает исключение: " + ExceptionTask.invokesException(IllegalArgumentException.class, () -> {
            var a = 2 * 2;
        }));
        System.out.println("Этот вызов бросает не то исключение: " + ExceptionTask.invokesException(IllegalArgumentException.class, () -> {
            throw new ArrayIndexOutOfBoundsException();
        }));
    }
}

interface Callable {
    void call();
}

class ExceptionTask {
    private ExceptionTask() {
    }

    /**
     * Задание 1 из 5: Java синтаксис: обработка ошибок - коды возврата, исключения (иерархия), ДЗ (53)
     * Напишите метод, который проверяет было ли выброшено исключение (класс исключения передаётся параметром в метод)
     */
    public static <T extends Throwable> boolean invokesException(Class<T> clazz, Callable callable) {
        try {
            callable.call();
        } catch (Exception e) {
            return e.getClass() == clazz;
        }
        return false;
    }
}