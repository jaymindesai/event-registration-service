package com.ers.domain;

import lombok.Getter;

public enum City {

    AHMEDABAD("Ahmedabad"),
    KOLKATA("Kolkata"),
    DELHI("Delhi"),
    MUMBAI("Mumbai");

    @Getter
    private String value;

    City(String value){
        this.value = value;
    }
}
