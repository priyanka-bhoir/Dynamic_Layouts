package com.priyanka.dynamiclayouts;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Data {
    String id;
    String name;
    String number;
    String email;
    String web;
    String pass;
    String dropdown;
    String radio;
    String checkbox;
    String date;
    String time;

    public Data(String name, String number, String email, String web, String pass, String dropdown, String radio, String checkbox, String date, String time) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.web = web;
        this.pass = pass;
        this.dropdown = dropdown;
        this.radio = radio;
        this.checkbox = checkbox;
        this.date = date;
        this.time = time;
    }



    public String getDropdown() {
        return dropdown;
    }

    public void setDropdown(String dropdown) {
        this.dropdown = dropdown;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getCheckbox() {

        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Data(String id, String name, String number, String email, String web, String pass, String dropdown, String radio, String checkbox, String date, String time) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.web = web;
        this.pass = pass;
        this.dropdown = dropdown;
        this.radio = radio;
        this.checkbox = checkbox;
        this.date = date;
        this.time = time;
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
