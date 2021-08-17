package ru.moore.lesson2.MyArrayList;


public class MyArrayApp {

    public static void main(String[] args) {

        MyArrayList<Object> myArrayList = new MyArrayList<>();
        myArrayList.add(1);
        myArrayList.add(2);
        myArrayList.add("кушать");
        for (int i = 0; i < myArrayList.size(); i++)
            System.out.println(myArrayList.get(i));

    }

}
