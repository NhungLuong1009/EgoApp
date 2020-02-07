package com.example.egoapp;

public class ShareData {

    public static String something = "";
    public static String something2 = "";

    public static String mTitle[] = {"Toronto to Ottawa",
            "Toronto to Waterloo",
            "Toronto to Cambridge",
            "Toronto to Quebec",
            "Toronto to Vancouver"};

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
}
