package ru.moore;

import java.util.Random;

public class Human {

    public void askFood(Plate plate, Cat cat) {
        Random random = new Random();
        while (true){
            if (plate.increaseFood(random.nextInt(50)))
                break;
        }
        cat.eat(plate);
    }

}
