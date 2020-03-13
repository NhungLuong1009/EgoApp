package com.example.egoapp.Object;

public class Payment {
    private int payID;
    private int orderID;
    private int accountID;
    private int priceTotal;
    private int paymentType;

    public Payment() {}

    public Payment(int payID, int orderID, int accountID, int priceTotal, int paymentType) {
        this.payID = payID;
        this.orderID = orderID;
        this.accountID = accountID;
        this.priceTotal = priceTotal;
        this.paymentType = paymentType;
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

    public int getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(int priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }
}
