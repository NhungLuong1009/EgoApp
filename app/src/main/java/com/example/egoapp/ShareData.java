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
    public static boolean tripSelectedRoundTripOption = false;

    public static int StartCityISEmpty = 1;
    public static int EndCityISEmpty = 2;
    public static int DateISEmpty = 3;
    public static int TimeIsEmpty = 4;
    public static int NoOfPassEmpty = 5;
    public static int RoundTripIsEmpty = 6;
    public static int SUCCESS = 7;
}
