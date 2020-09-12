package ru.moore;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Task1 {

    public static char[][] map;
    public static final int SIZE_MAP = 3;
    public static final int DOTS_TO_WIN = 3;

    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';

    public static final int SIZE_WINDOWS = 800;

    private static MyWindow myWindow;

    public static Random random = new Random();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                myWindow = new MyWindow();
            }
        });

        initMap();

    }

    private static void initMap() {
        map = new char[SIZE_MAP][SIZE_MAP];
        for (int i = 0; i < SIZE_MAP; i++) {
            for (int j = 0; j < SIZE_MAP; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void humanTurn(int rowIndex, int colIndex) {
        if (isCellValid(rowIndex - 1, colIndex - 1)) {
            map[rowIndex - 1][colIndex - 1] = DOT_X;
            myWindow.setX(rowIndex, colIndex);

            if (checkWin(DOT_X)) {
                JOptionPane.showMessageDialog(myWindow, "Победил человек!");
                return;
            }

            if (isMapFull()) {
                JOptionPane.showMessageDialog(myWindow, "Ничья!");
                return;
            }

            aiTurn();
            printMap();
            if (checkWin(DOT_O)) {
                JOptionPane.showMessageDialog(myWindow, "Победил искуственный интелект!");
                return;
            }

            if (isMapFull()) {
                JOptionPane.showMessageDialog(myWindow, "Ничья!");
                return;
            }
        }
    }

    public static boolean isCellValid(int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= SIZE_MAP || colIndex < 0 || colIndex >= SIZE_MAP) {
            return false;
        }
        return map[rowIndex][colIndex] == DOT_EMPTY;
    }

    private static void printMap() {
        for (int i = 0; i <= SIZE_MAP; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE_MAP; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE_MAP; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean checkWin(char symbol) {
        if (checkRowsAndCols(symbol)) return true;
        return checkDiagonals(symbol);
    }

    private static boolean checkDiagonals(char symbol) {
        int mainDiagCounter = 0;
        int sideDiagCounter = 0;
        for (int i = 0; i < SIZE_MAP; i++) {
            mainDiagCounter = (map[i][i] == symbol) ? mainDiagCounter + 1 : 0;
            sideDiagCounter = (map[i][map.length - 1 - i] == symbol) ? sideDiagCounter + 1 : 0;
            if (mainDiagCounter == DOTS_TO_WIN || sideDiagCounter == DOTS_TO_WIN) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkRowsAndCols(char symbol) {
        for (int i = 0; i < SIZE_MAP; i++) {
            int rowCounter = 0;
            int colCounter = 0;
            for (int j = 0; j < SIZE_MAP; j++) {
                rowCounter = (map[i][j] == symbol) ? rowCounter + 1 : 0;
                colCounter = (map[j][i] == symbol) ? colCounter + 1 : 0;
                if (rowCounter == DOTS_TO_WIN || colCounter == DOTS_TO_WIN) {
                    return true;
                }
            }
        }
        return false;
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

    private static void aiTurn() {
        int[] cell = getCellToBlockOpponent(DOT_O);
        if (cell == null) {
            cell = getCellToBlockOpponent(DOT_X);
            if (cell == null) {
                cell = getRandomEmptyCell();
            }
        }

        map[cell[0]][cell[1]] = DOT_O;
        myWindow.setO(cell[0] + 1, cell[1] + 1);
    }

    private static int[] getCellToBlockOpponent(char opponentSymbol) {
        for (int rowIndex = 0; rowIndex < map.length; rowIndex++) {
            for (int colIndex = 0; colIndex < map[rowIndex].length; colIndex++) {
                if (map[rowIndex][colIndex] == DOT_EMPTY && isGameMoveWinning(rowIndex, colIndex, opponentSymbol)) {
                    return new int[]{rowIndex, colIndex};
                }
            }
        }

        return null;
    }

    private static boolean isGameMoveWinning(int rowIndex, int colIndex, char opponentSymbol) {
        setCell(rowIndex, colIndex, opponentSymbol);
        boolean result = checkWin(opponentSymbol);
        setCell(rowIndex, colIndex, DOT_EMPTY);
        return result;
    }

    private static void setCell(int rowIndex, int colIndex, char symbol) {
        map[rowIndex][colIndex] = symbol;
    }

    private static int[] getRandomEmptyCell() {
        int rowIndex, colIndex;
        do {
            rowIndex = random.nextInt(SIZE_MAP);
            colIndex = random.nextInt(SIZE_MAP);
        } while (!isCellValid(rowIndex, colIndex));
        return new int[] {rowIndex, colIndex};
    }
}
