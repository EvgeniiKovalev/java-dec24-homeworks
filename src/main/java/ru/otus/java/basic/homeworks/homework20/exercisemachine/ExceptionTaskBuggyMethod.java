package ru.otus.java.basic.homeworks.homework20.exercisemachine;

public class ExceptionTaskBuggyMethod {
    private ExceptionTaskBuggyMethod(){}

    /**
     * Задание 3 из 5: Java синтаксис: обработка ошибок - коды возврата, исключения (иерархия), ДЗ (55)
     * Дан метод, который содержит скрытые ошибки в вычислениях.
     * Необходимо доработать метод так, чтобы при возникновении ошибок он продолжал работу,
     * а результат вычисления итерации, на которой была получена ошибка, был бы равен 0.
     */
    public static int buggyMethod(Integer[] inputNumbers) {
        if (inputNumbers == null) return 0;
        int result = 0;
        for (var item: inputNumbers) {
            try {
                if (item >= Integer.MAX_VALUE) {
                    //throw new ArithmeticException();
                    result += 0;
                }
                result += (item  + 1) / item;
            } catch (ArithmeticException e) {
                result += 0;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(buggyMethod(null));
        System.out.println(buggyMethod(new Integer[]{1, 0}));
    }
}