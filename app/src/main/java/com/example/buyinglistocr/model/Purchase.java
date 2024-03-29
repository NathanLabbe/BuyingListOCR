package com.example.buyinglistocr.model;

/**
 * Created by Chi Linh on 2019--2019-05-21--17:39.
 */
public class Purchase {
    private String name;
    private double price;
    private int quantite;
    private boolean findCores;


    public Purchase(String name, double price, int quantite) {
        this.name = name;
        this.price = price;
        this.quantite = quantite;
        this.findCores = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantite() {
        return quantite;
    }

    public boolean isFindCores() {
        return findCores;
    }

    public void setFindCores(boolean findCores) {
        this.findCores = findCores;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
