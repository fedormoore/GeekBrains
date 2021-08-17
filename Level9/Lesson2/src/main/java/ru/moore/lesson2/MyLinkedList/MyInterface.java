package ru.moore.lesson2.MyLinkedList;

public interface MyInterface {
    void addFirst(String o);

    void addLast(String o);

    void addByIndex(String o, int index);

    void removeFirst();

    void removeLast();

    void removeIndex(String key);
}