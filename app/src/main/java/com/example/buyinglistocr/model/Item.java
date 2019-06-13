package com.example.buyinglistocr.model;

public class Item {

    private int id;
    private String name;
    private int quantityDesired;
    private int quantityGot;
    private String note;
    private int status;
    private int idList;

    public Item(int id, String name, int quantityDesired, int quantityGot, String note, int status, int idList) {

        this.id = id;
        this.name = name;
        this.quantityDesired = quantityDesired;
        this.quantityGot = quantityGot;
        this.note = note;
        this.status = status;
        this.idList = idList;

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

    public int getQuantityDesired() {

        return this.quantityDesired;

    }

    public void setQuantityDesired(int quantityDesired) {

        this.quantityDesired = quantityDesired;

    }

    public int getQuantityGot() {

        return this.quantityGot;

    }

    public void setQuantityGot(int quantityGot) {

        this.quantityGot = quantityGot;

    }

    public String getNote() {

        return this.note;

    }

    public void setNote(String note) {

        this.note = note;

    }

    public int getStatus() {

        return this.status;

    }

    public void setStatus(int status) {

        this.status = status;

    }

    public int getIdList() {

        return idList;

    }

    public void setIdList(int idList) {

        this.idList = idList;

    }

}