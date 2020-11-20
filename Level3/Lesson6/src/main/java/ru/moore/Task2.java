package ru.moore;

import java.util.Arrays;

public class Task2 {

    public static void main(String[] args) {
        int[] tempOk = new int[]{1, 1, 4, 4, 1, 1, 1, 1, 4};
        int[] tempError = new int[]{1, 1, 4, 4, 1, 1, 1, 1, 4, 3};
        System.out.println(arrayCheck(tempOk));
        System.out.println(arrayCheck(tempError));
    }

    public static boolean arrayCheck(int[] arrIn) {
        boolean one = false;
        boolean four = false;
        for (int i : arrIn) {
            if (i != 1 && i != 4) return false;
            if (i == 1) one = true;
            if (i == 4) four = true;
        }
        return one && four;
    }

}
