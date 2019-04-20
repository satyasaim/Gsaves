package com.gsaves.media3.gsaves.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.LayoutInflater.*;

public class CustomAdapterCartItem extends RecyclerView.Adapter<CustomAdapterCartItem.MyViewHolder> {

    Context context;
    ArrayList<String> prod_name,discount_price;
    ArrayList<String> item_remove;
    boolean del_status=false;
    float finalamount=Getproductdata.calculatetotal();
    float finalamount2=finalamount;




    public CustomAdapterCartItem(Context context, ArrayList<String> prod_name, ArrayList<String> discount_price,ArrayList<String> item_remove) {
        this.context = context;
        this.prod_name = prod_name;
        this.discount_price = discount_price;
        this.item_remove=item_remove;

    }

    public void delete(int position,float amount) {
        //removes the row
        System.out.println("deleted pos:"+position);

        System.out.println("after call del amount:"+amount);




            prod_name.remove(position);
            discount_price.remove(position);
            item_remove.remove(position);
            del_status=true;
            new PrefManagerCart(context).saveArrayList(prod_name, "product_names");
            new PrefManagerCart(context).saveArrayList(discount_price, "discount_prices");
            new PrefManagerCart(context).saveArrayList(item_remove, "item_remove");


        float finalSum_amount;
        TextView tv=((DashboardActivity) context).findViewById(R.id.tv_wallet_code);
        System.out.println("retrieved pos"+position);
        if(position==0) {
            finalSum_amount = 0;
            System.out.println("final amount"+ finalSum_amount);

            tv.setText(String.valueOf(finalSum_amount));
            new PrefManagerPayment(context).updateTotal(finalSum_amount,"total_amount");
            WalletFragment.delete_code=1;
        }
        else
        {
            try
            {

                //float finalamount=Float.valueOf(new PrefManagerPayment(context).getDiscountTotal());

                System.out.println("from pfmngr:"+new PrefManagerPayment(context).getDiscountTotal());
                System.out.println("from class:"+Getproductdata.calculatetotal());

            //if(position==1)

            System.out.println("amount from prefmngr:"+finalamount);
            //{
                System.out.println("discount price:"+amount);
                if(finalamount2>0) {
                    finalSum_amount = finalamount2 - amount;
                    finalamount2=finalSum_amount;
                }
                else if(finalamount==0)
                {
                    finalSum_amount=amount;
                }
                else
                {
                    finalSum_amount=0;
                }
                tv.setText(String.valueOf(finalSum_amount));
                new PrefManagerPayment(context).updateTotal(finalSum_amount,"total_amount");
            //}
            /*else {
                finalSum_amount = finalamount - Float.valueOf(discount_price.get(position));
                tv.setText(String.valueOf(finalSum_amount));
                new PrefManagerPayment(context).updateTotal(finalSum_amount,"total_amount");
            }*/
                System.out.println(finalSum_amount);
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }


        }



        notifyItemRemoved(position);

    }
    public boolean delstatus()
    {
        return del_status;
    }
    public float getUpdatedamount()
    {
        return DashboardActivity.sum_amount;
    }
    public void deleteAll(Context context)
    {
        try {
        prod_name.clear();
        discount_price.clear();
        item_remove.clear();
            new PrefManagerCart(context).deleteAll();
            new PrefManagerCart(context);
        notifyDataSetChanged();
        }catch(Exception e){Log.d("error",e.getMessage());}
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = from(parent.getContext()).inflate(R.layout.cart_product,parent, false);
        CustomAdapterCartItem.MyViewHolder vh = new CustomAdapterCartItem.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        holder.tvv_cartitem_title.setText(prod_name.get(i));
        holder.tvv_item_disprice.setText(discount_price.get(i));
        holder.ibb_remove_cartitem.setImageResource(Integer.parseInt(item_remove.get(i)));
        final String updateamt = discount_price.get(i);




        //final float[] finalSum_amount = {sum_amount};
        holder.ibb_remove_cartitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                // Setting Alert Dialog Title
                alertDialogBuilder.setTitle("Confirm Delete..!!!");
                // Icon Of Alert Dialog
                //alertDialogBuilder.setIcon(R.drawable.question);
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("Are you sure,You want to delete");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(holder.getAdapterPosition(),Float.parseFloat(updateamt));
                        System.out.println("remove amount ::::"+updateamt);








                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this,"You clicked over No",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
       try {
           if (prod_name.size() != 0) {
               return prod_name.size();
           } else

               return 0;
       }catch (Exception e){
           Toast.makeText(context,"No items in the wallet",Toast.LENGTH_LONG).show();
       }
       return 0;
    }

    public static int getTotal()
    {


        return 0;
    }
    public int getPosition()
    {
        try {
            if (prod_name.size() != 0) {
                return (prod_name.size() - 1);
            }
            else
                return 0;
        }catch (Exception e){
            Toast.makeText(context,"No items in the wallet",Toast.LENGTH_LONG).show();
        }
        return -1;

    }
    public void insertItem(Context context,int position,String cart_item,String dprice,String delete)
    {

try {
    //position=getPosition();
    prod_name.add(position + 1, cart_item);
    discount_price.add(position + 1, dprice);
    item_remove.add(position + 1, delete);

    new PrefManagerCart(context).saveArrayList(prod_name, "product_names");
    new PrefManagerCart(context).saveArrayList(discount_price, "discount_prices");
    new PrefManagerCart(context).saveArrayList(item_remove, "item_remove");

    new PrefManagerPayment(context).updateTotal(Getproductdata.calculatetotal(),"total_amount");

    notifyDataSetChanged();
}catch(Exception e){Log.d("error",e.getMessage());}
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView tvv_cartitem_title,tvv_item_disprice;
        ImageButton ibb_remove_cartitem;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvv_cartitem_title=(TextView)itemView.findViewById(R.id.tv_cartitem_title);
            tvv_item_disprice=(TextView)itemView.findViewById(R.id.tv_item_disprice);
            ibb_remove_cartitem=(ImageButton)itemView.findViewById(R.id.ib_remove_cartitem);




        }



    }
}
