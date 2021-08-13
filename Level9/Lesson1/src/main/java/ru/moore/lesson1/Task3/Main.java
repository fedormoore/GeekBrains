package ru.moore.lesson1.Task3;

public class Main {

    public static void main(String[] args) {
        Figure[] a = new Figure[] {new Figure(), new Triangle(), new Square(), new Сircle()};

        for(int i = 0; i < a.length; i++) {
            a[i].say();
        }
    }

}
