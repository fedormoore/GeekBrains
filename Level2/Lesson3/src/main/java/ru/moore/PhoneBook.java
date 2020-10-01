package ru.moore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PhoneBook {

    private Map<Integer, String> PhoneBook = new HashMap<>();

    public void add(int telephoneNumber, String surname) {
        PhoneBook.put(telephoneNumber, surname);
    }

    public void get(String surname) {
        for (Integer val : PhoneBook.keySet()) {
            if (PhoneBook.get(val).equals(surname)) {
                System.out.println(PhoneBook.get(val) + " -> " + val);
            }
        }
    }

}
