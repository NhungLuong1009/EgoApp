//* FILE			: CityDB.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info

package com.example.egoapp;

public class CityNames {

    private String cityName;

    public CityNames(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return this.cityName;
    }
}


