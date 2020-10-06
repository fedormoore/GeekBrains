package ru.moore.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

    private final StringProperty login;

    public Person() {
        this(null);
    }

    public Person(String login) {
        this.login = new SimpleStringProperty(login);
    }

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }
}
