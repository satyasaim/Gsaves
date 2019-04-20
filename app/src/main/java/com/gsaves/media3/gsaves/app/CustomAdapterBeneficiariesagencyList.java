package com.gsaves.media3.gsaves.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapterBeneficiariesagencyList extends BaseAdapter {
    Context context;
    String[] countryNames;
    LayoutInflater inflter;

    public CustomAdapterBeneficiariesagencyList(Context context, String[] countryNames) {
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate(R.layout.custom_spinner_beneficiarylist, null);

        TextView names = (TextView) v.findViewById(R.id.tv_benef_list_name);

        names.setText(countryNames[position]);
        return v;
    }
}
