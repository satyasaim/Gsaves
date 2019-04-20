package com.gsaves.media3.gsaves.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefManagerPayment {
    Context context;
    static String ftotal;


    public PrefManagerPayment(Context context) {
        this.context = context;
       // proditems_name=new ArrayList<>();
       // itemsdiscount_prices=new ArrayList<>();
    }




    public void saveSelectedBeneficiaryDetails(ArrayList<String> list, String key){
        SharedPreferences prefs = context.getSharedPreferences("PaymentPageDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

   public ArrayList<String> getSelectedBeneficiaryDetails(String key){
        SharedPreferences prefs = context.getSharedPreferences("PaymentPageDetails", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveSelectedCardDetails(ArrayList<String> list, String key){
        SharedPreferences prefs = context.getSharedPreferences("PaymentPageDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<String> getSelectedCardDetails(String key){
        SharedPreferences prefs = context.getSharedPreferences("PaymentPageDetails", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }


    public void saveDiscountTotal(float total, String key){
        SharedPreferences prefs = context.getSharedPreferences("PaymentPageDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, String.valueOf(total));
        editor.commit();     // This line is IMPORTANT !!!
    }
    public  String getDiscountTotal()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PaymentPageDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("total_amount", ftotal);
    }
    public void updateTotal(float total,String key){
try {
    SharedPreferences prefs = context.getSharedPreferences("PaymentPageDetails", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();
        /*if(total>=0.0f && Float.parseFloat(getDiscountTotal())>0 ) {
            total = Float.parseFloat(getDiscountTotal()) - total;
        }
        else
        {
            total=0.0f;
        }*/
    ftotal = String.valueOf(total);
    editor.putString(key, String.valueOf(total));
    editor.commit();

}catch(Exception e)
{
   System.out.println(e.getLocalizedMessage());

}


    }


    public void deleteAlll()
    {
        SharedPreferences prefs = context.getSharedPreferences("PaymentPageDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.apply();
        editor.clear();

    }

}
