package ru.moore;

public class Cat {

    private final String name;
    private final int appetite;
    private boolean satiety;
    private Human human;

    public Cat(String name, int appetite, boolean satiety, Human human) {
        this.name = name;
        this.appetite = appetite;
        this.satiety = satiety;
        this.human = human;
    }

    public String getName() {
        return name;
    }

    public int getAppetite() {
        return appetite;
    }

    public void eat(Plate plate) {
        if (plate.decreaseFood(appetite)) {
            System.out.println("Кот по кличке " + name + " покушал");
            satiety = true;
        } else {
            System.out.println("Кот по кличке " + name + " пошел просить еды у человека");
            human.askFood(plate, this);
        }
    }
}
