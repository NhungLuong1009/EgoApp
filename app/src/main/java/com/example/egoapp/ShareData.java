//* FILE			: ShareData.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines share data for using in the app


package com.example.egoapp;

import java.lang.String;

public class ShareData {

    public static int priceForAdult = 3000;
    public static int priceForChild = 2000;
    public static int totalPrice = 0;

    public static String mTitle[] = {"Toronto to Ottawa",
            "Toronto to Waterloo",
            "Toronto to Cambridge",
            "Toronto to Quebec",
            "Toronto to Vancouver"};

    public static int selectedTrip = 0;
    public static Boolean makeOwnTrip = false;

    public static String mDescription[] = {"", "", "", "", ""};
    public static String mDistance[] = {"400Km", "110Km", "100Km", "800Km"};
    public static String mTime[] = {"4h 21m", "1h 12m", "1h", "8h"};

    public static int images[] = {R.drawable.ottawa,
            R.drawable.waterloo,
            R.drawable.cambridge,
            R.drawable.quebec,
            R.drawable.vancouver};

    // Make trip global variables
    public static String tripSelectedDate = "";
    public static String tripSelectedTime = "";
    public static String tripSelectedStartCity = "";
    public static String tripSelectedEndCity = "";
    public static String tripNumberOfAdult = "";
    public static String tripNumberOfChildren = "";
    public static String tripNotificationUserName = "";
    public static String tripNotificationDate = "";
    public static String userName = "";
    public static String userPhoneNumber = "";

    public static String GetStartCity(String mTitle)
    {
        String delimeter = " to ";
        String[] tripSelectedStartCity = mTitle.split(delimeter);
        return tripSelectedStartCity[0];
    }

    public static String GetEndCity(String mTitle)
    {
        String delimeter = " to ";
        String[] tripSelectedEndCity = mTitle.split(delimeter);
        return tripSelectedEndCity[1];
    }
    public static String tripSelectedRoundTripOption = "";

    public static int StartCityISEmpty = 1;
    public static int EndCityISEmpty = 2;
    public static int DateISEmpty = 3;
    public static int TimeIsEmpty = 4;
    public static int NoOfPassEmpty = 5;
    public static int RoundTripIsEmpty = 6;
    public static int SUCCESS = 7;
}