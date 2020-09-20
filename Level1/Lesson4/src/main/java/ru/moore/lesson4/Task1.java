package ru.moore.lesson4;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Task1 {

    public static char[][] map;
    public static final int SIZE = 5;
    public static final int DOTS_TO_WIN = 4;

    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';

    public static Scanner scanner = new Scanner(System.in);
    public static Random random = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Победил человек!");
                break;
            }

            if (isMapFull()) {
                System.out.println("Ничья!");
                break;
            }

            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                System.out.println("Победил искуственный интелект!");
                break;
            }

            if (isMapFull()) {
                System.out.println("Ничья!");
                break;
            }
        }
        System.out.println("Игра окончена!");
    }

    private static boolean isMapFull() {
        for (char[] row : map) {
            for (char cell : row) {
                if (cell == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(char symbol) {
        int vertical = 0, horizontal = 0, diagonal = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == symbol) {
                    vertical++;
                    if (vertical == DOTS_TO_WIN) {
                        return true;
                    }
                }
                if (map[j][i] == symbol) {
                    horizontal++;
                    if (horizontal == DOTS_TO_WIN) {
                        return true;
                    }
                }
            }

            if (map[i][i] == symbol || map[SIZE - i - 1][i] == symbol) {
                diagonal++;
                if (diagonal == DOTS_TO_WIN) {
                    return true;
                }
            } else {
                diagonal = 0;
            }
            vertical = 0;
            horizontal = 0;
        }
        return false;
    }

    private static void aiTurn() {
        int rowIndex, colIndex;
        do {
            rowIndex = random.nextInt(SIZE);
            colIndex = random.nextInt(SIZE);

            int vertical = 0, horizontal = 0, diagonalL = 0, diagonalR = 0;
            int rowIndexTemp = 0, colIndexTemp = 0;
            for (int i = 0; i < SIZE; i++) {
                //ПРОВЕРЯЕМ ГОРИЗОНТАЛЬ
                for (int j = 0; j < SIZE; j++) {
                    if (map[i][j] == DOT_X) {
                        horizontal++;
                    } else {
                        colIndexTemp = j;
                    }
                }
                if (horizontal == (DOTS_TO_WIN - 1)) {
                    colIndex = colIndexTemp;
                    rowIndex = i;
                    break;
                }

                //ПРОВЕРЯЕМ ВЕРТИКАЛЬ
                int j;
                for (j = 0; j < SIZE; j++) {
                    if (map[j][i] == DOT_X) {
                        vertical++;
                    } else {
                        rowIndexTemp = j;
                    }
                }
                if (vertical == (DOTS_TO_WIN - 1)) {
                    rowIndex = rowIndexTemp;
                    colIndex = i;
                    break;
                }
                vertical = 0;
                horizontal = 0;
            }
            for (int i = 0; i < SIZE; i++) {
                //ПРОВЕРЯЕМ ДИАГОНАЛЬ
                if (map[i][i] == DOT_X) {
                    diagonalL++;
                } else {
                    rowIndexTemp = i;
                    colIndexTemp = i;
                }
                if (diagonalL == (DOTS_TO_WIN - 1)) {
                    rowIndex = rowIndexTemp;
                    colIndex = colIndexTemp;
                    break;
                }
            }
            for (int i = 0; i < SIZE; i++) {
                //ПРОВЕРЯЕМ ДИАГОНАЛЬ
                if (map[i][SIZE - i - 1] == DOT_X) {
                    diagonalR++;
                } else {
                    rowIndexTemp = SIZE - i - 1;
                    colIndexTemp = i;
                }
                if (diagonalR == (DOTS_TO_WIN - 1)) {
                    rowIndex = rowIndexTemp;
                    colIndex = colIndexTemp;
                    break;
                }
            }
        } while (!isCellValid(rowIndex, colIndex));
        map[rowIndex][colIndex] = DOT_O;
    }

    private static void humanTurn() {
        int rowIndex = -1, colIndex = -1;
        do {
            System.out.println("Введите координаты в формате '<номер строки> <номер колонки>'");
            String[] stringData = scanner.nextLine().split(" ");
            if (stringData.length != 2) {
                continue;
            }
            try {
                rowIndex = Integer.parseInt(stringData[0]) - 1;
                colIndex = Integer.parseInt(stringData[1]) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Были введены некорректные данные!");
            }
        } while (!isCellValid(rowIndex, colIndex));
        map[rowIndex][colIndex] = DOT_X;
    }

    public static boolean isCellValid(int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= SIZE || colIndex < 0 || colIndex >= SIZE) {
            return false;
        }
        return map[rowIndex][colIndex] == DOT_EMPTY;
    }

    private static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

}
