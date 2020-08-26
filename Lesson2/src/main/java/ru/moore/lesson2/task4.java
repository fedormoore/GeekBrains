package ru.moore.lesson2;

public class task4 {

    public static void main(String[] args) {
        int[][] arr = new int[5][5];

        for (int i = 0; i < 5; i++) {
            for (int x = 0; x < 5; x++) {
                if (i == x) {
                    arr[i][x] = 1;
                    break;
                }
            }
        }

        for (int row[] : arr) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

}
