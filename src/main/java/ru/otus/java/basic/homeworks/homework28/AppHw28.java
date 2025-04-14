package ru.otus.java.basic.homeworks.homework28;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AppHw28 {
    public static void main(String[] args) throws IOException {
        boolean debug = false;
        String filePath;
        String sequence;
        if (debug) {
            filePath = new java.io.File(".").getCanonicalPath() +
                    "\\src\\main\\java\\ru\\otus\\java\\basic\\homeworks\\homework28\\" +
                    "file.txt";
            sequence = "ЯЯЯЯ"; // возможен поиск многострочной последовательности
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
            int count = countSequenceInFile(filePath, sequence, bufferSize, debug);
            System.out.println("Количество повторений строки: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int countSequenceInFile(String filePath, String sequence, int bufferSize, boolean debug)
            throws IOException {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath),
                StandardCharsets.UTF_8)) {
            if (debug) System.out.println("sequence.length = " + sequence.length());

            int count = 0;
            char[] chars = new char[bufferSize];
            int numRead;
            int sequenceLength = sequence.length();
            StringBuilder buffer = new StringBuilder();
            int i = 1;
            int sleepMilliseconds = 0;
            while ((numRead = reader.read(chars)) != -1) {
                if (debug) {
                    System.out.printf("Итерация %d, chars ='%s'\n", i, String.valueOf(chars));
                    Thread.sleep(sleepMilliseconds);
                }
                buffer.append(chars, 0, numRead);
                if (buffer.length() < sequenceLength) {
                    continue;
                }
                if (debug) {
                    Thread.sleep(sleepMilliseconds);
                    System.out.printf("\t\t 1 buffer.length() = %s\n", buffer.length());
                }
                int index;
                while ((index = buffer.indexOf(sequence, 0)) != -1) {
                    buffer.deleteCharAt(index);
                    count++;
                    if (debug) {
                        Thread.sleep(sleepMilliseconds);
                        System.out.printf("\t\t\t 2 buffer.length() = %s, count=%d\n", buffer.length(),
                                count);
                    }
                }
                int countDelete = buffer.length() - sequenceLength;
                if (countDelete >= 0) {
                    buffer.delete(0, countDelete);
                }
                i++;
            }
            return count;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
