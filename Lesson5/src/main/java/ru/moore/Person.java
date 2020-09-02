package ru.moore;

public class Person {

    String name;
    String lastName;
    String surName;
    String position;
    String email;
    String telNumber;
    int salary;
    int age;

    public Person(String name, String lastName, String surName, String position, String email, String telNumber, int salary, int age) {
        this.name = name;
        this.lastName = lastName;
        this.surName = surName;
        this.position = position;
        this.email = email;
        this.telNumber = telNumber;
        this.salary = salary;
        this.age = age;
    }

    public void printPerson() {
        System.out.println("ФИО -> " + name + " " + lastName + " " + surName);
        System.out.println("Должность -> " + position);
        System.out.println("E-mail -> " + email);
        System.out.println("Телефон -> " + telNumber);
        System.out.println("Зарплата -> " + salary);
        System.out.println("Возраст -> " + age);
        System.out.println("----------------------------------------");
    }
}
