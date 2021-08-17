package ru.moore.lesson2.MyLinkedList;

public class App {

    public static void main(String[] args) {

        MyLinkedList doublyLinkedList = new MyLinkedList();
        doublyLinkedList.addFirst("Toyota");
        doublyLinkedList.addFirst("Honda");

        System.out.println(doublyLinkedList);

        doublyLinkedList.addLast("Subaru");
        doublyLinkedList.addLast("Mazda");

        System.out.println(doublyLinkedList);

        doublyLinkedList.addByIndex("KIA", 2);

        System.out.println(doublyLinkedList);

        doublyLinkedList.removeFirst();

        System.out.println(doublyLinkedList);

        doublyLinkedList.removeLast();

        System.out.println(doublyLinkedList);
        doublyLinkedList.removeIndex("KIA");

        System.out.println(doublyLinkedList);

    }

}