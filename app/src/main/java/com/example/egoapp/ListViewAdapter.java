//* FILE			: ListViewAdapter.java
//* PROJECT			: SENG2040-20W-Mobile Application Development - Assignment #1
//* PROGRAMMER		: Nhung Luong, Younchul Choi, Trung Nguyen, Abdullar
//* FIRST VERSON	: Feb 8, 2018
//* DESCRIPTION		: The file defines the ListViewAdapter
package com.example.egoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.egoapp.Object.Cities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    //define Logger Class
    private static final String LOGTAG = "ListViewAdapter.class";

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Cities> CityNamesList = null;
    private ArrayList<Cities> arraylist;

    public ListViewAdapter(Context context, ArrayList<Cities> CityNamesList) {
        Log.i(LOGTAG, "Running the ListViewAdapter screen....");
        mContext = context;
        this.CityNamesList = CityNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Cities>();
        this.arraylist.addAll(CityNamesList);
    }


    public class ViewHolder {
        TextView name;
    }


    @Override
    public int getCount() {
        return CityNamesList.size();
    }


    @Override
    public Cities getItem(int position) {
        return CityNamesList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    /*
     * Function: getView
     * Description:
     * Input:
     * Return: none
     */
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        Log.d(LOGTAG, "getView running... ");
        try{
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.list_view_items, null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.name);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            holder.name.setText(CityNamesList.get(position).getCityName());
        }
        catch(Exception e) {
            Log.e(LOGTAG, "getView method Exception: " + e.getMessage());
        }

        return view;
    }


    /*
     * Function: filter
     * Description:
     * Input:
     * Return: none
     */
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Log.d(LOGTAG, "filter running... ");
        try{
            CityNamesList.clear();
            if (charText.length() == 0) {
                CityNamesList.addAll(arraylist);
            } else {
                for (Cities wp : arraylist) {
                    if (wp.getCityName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        CityNamesList.add(wp);
                    }
                }
            }
        }
        catch(Exception e){
            Log.e(LOGTAG, "filter method Exception : " + e.getMessage());
        }

        notifyDataSetChanged();
    }

}
