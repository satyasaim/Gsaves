package com.gsaves.media3.gsaves.app;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.Addauthorizeagentresponse;
import com.gsaves.media3.gsaves.app.pojo.AuthagentData;
import com.gsaves.media3.gsaves.app.pojo.AuthorisedagentslistResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizedAgents extends Fragment {
    View view;
    Fragment f=null;
    FragmentActivity fact;
    Intent mintent;
    RecyclerView rv_aagents_obj_list;
    ImageButton ib_addagents;



    ArrayList<String> agent_name,agent_mobile,agent_loc,agent_email,agent_addr,agent_status,agent_id;
    ArrayList agent_edit,agent_delete;

    public AuthorizedAgents() {

        agent_name = new ArrayList<>();
        agent_mobile =  new ArrayList<>();
        agent_loc =  new ArrayList<>();
        agent_email =  new ArrayList<>();
        agent_addr =  new ArrayList<>();
        agent_status =  new ArrayList<>();
        agent_id =  new ArrayList<>();
        agent_edit =  new ArrayList();
        agent_delete =  new ArrayList();
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fact=getActivity();
        mintent=fact.getIntent();
        ib_addagents=(ImageButton)view.findViewById(R.id.ib_add_aagents);

try {
    (Api.getClient().getauthagents()).enqueue(new Callback<AuthorisedagentslistResponse>() {
        @Override
        public void onResponse(Call<AuthorisedagentslistResponse> call, Response<AuthorisedagentslistResponse> response) {
            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
            List<AuthagentData> innerdata=response.body().getData();
            for(AuthagentData i:innerdata)
            {
                agent_id.add(i.getId());
                agent_name.add(i.getAuthorisedName());
                agent_mobile.add(i.getMobileNumber());
                agent_loc.add(i.getLocation());
                agent_email.add(i.getEmail());
                agent_addr.add(i.getAddress());
                agent_status.add(i.getStatus());
                agent_edit.add(R.drawable.edit);
                agent_delete.add(R.drawable.delete);

                System.out.println(i.getAuthorisedName()+" "+i.getMobileNumber()+" ");
                Log.d("innerdata result"," "+i.getId());

            }
            GridLayoutManager gridLayoutManagerfaq = new GridLayoutManager(fact.getApplicationContext(), 1);
            gridLayoutManagerfaq.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
            rv_aagents_obj_list.setLayoutManager(gridLayoutManagerfaq);

            CustomAdapterAuthorisedagentsList customAdapteragents = new CustomAdapterAuthorisedagentsList(fact.getApplicationContext(),agent_name,agent_mobile,agent_loc,agent_email,agent_addr,agent_status,agent_edit,agent_delete,agent_id);
            rv_aagents_obj_list.setAdapter(customAdapteragents); // set the Adapter to RecyclerView

            rv_aagents_obj_list.addItemDecoration(new SimpleDividerItemDecoration(fact.getApplicationContext()));

        }

        @Override
        public void onFailure(Call<AuthorisedagentslistResponse> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

        }
    });



}catch(Exception e)
{
    System.out.println(e.getMessage());
}

ib_addagents.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.authagents_add_form);
        Button bt_reg=(Button)dialog.findViewById(R.id.bt_authagent_register);
        final EditText et_agentname,et_agent_mobile,et_agent_ssn,et_agent_email,et_agent_address;
        et_agentname=(EditText)dialog.findViewById(R.id.et_authagent_name);
        et_agent_mobile=(EditText)dialog.findViewById(R.id.et_authagent_mobile);
        et_agent_ssn=(EditText)dialog.findViewById(R.id.et_authagent_ssn);
        et_agent_email=(EditText)dialog.findViewById(R.id.et_authagent_id);
        et_agent_address=(EditText)dialog.findViewById(R.id.et_authagent_address);

        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        try
        {
           Api.getClient().addauthagent(et_agentname.getText().toString().trim(),et_agent_mobile.getText().toString().trim(),et_agent_ssn.getText().toString().trim(),et_agent_email.getText().toString().trim(),et_agent_address.getText().toString().trim(),new PrefManager(getContext()).getId()).enqueue(new Callback<Addauthorizeagentresponse>() {
               @Override
               public void onResponse(Call<Addauthorizeagentresponse> call, Response<Addauthorizeagentresponse> response) {
                 Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                   CustomAdapterAuthorisedagentsList customInsertagent = new CustomAdapterAuthorisedagentsList(fact.getApplicationContext(),agent_name,agent_mobile,agent_loc,agent_email,agent_addr,agent_status,agent_edit,agent_delete,agent_id);
                   customInsertagent.insertItem(customInsertagent.getPosition(),et_agentname.getText().toString().trim(),et_agent_mobile.getText().toString().trim(),et_agent_ssn.getText().toString().trim(),et_agent_email.getText().toString().trim(),et_agent_address.getText().toString().trim());
                   rv_aagents_obj_list.setAdapter(customInsertagent); // set the Adapter to RecyclerView

                 dialog.dismiss();
               }

               @Override
               public void onFailure(Call<Addauthorizeagentresponse> call, Throwable t) {
                   Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

               }
           });
        }catch(Exception e)
        {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
            }
        });

        dialog.show();
    }
});

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.auth_agents_fragment, container, false);
        rv_aagents_obj_list=(RecyclerView)view.findViewById(R.id.rv_aagents_list);

        return view;
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
