package ru.moore;

public class Person {

    private String name;
    private String lastName;
    private String surName;
    private String position;
    private String email;
    private String telNumber;
    private int salary;
    private int age;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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
