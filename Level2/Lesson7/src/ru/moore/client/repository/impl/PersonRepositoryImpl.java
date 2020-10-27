package ru.moore.client.repository.impl;

import ru.moore.client.model.Person;
import ru.moore.client.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    @Override
    public List<Person> getAllData() {
        List<Person> listPerson = new ArrayList<>();
        listPerson.add(new Person("Отправить всем"));
        listPerson.add(new Person("Oleg"));
        listPerson.add(new Person("Alexey"));
        listPerson.add(new Person("Peter"));

        return listPerson;
    }

}
