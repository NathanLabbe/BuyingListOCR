package com.example.buyinglistocr.model;

public class Product {

    private int id;
    private String name;
    private int idShop;

    public Product(int id, String name, int idShop) {

        this.id = id;
        this.name = name;

        this.idShop = idShop;

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

    public int getIdShop() {

        return this.idShop;

    }

    public void setIdShop(int idShop) {

        this.idShop = idShop;

    }

}