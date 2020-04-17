package com.example.egoapp.Object;

import android.content.ContentValues;

import com.example.egoapp.DBHandler.PaymentDB;

public class Payment {
    private int payID;
    private int orderID;
    private int accountID;
    private String tripFrom;
    private String tripTo;
    private float priceTotal;

    public Payment() {}

    public Payment(ContentValues values) {
        this.payID = (int) values.get(PaymentDB.PAYMENT_ID);
        this.orderID = (int) values.get(PaymentDB.PAYMENT_ORDER_ID);
        this.accountID = (int) values.get(PaymentDB.PAYMENT_ACCOUNT_ID);
        this.tripFrom = (String) values.get(PaymentDB.PAYMENT_TRIP_FROM);
        this.tripTo = (String) values.get(PaymentDB.PAYMENT_TRIP_TO);
        this.priceTotal = (int) values.get(PaymentDB.PAYMENT_PRICE);
    }

    public Payment(float priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getPayID() {
        return payID;
    }

    public void setPayID(int payID) {
        this.payID = payID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getTripFrom() {
        return tripFrom;
    }

    public void setTripFrom(String tripFrom) {
        this.tripFrom = tripFrom;
    }

    public String getTripTo() {
        return tripTo;
    }

    public void setTripTo(String tripTo) {
        this.tripTo = tripTo;
    }

    public float getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(float priceTotal) {
        this.priceTotal = priceTotal;
    }

}
