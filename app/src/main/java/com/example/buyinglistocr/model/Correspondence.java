package com.example.buyinglistocr.model;

/**
 * Created by Chi Linh on 2019--2019-06-11--12:27.
 */
public class Correspondence {
    public long id;
    public String name;

    public Correspondence(long id, String name) {
        this.id = id;
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
