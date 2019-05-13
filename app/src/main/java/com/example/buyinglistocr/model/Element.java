package com.example.buyinglistocr.model;

public class Element {

    // proprietes

    private Integer id;
    private String str;

    public Element(String str) {
        id = 0;
        this.str = str;
    }

    public Element(Integer id, String str) {
        this.id = id;
        this.str = str;
    }

    public Integer getId() {
        return id;
    }

    public String getStr() {
        return str;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStr(String str) {
        this.str = str;
    }
}



