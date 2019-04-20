package com.gsaves.media3.gsaves.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.SignUpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    TextView tv_already_account;
    EditText et_name,et_email,et_phone,et_password,et_conf_password;
    Button bt_signup;
    private static final String TAG = "SignupActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tv_already_account=(TextView)findViewById(R.id.tv_already_account);
        et_name=(EditText)findViewById(R.id.et_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_password=(EditText)findViewById(R.id.et_password);
        et_conf_password=(EditText)findViewById(R.id.et_conf_password);
        bt_signup=(Button)findViewById(R.id.bt_signup);
        tv_already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }
    public void signUp() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignUpFailed();
            return;
        }
        else if(validate())
        {
            Toast.makeText(getApplicationContext(),"Sign up started",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Login");
            final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setCancelable(false); // set cancelable to false
            progressDialog.setMessage("Please Wait"); // set message
            progressDialog.show(); // show progress dialog
            Api.getapiContext(getApplicationContext());
            (Api.getClient().registration(et_name.getText().toString().trim(),et_phone.getText().toString().trim(),et_email.getText().toString().trim(),et_password.getText().toString().trim())).enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                    Toast.makeText(getApplicationContext(),response.body().getStatus(),Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    gotologin();
                }

                private void gotologin() {
                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {
                    Log.d("Usernotcreated", t.getStackTrace().toString());

                    Log.i("RM",  t.getMessage());
                    progressDialog.dismiss();
                }
            });

        }
    }
    public void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

        bt_signup.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;
//et_name,et_email,et_phone,et_password,et_conf_password;
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String phone = et_phone.getText().toString();
        String password = et_password.getText().toString();
        String conPassword = et_conf_password.getText().toString();
        if (name.isEmpty()) {
            et_name.setError("enter name");
            valid = false;
        } else {
            et_name.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("enter a valid email address");
            valid = false;
        } else {
            et_email.setError(null);
        }
        if (phone.isEmpty()) {
            et_phone.setError("enter phone number");
            valid = false;
        } else {
            et_phone.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            et_password.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10 || !password.equals(conPassword)) {
            et_conf_password.setError("between 4 and 10 alphanumeric characters or confirm password incorrect");
            valid = false;
        } else {
            et_conf_password.setError(null);
        }

        return valid;
    }
}
