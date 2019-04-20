package com.gsaves.media3.gsaves.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class WalletFragment extends Fragment {
    View view;
    Fragment f=null;
    float sum_amount=0;
    static int delete_code;
    TextView tv_final_dis_amount;
    // note that these credentials will differ between live & sandbox
    // environments.
    private static final String CONFIG_CLIENT_ID = "AUUumZ4l4t7UvAMDLqpdTFKZwt1vhzwHR2KefM5Jo6BABrdzNkycrMqptNxTzpZUp_j62jpO44w1Hyc8";

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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.product_wallet_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btt_wallet_scan_code=(Button)view.findViewById(R.id.bt_wallet_scan_code);
        Button btt_wallet_pay=(Button)view.findViewById(R.id.bt_wallet_pay);
        final RecyclerView rvv_wallet_items=(RecyclerView)view.findViewById(R.id.rv_wallet_items);
        final TextView tvv_wallet_code=(TextView)view.findViewById(R.id.tv_wallet_code);
        TextView tvv_delete_allcartitems=(TextView)view.findViewById(R.id.tv_delete_allcartitems);
        Bundle b=getArguments();
        int array_size=0;


        GridLayoutManager gridLayoutManagerfaq = new GridLayoutManager(view.getContext(),1);
        gridLayoutManagerfaq.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        rvv_wallet_items.setLayoutManager(gridLayoutManagerfaq);

        CustomAdapterCartItem customAdaptercartitems = new CustomAdapterCartItem(view.getContext(), new PrefManagerCart(view.getContext()).getArrayList("product_names"), new PrefManagerCart(view.getContext()).getArrayList("discount_prices"),  new PrefManagerCart(view.getContext()).getArrayList("item_remove"));
        rvv_wallet_items.setAdapter(customAdaptercartitems); // set the Adapter to RecyclerView
        rvv_wallet_items.addItemDecoration(new SimpleDividerItemDecoration(view.getContext()));
        try {

            array_size = new PrefManagerCart(view.getContext()).getArrayList("discount_prices").size();
        }catch(Exception e){
            array_size=0;
        }
        if(array_size!=0) {
               for (int i = 0; i < array_size; i++) {
                sum_amount += Float.parseFloat(new PrefManagerCart(view.getContext()).getArrayList("discount_prices").get(i));
            }


        }
        else
        {
            sum_amount=0;
        }

         /* if(b.get("comeback").equals("true"))
          {
              System.out.println("comback captured");
          }*/
        tvv_wallet_code.setText(String.valueOf(sum_amount));

        btt_wallet_scan_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator((Activity)v.getContext()).setCaptureActivity(Barcodescanning2Activity.class).initiateScan();
            }
        });
        btt_wallet_pay.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                Intent intent = new Intent(getContext(), PayPalService.class);
                                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                                c=new ContextWrapper(getContext());
                                c.startService(intent);

                                getPayment();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                f=new BarcodescannerFragment();
                                if (f != null) {

                                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                Toast.makeText(getContext(),"Payment page called",Toast.LENGTH_SHORT).show();



             /*   Bundle b=new Bundle();
                b.putString("user id",new PrefManager(v.getContext()).getId());
                b.putInt("sum amount",sum_amount);
                //f=new BeneficiariesFragment_test();
                f=new PaymentPageFragment();
                f.setArguments(b);
                if (f != null) {

                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                    ft.addToBackStack(null);
                    ft.commit();
                }*/
               /* final Dialog mdialog = new Dialog(v.getContext());
                mdialog.setContentView(R.layout.account_options);

                Button bt_accoption_ok=(

                        Button)mdialog.findViewById(R.id.bt_accoption_ok);
                Button bt_accoption_cancel=(

                        Button)mdialog.findViewById(R.id.bt_accoption_cancel);
                final RadioButton savedacc=(

                        RadioButton)mdialog.findViewById(R.id.rdbut_savedacc);
                final RadioButton newacc=(

                        RadioButton)mdialog.findViewById(R.id.rdbut_newacc);
                savedacc.setChecked(true);
                bt_accoption_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mdialog.dismiss();
                                            }
                });

                bt_accoption_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // mdialog.dismiss();
                        *//*Intent intent = new Intent(mdialog.getContext(), BeneficiariesFragment_test.class);
                        startActivity(intent);*//*
                        Bundle b=new Bundle();
                        b.putString("user id",new PrefManager(v.getContext()).getId());
                        b.putInt("sum amount",sum_amount);
                        //f=new BeneficiariesFragment_test();
                        f=new PaymentPageFragment();
                        f.setArguments(b);
                        if (f != null) {

                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                        mdialog.dismiss();
                    }
                });


                mdialog.show();*/
            }

            private void getPayment() {
                String total=new PrefManagerPayment(getContext()).getDiscountTotal();

               // String total=String.valueOf(sum_amount);



                System.out.println("Actual amount:"+sum_amount);

                if(sum_amount==0)
                {
                    total="0.0";
                }

                System.out.println("total to proceed :"+total);
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.discount_options);
                Button bt_100 = (Button) dialog.findViewById(R.id.bt_100);
                Button bt_75 = (Button) dialog.findViewById(R.id.bt_75);
                Button bt_50 = (Button) dialog.findViewById(R.id.bt_50);
                Button bt_25 = (Button) dialog.findViewById(R.id.bt_25);
                Button bt_dis_cancel = (Button) dialog.findViewById(R.id.bt_discount_cancel);
                Button bt_dis_ok = (Button) dialog.findViewById(R.id.bt_discount_ok);
                final EditText et_custom_discount=(EditText)dialog.findViewById(R.id.et_discount_custome);
                tv_final_dis_amount=(TextView)dialog.findViewById(R.id.tv_final_dis_amount);
                // if button is clicked, close the custom dialog
                tv_final_dis_amount.setText("$ "+total);
                final String[] finalTotal = {total};
                final String[] finalTotal2 = {total};
                et_custom_discount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String finalpay;
                        float per;
                        if(et_custom_discount.getText().toString().equals(""))
                        {
                            per=0;
                        }
                        else {
                            per = Float.parseFloat(et_custom_discount.getText().toString());
                            per=100-per;
                        }
                        finalpay = cal_dis_option(finalTotal[0], per);
                        tv_final_dis_amount.setText("$ "+finalpay);
                        finalTotal2[0]=finalpay;
                    }

                    @Override
                    public void afterTextChanged(Editable s) {


                            String finalpay;
                            float per;
                            if(et_custom_discount.getText().toString().equals(""))
                            {
                                per=0;
                            }
                            else {
                                per = Float.parseFloat(et_custom_discount.getText().toString());
                                per=100-per;
                            }
                            finalpay = cal_dis_option(finalTotal[0], per);
                            tv_final_dis_amount.setText("$ "+finalpay);
                        finalTotal2[0]=finalpay;

                    }
                });
                bt_50.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   et_custom_discount.setText("50%");
                        String finalpay;
                        finalpay =cal_dis_option(finalTotal[0],50);
                        finalTotal2[0]=finalpay;
                        tv_final_dis_amount.setText("$ "+finalTotal2[0]);

                    }
                });
                bt_25.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   et_custom_discount.setText("25%");
                        String finalpay;
                        finalpay=cal_dis_option(finalTotal[0],75);
                        finalTotal2[0]=finalpay;
                        tv_final_dis_amount.setText("$ "+finalTotal2[0]);

                    }
                });

                bt_75.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //    et_custom_discount.setText("75%");
                        String finalpay;
                        finalpay=cal_dis_option(finalTotal[0],25);
                        finalTotal2[0]=finalpay;
                        tv_final_dis_amount.setText("$ "+finalTotal2[0]);

                    }
                });
                bt_dis_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // DashboardActivity.sum_amount=Float.parseFloat(finalTotal2[0]);
                        proceedtopay(finalTotal2[0]);
                        //new PrefManagerPayment(getContext()).saveDiscountTotal(DashboardActivity.sum_amount,"total_amount");
                        dialog.dismiss();

                    }
                });
                bt_dis_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                bt_100.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // dialog.dismiss();
                        //Toast.makeText(getContext(),"Dismissed..!!",Toast.LENGTH_SHORT).show();
                       // et_custom_discount.setText("100%");
                        tv_final_dis_amount.setText("$ "+finalTotal[0]);


                    }
                });
                dialog.show();
                Log.d("amount in cart",""+total);

            }

            private void proceedtopay(String s) {
                System.out.println("calculated val:: "+s);
                new PrefManagerPayment(getContext()).updateTotal(Float.parseFloat(s),"total_amount");
                thingToBuy = new PayPalPayment(new BigDecimal(s), "USD",
                        "Amount for Beneficiary", PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(getContext(),
                        PaymentActivity.class);

                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
            }

            private String cal_dis_option(String fnTotal,float percentage) {
                String cal_dis=null;
                float f=roundfn(Float.parseFloat(fnTotal));
                float p=percentage;
                f=f-f*(p/100);
                cal_dis=String.valueOf(f);
                return cal_dis;
            }

            private float roundfn(float v) {
                BigDecimal bd = new BigDecimal(v);
                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                return bd.floatValue();
            }
        });


        tvv_delete_allcartitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardActivity.count=0;
                DashboardActivity.sum_amount=0;
                CustomAdapterCartItem customAdaptercartitemsdel = new CustomAdapterCartItem(v.getContext(), new PrefManagerCart(v.getContext()).getArrayList("product_names"), new PrefManagerCart(v.getContext()).getArrayList("discount_prices"),  new PrefManagerCart(v.getContext()).getArrayList("item_remove"));
                customAdaptercartitemsdel.deleteAll(v.getContext());
                rvv_wallet_items.setAdapter(customAdaptercartitemsdel);
                tvv_wallet_code.setText(String.valueOf( DashboardActivity.sum_amount));
                new PrefManagerPayment(getContext()).saveDiscountTotal(DashboardActivity.sum_amount,"total_amount");
                delete_code=1;


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                tv_final_dis_amount.setText("$ 0.0");

                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));


                        System.out.println(confirm.getProofOfPayment().toJSONObject().toString(4));
                        JSONObject resultjson=confirm.getProofOfPayment().toJSONObject();
                        Log.d("paypalresponseid",resultjson.getString("id"));
                        Toast.makeText(getContext(), "Amount debited successfully",
                                Toast.LENGTH_LONG).show();
                        Bundle b=new Bundle();
                        b.putString("paypalresponseid",resultjson.getString("id"));
                        f=new BeneficiariesFragment_test();
                        f.setArguments(b);
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
                f=new WalletFragment();
                if (f != null) {

                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                    ft.addToBackStack(null);
                    ft.commit();
                }
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs testtt.");
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                f=new BarcodescannerFragment();
                                if (f != null) {

                                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("The specified amount must be greater than zero.Invalid Amount cannot Proceed further for Payment ! Please scan product and add to wallet to continue for payment.").setPositiveButton("ok", dialogClickListener)
                        .setNegativeButton(" ", dialogClickListener).show();


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
        c=new ContextWrapper(getContext());
        c.stopService(new Intent(getContext(), PayPalService.class));
        super.onDestroy();
    }
    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

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
