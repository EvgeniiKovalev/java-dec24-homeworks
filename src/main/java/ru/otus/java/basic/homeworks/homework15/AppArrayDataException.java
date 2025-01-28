package ru.otus.java.basic.homeworks.homework15;

public class AppArrayDataException extends Exception{
    private int rowNum;
    private int colNum;

    public int getRowNum(){return rowNum;}
    public int getColNum(){return colNum;}

    public AppArrayDataException(String message, int rowNum, int colNum){

        super(String.format("%s [%d,%d]",message,rowNum, colNum));
        this.rowNum=rowNum;
        this.colNum= colNum;
    }

}
