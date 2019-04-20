package com.gsaves.media3.gsaves.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.BeneficiariesData;
import com.gsaves.media3.gsaves.app.pojo.BeneficiarieslistResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BeneficiariesFragment_test extends Fragment {
    View view;
    FragmentActivity fact;


    Intent mintent;

    RecyclerView rv_beneficiaries_list;

    ArrayList<String> beneficiary_names;
    ArrayList<String> beneficiary_agency;
    ArrayList<String> beneficiary_mobile;
    ArrayList<String> beneficiary_loc;
    ArrayList<String> beneficiary_email;
    ArrayList<String> beneficiary_code;
    ArrayList<String> beneficiary_address;

    ArrayList beneficiary_edit;
    ArrayList beneficiary_delete;
    ArrayList beneficiary_id;
    ArrayList beneficiary_userid;
    int selectedpos;
    static int count;
    static int sum_amount;
    String paypalid;

    String userid=null;
    Fragment f=null;

    public  BeneficiariesFragment_test() {
        beneficiary_names = new ArrayList<>();
        beneficiary_agency = new ArrayList<>();
        beneficiary_edit = new ArrayList<>();
        beneficiary_delete = new ArrayList<>();
        beneficiary_id = new ArrayList<>();

        beneficiary_mobile = new ArrayList<>();
        beneficiary_loc = new ArrayList<>();
        beneficiary_email = new ArrayList<>();
        beneficiary_code = new ArrayList<>();
        beneficiary_address = new ArrayList<>();
        beneficiary_userid = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.beneficiaries_fragment_test, container, false);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* beneficiary_names = null;
        beneficiary_agency = null;
        beneficiary_edit = null;
        beneficiary_delete = null;
        beneficiary_id = null;

        beneficiary_mobile = null;
        beneficiary_loc = null;
        beneficiary_email =null;
        beneficiary_code = null;
        beneficiary_address =null;
        beneficiary_userid = null;*/

        fact = getActivity();
        mintent = fact.getIntent();
        rv_beneficiaries_list = (RecyclerView) fact.findViewById(R.id.rv_beneficiaries_list);
        count++;
        final Bundle bun=getArguments();
        if(bun!=null) {
            userid = bun.getString("user id");
            sum_amount=bun.getInt("sum_amount");
            paypalid=bun.getString("paypalresponseid");


        }



            Toast.makeText(view.getContext(), "Valid fragment call"+userid+" count is:"+count, Toast.LENGTH_SHORT).show();

        if (bun!=null && (count>1)) {

            beneficiary_names.clear();
            beneficiary_agency.clear();
            beneficiary_edit.clear();
            beneficiary_delete.clear();
            beneficiary_id.clear();


            beneficiary_mobile.clear();
            beneficiary_loc.clear();
            beneficiary_email.clear();
            beneficiary_code.clear();
            beneficiary_address.clear();
            beneficiary_userid.clear();
        }

            (Api.getClient().getbeneficiaries()).enqueue(new Callback<BeneficiarieslistResponse>() {
                @Override
                public void onResponse(Call<BeneficiarieslistResponse> call, Response<BeneficiarieslistResponse> response) {
                   // Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   try {
                       List<BeneficiariesData> innerdata = response.body().getData();


                       for (BeneficiariesData b : innerdata) {
                           Log.d("innerdata result", " " + b.getAddresslocal());

                           if (b.getUser_id().equals(new PrefManager(view.getContext()).getId())) {


                               beneficiary_names.add(b.getUnivercityName());
                               beneficiary_agency.add(b.getCollageName());
                               beneficiary_edit.add(R.drawable.edit);
                               beneficiary_delete.add(R.drawable.delete);
                               beneficiary_id.add(b.getId());


                               beneficiary_mobile.add(b.getMobileNumber());
                               beneficiary_loc.add(b.getLocation());
                               beneficiary_email.add(b.getEmail());
                               beneficiary_code.add(b.getUnivercityCode());
                               beneficiary_address.add(b.getAddresslocal());
                               beneficiary_userid.add(b.getUser_id());

                           }
                       }


                       GridLayoutManager gridLayoutManagerfaq = new GridLayoutManager(fact.getApplicationContext(), 1);
                       gridLayoutManagerfaq.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                       rv_beneficiaries_list.setLayoutManager(gridLayoutManagerfaq);

                       CustomAdapterBeneficiariesList2 customAdapterfaq = new CustomAdapterBeneficiariesList2(fact.getApplicationContext(), beneficiary_names, beneficiary_agency, beneficiary_edit, beneficiary_delete, beneficiary_id, beneficiary_mobile, beneficiary_loc, beneficiary_email, beneficiary_code, beneficiary_address, selectedpos, beneficiary_userid, paypalid);
                       customAdapterfaq.getdiscountTotal(sum_amount);
                       rv_beneficiaries_list.setAdapter(customAdapterfaq); // set the Adapter to RecyclerView

                       rv_beneficiaries_list.addItemDecoration(new SimpleDividerItemDecoration(fact.getApplicationContext()));
                       //count = 0;
                   }catch(Exception e)
                   {
                       Toast.makeText(getContext(),"Server Not Responding",Toast.LENGTH_LONG).show();
                       System.out.println(e.getMessage());
                   }
                }


                @Override
                public void onFailure(Call<BeneficiarieslistResponse> call, Throwable t) {
                    Toast.makeText(view.getContext(), "failed to retrieve", Toast.LENGTH_SHORT).show();
                }


            });


        }
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                   // Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    // Setting Alert Dialog Title
                    alertDialogBuilder.setTitle("Transaction information !!!");
                    // Icon Of Alert Dialog
                    alertDialogBuilder.setIcon(R.drawable.report_problem);
                    // Setting Alert Dialog Message
                    alertDialogBuilder.setMessage("Transaction cannot be cancelled at this moment.");
                    alertDialogBuilder.setCancelable(true);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    final AlertDialog finalAlertDialog = alertDialog;
                    new Thread(new Runnable() {
                        public void run() {

                            try {
                                Thread.sleep(5000);



                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            finalAlertDialog.dismiss();
                            f=new BeneficiariesFragment_test();
                            if (f != null) {

                                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        }
                    }).start();

                    /*DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked


                                    f=new WalletFragment();
                                    if (f != null) {

                                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                                        ft.addToBackStack(null);
                                        ft.commit();
                                    }
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                   Toast.makeText(getContext(),"Please Select your beneficiary",Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure want to cancel this transaction?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();*/

                    return true;
                }
                return false;
            }
        });
    }


    }

