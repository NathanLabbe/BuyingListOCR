package com.example.buyinglistocr.model;

public class List {

    private int id;
    private String name;
    private double spent;
    private int status;
    private int idUser;

    public List(int id, String name, double spent, int status, int idUser) {

        this.id = id;
        this.name = name;
        this.spent = spent;
        this.status = status;
        this.idUser = idUser;

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

    public double getSpent() {

        return this.spent;

    }

    public void setSpent(double spent) {

        this.spent = spent;

    }

    public int getStatus() {

        return status;

    }

    public void setStatus(int status) {

        this.status = status;

    }

    public int getIdUser() {

        return idUser;

    }

    public void setIdUser(int idUser) {

        this.idUser = idUser;

    }

}



