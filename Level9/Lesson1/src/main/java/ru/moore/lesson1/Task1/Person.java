package ru.moore.lesson1.Task1;

public class Person {

    String firstName;
    String lastName;
    String middleName;
    String country;
    String address;
    String phone;
    int age;
    String gender;

    public static void main(String[] args) {
        Person myPerson = new Person.Builder()
                .firstName("Moore")
                .lastName("Fedor")
                .middleName("Anatolevich")
                .country("Russian")
                .address("Russian")
                .phone("1234567890")
                .age(35)
                .gender("Мужской")
                .build();

        System.out.println(myPerson.toString());
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

    public static class Builder {

        private Person newPerson;

        public Builder() {
            newPerson = new Person();
        }

        public Builder firstName(String firstName) {
            newPerson.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            newPerson.lastName = lastName;
            return this;
        }

        public Builder middleName(String middleName) {
            newPerson.middleName = middleName;
            return this;
        }

        public Builder country(String country) {
            newPerson.country = country;
            return this;
        }

        public Builder address(String address) {
            newPerson.address = address;
            return this;
        }

        public Builder phone(String phone) {
            newPerson.phone = phone;
            return this;
        }

        public Builder age(int age) {
            newPerson.age = age;
            return this;
        }

        public Builder gender(String gender) {
            newPerson.gender = gender;
            return this;
        }

        public Person build() {
            return newPerson;
        }

    }
}

