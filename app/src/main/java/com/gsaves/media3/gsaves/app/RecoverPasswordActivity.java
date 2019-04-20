package com.gsaves.media3.gsaves.app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.ForgotpasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoverPasswordActivity extends AppCompatActivity {
ImageButton ib_back;
TextView tv_recover_passwd;
Toolbar toolbar2;
Button bt_recoverpwd;
EditText et_email_recover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        ib_back=(ImageButton)findViewById(R.id.ib_back_arrrow);
        tv_recover_passwd=(TextView)findViewById(R.id.tv_recover_password);
        bt_recoverpwd=(Button)findViewById(R.id.bt_recoverpwd);
        et_email_recover=(EditText)findViewById(R.id.et_email_recover);
        //toolbar2=(Toolbar)findViewById(R.id.toolbar2);
           String email=getIntent().getStringExtra("Email");


        et_email_recover.setText(email);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecoverPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        tv_recover_passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
       /* toolbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecoverPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });*/
       bt_recoverpwd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final Dialog dialog = new Dialog(v.getContext());
               dialog.setContentView(R.layout.recovery_password_alert);
               ImageButton ib_close=(ImageButton)dialog.findViewById(R.id.ib_recover_alert_close);
               ib_close.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();

                   }
               });
               dialog.show();
               Toast.makeText(getApplicationContext(),et_email_recover.getText().toString(),Toast.LENGTH_LONG).show();
               dialog.dismiss();

               if(et_email_recover.getText().equals(null)) {
                   Toast.makeText(getApplicationContext(),"Enter email",Toast.LENGTH_LONG).show();

               }

               else
               {
                   (Api.getClient().forgotpassword(et_email_recover.getText().toString().trim())).enqueue(new Callback<ForgotpasswordResponse>() {
                       @Override
                       public void onResponse(Call<ForgotpasswordResponse> call, Response<ForgotpasswordResponse> response) {
                           Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                           Intent intent = new Intent(RecoverPasswordActivity.this, LoginActivity.class);
                           startActivity(intent);



                       }

                       @Override
                       public void onFailure(Call<ForgotpasswordResponse> call, Throwable t) {
                           Log.d("Email doesn't exist", t.getStackTrace().toString());
                           Log.i("RM", t.getMessage());
                           dialog.dismiss();

                       }
                   });

               }


           }
       });
    }
}
