package com_calculator.java_calculator.checkcalculator.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("country")
    private String country;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("about")
    private String about;
    @Expose
    @SerializedName("image")
    private String imageProfile;

    public User(){}

    public User(int id) {
        this.id = id;
    }

    public User(String name, String email, String country, String about) {
        this.name = name;
        this.email = email;
        this.country = country;
    }

    public String getAbout() {
        return about;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
