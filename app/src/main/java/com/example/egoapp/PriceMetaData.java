/*
 * File: PriceMetaData.java
 * Name: Trung Nguyen - Abdullah - Nhung Luong - Huynchul Choi
 * Date: 08 Feb, 2020
 * Description: contains the metadata for Trip Planner App
 */
package com.example.egoapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PriceMetaData {

    public static Map setPriceMeta() {
        Map<PriceVO, String> hMap = new HashMap<>();
        hMap.put(new PriceVO("Waterloo", "Kitchener", 50), "Waterloo->Kitchener");
        hMap.put(new PriceVO("Kitchener", "Waterloo", 50), "Kitchener->Waterloo");
        hMap.put(new PriceVO("Waterloo", "Cambridge", 70), "Waterloo->Cambridge");
        hMap.put(new PriceVO("Cambridge", "Waterloo", 70), "Cambridge->Waterloo");
        hMap.put(new PriceVO("Kitchener", "Cambridge", 40), "Kitchener->Cambridge");
        hMap.put(new PriceVO("Cambridge", "Kitchener", 40), "Cambridge->Kitchener");
        hMap.put(new PriceVO("Waterloo", "Mississauga", 100), "Waterloo->Mississauga");
        hMap.put(new PriceVO("Mississauga", "Waterloo", 100), "Mississauga->Waterloo");
        hMap.put(new PriceVO("Kitchener", "Mississauga", 85), "Kitchener->Mississauga");
        hMap.put(new PriceVO("Mississauga", "Kitchener", 85), "Mississauga->Kitchener");
        hMap.put(new PriceVO("Cambridge", "Mississauga", 60), "Cambridge->Mississauga");
        hMap.put(new PriceVO("Mississauga", "Cambridge", 60), "Mississauga->Cambridge");
        hMap.put(new PriceVO("Waterloo", "Hamilton", 120), "Waterloo->Hamilton");
        hMap.put(new PriceVO("Hamilton", "Waterloo", 120), "Hamilton->Waterloo");
        hMap.put(new PriceVO("Kitchener", "Hamilton", 100), "Kitchener->Hamilton");
        hMap.put(new PriceVO("Hamilton", "Kitchener", 100), "Hamilton->Kitchener");
        hMap.put(new PriceVO("Cambridge", "Hamilton", 85), "Cambridge->Hamilton");
        hMap.put(new PriceVO("Hamilton", "Cambridge", 85), "Hamilton->Cambridge");
        hMap.put(new PriceVO("Mississauga", "Hamilton", 65), "Waterloo->Hamilton");
        hMap.put(new PriceVO("Hamilton", "Mississauga", 65), "Hamilton->Waterloo");
        hMap.put(new PriceVO("Waterloo", "Toronto", 140), "Waterloo->Toronto");
        hMap.put(new PriceVO("Toronto", "Waterloo", 140), "Toronto->Waterloo");
        hMap.put(new PriceVO("Kitchener", "Toronto", 120), "Kitchener->Toronto");
        hMap.put(new PriceVO("Toronto", "Kitchener", 120), "Toronto->Kitchener");
        hMap.put(new PriceVO("Cambridge", "Toronto", 100), "Cambridge->Toronto");
        hMap.put(new PriceVO("Toronto", "Cambridge", 100), "Toronto->Cambridge");
        hMap.put(new PriceVO("Mississauga", "Toronto", 60), "Mississauga->Toronto");
        hMap.put(new PriceVO("Toronto", "Mississauga", 60), "Toronto->Mississauga");
        hMap.put(new PriceVO("Hamilton", "Toronto", 40), "Hamilton->Toronto");
        hMap.put(new PriceVO("Toronto", "Hamilton", 40), "Toronto->Hamilton");
        hMap.put(new PriceVO("Waterloo", "Kingston", 250), "Waterloo->Kingston");
        hMap.put(new PriceVO("Kingston", "Waterloo", 250), "Kingston->Waterloo");
        hMap.put(new PriceVO("Kitchener", "Kingston", 240), "Kitchener->Kingston");
        hMap.put(new PriceVO("Kingston", "Kitchener", 240), "Kingston->Kitchener");
        hMap.put(new PriceVO("Cambridge", "Kingston", 230), "Cambridge->Kingston");
        hMap.put(new PriceVO("Kingston", "Cambridge", 230), "Kingston->Cambridge");
        hMap.put(new PriceVO("Mississauga", "Kingston", 215), "Mississauga->Kingston");
        hMap.put(new PriceVO("Kingston", "Mississauga", 215), "Kingston->Mississauga");
        hMap.put(new PriceVO("Hamilton", "Kingston", 200), "Hamilton->Kingston");
        hMap.put(new PriceVO("Kingston", "Hamilton", 200), "Kingston->Hamilton");
        hMap.put(new PriceVO("Toronto", "Kingston", 180), "Toronto->Kingston");
        hMap.put(new PriceVO("Kingston", "Toronto", 180), "Kingston->Toronto");
        hMap.put(new PriceVO("Waterloo", "Ottawa", 300), "Waterloo->Ottawa");
        hMap.put(new PriceVO("Ottawa", "Waterloo", 300), "Ottawa->Waterloo");
        hMap.put(new PriceVO("Kitchener", "Ottawa", 280), "Kitchener->Ottawa");
        hMap.put(new PriceVO("Ottawa", "Kitchener", 280), "Ottawa->Kitchener");
        hMap.put(new PriceVO("Cambridge", "Ottawa", 270), "Cambridge->Ottawa");
        hMap.put(new PriceVO("Ottawa", "Cambridge", 270), "Ottawa->Cambridge");
        hMap.put(new PriceVO("Mississauga", "Ottawa", 255), "Mississauga->Ottawa");
        hMap.put(new PriceVO("Ottawa", "Mississauga", 255), "Ottawa->Mississauga");
        hMap.put(new PriceVO("Hamilton", "Ottawa", 240), "Hamilton->Ottawa");
        hMap.put(new PriceVO("Ottawa", "Hamilton", 240), "Ottawa->Hamilton");
        hMap.put(new PriceVO("Toronto", "Ottawa", 230), "Toronto->Ottawa");
        hMap.put(new PriceVO("Ottawa", "Toronto", 230), "Ottawa->Toronto");
        hMap.put(new PriceVO("Kingston", "Ottawa", 100), "Kingston->Ottawa");
        hMap.put(new PriceVO("Ottawa", "Kingston", 100), "Ottawa->Kingston");
        hMap.put(new PriceVO("Toronto", "Quebec", 200), "Toronto->Quebec");
        hMap.put(new PriceVO("Toronto", "Vancouver", 500), "Toronto->Vancouver");

        return hMap;
    }

    /*
     * Function: getPriceInfo
     * Description: return the price details to the screen based on start city & end city
     * Input: Map<PriceVO, String> map, String startCity, String endCity
     * Return: Int - price
     */
    public static int getPriceInfo(Map<PriceVO, String> map, String startCity, String endCity){
        PriceVO priceInfo;
        int price=0;
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            priceInfo = (PriceVO) pair.getKey();

            if((priceInfo.startCity.equals(startCity)) && (priceInfo.endCity.equals(endCity))){
                System.out.println(priceInfo.startCity + ":" + priceInfo.endCity + ":" + priceInfo.price );
                price = priceInfo.price;
                break;
            }
        }

        return price;
    }
}