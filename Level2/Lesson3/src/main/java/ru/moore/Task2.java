package ru.moore;

public class Task2 {

    public static void main(String[] args) {
        PhoneBook phonebook = new PhoneBook();

        phonebook.add(123456, "Мур");
        phonebook.add(789012, "Спатт");
        phonebook.add(345678, "Ким");
        phonebook.add(901234, "Спатт");
        phonebook.add(567890, "Фармер");
        phonebook.add(112233, "Мур");

        phonebook.get("Фармер");
        phonebook.get("Мур");
    }

}
