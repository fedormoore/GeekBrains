package ru.moore;

import java.util.Random;

public class Task1 {

    static String[] name = {"Дмитрий", "Николай", "Владислав", "Анатолий", "Леонид"};
    static String[] lastName = {"Леонидович", "Анатольевич", "Владислалович", "Николаевич", "Дмитриевич"};
    static String[] surName = {"Яковлев", "Антонов", "Туполев", "Илюшин", "Гагарин"};
    static String[] position = {"Инженер", "Программист", "Физик-ядерщик", "Пилот F1", "Режиссер"};
    static String[] email = {"123@mail.ru", "456@rambler.ru", "789@gmail.com", "147@yandex.ru", "369@bk.ru"};
    static String[] telNumber = {"7123456789", "7123456798", "9876543210", "9876543201", "1593578624"};

    static Random random = new Random();

    public static void main(String[] args) {
        Person[] persArr = new Person[5];
        setPerson(persArr);
        getPerson(persArr);
    }

    public static void setPerson(Person[] persArr) {
        for (int i = 0; i < 5; i++) {
            persArr[i] = new Person(name[random.nextInt(name.length)], lastName[random.nextInt(lastName.length)], surName[random.nextInt(surName.length)], position[random.nextInt(position.length)], email[random.nextInt(email.length)], telNumber[random.nextInt(telNumber.length)], random.nextInt(100000), random.nextInt(85));
        }
    }

    public static void getPerson(Person[] persArr) {
        for (Person p : persArr) {
            if (p.getAge() > 40) {
                p.printPerson();
            }
        }
    }
}
