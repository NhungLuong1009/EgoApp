package com.example.egoapp.Object;

public class Policy {
    private int policyID;
    private String policyType;
    private int policyPrice;

    public Policy(int policyID, String policyType, int policyPrice) {
        this.policyID = policyID;
        this.policyType = policyType;
        this.policyPrice = policyPrice;
    }

    public int getPolicyID() {
        return policyID;
    }

    public void setPolicyID(int policyID) {
        this.policyID = policyID;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public int getPolicyPrice() {
        return policyPrice;
    }

    public void setPolicyPrice(int policyPrice) {
        this.policyPrice = policyPrice;
    }
}
