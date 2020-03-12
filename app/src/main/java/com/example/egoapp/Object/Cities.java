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

