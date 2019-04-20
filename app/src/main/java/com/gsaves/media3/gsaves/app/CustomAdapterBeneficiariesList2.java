package com.gsaves.media3.gsaves.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.Deletebeneficiaryresponse;
import com.gsaves.media3.gsaves.app.pojo.UpdatebeneficiaryDataResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapterBeneficiariesList2 extends RecyclerView.Adapter<CustomAdapterBeneficiariesList2.MyViewHolder> {
    Context context;
ArrayList<String> beneficiary_names,beneficiary_agency;
    ArrayList<String> beneficiary_mobile,beneficiary_loc,beneficiary_email,beneficiary_code;
    ArrayList beneficiary_edit,beneficiary_delete,beneficiary_id,beneficiary_address,beneficiary_userid;
    ArrayList<String> prod_name,discount_price,item_remove;

    //List<String> countryNames = new ArrayList<String>();
    String[] countryNames={"India","China","Australia","Portugle","America","New Zealand"};
    String selected_loc;
    int selected_pos;
    int retrieve_pos;
    private final static int FADE_DURATION = 1000;
    Fragment f=null;
   static int stotal;
   String paypalid;

    public CustomAdapterBeneficiariesList2()
    {

    }

    public CustomAdapterBeneficiariesList2(Context context,ArrayList<String> beneficiary_names, ArrayList<String> beneficiary_agency, ArrayList beneficiary_edit, ArrayList beneficiary_delete,ArrayList beneficiary_id,ArrayList<String> beneficiary_mobile,ArrayList<String> beneficiary_loc,ArrayList<String> beneficiary_email,ArrayList<String> beneficiary_code,ArrayList beneficiary_address,int selected_pos,ArrayList beneficiary_userid,String paypalid) {
        this.context=context;
        this.beneficiary_names = beneficiary_names;
        this.beneficiary_agency = beneficiary_agency;
        this.beneficiary_edit = beneficiary_edit;
        this.beneficiary_delete = beneficiary_delete;
        this.beneficiary_id=beneficiary_id;
        this.beneficiary_mobile=beneficiary_mobile;
        this.beneficiary_loc=beneficiary_loc;
        this.beneficiary_email=beneficiary_email;
        this.beneficiary_code=beneficiary_code;
        this.beneficiary_address=beneficiary_address;
        this.selected_pos=selected_pos;
        this.beneficiary_userid=beneficiary_userid;
        this.paypalid=paypalid;
    }

    public void getdiscountTotal(int total)
    {
        stotal=total;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beneficiaries_list_test,parent, false);


        CustomAdapterBeneficiariesList2.MyViewHolder vh = new CustomAdapterBeneficiariesList2.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tv_beneficiary_name.setText( beneficiary_names.get(position));
        holder.tv_beneficiary_agency.setText(beneficiary_agency.get(position));
       /* holder.ib_beneficiary_edit.setImageResource((Integer)beneficiary_edit.get(position));
        holder.ib_beneficiary_delete.setImageResource((Integer)beneficiary_delete.get(position));*/
        final String benefid=(String) beneficiary_id.get(position);
        final String benefmobile=(String)beneficiary_mobile.get(position);
        final String benefloc=(String)beneficiary_loc.get(position);
        final String benefemail=(String)beneficiary_email.get(position);
        final String benefcode=(String)beneficiary_code.get(position);
        final String benefaddress=(String)beneficiary_address.get(position);
        final int selectedpos=selected_pos;
        final String benefuserid=(String) beneficiary_userid.get(position);


        Log.d("benefadd",""+benefaddress);

      // new PrefManager(holder.itemView.getContext()).savebeneficiary(benefid,beneficiary_names.get(position),beneficiary_agency.get(position),benefmobile,benefloc,benefemail,benefcode,benefaddress);



         retrieve_pos=holder.getAdapterPosition();

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // Set the view to fade in
                 setFadeAnimation(holder.itemView);
                 ArrayList<String> benefdata=new ArrayList();
                 benefdata.add(benefid);
                 benefdata.add(beneficiary_names.get(position));
                 benefdata.add(String.valueOf(stotal));
                 new PrefManagerPayment(v.getContext()).saveSelectedBeneficiaryDetails(benefdata,"selected beneficiary");

                 Toast.makeText(v.getContext(),"inner adap"+new PrefManager(v.getContext()).getId(),Toast.LENGTH_SHORT).show();
                 Bundle b=new Bundle();
                 b.putString("benef id",new PrefManager(v.getContext()).getId());
                 b.putString("benef_email",benefemail);
                 b.putString("benef_code",benefcode);
                 b.putString("benef_userid",benefuserid);
                 b.putString("paypalid",paypalid);

                 f=new PayoutFragment();
                 f.setArguments(b);
                 if (f != null) {
                     AppCompatActivity activity = (AppCompatActivity) v.getContext();

                     final FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                     ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                     ft.addToBackStack(null);
                     ft.commit();

                     prod_name=new PrefManagerCart(v.getContext()).getArrayList("product_names");
                     discount_price=new PrefManagerCart(v.getContext()).getArrayList("discount_prices");
                     item_remove=new PrefManagerCart(v.getContext()).getArrayList("item_remove");
                     if(prod_name.size()!=0) {

                        /* prod_name=new ArrayList<>();
                         discount_price=new ArrayList<>();
                         item_remove=new ArrayList<>();
                         new PrefManagerCart(v.getContext()).saveArrayList(prod_name, "product_names");
                         discount_price.clear();

                         new PrefManagerCart(v.getContext()).saveArrayList(discount_price, "discount_prices");
                         item_remove.clear();
                         new PrefManagerCart(v.getContext()).saveArrayList(item_remove, "item_remove");*/

                        DashboardActivity.count=0;
                     }
                    // new PrefManagerPayment(v.getContext()).updateTotal(DashboardActivity.sum_amount,"total_amount");
/*                     TextView tv=((DashboardActivity) context).findViewById(R.id.tv_wallet_code);
                     tv.setText(String.valueOf(DashboardActivity.sum_amount));
                     */

                     //new PrefManagerCart(v.getContext()).deleteAll();
                 }


                 
             }


         });








    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
        //view.setBackgroundColor(Color.RED);
    }
    @Override
    public int getItemCount() {
        return beneficiary_names.size();

    }



    public int getPosition()
    {
        return (beneficiary_names.size()-1);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_beneficiary_name,tv_beneficiary_agency;
        /*ImageButton ib_beneficiary_edit,ib_beneficiary_delete;*/
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_beneficiary_name=(TextView)itemView.findViewById(R.id.tv_beneficiary_name);
            tv_beneficiary_agency=(TextView)itemView.findViewById(R.id.tv_beneficiary_agency);
           /* ib_beneficiary_edit=(ImageButton)itemView.findViewById(R.id.ib_beneficiary_edit);
            ib_beneficiary_delete=(ImageButton)itemView.findViewById(R.id.ib_beneficiary_delete);*/
        }
    }
}
