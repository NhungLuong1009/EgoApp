//* FILE			: Cities.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #2
//* PROGRAMMER		: Nhung Luong, Hyunchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSION	: Mar 14, 2020
//* DESCRIPTION		: The file defines the first screen of the app asking for getting trip info

package com.example.egoapp.Object;

public class Cities {
    private long cityID;
    private String cityName;

    public Cities(long cityID, String cityName) {
        this.cityID = cityID;
        this.cityName = cityName;
    }

    public Cities() {}

    public long getCityID() {
        return cityID;
    }

    public void setCityID(long cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}

