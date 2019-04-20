package com.gsaves.media3.gsaves.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.Deletebeneficiaryresponse;
import com.gsaves.media3.gsaves.app.pojo.Deletecardresponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapterCardsList extends RecyclerView.Adapter<CustomAdapterCardsList.MyViewHolder> {
    Context context;
ArrayList<String> card_names,holder_names;
ArrayList card_delete, card_id;
String benefid;
    ArrayList card_number;
    ArrayList card_cvvs;
    ArrayList card_expiry_month;
    ArrayList card_expiry_year;

    Fragment f=null;

    public CustomAdapterCardsList(Context context,ArrayList<String> card_id,ArrayList<String> card_names, ArrayList<String> holder_names, ArrayList card_delete,String benefid,ArrayList card_number,ArrayList card_cvvs,ArrayList card_expiry_month,ArrayList card_expiry_year) {
        this.context=context;
        this.card_id=card_id;
        this.card_names = card_names;
        this.holder_names = holder_names;
        this.card_delete = card_delete;
        this.benefid=benefid;

        this.card_number=card_number;
        this.card_cvvs=card_cvvs;
        this.card_expiry_month=card_expiry_month;
        this.card_expiry_year=card_expiry_year;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_list,parent, false);

        CustomAdapterCardsList.MyViewHolder vh = new CustomAdapterCardsList.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
    holder.tv_card_name.setText(card_names.get(position));
        holder.tv_holder_name.setText(holder_names.get(position));
        holder.ib_card_delete.setImageResource((Integer)card_delete.get(position));
        final String cardid=(String)card_id.get(position);

        final String cardnum=(String)card_number.get(position);
        final String cardcvv=(String)card_cvvs.get(position);
        final String card_month=(String)card_expiry_month.get(position);
        final String card_year=(String)card_expiry_year.get(position);

        if(benefid!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),""+benefid,Toast.LENGTH_LONG).show();
                    new CustomAdapterBeneficiariesList2().setFadeAnimation(holder.itemView);
                    ArrayList<String> selectedcard=new ArrayList<>();
                    selectedcard.add(card_names.get(position));
                    selectedcard.add(cardnum);
                    selectedcard.add(cardcvv);
                    selectedcard.add(card_month);
                    selectedcard.add(card_year);

                    ArrayList<String> benefdata=new PrefManagerPayment(v.getContext()).getSelectedBeneficiaryDetails("selected beneficiary");

                    Bundle finalbun=new Bundle();
                    finalbun.putStringArrayList("carddata",selectedcard);
                    finalbun.putStringArrayList("benefdata",benefdata);
                    f=new PaymentPageFragment();
                    f.setArguments(finalbun);

                    if (f != null) {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();

                        final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                }
            });
        }

        holder.ib_card_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setTitle("Confirm Delete..!!!");
                alertDialogBuilder.setMessage("Are you sure,You want to delete");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        (Api.getClient().deletecard(cardid)).enqueue(new Callback<Deletecardresponse>() {
                            @Override
                            public void onResponse(Call<Deletecardresponse> call, Response<Deletecardresponse> response) {

                                Toast.makeText(alertDialogBuilder.getContext(),response.body().getStatus(),Toast.LENGTH_LONG).show();

                                delete(holder.getAdapterPosition());

                            }

                            @Override
                            public void onFailure(Call<Deletecardresponse> call, Throwable t) {

                            }
                        });
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
        return card_names.size();
    }
    public void delete(int position)
    {
        card_names.remove(position);
        notifyItemRemoved(position);
    }
    public int getPosition()
    {
        return (card_names.size()-1);
    }
    public void insertItem(Context context,int position,String id,String cardname,String card_holdername,Object delete)
    {
        position=getPosition();
        card_id.add(position+1,id);
        card_names.add(position+1,cardname);
        holder_names.add(position+1,card_holdername);
        card_delete.add(position+1,delete);

        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_card_name,tv_holder_name;
        ImageButton ib_card_delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_card_name=(TextView)itemView.findViewById(R.id.tv_card_name);
            tv_holder_name=(TextView)itemView.findViewById(R.id.tv_card_holder);
            ib_card_delete=(ImageButton)itemView.findViewById(R.id.ib_card_delete);
        }
    }
}
