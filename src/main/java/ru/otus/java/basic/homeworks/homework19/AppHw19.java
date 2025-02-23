package ru.otus.java.basic.homeworks.homework19;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <pre>
 * Домашнее задание
 * Работа с чтением/записью файлов
 *
 * Цель:
 * научиться работать с файлами в Java.
 *
 *
 * Описание/Пошаговая инструкция выполнения домашнего задания:
 * Реализуйте приложение, позволяющее работать с текстовыми файлами
 * При старте приложения, в консоль выводится список текстовых файлов из корневого каталоге проекта
 * Далее программа запрашивает имя файла, с которым хочет работать пользователь
 * Содержимое файла выводится в консоль
 * Затем любую введенную пользователем строку необходимо записывать в указанный файл
 * </pre>
 **/

public class AppHw19 {
    /**
     * выводит файл в консоль
     * буферизация чтения файла в InputStreamReader, чтение из буфера частями равными размеру буфера
     *
     * @param filename имя файла
     * @param encoding кодировка файла
     */
    public static void printFileInputStream(String filename, Charset encoding) {
        System.out.printf("Содержимое файла '%s':\n", filename);
        try (FileInputStream fis = new FileInputStream(filename);
             InputStreamReader isr = new InputStreamReader(fis, encoding)) {
            char[] buffer = new char[10];
            int charsRead;
            int i = 0;

            while ((charsRead = isr.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, charsRead));
                i++;
            }
            System.out.println("\n Количество итераций чтения = " + i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * выводит содержимое файла в консоль
     * буферизация чтения файла с диска в BufferedInputStream, чтение всего из буфера, не учитывает кодировку файла
     *
     * @param filename имя файла
     */
    public static void printFileBufferedInputStream(String filename) {
        int bufSize = 20;
        System.out.printf("Не поддерживается выбор кодировки, предполагается что файл в кодировке ISO-8859-1, содержимое файла '%s':\n", filename);
        try (FileInputStream fis = new FileInputStream(filename);
             BufferedInputStream bis = new BufferedInputStream(fis, bufSize)
        ) {
            int i = 0;
            while (bis.available() > 0) {
                byte[] bytes = bis.readNBytes(bufSize);
                System.out.print(new String(bytes));
                i++;
            }
            System.out.println("\n Количество итераций чтения = " + i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * вывод содержимого файла в консоль
     * буферизация чтения файла в BufferedInputStream, чтение всего из буфера, читает файл в указанной кодировке
     *
     * @param filename имя файла
     * @param encoding кодировка файла
     */
    public static void printFileBufferedInputStream(String filename, Charset encoding) {
        int bufSize = 20;
        System.out.printf("Содержимое файла '%s':\n", filename);
        try (FileInputStream fis = new FileInputStream(filename);
             BufferedInputStream bis = new BufferedInputStream(fis, bufSize);
             InputStreamReader isr = new InputStreamReader(bis, encoding)
        ) {
            int i = 0;
            int charsRead;
            char[] buffer = new char[bufSize];
            while ((charsRead = isr.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, charsRead));
                i++;
            }
            System.out.println("\n Количество итераций чтения = " + i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * вывод содержимого файла в консоль
     * буферизация на уровне DataInputStream
     *
     * @param filename имя файла
     */
    public static void printFileDataInputStream(String filename) {
        System.out.printf("Не поддерживается выбор кодировки, предполагается что файл в кодировке ISO-8859-1, содержимое файла '%s':\n", filename);
        try (FileInputStream fis = new FileInputStream(filename);
             DataInputStream dis = new DataInputStream(fis)) {
            int i = 0;
            while (dis.available() > 0) {
                //System.out.println(dis.readLine()); //построчное чтение файла тоже работает
                byte[] buf = dis.readNBytes(10);  //чтение по 10 байт
                System.out.print(new String(buf));
                i++;
            }
            System.out.println("\n Количество итераций чтения = " + i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * вывод содержимого файла в консоль
     * буферизация на уровне ByteArrayInputStream
     *
     * @param filename имя файла
     */
    public static void printFileByteArrayInputStream(String filename) {
        System.out.printf("Не поддерживается выбор кодировки, предполагается что файл в кодировке ISO-8859-1, содержимое файла '%s':\n", filename);
        try (FileInputStream fis = new FileInputStream(filename);
             ByteArrayInputStream bis = new ByteArrayInputStream(fis.readAllBytes())
        ) {
            int i = 0;
            byte[] buf = new byte[20];
            int charsRead;

            while (bis.available() > 0) {
                while ((charsRead = bis.read(buf)) != -1) {
                    System.out.print(new String(buf, 0, charsRead));
                    i++;
                }
            }
            System.out.println("\n Количество итераций чтения = " + i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * выводит содержимое файла в консоль
     * буферизация на уровне CharArrayReader
     *
     * @param filename имя файла
     * @param encoding кодировка файла
     */
    public static void printFileCharArrayReader(String filename, Charset encoding) {
        try (FileInputStream fis = new FileInputStream(filename);
             InputStreamReader isr = new InputStreamReader(fis, encoding)) {

            char[] buffer = new char[10];
            int charsRead;
            int i = 0;

            while ((charsRead = isr.read(buffer)) != -1) {
                CharArrayReader car = new CharArrayReader(buffer, 0, charsRead);
                char[] buf = new char[charsRead];
                if (car.read(buf, 0, charsRead) > 0) {
                    System.out.print(new String(buf));
                }
                i++;
            }
            System.out.println("\n Количество итераций чтения = " + i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SaveFileOutputStream(String filename, String inputString, Charset encoding, Boolean append) {
        try (FileOutputStream fos = new FileOutputStream(filename, append);
             OutputStreamWriter osw = new OutputStreamWriter(fos, String.valueOf(encoding))) {
            osw.append("\r\n").append(inputString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SaveFileBufferedOutputStream(String filename, String inputString, Charset encoding, int bufferSize, Boolean append) {
        try (FileOutputStream fos = new FileOutputStream(filename, append);
             BufferedOutputStream bos = new BufferedOutputStream(fos, bufferSize);
             OutputStreamWriter osw = new OutputStreamWriter(bos, String.valueOf(encoding))) {
            osw.append("\r\n").append(inputString);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFileDataOutputStream(String filename, String inputString, Boolean append) {
        try (FileOutputStream fos = new FileOutputStream(filename, append);
             DataOutputStream dos = new DataOutputStream(fos)) {
            dos.writeBytes("\r\n" + inputString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFileByteArrayOutputStream(String filename, String inputString, boolean append) {
        try (FileOutputStream fos = new FileOutputStream(filename, append);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write(("\r\n" + inputString).getBytes());
            fos.write(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFileCharArrayWriter(String filename, String inputString, boolean append) {
        try (FileOutputStream fos = new FileOutputStream(filename, append);
             CharArrayWriter caw = new CharArrayWriter()) {
            caw.write("\r\n" + inputString);
            fos.write(caw.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //список тестируемых классов
        List<ThemeForStudy> list = new ArrayList<>();
        list.add(ThemeForStudy.FILEINPUTSTREAM);
        list.add(ThemeForStudy.BUFFEREDINPUTSTREAM);
        list.add(ThemeForStudy.DATAINPUTSTREAM);
        list.add(ThemeForStudy.BYTEARRAYINPUTSTREAM);
        list.add(ThemeForStudy.CHARARRAYREADER);

        //список файлов корневой папке проекта
        Charset encoding = StandardCharsets.UTF_8;
        System.out.println(encoding);

        File folder = new File((new File("")).getAbsolutePath());
        String extension = ".txt";
        List<String> listFiles = Arrays.stream(Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(extension)))).filter(File::isFile).map(File::getAbsolutePath)
                //.forEach(System.out::println);
                .collect(Collectors.toList());
        System.out.printf("Список файлов с расширением '%s' в корневой папке проекта '%s':\n", extension, folder);
        listFiles.forEach(System.out::println);
        System.out.println();


        //спрашиваем имя файла
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String filename;
        System.out.print("Введите имя файла:");
        try {
            filename = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();


        //выводим содержимое файла в консоль
        if (list.contains(ThemeForStudy.FILEINPUTSTREAM)) {
            System.out.println("Тест FILEINPUTSTREAM");
            printFileInputStream(filename, encoding);
            System.out.println();
        }
        if (list.contains(ThemeForStudy.BUFFEREDINPUTSTREAM)) {
            System.out.println("Тест BUFFEREDINPUTSTREAM");
            printFileBufferedInputStream(filename, encoding);
            //printFileBufferedInputStream(filename);
            System.out.println();
        }
        if (list.contains(ThemeForStudy.DATAINPUTSTREAM)) {
            System.out.println("Тест DATAINPUTSTREAM");
            printFileDataInputStream(filename);
            System.out.println();
        }
        if (list.contains(ThemeForStudy.BYTEARRAYINPUTSTREAM)) {
            System.out.println("Тест BYTEARRAYINPUTSTREAM");
            //printFileByteArrayInputStream(filename, encoding);
            printFileByteArrayInputStream(filename);
            System.out.println();
        }

        if (list.contains(ThemeForStudy.CHARARRAYREADER)) {
            System.out.println("Тест CHARARRAYREADER");
            printFileCharArrayReader(filename, encoding);
            System.out.println();
        }

        //спрашиваем строку текста для записи в файл
        System.out.printf("Введите одну строку для записи в конец файла %s:", filename);
        String inputString;
        try {
            inputString = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //записываем строку в файл
        if (list.contains(ThemeForStudy.FILEINPUTSTREAM)) {
            SaveFileOutputStream(filename, inputString, encoding, true);
        }
        if (list.contains(ThemeForStudy.BUFFEREDINPUTSTREAM)) {
            SaveFileBufferedOutputStream(filename, inputString, encoding, inputString.length(), true);
        }
        if (list.contains(ThemeForStudy.DATAINPUTSTREAM)) {
            saveFileDataOutputStream(filename, inputString, true);
        }
        if (list.contains(ThemeForStudy.BYTEARRAYINPUTSTREAM)) {
            saveFileByteArrayOutputStream(filename, inputString, true);
        }
        if (list.contains(ThemeForStudy.CHARARRAYREADER)) {
            saveFileCharArrayWriter(filename, inputString, true);
        }
    }

}
