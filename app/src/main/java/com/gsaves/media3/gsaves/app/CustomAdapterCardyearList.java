package com.gsaves.media3.gsaves.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterCardyearList extends BaseAdapter {
    Context context;
    ArrayList<String> years;
    LayoutInflater inflter;

    public CustomAdapterCardyearList(Context context, ArrayList<String> years) {
        this.context = context;
        this.years = years;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return years.size();
    }

    @Override
    public Object getItem(int position) {
        return years.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate(R.layout.custom_spinner_yearlist, null);

        TextView names = (TextView) v.findViewById(R.id.tv_card_year);

        names.setText(years.get(position));
        return v;
    }
}
