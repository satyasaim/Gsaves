package com.gsaves.media3.gsaves.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapterCardmonthList extends BaseAdapter {
    Context context;
    String[] countryNames;
    LayoutInflater inflter;

    public CustomAdapterCardmonthList(Context context, String[] countryNames) {
        this.context = context;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return countryNames.length;
    }

    @Override
    public Object getItem(int position) {
        return countryNames[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate(R.layout.custom_spinner_monthlist, null);

        TextView names = (TextView) v.findViewById(R.id.tv_card_month_name);

        names.setText(countryNames[position]);
        return v;
    }
}
