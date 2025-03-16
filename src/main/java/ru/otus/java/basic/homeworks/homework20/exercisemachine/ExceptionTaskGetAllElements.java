package ru.otus.java.basic.homeworks.homework20.exercisemachine;

import java.util.ArrayList;
import java.util.Arrays;

public class ExceptionTaskGetAllElements {
    private static String processString(String item) {
        if(item.contains("Ю")) {
            throw new IllegalArgumentException("Обнаружена буква Ю!");
        }
        if(item.contains("Ё")) {
            throw new NullPointerException("Ё не разрешено!");
        }
        return item;
    }

    /**
     * Задание 5 из 5: Java синтаксис: обработка ошибок - коды возврата, исключения (иерархия), ДЗ (57)
     *Доработайте метод getAllElements так, чтобы в случае возникновения ошибки он
     * в качестве результата записывал в список сообщение выброшенного исключения,
     * а также после каждой итерации в любом случае добавлял бы строку ""end""
     */
    public static String[] getAllElements(String[] input) {
        var result = new ArrayList<String>(input.length);
        StringBuilder  stringBuilder = new StringBuilder();
        for (String item : input) {
            try {
                stringBuilder.setLength(0);
                stringBuilder.append(processString(item));
            } catch (IllegalArgumentException|NullPointerException e){
                stringBuilder.append(e.getMessage());
            }
            finally {
                stringBuilder.append("\"\"end\"\"");
            }
            result.add(stringBuilder.toString());
        }
        return result.toArray(new String[0]);
    }


    public static void main(String[] args) {
        System.out.println(Arrays.toString(getAllElements(new String[]{"Ю", "б", "в", "Ю", "Д", "ЁЖ"})));
    }
}