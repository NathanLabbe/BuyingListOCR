package com.example.buyinglistocr.model;

public class SavePurchase {
    private long id;
    private String name;

    public SavePurchase(long id, String name) {
        this.id=id;
        this.name = name;
    }
    public SavePurchase( String name) {

        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
