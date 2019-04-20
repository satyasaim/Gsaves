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
import android.text.Editable;
import android.text.TextWatcher;
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
import com.gsaves.media3.gsaves.app.pojo.AddCardsData;
import com.gsaves.media3.gsaves.app.pojo.CardsData;
import com.gsaves.media3.gsaves.app.pojo.CardslistResponse;
import com.gsaves.media3.gsaves.app.pojo.CreatecardResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    FragmentActivity fact;
    Intent mintent;

    RecyclerView rv_cards_list;
    ArrayList card_id=new ArrayList<>();
    ArrayList<String> card_names=new ArrayList<>();
    ArrayList<String> holder_names=new ArrayList<>();
    ArrayList card_delete=new ArrayList<>();

    ArrayList card_number=new ArrayList();
    ArrayList card_cvvs=new ArrayList();
    ArrayList card_expiry_month=new ArrayList();
    ArrayList card_expiry_year=new ArrayList();

    String[] Month={"01","02","03","04","05","06","07","08","09","10","11","12"};
    ArrayList<String> year = new ArrayList<String>();

    //int thisYear2 = Calendar.getInstance().get(Calendar.YEAR);
    int thisYear2 = 2200;

    String benefid=null;
    static int count;
    Fragment f=null;




    //String[] year={"India","China","Australia","Portugle","America","New Zealand"};
    public CardsFragment() {
        for(int i=1960;i<=thisYear2;i++) {
            year.add(Integer.toString(i));
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cards_fragment, container, false);
        rv_cards_list=(RecyclerView)view.findViewById(R.id.rv_cards_list);
        final String user_id=new PrefManager(view.getContext()).getId();
        Bundle b=getArguments();
        if(b!=null)

        {
            benefid = b.getString("benef id");

        }

        Toast.makeText(getContext(),"Selected beneficiary"+benefid,Toast.LENGTH_LONG).show();

        ImageButton ib_add=(ImageButton)view.findViewById(R.id.ib_add_card);
        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.card_add_form);
                final EditText card_name,card_cvv,bank_name;
                final CreditCardEditText card_num;
                card_num=(CreditCardEditText)dialog.findViewById(R.id.et_card_num);
                card_name=(EditText)dialog.findViewById(R.id.et_card_name);
                bank_name=(EditText)dialog.findViewById(R.id.et_card_id);
                card_cvv=(EditText)dialog.findViewById(R.id.et_card_cvv);
                ImageButton ib_close=(ImageButton)dialog.findViewById(R.id.ib_card_add_close);

                final Spinner spin=(Spinner)dialog.findViewById(R.id.spin_month);
                final Spinner spin2=(Spinner)dialog.findViewById(R.id.spin_year);
                spin.setOnItemSelectedListener(CardsFragment.this);
                spin2.setOnItemSelectedListener(CardsFragment.this);


                final CustomAdapterCardmonthList customAdapter1=new CustomAdapterCardmonthList(dialog.getContext(),Month);
                spin.setAdapter(customAdapter1);
                final CustomAdapterCardyearList customAdapter2=new CustomAdapterCardyearList(dialog.getContext(),year);
                spin2.setAdapter(customAdapter2);




                card_num.addTextChangedListener(new CreditCardEditText(getContext(),card_num));
               ib_close.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
                       Toast.makeText(v.getContext(),"close Card",Toast.LENGTH_LONG).show();
                   }
               });
                Button bt_card_reg=(Button)dialog.findViewById(R.id.bt_card_register);
                bt_card_reg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        String expiry_month= (String) customAdapter1.getItem(spin.getSelectedItemPosition());
                        String expiry_year= (String) customAdapter2.getItem(spin2.getSelectedItemPosition());
                        (Api.getClient().createcard(String.valueOf(card_name.getText()),String.valueOf(card_num.getText()),String.valueOf(bank_name.getText()),String.valueOf(card_cvv.getText()),user_id,expiry_year,expiry_month)).enqueue(new Callback<CreatecardResponse>() {
                            @Override
                            public void onResponse(Call<CreatecardResponse> call, Response<CreatecardResponse> response) {

                               // Toast.makeText(v.getContext(),"success msg"+response.body().getMessage(),Toast.LENGTH_LONG).show();
                               // Log.d("success msg",response.body().getMessage());
                                AddCardsData b=response.body().getData();
                                CustomAdapterCardsList insertlist = new CustomAdapterCardsList(view.getContext(),card_id,card_names,holder_names,card_delete,benefid,card_number,card_cvvs,card_expiry_month,card_expiry_year);
                                insertlist.insertItem(getContext(),insertlist.getPosition(),b.getId(),b.getCardName(),b.getBranchLocation(),R.drawable.delete);
                                rv_cards_list.setAdapter(insertlist); // set the Adapter to RecyclerView
                            }

                            @Override
                            public void onFailure(Call<CreatecardResponse> call, Throwable t) {
                            Toast.makeText(v.getContext(),"failed msg"+t.getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("failed msg",t.getMessage());

                            }
                        });

                       // Toast.makeText(v.getContext(),"selected year"+expiry_year,Toast.LENGTH_LONG).show();
                        dialog.dismiss();


                    }
                });
                dialog.show();
                Toast.makeText(v.getContext(),"Add Card",Toast.LENGTH_LONG).show();
            }
        });



        /*GridLayoutManager gridLayoutManagerfaq = new GridLayoutManager(view.getContext(),1);
        gridLayoutManagerfaq.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        rv_cards_list.setLayoutManager(gridLayoutManagerfaq);

        CustomAdapterCardsList customAdaptercards = new CustomAdapterCardsList(view.getContext(), card_names,holder_names,card_delete);
        rv_cards_list.setAdapter(customAdaptercards); // set the Adapter to RecyclerView
        rv_cards_list.addItemDecoration(new SimpleDividerItemDecoration(view.getContext()));*/
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fact=getActivity();
        mintent=fact.getIntent();
        count++;

        if(count>1)
        {
            card_id.clear();
            card_names.clear();
            holder_names.clear();
            card_delete.clear();
            card_number.clear();
            card_cvvs.clear();
            card_expiry_month.clear();
            card_expiry_year.clear();
        }

        (Api.getClient().getcardlist()).enqueue(new Callback<CardslistResponse>() {
            @Override
            public void onResponse(Call<CardslistResponse> call, Response<CardslistResponse> response) {
                List<CardsData> innerdata=response.body().getData();
                for(CardsData b:innerdata)
                {
                    Log.d("innerdata result"," "+b.getUserId());

                    if(b.getUserId().equals(new PrefManager(view.getContext()).getId())) {
                        card_id.add(b.getId());
                        card_names.add(b.getCardName());
                        holder_names.add(b.getBranchLocation());
                        card_delete.add(R.drawable.delete);
                        card_number.add(b.getCardNumber());
                        card_cvvs.add(b.getCardIfsc());
                        card_expiry_month.add(b.getMonth());
                        card_expiry_year.add(b.getYear());
                    }
                }

                GridLayoutManager gridLayoutManagerfaq = new GridLayoutManager(view.getContext(),1);
                gridLayoutManagerfaq.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                rv_cards_list.setLayoutManager(gridLayoutManagerfaq);

                CustomAdapterCardsList customAdaptercards = new CustomAdapterCardsList(view.getContext(),card_id,card_names,holder_names,card_delete,benefid,card_number,card_cvvs,card_expiry_month,card_expiry_year);
                rv_cards_list.setAdapter(customAdaptercards); // set the Adapter to RecyclerView
                rv_cards_list.addItemDecoration(new SimpleDividerItemDecoration(view.getContext()));
            }

            @Override
            public void onFailure(Call<CardslistResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
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
