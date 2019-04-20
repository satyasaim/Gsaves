package com.gsaves.media3.gsaves.app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.Data;
import com.gsaves.media3.gsaves.app.pojo.LoginResponse;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Response;



public class LoginActivity extends AppCompatActivity {

    Button bt_login2,bt_twittersignin,bt_fbsignin;
    TextView tv_forgot_pwd,tv_new_signup;
    EditText et_email,et_password;
    CallbackManager callbackManager;
    LoginButton loginButton;
    TwitterLoginButton tw_loginButton;
    private static final String TAG = "LoginActivity";
    boolean loggedOut;
    Bundle parameters2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        tw_loginButton.onActivityResult(requestCode, resultCode, data);


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        //Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(
                        getString(R.string.twitter_CONSUMER_KEY),
                        getString(R.string.twitter_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);

        setContentView(R.layout.activity_login);

        tv_forgot_pwd=(TextView)findViewById(R.id.tv_forgot_pwd);
        tv_new_signup=(TextView)findViewById(R.id.tv_new_signup);
        bt_login2=(Button)findViewById(R.id.bt_login2);
        bt_twittersignin=(Button)findViewById(R.id.bt_twittersignin);
        bt_fbsignin=(Button)findViewById(R.id.bt_fbsignin);

        loginButton=(LoginButton)findViewById(R.id.loginButton);
        loginButton.setTextSize(12);

        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        tw_loginButton = (TwitterLoginButton) findViewById(R.id.bt_twittersignin2);
        tw_loginButton.setText("SIGN IN");
        tw_loginButton.setTextSize(12);


        final String[] first_name = new String[1];
        final String[] last_name = new String[1];
        final String[] email = new String[1];
        final String[] id = new String[1];
        final String[] image_url = new String[1];


        callbackManager = CallbackManager.Factory.create();


        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

        loggedOut = AccessToken.getCurrentAccessToken() == null;
        final Bundle parameters = new Bundle();
        parameters2=new Bundle();
        if(loggedOut)
        {
            bt_fbsignin.setText("SIGN IN");
        }

       if (!loggedOut) {
           // Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(imageView);
            Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());
           bt_fbsignin.setText("SIGN OUT");
           bt_twittersignin.setFocusable(false);
           bt_login2.setFocusable(false);

            //Using Graph API
            //getUserProfile(AccessToken.getCurrentAccessToken());
        }

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {



                getUserProfile(AccessToken.getCurrentAccessToken());




                GraphRequest request=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Log.d("TAG33", object.toString());
                        try {
                            first_name[0] = object.getString("first_name");
                            last_name[0] = object.getString("last_name");
                            email[0] = object.getString("email");
                            id[0] = object.getString("id");
                            image_url[0] = "https://graph.facebook.com/" + id[0] + "/picture?type=normal";

                            parameters2.putString("first_name",first_name[0]);
                            parameters2.putString("last_name",last_name[0]);
                            parameters2.putString("email",email[0]);
                            parameters2.putString("id",id[0]);
                            parameters2.putString("image_url",image_url[0]);
                            parameters2.putString("login_type","fb");

                            Log.d("intentparameters",""+parameters2);



                            Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
                            intent.putExtras(parameters2);
                            startActivity(intent);






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });

                parameters.putString("fields", "first_name,last_name,email,id");
                Log.d("info",""+parameters);
                request.setParameters(parameters);
                request.executeAsync();

               // finish();
            }

            private void getUserProfile(AccessToken currentAccessToken) {

            }

            @Override
            public void onCancel() {
                Log.d("fbloginstatus","Logged failed");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

       //Twitter Login Button
        tw_loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                bt_twittersignin.setText("SIGN OUT");
                Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();


                String userName = result.data.getUserName();
                long userID = result.data.getId();



                String token = authToken.token;
                String secret = authToken.secret;

                GraphRequest request=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("twitterTAG33", object.toString());
                    }
                });


                /*((TextView) findViewById(R.id.display)).setText(
                        "User Name:" + userName +
                                "\nUser ID: " + userID +
                                "\nToken Key: " + token +
                                "\nT.Secret: " + secret);*/

                startActivity(intent);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("twitterexception",""+exception);
                bt_twittersignin.setText("SIGN IN");
            }
        });


        bt_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (validate()) {

                        login();


                }
                if (!validate()) {
                    onLoginFailed();
                    return;
                }
            }
        });
        tv_forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecoverPasswordActivity.class);
                intent.putExtra("Email",et_email.getText().toString());

                startActivity(intent);

                finish();
            }
        });
        tv_new_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
        Api.getapiContext(getApplicationContext());
        (Api.getClient().signin(et_email.getText().toString().trim(),et_password.getText().toString().trim())).enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Toast.makeText(getApplicationContext(),response.body().getStatus(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                if(response.body().getStatus().equals("success")) {

                    getProfiledata(response.body().getData());

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid credentials",Toast.LENGTH_LONG).show();
                }
            }

            private void getProfiledata(Data data) {

                Log.d("Name is",data.getName());

                parameters2.putString("first_name",data.getUserName());
                parameters2.putString("last_name",data.getLastName());
                parameters2.putString("email",data.getEmail());
                parameters2.putString("id",data.getId());
                parameters2.putString("image_url",data.getProfileImage());
                parameters2.putString("mobile_number",data.getMobileNumber());
                parameters2.putString("address",data.getAddress());
                parameters2.putString("contact_address",data.getContactAddress());
                parameters2.putString("login_type","Normal");

                Log.d("parameters list is",""+parameters2);
                saveLoginDetails(data.getEmail(),et_password.getText().toString().trim(),data.getId());
                saveProfileDetails(data.getUserName(),data.getLastName(),data.getEmail(),data.getMobileNumber(),data.getAddress(),data.getContactAddress(),data.getProfileImage(),data.getPassword());

                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.putExtras(parameters2);
                startActivity(intent);


            }

            private void saveProfileDetails(String fname,String lname,String email,String mobile,String address1,String address2,String img_url,String passwd) {
                new PrefManager(getApplicationContext()).saveProfileDetails(fname,lname,email,mobile,address1,address2,img_url,passwd);
            }

            private void saveLoginDetails(String email, String password,String id) {
                new PrefManager(getApplicationContext()).saveLoginDetails(email, password,id);
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Usernotcreatedresponse", t.getStackTrace().toString());
                onLoginFailed();
                Toast.makeText(getApplicationContext(),"Invalid credentials",Toast.LENGTH_LONG).show();
                Log.i("RM",  t.getMessage());
                progressDialog.dismiss();
            }
        });







            }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        bt_login2.setEnabled(true);
    }

    public boolean validate() {
            boolean valid = true;

            String email = et_email.getText().toString();
            String password = et_password.getText().toString();

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                et_email.setError("enter a valid email address");
                valid = false;
            } else {
                et_email.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                et_password.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                et_password.setError(null);
            }

            return valid;
        }
    public void onClickTwitterButton(View v)
    {
if(v==bt_twittersignin)
{
    tw_loginButton.performClick();


}
if(v==bt_fbsignin) {
    loginButton.performClick();
    loggedOut = AccessToken.getCurrentAccessToken() == null;
    if(loggedOut) {
        bt_fbsignin.setText("SIGN IN");
    }
}
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
        startActivity(intent);

        finish();
    }
}
