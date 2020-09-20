package ru.moore;

import java.util.Random;

public class Task1 {

    public static void main(String[] args) {
        Random random = new Random();

        Human human = new Human();

        Cat cat1 = new Cat("Barsik", random.nextInt(5), false, human);
        Cat cat2 = new Cat("Murzik", random.nextInt(5), false, human);
        Cat cat3 = new Cat("Vaska", random.nextInt(5), false, human);
        Cat cat4 = new Cat("Musya", random.nextInt(5), false, human);
        Cat cat5 = new Cat("Myau", random.nextInt(5), false, human);

        catEat(cat1, cat2, cat3, cat4, cat5);
    }
    
    public static void catEat(Cat...cat){
        Plate plate = new Plate();
        for (Cat instance : cat) {
            instance.eat(plate);
            plate.info();
        }
    }

}
