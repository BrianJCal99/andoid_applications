package com.example.lostandfoundapp.model;

import android.widget.RadioButton;

import java.sql.Date;

public class Post {

    private int post_id;

    private String post_type;

    private String user_id;

    private String name;

    private String phone;

    private String description;

    private String date;

    private String month;

    private String year;

    private String location;

    public  Post (String post_type, String user_id, String name, String phone, String description, String date, String month, String year, String location){
        this.post_type = post_type;
        this.user_id = user_id;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.month = month;
        this.year = year;
        this.location = location;
    }

    public Post() {
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
