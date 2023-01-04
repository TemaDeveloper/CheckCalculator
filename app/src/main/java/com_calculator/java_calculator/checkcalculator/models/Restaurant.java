package com_calculator.java_calculator.checkcalculator.models;

public class Restaurant {

    private String title;
    private int rating, image;

    public Restaurant() {
    }

    public Restaurant(String title, int rating, int image) {
        this.title = title;
        this.rating = rating;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getRating() {
        return rating;
    }

}
