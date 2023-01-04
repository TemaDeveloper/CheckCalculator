package com_calculator.java_calculator.checkcalculator.models;

import com.google.gson.annotations.SerializedName;

public class Friend {

    @SerializedName("id")
    private int id;
    @SerializedName("request")
    private int request;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private String country;
    @SerializedName("about")
    private String about;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("my_id")
    private int my_id;
    @SerializedName("image")
    private String imageProfile;
    @SerializedName("friendName")
    private String friendName;
    @SerializedName("friendImage")
    private String friendImage;

    public String getFriendImage() {
        return friendImage;
    }

    public String getFriendName() {
        return friendName;
    }

    public int getMy_id() {
        return my_id;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public int getRequest() {
        return request;
    }

    public Friend(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
