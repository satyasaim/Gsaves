package com.gsaves.media3.gsaves.app;

import android.content.Context;

import java.util.ArrayList;

public class Getproductdata {
    String product_name,product_price,item_rem;
    static ArrayList<String> prod_name;
    static ArrayList<String> discount_price;
    ArrayList<String> item_remove;
    ArrayList<String> prod_name2=new ArrayList<>();
    ArrayList<String> discount_price2=new ArrayList<>();
    ArrayList<String> item_remove2=new ArrayList<>();
    static ArrayList<String> discount_total=new ArrayList<>();
   static Context context;
    int count;
    static int mycount=0;
    int arraycount;


    public Getproductdata(String product_name, String product_price, String item_remov,Context context,int count) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.item_rem = item_remov;
        this.context=context;
        this.count=count;
        try {
            arraycount = new PrefManagerCart(context).getArrayList("product_names").size();
            System.out.println("arraycount::"+arraycount);
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        if(arraycount==0) {
            prod_name = new ArrayList<>();
            discount_price = new ArrayList<>();
            item_remove = new ArrayList<>();
        }
        else {
            prod_name = new PrefManagerCart(context).getArrayList("product_names");
            discount_price = new PrefManagerCart(context).getArrayList("discount_prices");
            item_remove = new PrefManagerCart(context).getArrayList("item_remove");

            for(String  i:prod_name)
            {
                System.out.println(i.toString());
            }
        }
    }
    void addProduct()
    {
        System.out.println("check count val:"+count);
        System.out.println("check prod_name size:"+prod_name.size());
        System.out.println("check delete_status:"+WalletFragment.delete_code);
        mycount++;
        if(WalletFragment.delete_code==1 || count==1 ) {
            new PrefManagerCart(context).deleteAll();
            prod_name.add(product_name);
            discount_price.add(product_price);
            item_remove.add(item_rem);

        }
        else
        {




            System.out.println("before Array size:"+prod_name.size()+" "+mycount);
            prod_name2.add(product_name);
            discount_price2.add(product_price);
            item_remove2.add(item_rem);
           // prod_name.
            CustomAdapterCartItem customAdaptercartitems = new CustomAdapterCartItem(context, prod_name2, discount_price2,  item_remove2);
            customAdaptercartitems.insertItem(context, customAdaptercartitems.getPosition(), product_name, product_price, item_rem);
            customAdaptercartitems.notifyDataSetChanged();

            prod_name.add(product_name);
            discount_price.add(product_price);
            item_remove.add(item_rem);




        }

        System.out.println(product_name+" "+product_price+" "+item_rem+"  del code: "+WalletFragment.delete_code);
        System.out.println("Array size:"+prod_name.size()+" "+mycount);
        try {

            new PrefManagerCart(context).saveArrayList(prod_name, "product_names");
            new PrefManagerCart(context).saveArrayList(discount_price, "discount_prices");
            new PrefManagerCart(context).saveArrayList(item_remove, "item_remove");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    static float calculatetotal()
    {
        float total=0;
        try {
            discount_total = discount_price;
            for (String i : discount_total) {
                total += Float.parseFloat(i);
            }
            new PrefManagerPayment(context).updateTotal(total,"total_amount");
            return total;
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        new PrefManagerPayment(context).updateTotal(total,"total_amount");
        return total;
    }
    static int prodsSize()
    {
        return prod_name.size();
    }


}
