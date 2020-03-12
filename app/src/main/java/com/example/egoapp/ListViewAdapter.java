package com.example.egoapp;

import android.content.Context;
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

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Cities> CityNamesList = null;
    private ArrayList<Cities> arraylist;

    public ListViewAdapter(Context context, ArrayList<Cities> CityNamesList) {
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

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
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
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
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
        notifyDataSetChanged();
    }

}
