package com_calculator.java_calculator.checkcalculator.models;

public class Check {

    private String restaurantTitle, date;
    private boolean progress;

    public Check() {
    }

    public Check(String restaurantTitle, String date, boolean progress) {
        this.restaurantTitle = restaurantTitle;
        this.date = date;
        this.progress = progress;
    }

    public Check(String restaurantTitle) {
        this.restaurantTitle = restaurantTitle;
    }

    public String getRestaurantTitle() {
        return restaurantTitle;
    }


    public String getDate() {
        return date;
    }


    public boolean isProgress() {
        return progress;
    }

}
