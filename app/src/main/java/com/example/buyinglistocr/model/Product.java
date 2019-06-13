package com.example.buyinglistocr.model;

public class Product {

    private long id;
    private String name;
    private long idShop;

    public Product(long id, String name, long idShop) {

        this.id = id;
        this.name = name;

        this.idShop = idShop;

    }

    public long getId() {

        return this.id;

    }

    public void setId(long id) {

        this.id = id;

    }

    public String getName() {

        return this.name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public long getIdShop() {

        return this.idShop;

    }

    public void setIdShop(int idShop) {

        this.idShop = idShop;

    }

}