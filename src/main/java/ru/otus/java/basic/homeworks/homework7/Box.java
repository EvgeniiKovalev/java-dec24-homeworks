package ru.otus.java.basic.homeworks.homework7;
/*
Попробуйте реализовать класс по его описания: объекты класса Коробка должны иметь размеры и цвет.
Коробку можно открывать и закрывать. Коробку можно перекрашивать. Изменить размер коробки после создания нельзя.
У коробки должен быть метод, печатающий информацию о ней в консоль.
В коробку можно складывать предмет (если в ней нет предмета), или выкидывать его оттуда
(только если предмет в ней есть), только при условии что коробка открыта (предметом читаем просто строку).
Выполнение методов должно сопровождаться выводом сообщений в консоль.
*/
public class Box {
    public Color color;
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

    public boolean getIsOpened() {
        return this.isOpened;
    }

    public void setIsOpened(boolean isOpened) throws Exception {
        if (this.isOpened == isOpened) {
            throw new Exception("Коробка уже " + (isOpened ? "открыта" : "закрыта") + "!!!");
        }
        this.isOpened = isOpened;
        System.out.println("Коробку " + (isOpened ? "открыли" : "закрыли"));
    }

    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    public void setIsEmpty(boolean isEmpty) throws Exception {
        if (!this.isOpened) {
            throw new Exception("Коробка закрыта, чтобы освободить/заполнить коробку сначала откройте коробку !!!");
        }
        if (this.isEmpty == isEmpty) {
            throw new Exception("Коробка " + (this.isEmpty ? "пуста" : "заполнена"));
        }
        this.isEmpty = isEmpty;
        System.out.println("Коробку " + (isEmpty ? "освободили" : "заполнили"));
    }

    @Override
    public String toString() {
        return "Box{" + "width=" + width + ", length=" + length + ", depth=" + depth + ", color=" + color + ", isOpened=" + isOpened + ", isEmpty=" + isEmpty + '}';
    }
}
