package com_calculator.java_calculator.checkcalculator.models;

public class Dish {

    private String dishName;
    private int price;

    public Dish(String dishName, int price) {
        this.dishName = dishName;
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public int getPrice() {
        return price;
    }
}
