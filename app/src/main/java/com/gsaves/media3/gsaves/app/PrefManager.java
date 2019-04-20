package com.gsaves.media3.gsaves.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.gsaves.media3.gsaves.app.pojo.BeneficiariesData;

import java.util.ArrayList;

public class PrefManager {
    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }
    public void saveLoginDetails(String email, String password,String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.putString("ID",id);
        editor.commit();
    }
 public void saveImage(String img_url)
 {
     SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
     SharedPreferences.Editor editor = sharedPreferences.edit();
     editor.putString("Image2",img_url);
     editor.commit();

 }


    public void saveProfileDetails(String fname,String lname,String email,String mobile,String address1,String address2,String img_url,String password)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("First Name",fname);
        editor.putString("Last Name",lname);
        editor.putString("Email", email);
        editor.putString("Mobile", mobile);
        editor.putString("Address1",address1);
        editor.putString("Address2",address2);
        editor.putString("Image2",img_url);
        editor.putString("Password",password);
        editor.commit();

    }

    public void savebeneficiary(String benef_id,String benef_name1,String benef_name2,String benef_mobile,String benef_loc,String benef_email,String benef_code,String benef_address)
    {

        SharedPreferences sharedPreferences = context.getSharedPreferences("BeneficiaryDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Benef_id",benef_id);
        editor.putString("Benef_name1",benef_name1);
        editor.putString("Benef_name2",benef_name2);
        editor.putString("Benef_mobile",benef_mobile);
        editor.putString("Benef_loc", benef_loc);
        editor.putString("Benef_email",benef_email);
        editor.putString("Benef_code",benef_code);
        editor.putString("Benef_address",benef_address);
        editor.commit();
    }
    public  String getBenefid()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BeneficiaryDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Benef_id", "");
    }
    public  String getBenef_name1()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BeneficiaryDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Benef_name1", "");
    }
    public  String getBenef_name2()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BeneficiaryDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Benef_name2", "");
    }
    public  String getBenef_mobile()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BeneficiaryDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Benef_mobile", "");
    }
    public  String getBenef_loc()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BeneficiaryDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Benef_loc", "");
    }
    public  String getBenef_email()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BeneficiaryDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Benef_email", "");
    }

    public  String getBenef_code()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BeneficiaryDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Benef_code", "");
    }
    public  String getBenef_address()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BeneficiaryDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Benef_address", "");
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }

    public String getPassword() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Password", "");
    }
    public String getId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("ID", "");
    }

    public String getFirstName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("First Name", "");
    }
    public String getLastName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Last Name", "");
    }
    public String getMobile() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Mobile", "");
    }

    public String getAddress1() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Address1", "");
    }

    public String getAddress2() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Address2", "");
    }

    public String getImage() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Image2", "");
    }
    public String getUpdatedPassword() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Password", "");
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }
    public void deleteLoginDetails()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
       // AppState.getSingleInstance().setLoggingOut(true);
    }
    public void deleteProfileDetails()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        // AppState.getSingleInstance().setLoggingOut(true);
    }
    public void deleteBeneficiarydetails()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BeneficiaryDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


}

/*
class AppState {
    private static AppState singleInstance;

    private boolean isLoggingOut;

    private AppState() {
    }

    public static AppState getSingleInstance() {
        if (singleInstance == null) {
            singleInstance = new AppState();
        }
        return singleInstance;
    }

    public boolean isLoggingOut() {
        return isLoggingOut;
    }

    public void setLoggingOut(boolean isLoggingOut) {
        this.isLoggingOut = isLoggingOut;
    }
}
*/
