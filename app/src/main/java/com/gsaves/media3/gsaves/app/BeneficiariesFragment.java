package com.gsaves.media3.gsaves.app;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.AddbeneficiaryData;
import com.gsaves.media3.gsaves.app.pojo.AddbeneficiaryResponse;
import com.gsaves.media3.gsaves.app.pojo.BeneficiariesData;
import com.gsaves.media3.gsaves.app.pojo.BeneficiarieslistResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeneficiariesFragment extends Fragment implements AdapterView.OnItemSelectedListener {
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

    String[] countryNames={"India","China","Australia","Portugle","America","New Zealand"};

    String selectedcountry;
    int selectedpos;
    CustomAdapterBeneficiariesagencyList customAdapter;
    int count=0;
    Fragment f=null;



    EditText et_univname,et_collegename,et_mobile,et_location,et_univemail,et_univcode,et_address;

    public BeneficiariesFragment() {
        beneficiary_names = new ArrayList<>();
        beneficiary_agency =new ArrayList<>();
        beneficiary_edit=new ArrayList<>();
        beneficiary_delete=new ArrayList<>();
        beneficiary_id=new ArrayList<>();

         beneficiary_mobile=new ArrayList<>();
         beneficiary_loc=new ArrayList<>();
         beneficiary_email=new ArrayList<>();
         beneficiary_code=new ArrayList<>();
         beneficiary_address=new ArrayList<>();
        beneficiary_userid=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.beneficiaries_fragment, container, false);
        ImageButton ib_add=(ImageButton)view.findViewById(R.id.ib_add_beneficiary);




        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.beneficiary_add_form);
                Spinner spin=(Spinner)dialog.findViewById(R.id.spin_add_benef_agency);
               spin.setOnItemSelectedListener(BeneficiariesFragment.this);

                 customAdapter=new CustomAdapterBeneficiariesagencyList(dialog.getContext(),countryNames);
                spin.setAdapter(customAdapter);







                ImageButton ib_close=(ImageButton)dialog.findViewById(R.id.ib_benef_edit_close);
                ib_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();
                        Toast.makeText(v.getContext(),"close benef",Toast.LENGTH_LONG).show();
                    }
                });

                Button bt_benef_reg=(Button)dialog.findViewById(R.id.bt_benef_register);

                et_univname=(EditText)dialog.findViewById(R.id.et_benef_name);
                et_collegename=(EditText)dialog.findViewById(R.id.et_benef_name2);

                et_univemail=(EditText)dialog.findViewById(R.id.et_benef_id);
                et_univcode=(EditText)dialog.findViewById(R.id.et_benef_ssn);
                et_mobile=(EditText)dialog.findViewById(R.id.et_benef_mobile);
                et_address=(EditText)dialog.findViewById(R.id.et_benef_address);


                bt_benef_reg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        count++;
                        (Api.getClient().addbeneficiary(et_univname.getText().toString().trim(),et_collegename.getText().toString().trim(),et_mobile.getText().toString().trim(),selectedcountry,et_univemail.getText().toString().trim(),et_univcode.getText().toString().trim(),et_address.getText().toString().trim(),new PrefManager(v.getContext()).getId())).enqueue(new Callback<AddbeneficiaryResponse>() {
                            @Override
                            public void onResponse(Call<AddbeneficiaryResponse> call, Response<AddbeneficiaryResponse> response) {
                                Toast.makeText(dialog.getContext(),response.body().getStatus(),Toast.LENGTH_LONG).show();

                                AddbeneficiaryData b=response.body().getData();



                                CustomAdapterBeneficiariesList  insertlist = new CustomAdapterBeneficiariesList(fact.getApplicationContext(), beneficiary_names,beneficiary_agency,beneficiary_edit,beneficiary_delete,beneficiary_id,beneficiary_mobile,beneficiary_loc,beneficiary_email,beneficiary_code,beneficiary_address,selectedpos,beneficiary_userid);
                                if(count==1) {
                                    insertlist.insertItem(dialog.getContext(), insertlist.getPosition(), b.getId(), b.getUnivercityName(), b.getCollageName(), R.drawable.edit, R.drawable.delete, b.getMobileNumber(), b.getLocation(), b.getEmail(), b.getUnivercityCode(), b.getAddress(), b.getUser_id());
                                count=0;
                                }
                                rv_beneficiaries_list.setAdapter(insertlist);


                            }

                            @Override
                            public void onFailure(Call<AddbeneficiaryResponse> call, Throwable t) {
                                dialog.dismiss();

                            }
                        });
                        dialog.dismiss();
                    }
                });

                dialog.show();
                Toast.makeText(v.getContext(),"Add beneficiary",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fact=getActivity();
        mintent=fact.getIntent();
        rv_beneficiaries_list=(RecyclerView)fact.findViewById(R.id.rv_beneficiaries_list);


        (Api.getClient().getbeneficiaries()).enqueue(new Callback<BeneficiarieslistResponse>() {
            @Override
            public void onResponse(Call<BeneficiarieslistResponse> call, Response<BeneficiarieslistResponse> response) {
                Toast.makeText(view.getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                List<BeneficiariesData> innerdata=response.body().getData();
                for(BeneficiariesData b:innerdata)
                {
                    Log.d("innerdata result"," "+b.getAddresslocal());

                    if(b.getUser_id().equals(new PrefManager(view.getContext()).getId())) {

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




                GridLayoutManager gridLayoutManagerfaq = new GridLayoutManager(fact.getApplicationContext(),1);
                gridLayoutManagerfaq.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                rv_beneficiaries_list.setLayoutManager(gridLayoutManagerfaq);

                CustomAdapterBeneficiariesList customAdapterfaq = new CustomAdapterBeneficiariesList(fact.getApplicationContext(), beneficiary_names,beneficiary_agency,beneficiary_edit,beneficiary_delete,beneficiary_id,beneficiary_mobile,beneficiary_loc,beneficiary_email,beneficiary_code,beneficiary_address,selectedpos,beneficiary_userid);
                rv_beneficiaries_list.setAdapter(customAdapterfaq); // set the Adapter to RecyclerView

                rv_beneficiaries_list.addItemDecoration(new SimpleDividerItemDecoration(fact.getApplicationContext()));



            }

            @Override
            public void onFailure(Call<BeneficiarieslistResponse> call, Throwable t) {
                Toast.makeText(view.getContext(),"failed to retrieve",Toast.LENGTH_SHORT).show();
            }


        });



    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(),countryNames[position],Toast.LENGTH_SHORT).show();
        selectedcountry=countryNames[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
Toast.makeText(getContext(),"nothing selected",Toast.LENGTH_SHORT).show();
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
                  //  Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    f=new BarcodescannerFragment();
                    if (f != null) {

                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
