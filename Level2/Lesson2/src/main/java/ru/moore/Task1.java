package ru.moore;

public class Task1 {

    public static final int MAX_ARRAY_LENGTH = 4;

    public static void main(String[] args) {
        String[][] myArray1 = new String[4][4];
        for (int i = 0; i < myArray1.length; i++) {
            myArray1[i][0] = "1";
            myArray1[i][1] = "1";
            myArray1[i][2] = "1";
            myArray1[i][3] = "1";
        }

        try {
            setArray(myArray1);
        } catch (MyArraySizeException e) {
            e.printStackTrace();
        } catch (MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    public static void setArray(String[][] array) throws MyArraySizeException, MyArrayDataException{
        if (array.length != MAX_ARRAY_LENGTH) {
            throw new MyArraySizeException();
        }
        for (String[] row : array) {
            if (row.length != MAX_ARRAY_LENGTH) {
                throw new MyArraySizeException();
            }
        }

        try {
            String verificationArrayLength = array[MAX_ARRAY_LENGTH - 1][MAX_ARRAY_LENGTH - 1];

            int sum = 0;
            for (int i = 0; i < MAX_ARRAY_LENGTH; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    sum += charToInt(array[i][j], i, j);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new MyArraySizeException();
        }
    }

    public static int charToInt(String val, int i, int j) {
        try {
            return Integer.valueOf(val);
        } catch (NumberFormatException e) {
            throw new MyArrayDataException(i, j);
        }
    }

}
