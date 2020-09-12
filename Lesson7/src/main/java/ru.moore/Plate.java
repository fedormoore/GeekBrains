package ru.moore;

public class Plate {

    private final int SIZE_PLACE = 10;
    private int food;

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void info() {
        System.out.println("plate: " + food);
    }

    public boolean decreaseFood(int appetite) {
        if (appetite < food) {
            this.food -= appetite;
            return true;
        } else {
            return false;
        }
    }

    public boolean increaseFood(int food) {
        if ((this.food + food) < SIZE_PLACE) {
            System.out.println("Человек насыпал еды в миску");
            this.food += food;
            return true;
        } else {
            System.out.println("Человек насыпал очень много еды");
            return false;
        }
    }
}
