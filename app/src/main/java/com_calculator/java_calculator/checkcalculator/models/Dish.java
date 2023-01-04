package com_calculator.java_calculator.checkcalculator.models;

public class Dish {

    private String dishName, price;

    public Dish(String dishName, String price) {
        this.dishName = dishName;
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public String getPrice() {
        return price;
    }
}
