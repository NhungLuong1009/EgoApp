package com.example.egoapp;

public class PriceVO {

    String startCity;
    String endCity;
    int price;

    public PriceVO(String startCity, String endCity, int prices){
        this.startCity=startCity;
        this.endCity = endCity;
        this.price = prices;
    }

}
