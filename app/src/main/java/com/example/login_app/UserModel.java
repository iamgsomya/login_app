package com.example.login_app;

import java.sql.Timestamp;

public class UserModel {
    public  String name;
    public  String country;
    public  int age;
    public  String gender;
    public UserModel() {}
    public UserModel(String name, String country, int age, String gender) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.gender = gender;
    }
}
