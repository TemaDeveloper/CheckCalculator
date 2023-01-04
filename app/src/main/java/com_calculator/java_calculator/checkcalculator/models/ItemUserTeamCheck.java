package com_calculator.java_calculator.checkcalculator.models;

public class ItemUserTeamCheck {

    private int type;
    private Object object;
    private String name, titleOfRest;

    public ItemUserTeamCheck(int type, Object object, String name, String titleOfRest) {
        this.type = type;
        this.object = object;
        this.name = name;
        this.titleOfRest = titleOfRest;
    }

    public ItemUserTeamCheck(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public String getTitleOfRest() {
        return titleOfRest;
    }

    public int getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }
}
