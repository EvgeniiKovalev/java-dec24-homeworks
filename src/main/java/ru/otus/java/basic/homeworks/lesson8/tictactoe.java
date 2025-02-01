package ru.otus.java.basic.homeworks.lesson8;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


//игра крестики нолики в консольном варианте
public class tictactoe {
    private final static int SIZE = 3;
    private final static int maxMovies = SIZE*SIZE;
    private static char [][] field;
    private final static char DOT_X = 'X';
    private final static char DOT_0 = '0';
    private final static char DOT_EMPTY = '.';
    private static final Scanner scaner = new Scanner(System.in);
    final static Random random = new Random();
    private static int countCompletedMovies = 0;

    public static void main(String[] args) {
        initMap();
        printMap();

        do {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Вы победили !!!");
                return;
            }
            if (isFill()) {
                System.out.println("Ничья");
                return;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_0)) {
                System.out.println("ПК победил !");
                return;
            }
        }while (!isFill());
        System.out.println("Ничья");
    }

    public static boolean checkWin(char ch){
        boolean result = true;

        //диагональ 1
        for (int i = 0; i < SIZE; i++) {
            //вариант 1
            //result = result && field[i][i]==ch;

            //вариант 2
            if (field[i][i] != ch) {
                result = false;
                break;
            }
        }
        if (result) {return true;}

        //диагональ 2
        result = true;
        for (int i = 0; i < SIZE; i++) {
            //вариант 1
            //result = result && field[SIZE - i - 1][i]==ch;

            //вариант 2
            if (field[SIZE - i - 1][i]!=ch) {
                result = false;
                break;
            }
        }
        if (result) {return true;}

        //горизонталь
        for (int i = 0; i < SIZE; i++) {
            result = true;
            for (int j = 0; j < SIZE; j++) {
                //вариант 1
                //result = result && field[i][j]==ch;

                //вариант 2
                if (field[i][j]!=ch) {
                    result = false;
                    break;
                }
            }
            if (result) {return true;}
        }

        //вертикаль
        for (int j = 0; j < SIZE; j++) {
            result = true;
            for (int i = 0; i < SIZE; i++) {
                //вариант 1
                //result = result && field[i][j]==ch;

                //вариант 2
                if (field[i][j]!=ch) {
                    result = false;
                    break;
                }
            }
            if (result) {return true;}
        }
        return false;
    }

    private static boolean isFill() {
// вариант 1
//        for (int i = 0; i < SIZE; i++) {
//            for (int j = 0; j < SIZE; j++) {
//                if (field[i][j] == DOT_EMPTY) {
//                    return false;
//                }
//            }
//        }
//        return true;
// вариант 2
        //оптимизированный вариант
        return countCompletedMovies >= maxMovies;
    }

    private static  void initMap() {
        field = new char[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++) {
            //вариант 1
//            for (int y = 0; y < SIZE; y++) {
//                field[x][y] = DOT_EMPTY;
//            }
            //вариант 2
            Arrays.fill(field[x], 0, SIZE, DOT_EMPTY);

        }
    }

    private static void printMap() {
        System.out.print("  ");
        for (int x = 1; x <= SIZE ; x++) {
            System.out.print(x + " " );
        }

        System.out.println();
        for (int x = 0; x < SIZE; x++) {
            System.out.print(x+1 + " ");
            for (int y = 0; y < SIZE; y++) {
                System.out.print(field[x][y] + " ");
            }
            System.out.println();
        }
    }

    public static boolean isCellValid (int x, int y) {
        return x >= 0 && y >= 0 && x < SIZE && y < SIZE;
    }

    public static boolean isCellChar (int x, int y, char ch) {
        return field[x][y] == ch;
    }

    static void aiTurn() {
        countCompletedMovies++;
        boolean doMovie = false;
        int x=0, y=0;
        //стратегия выигрыша на следующем ходе
        for (x = 0; x < SIZE; x++) {
            for (y = 0; y < SIZE; y++) {
                if (isCellChar(x, y, DOT_EMPTY)) {
                    field[x][y]=DOT_0;
                    doMovie = checkWin(DOT_0);
                    if (doMovie) {break;}
                    else { field[x][y]=DOT_EMPTY;}
                }
            }
            if (doMovie) {break;}
        }

        //стратегия не выигрыша противника на следующем ходе
        if (!doMovie) {
            for (x = 0; x < SIZE; x++) {
                for (y = 0; y < SIZE; y++) {
                    if (isCellChar(x, y, DOT_EMPTY)) {
                        field[x][y]=DOT_X;
                        doMovie = checkWin(DOT_X);
                        if (doMovie) {
                            field[x][y]=DOT_0;
                            break;
                        } else {
                            field[x][y]=DOT_EMPTY;
                        }
                    }
                }
                if (doMovie) {break;}
            }
        }

        if (!doMovie) {
            //случайная стратегия
            do {
                y = random.nextInt(SIZE);
                x = random.nextInt(SIZE);
            } while (!isCellValid(x, y) || !isCellChar(x, y, DOT_EMPTY));
            field[x][y] = DOT_0;
        }
        System.out.printf("ход пк %d %d\n", x+1,y+1);
    }

    public static void humanTurn() {
        int x, y;
        do {
            System.out.print("Введите координаты строки и столбика ");
            y = scaner.nextInt()-1;
            x = scaner.nextInt()-1;
        } while (!isCellValid(y, x) || !isCellChar(y, x, DOT_EMPTY));
        field[y][x] = DOT_X;
        countCompletedMovies++;
    }
}
