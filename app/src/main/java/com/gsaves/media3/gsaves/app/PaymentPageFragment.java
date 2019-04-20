package com.gsaves.media3.gsaves.app;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PaymentPageFragment extends Fragment {
    View view;
    TextView tv_payment_title,tv_payment_holdername,tv_payment_benef_name,tv_payment_cardnum,tv_payment_card_cvv;


    private Button buttonPay;
    private EditText editTextAmount;

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox
    // environments.
  //private static final String CONFIG_CLIENT_ID = "AUUumZ4l4t7UvAMDLqpdTFKZwt1vhzwHR2KefM5Jo6BABrdzNkycrMqptNxTzpZUp_j62jpO44w1Hyc8";
    private static final String CONFIG_CLIENT_ID = "AQrq7fVTVMqWgxZq3grJedgmoRAR8KN2nhqLPf2o5J07Ckw1uogEl8txW2OqIj4VQ0SH45VodAkRe3oh";


    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    //Paypal intent request code to track onActivityResult method


    public static final int PAYPAL_REQUEST_CODE = 123;


    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(CONFIG_CLIENT_ID);
    PayPalPayment thingToBuy;

    //Payment Amount
    private String paymentAmount;
    ContextWrapper c;
    Fragment f=null;



    public PaymentPageFragment() {

    }

    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.payment_page, container, false);
        return null;
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      /*  tv_payment_title=(TextView)view.findViewById(R.id.tv_payment_title);
        tv_payment_holdername=(TextView)view.findViewById(R.id.tv_payment_holdername);
        tv_payment_benef_name=(TextView)view.findViewById(R.id.tv_payment_benef_name);
        tv_payment_cardnum=(TextView)view.findViewById(R.id.tv_payment_cardnum);
        tv_payment_card_cvv=(TextView)view.findViewById(R.id.tv_payment_card_cvv);
        Bundle getbun=getArguments();
        ArrayList<String> carddata=getbun.getStringArrayList("carddata");
        ArrayList<String> benefdata=getbun.getStringArrayList("benefdata");

        String cardarr[]=new String[carddata.size()];
        String benefarr[]=new String[benefdata.size()];
        int j=0;
        for(String i:carddata)
        {
            cardarr[j]=i.toString();

            j++;
        }

        for(int i=0;i<carddata.size();i++)
        {
            Log.d("card data",cardarr[i]);
        }
        tv_payment_holdername.setText(cardarr[0]);
        tv_payment_cardnum.setText(cardarr[1]);
        tv_payment_card_cvv.setText(cardarr[2]);
        int k=0;
        for(String i:benefdata)
        {
            benefarr[k]=i.toString();
            Log.d("benef data",i.toString());
            k++;
        }
        tv_payment_benef_name.setText(new PrefManagerPayment(getContext()).getDiscountTotal());
*/
        Intent intent = new Intent(getContext(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        c=new ContextWrapper(getContext());
        c.startService(intent);

        getPayment();


    }




    private void getPayment() {
        String total=new PrefManagerPayment(getContext()).getDiscountTotal();
        if(total.isEmpty())
        {
            total="0.1";
        }
        thingToBuy = new PayPalPayment(new BigDecimal(total), "USD",
                "Discount Amount for Beneficiary", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getContext(),
                PaymentActivity.class);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));

                        Toast.makeText(getContext(), "Amount debited successfully",
                                Toast.LENGTH_LONG).show();
                        f=new BeneficiariesFragment_test();
                        if (f != null) {
                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.lay_homeframe,f, "Terms and conditions");
                            ft.addToBackStack(null);
                            ft.commit();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getContext(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    @Override
    public void onDestroy() {
        // Stop service when done
       c.stopService(new Intent(getContext(), PayPalService.class));
        super.onDestroy();
    }
    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }
}
