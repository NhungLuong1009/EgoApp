//* FILE			: ShareData.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines share data for using in the app



package com.example.egoapp;

public class ShareData {

    public static String something = "";
    public static String something2 = "";

    public static String mTitle[] = {"Toronto to Ottawa",
            "Toronto to Waterloo",
            "Toronto to Cambridge",
            "Toronto to Quebec",
            "Toronto to Vancouver"};

    public static int selectedTrip = 0;
    public static Boolean makeOwnTrip = false;

    public static String mDescription[] = {"$140", "$50", "$100", "$200", "$500"};

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
    public static String tripNumberOfPassenger = "";
    public static Boolean tripSelectedRoundTripOption = false;

    public static String GetStartCity(String mTitle)
    {
        String delimeter = " to ";
        String[] tripSelectedStartCity = mTitle.split(delimeter);
        return tripSelectedStartCity[0];
    }

    public static String GetEndCity(String mTitle[])
    {
        String delimeter = " to ";
        String[] tripSelectedEndCity = mTitle.split(delimeter);
        return tripSelectedEndCity[1];
    }
    public static boolean tripSelectedRoundTripOption = false;

    public static int StartCityISEmpty = 1;
    public static int EndCityISEmpty = 2;
    public static int DateISEmpty = 3;
    public static int TimeIsEmpty = 4;
    public static int NoOfPassEmpty = 5;
    public static int RoundTripIsEmpty = 6;
    public static int SUCCESS = 7;
}
