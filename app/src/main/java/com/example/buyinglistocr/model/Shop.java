package com.example.buyinglistocr.model;

public class Shop {

    // Different attributes of Shop
    private int id;
    private String name;

    public Shop (int id, String name) {

        this.id = id;
        this.name = name;

    }

    public int getId() {

        return this.id;

    }

    public void setId(int id) {

        this.id = id;

    }

    public String getName() {

        return this.name;

    }

    public void setName(String name) {

        this.name = name;

    }

}
