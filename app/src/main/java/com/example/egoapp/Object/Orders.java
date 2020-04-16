//* FILE			: Orders.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines the class for Orders
package com.example.egoapp.Object;

public class Orders {
    private int orderID;
    private String orderDate;
    private String orderTime;
    private String startCity;
    private String endCity;
    private int numOfAdults;
    private int numOfChild;
    private String roundTrip;
    private double miles;

    public Orders(String orderDate, String orderTime, String startCity, String endCity, int numOfAdults, int numOfChild, String roundTrip, double miles) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.startCity = startCity;
        this.endCity = endCity;
        this.numOfAdults = numOfAdults;
        this.numOfChild = numOfChild;
        this.roundTrip = roundTrip;
        this.miles = miles;
    }

    public Orders() {}

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public int getNumOfAdults() {
        return numOfAdults;
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
    }

    public int getNumOfChild() {
        return numOfChild;
    }

    public void setNumOfChild(int numOfChild) {
        this.numOfChild = numOfChild;
    }

    public String getRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(String roundTrip) {
        this.roundTrip = roundTrip;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }
}