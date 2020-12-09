package com.priyanka.dynamiclayouts;

public class Data {
    String id;
    String name;
    String number;
    String email;
    String web;
    String pass;

    public Data(String name, String number, String email, String web, String pass) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.web = web;
        this.pass = pass;
    }
    public Data(String name, String number, String email, String web, String pass,String id) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.web = web;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
