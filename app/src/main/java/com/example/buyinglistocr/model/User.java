package com.example.buyinglistocr.model;

public class User {

    private int id;
    private String login;
    private String password;
    private String mail;

    public User(int id, String login, String password, String mail) {

        this.id = id;
        this.login = login;
        this.password = password;
        this.mail = mail;

    }

    public int getId() {

        return id;

    }

    public void setId(int id) {

        this.id = id;

    }

    public String getLogin() {

        return login;

    }

    public void setLogin(String login) {

        this.login = login;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {

        this.password = password;

    }

    public String getMail() {

        return mail;

    }

    public void setMail(String mail) {

        this.mail = mail;

    }

}
