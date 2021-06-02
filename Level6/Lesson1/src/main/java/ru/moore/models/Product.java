package ru.moore.models;

public class Product {

    private Long id;
    private String title;
    private String coast;

    public Product(Long id, String title, String coast) {
        this.id = id;
        this.title = title;
        this.coast = coast;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoast() {
        return coast;
    }

    public void setCoast(String coast) {
        this.coast = coast;
    }
}
