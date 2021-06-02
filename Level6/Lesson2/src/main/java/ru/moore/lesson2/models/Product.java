package ru.moore.lesson2.models;

public class Product {

    private int id;
    private String title;
    private int cost;

    public Product(int id, String title, Integer cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("Product: [%d %s %d]", id, title, cost);
    }
}
