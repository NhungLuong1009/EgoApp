/*
 * File: PriceVO.java
 * Name: Trung Nguyen - Abdullah - Nhung Luong - Huynchul Choi
 * Date: 08 Feb, 2020
 * Description: contains the Price info data for Trip Planner App
 */
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
