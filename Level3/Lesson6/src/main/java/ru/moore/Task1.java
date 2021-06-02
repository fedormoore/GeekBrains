package ru.moore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task1 {

    public static void main(String[] args) {
        int[] tempOk = new int[]{1, 2, 4, 4, 2, 4, 3, 1, 7};
        int[] tempError = new int[]{1, 2, 5, 5, 2, 3, 6, 1, 7};
        System.out.println(Arrays.toString(arrayChange(tempOk)));
        System.out.println(Arrays.toString(arrayChange(tempError)));
    }

    public static int[] arrayChange(int[] arrayList) {
        int[] temp;
        for (int i = arrayList.length-1; i > 0; i--) {
            if (arrayList[i] == 4) {
                int length = arrayList.length-i-1;
                temp= new int[length];
                System.arraycopy(arrayList, (i+1), temp,0,length);
                return temp;
            }
        }
        throw new RuntimeException("ОШИБКА!!! В массиве нет цифры 4");
    }
}