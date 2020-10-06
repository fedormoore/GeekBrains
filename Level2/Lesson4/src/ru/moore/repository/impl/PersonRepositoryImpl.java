package ru.moore.repository.impl;

import ru.moore.model.Person;
import ru.moore.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    @Override
    public List<Person> getAllData() {
        List<Person> listPerson = new ArrayList<>();
        listPerson.add(new Person("User1"));
        listPerson.add(new Person("User2"));
        listPerson.add(new Person("User3"));

        return listPerson;
    }

}
