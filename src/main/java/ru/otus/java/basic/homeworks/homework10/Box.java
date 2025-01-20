package ru.otus.java.basic.homeworks.homework10;
/*
Попробуйте реализовать класс по его описания: объекты класса Коробка должны иметь размеры и цвет.
Коробку можно открывать и закрывать. Коробку можно перекрашивать. Изменить размер коробки после создания нельзя.
У коробки должен быть метод, печатающий информацию о ней в консоль.
В коробку можно складывать предмет (если в ней нет предмета), или выкидывать его оттуда
(только если предмет в ней есть), только при условии что коробка открыта (предметом читаем просто строку).
Выполнение методов должно сопровождаться выводом сообщений в консоль.
*/
public class Box {
    private Color color;
    private final int width;
    private final int length;
    private final int depth;
    private boolean isOpened = false;
    private boolean isEmpty = true;

    public Box(int width, int length, int depth, Color color) {
        this.width = width;
        this.length = length;
        this.depth = depth;
        this.color = color;
    }

    public void toRecolor(Color color){
        this.color = color;
        System.out.println("Коробку перекрасили в цвет " + color);
    }

    public boolean isOpened() {
        return this.isOpened;
    }

    public void open() throws Exception {
        if (this.isOpened) {
            throw new Exception("Коробка уже открыта !!!");
        }
        this.isOpened = true;
        System.out.println("Коробку открыли");
    }

    public void close() throws Exception {
        if (!this.isOpened) {
            throw new Exception("Коробка уже закрыта !!!");
        }
        this.isOpened = false;
        System.out.println("Коробку закрыли");
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public void put() throws Exception {
        if (!this.isOpened) {
            throw new Exception("Коробка закрыта, чтобы заполнить коробку сначала откройте коробку !!!");
        }
        if (!this.isEmpty) {
            throw new Exception("Коробка уже заполнена !!!");
        }
        this.isEmpty = false;
        System.out.println("Коробку заполнили");
    }


    public void clear() throws Exception {
        if (!this.isOpened) {
            throw new Exception("Коробка закрыта, чтобы освободить коробку сначала откройте коробку !!!");
        }
        if (this.isEmpty) {
            throw new Exception("Коробка уже пуста !!!");
        }
        this.isEmpty = true;
        System.out.println("Коробку освободили");
    }

    @Override
    public String toString() {
        return "Box{" + "width=" + width + ", length=" + length + ", depth=" + depth + ", color=" + color + ", isOpened=" + isOpened + ", isEmpty=" + isEmpty + '}';
    }
}
