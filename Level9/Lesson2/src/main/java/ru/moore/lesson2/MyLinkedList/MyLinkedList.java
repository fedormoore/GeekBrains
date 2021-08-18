package ru.moore.lesson2.MyLinkedList;

public class MyLinkedList implements MyInterface {
    private Node head;
    private Node tail;

    @Override
    public void addFirst(String o) {
        Node temp = new Node(o);
        if (head == null) {
            tail = temp;
        } else
            head.prev = temp;
        temp.next = head;
        head = temp;
    }

    @Override
    public void addLast(String o) {
        Node temp = new Node(o);
        if (head == null) {
            head = temp;
        } else
            tail.next = temp;
        temp.prev = tail;
        tail = temp;
    }

    @Override
    public void addByIndex(String o, int index) {
        Node cur = head;
        int c = 0;

        while (cur != null && c != index) {
            cur = cur.next;
            c++;
        }
        Node temp = new Node(o);
        cur.prev.next = temp;
        temp.prev = cur.prev;
        cur.prev = temp;
        temp.next = cur;
    }

    @Override
    public void removeFirst() {
        Node temp = head;

        if (head.next == null) {
            tail = null;
        } else
            head.next.prev = null;
        head = head.next;
    }

    @Override
    public void removeLast() {
        if (head.next == null) {
            head = null;
        } else {
            tail.prev.next = null;
            tail = tail.prev;
        }
    }

    @Override
    public void removeIndex(String key) {
        Node cur = head;
        while (cur.val != key) {
            cur = cur.next;
            if (cur == null)
                return;
        }
        if (cur == head)
            removeFirst();
        else
            cur.prev.next = cur.next;
        if (cur == tail)
            removeLast();
        else
            cur.next.prev = cur.prev;
    }

    public void add(Node current, String o) {
        if (current.getNext() == null) {
            current.setNext(new Node(o));
            return;
        }
        add(current.getNext(), o);
    }

    public MyLinkedList() {
        this.head = head;
        this.tail = tail;
    }

    private boolean isEmpty() {
        return head == null;
    }


    private static class Node {
        private String val;
        private Node next;
        private Node prev;

        public Node(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val='" + val + '\'' +
                    ", next=" + next +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "{" +
                head +
                '}';
    }
}