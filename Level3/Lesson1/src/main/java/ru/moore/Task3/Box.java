package ru.moore.Task3;

import java.util.ArrayList;

public class Box<T extends Fruit> {

    private ArrayList<T> basket;

    public Box() {
        basket = new ArrayList<>();
    }

    public void addFruit(T fruit) {
        basket.add(fruit);
    }

    public float getWeight() {
        return basket.size() * basket.get(0).getWeight();
    }

    public boolean compare(Box<? extends Fruit> compareBox) {
        return this.getWeight() == compareBox.getWeight();
    }

    public void moveAt(Box<T> moveAtBox) {
        for (int i = 0; i < basket.size(); i++) {
            moveAtBox.addFruit(basket.get(i));
        }
        basket.clear();
    }
}
