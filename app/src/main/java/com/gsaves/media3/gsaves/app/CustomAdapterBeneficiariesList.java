package com.gsaves.media3.gsaves.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.service.autofill.TextValueSanitizer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapterBeneficiariesList extends RecyclerView.Adapter<CustomAdapterBeneficiariesList.MyViewHolder> {
    Context context;
ArrayList<String> beneficiary_names,beneficiary_agency;
    ArrayList<String> beneficiary_mobile,beneficiary_loc,beneficiary_email,beneficiary_code;
    ArrayList beneficiary_edit,beneficiary_delete,beneficiary_id,beneficiary_address,beneficiary_userid;
    //List<String> countryNames = new ArrayList<String>();
    String[] countryNames={"India","China","Australia","Portugle","America","New Zealand"};
    String selected_loc;
    int selected_pos;
    int retrieve_pos;
    EditText et_benef_email,et_benef_mobile2;


    public CustomAdapterBeneficiariesList(Context context,ArrayList<String> beneficiary_names, ArrayList<String> beneficiary_agency, ArrayList beneficiary_edit, ArrayList beneficiary_delete,ArrayList beneficiary_id,ArrayList<String> beneficiary_mobile,ArrayList<String> beneficiary_loc,ArrayList<String> beneficiary_email,ArrayList<String> beneficiary_code,ArrayList beneficiary_address,int selected_pos,ArrayList beneficiary_userid) {
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
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beneficiaries_list,parent, false);


        CustomAdapterBeneficiariesList.MyViewHolder vh = new CustomAdapterBeneficiariesList.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tv_beneficiary_name.setText( beneficiary_names.get(position));
        holder.tv_beneficiary_agency.setText(beneficiary_agency.get(position));
        holder.ib_beneficiary_edit.setImageResource((Integer)beneficiary_edit.get(position));
        holder.ib_beneficiary_delete.setImageResource((Integer)beneficiary_delete.get(position));
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





        holder.ib_beneficiary_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.beneficiary_edit_form);
                Spinner spin=(Spinner)dialog.findViewById(R.id.spin_add_benef_agency);






                CustomAdapterBeneficiariesagencyList customAdapter=new CustomAdapterBeneficiariesagencyList(dialog.getContext(),countryNames);

                spin.setAdapter(customAdapter);

                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selected_loc=countryNames[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                final EditText et_benef_name12=(EditText)dialog.findViewById(R.id.et_benef_name12);
                final EditText et_benef_name22=(EditText)dialog.findViewById(R.id.et_benef_name22);
                 et_benef_email=(EditText)dialog.findViewById(R.id.et_benef_id2);
                final EditText et_benef_ssn2=(EditText)dialog.findViewById(R.id.et_benef_ssn2);
                 et_benef_mobile2=(EditText)dialog.findViewById(R.id.et_benef_mobile2);
                final EditText et_benef_address2=(EditText)dialog.findViewById(R.id.et_benef_address2);
                final ImageButton ib_beneficiary_edit=(ImageButton)dialog.findViewById(R.id.ib_beneficiary_edit);
                final ImageButton ib_beneficiary_delete=(ImageButton)dialog.findViewById(R.id.ib_beneficiary_delete);













                if(benefid.equals(new PrefManager(dialog.getContext()).getBenefid())) {

                    et_benef_name12.setText(new PrefManager(dialog.getContext()).getBenef_name1());
                    et_benef_name22.setText(new PrefManager(dialog.getContext()).getBenef_name2());
                    et_benef_email.setText(new PrefManager(dialog.getContext()).getBenef_email());
                    et_benef_ssn2.setText(new PrefManager(dialog.getContext()).getBenef_code());
                    et_benef_mobile2.setText(new PrefManager(dialog.getContext()).getBenef_mobile());
                    et_benef_address2.setText(new PrefManager(dialog.getContext()).getBenef_address());
                }
                else {

                       et_benef_name12.setText(holder.tv_beneficiary_name.getText());
                       et_benef_name22.setText(holder.tv_beneficiary_agency.getText());
                       et_benef_email.setText(benefemail);
                       et_benef_ssn2.setText(benefcode);
                       et_benef_mobile2.setText(benefmobile);

                       et_benef_ssn2.setText(benefcode);
                       et_benef_address2.setText(benefaddress);


                                    }



               // spin.setSelection(selectedpos,true);




                ImageButton ib_close=(ImageButton)dialog.findViewById(R.id.ib_benef_edit_close);
                ib_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        Toast.makeText(v.getContext(),"close benef",Toast.LENGTH_LONG).show();
                    }
                });
                Button bt_benef_update=(Button)dialog.findViewById(R.id.bt_benef_update);
                bt_benef_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dialog.dismiss();
                        if(validate()) {
                            (Api.getClient().updatebeneficiary(benefid, et_benef_name12.getText().toString().trim(), et_benef_name22.getText().toString().trim(), et_benef_email.getText().toString().trim(), et_benef_mobile2.getText().toString().trim(), selected_loc, et_benef_ssn2.getText().toString().trim(), et_benef_address2.getText().toString().trim(), new PrefManager(v.getContext()).getId())).enqueue(new Callback<UpdatebeneficiaryDataResponse>() {
                                @Override
                                public void onResponse(Call<UpdatebeneficiaryDataResponse> call, Response<UpdatebeneficiaryDataResponse> response) {
                                    Toast.makeText(dialog.getContext(), response.body().getStatus(), Toast.LENGTH_LONG).show();

                                    et_benef_name12.setText(et_benef_name12.getText());
                                    et_benef_name22.setText(et_benef_name22.getText());
                                    et_benef_email.setText(et_benef_email.getText());
                                    et_benef_ssn2.setText(et_benef_ssn2.getText());
                                    et_benef_mobile2.setText(et_benef_mobile2.getText());
                                    et_benef_address2.setText(et_benef_address2.getText());


                                    new PrefManager(dialog.getContext()).savebeneficiary(benefid, et_benef_name12.getText().toString().trim(), et_benef_name22.getText().toString().trim(), et_benef_mobile2.getText().toString().trim(), selected_loc, et_benef_email.getText().toString().trim(), et_benef_ssn2.getText().toString().trim(), et_benef_address2.getText().toString().trim());

                                    // new PrefManager(dialog.getContext()).deleteBeneficiarydetails();
                                    updateItem(position, benefid, et_benef_name12.getText().toString().trim(), et_benef_name22.getText().toString().trim(), R.drawable.edit, R.drawable.delete, et_benef_mobile2.getText().toString().trim(), selected_loc, et_benef_email.getText().toString().trim(), et_benef_ssn2.getText().toString().trim(), et_benef_address2.getText().toString().trim(), new PrefManager(dialog.getContext()).getId());


                                }

                                @Override
                                public void onFailure(Call<UpdatebeneficiaryDataResponse> call, Throwable t) {
                                    Toast.makeText(dialog.getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                            holder.tv_beneficiary_name.setText(et_benef_name12.getText().toString().trim());
                            holder.tv_beneficiary_agency.setText(et_benef_name22.getText().toString().trim());
                            holder.ib_beneficiary_edit.setImageResource((Integer) beneficiary_edit.get(position));
                            holder.ib_beneficiary_delete.setImageResource((Integer) beneficiary_delete.get(position));
                            dialog.dismiss();
                        }

                    }
                });
                dialog.show();
                //Toast.makeText(v.getContext(),"Add beneficiary",Toast.LENGTH_LONG).show();
                Toast.makeText(v.getContext(),""+benefid+" "+benefaddress,Toast.LENGTH_SHORT).show();
            }

            private boolean validate() {
                boolean valid = true;
                String email = et_benef_email.getText().toString();
                String phone = et_benef_mobile2.getText().toString();
                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    et_benef_email.setError("enter a valid email address");
                    valid = false;
                } else {
                    et_benef_email.setError(null);
                }
                if (phone.isEmpty()) {
                    et_benef_mobile2.setError("enter phone number");
                    valid = false;
                } else {
                    et_benef_mobile2.setError(null);
                }
                return valid;
            }
        });

        holder.ib_beneficiary_delete.setOnClickListener(new View.OnClickListener() {
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

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        (Api.getClient().deletebeneficiary(benefid)).enqueue(new Callback<Deletebeneficiaryresponse>() {
                            @Override
                            public void onResponse(Call<Deletebeneficiaryresponse> call, Response<Deletebeneficiaryresponse> response) {
                                Toast.makeText(alertDialogBuilder.getContext(),response.body().getStatus(),Toast.LENGTH_LONG).show();

                                  delete(holder.getAdapterPosition());
                            }

                            @Override
                            public void onFailure(Call<Deletebeneficiaryresponse> call, Throwable t) {
                                Toast.makeText(alertDialogBuilder.getContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

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
        return beneficiary_names.size();

    }

    public void delete(int position) { //removes the row
        beneficiary_names.remove(position);
        notifyItemRemoved(position);
    }
    public void insertItem(Context context,int position,String id,String University,String College,Object edit,Object delete,String mobile,String location,String email,String code,String address,String user_id)
    {
        position=getPosition();
        beneficiary_id.add(position+1,id);
        beneficiary_names.add(position+1,University);
        beneficiary_agency.add(position+1,College);
        beneficiary_edit.add(position+1,edit);
        beneficiary_delete.add(position+1,delete);
        beneficiary_mobile.add(position+1,mobile);
        beneficiary_loc.add(position+1,location);
        beneficiary_email.add(position+1,email);
        beneficiary_code.add(position+1,code);
        beneficiary_address.add(position+1,address);
        beneficiary_userid.add(position+1,user_id);
    notifyDataSetChanged();

    }
    public int getPosition()
    {
        return (beneficiary_names.size()-1);
    }
public void updateItem(int position,String id,String University,String College,Object edit,Object delete,String mobile,String location,String email,String code,String address,String user_id)
{

    if (beneficiary_id != null) {


        beneficiary_id.set(position,id);
        beneficiary_names.set(position,University);
        beneficiary_agency.set(position,College);
        beneficiary_edit.set(position,edit);
        beneficiary_delete.set(position,delete);
        beneficiary_mobile.set(position,mobile);
        beneficiary_loc.set(position,location);
        beneficiary_email.set(position,email);
        beneficiary_code.set(position,code);
        beneficiary_address.set(position,address);
        beneficiary_userid.set(position,user_id);

    }

    notifyDataSetChanged();
}
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_beneficiary_name,tv_beneficiary_agency;
        ImageButton ib_beneficiary_edit,ib_beneficiary_delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_beneficiary_name=(TextView)itemView.findViewById(R.id.tv_beneficiary_name);
            tv_beneficiary_agency=(TextView)itemView.findViewById(R.id.tv_beneficiary_agency);
            ib_beneficiary_edit=(ImageButton)itemView.findViewById(R.id.ib_beneficiary_edit);
            ib_beneficiary_delete=(ImageButton)itemView.findViewById(R.id.ib_beneficiary_delete);
        }
    }


}
