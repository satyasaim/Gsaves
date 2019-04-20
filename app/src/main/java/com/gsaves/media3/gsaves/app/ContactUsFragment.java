package com.gsaves.media3.gsaves.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.ContactUsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsFragment extends Fragment {
    View view;
    public ContactUsFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final EditText contact_name=(EditText)view.findViewById(R.id.et_contact_name);
        final EditText contact_mail=(EditText)view.findViewById(R.id.et_contact_email);
        final EditText contact_phone=(EditText)view.findViewById(R.id.et_contact_phone);
        final EditText contact_msg=(EditText)view.findViewById(R.id.et_contact_msg);
        Button submit=(Button)view.findViewById(R.id.bt_contact_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (Api.getClient()).contactus(contact_name.getText().toString().trim(),contact_mail.getText().toString().trim(),contact_phone.getText().toString().trim(),contact_msg.getText().toString().trim()).enqueue(new Callback<ContactUsResponse>() {
                    @Override
                    public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                        Toast.makeText(getContext(),"Thank you for contacting Us,will update you soon.",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                        Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.contact_us, container, false);
        return view;
    }
}
