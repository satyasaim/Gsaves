package com.gsaves.media3.gsaves.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrefManagerCart {
    Context context;
    ArrayList<String> proditems_name,itemsdiscount_prices;

    public PrefManagerCart(Context context) {
        this.context = context;
       // proditems_name=new ArrayList<>();
       // itemsdiscount_prices=new ArrayList<>();
    }




    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = context.getSharedPreferences("CartItemDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

   public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = context.getSharedPreferences("CartItemDetails", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void deleteAll()
    {
        SharedPreferences prefs = context.getSharedPreferences("CartItemDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }


    /*private static SharedPreferences getPref(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveString(Context context, String key, String value) {
        getPref(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        return getPref(context).getString(key, defValue);
    }

    public static void removeString(Context context, String key) {
        getPref(context).edit().remove(key).apply();
    }

    public static List getAllValues(Context context) {
        //Map values = getPref(context).getAll();
        List prefDataList = new ArrayList<>();

        *//* for(Map.Entry entry : values.entrySet()) {
            PrefCartData prefData = new PrefCartData();
            prefData.key = (String) entry.getKey();
            prefData.value = entry.getValue().toString();
            prefDataList.add(prefData);
        }*//*
        return prefDataList;
    }*/
}
