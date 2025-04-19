package ru.otus.java.basic.homeworks.homework28;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *         Домашнее задание
 *         Работа с чтением/записью файлов
 *         Цель:
 *         улучшить навыки работы с потоками ввода/вывода.
 *         Описание/Пошаговая инструкция выполнения домашнего задания:
 *         Реализуйте метод, который подсчитывает сколько раз в текстовом файле (с кодировкой UTF-8)
 *         встречается указанная последовательность символов с учетом регистра;
 *         При запуске приложения пользователь вводит имя файла и искомую последовательность символов,
 *         программа должна выполнить расчет и напечатать результат в консоль.
 */
public class AppHw28 {
    public static void main(String[] args) throws IOException {
        String filePath;
        String sequence;
        if (true) {
            Path path = Paths.get("src/main/java/ru/otus/java/basic/homeworks/homework28/file.txt");
            filePath = path.toAbsolutePath().toString();
            sequence = "Я \r\nЯ";
        } else {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Введите имя файла: ");
            String filename = scanner.nextLine();
            filePath = new java.io.File(".").getCanonicalPath() +
                    "\\src\\main\\java\\ru\\otus\\java\\basic\\homeworks\\homework28\\" +
                    filename;

            System.out.print("Введите строку для поиска в файле: ");
            sequence = scanner.nextLine();
        }

        int bufferSize = sequence.length();
        try {
            int count = countSequenceInFile(filePath, sequence, bufferSize);
            System.out.println("Количество повторений строки: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int countSequenceInFile(String filePath, String sequence, int bufferSize)
            throws IOException {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath),
                StandardCharsets.UTF_8)) {
            int count = 0;
            int numRead;
            char[] chars = new char[bufferSize];
            int sequenceLength = sequence.length();
            StringBuilder buffer = new StringBuilder();
            while ((numRead = reader.read(chars)) != -1) {
                buffer.append(chars, 0, numRead);
                if (buffer.length() < sequenceLength) {
                    continue;
                }
                int index;
                while ((index = buffer.indexOf(sequence, 0)) != -1) {
                    buffer.delete(0, index + 1);
                    count++;
                }
                int countDelete = buffer.length() - sequenceLength;
                if (countDelete >= 0) {
                    buffer.delete(0, countDelete);
                }
            }
            return count;
        }
    }
}
