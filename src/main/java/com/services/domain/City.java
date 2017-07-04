package com.services.domain;

public enum City {

    AHMEDABAD("Ahmedabad"),
    KOLKATA("Kolkata"),
    DELHI("Delhi"),
    MUMBAI("Mumbai");

    private String value;

    City(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
}
