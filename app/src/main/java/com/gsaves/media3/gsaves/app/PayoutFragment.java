package com.gsaves.media3.gsaves.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
//import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.zxing.integration.android.IntentIntegrator;
import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.api.PaypalApi;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.BatchHeader;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.Errors;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.Item;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutAmount;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutRequest;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutStatusItem;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutStatusResponse;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutStatusresponseItem;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.SenderBatchHeader;
import com.gsaves.media3.gsaves.app.paypalclasses.PaymentDetailsResponse;
import com.gsaves.media3.gsaves.app.pojo.PayoutResponse;
import com.gsaves.media3.gsaves.app.pojo.PaypalAuthenticateResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PayoutFragment extends Fragment {
    View view;
    Fragment f=null;
    String payout_batchid;
    ProgressDialog progressDialog;
    TextView tv_receiver_email,tv_receiver_status,tv_receiver_msg,tv_receiver_email_title,tv_receiver_status_title;
    ImageView iv_transaction_status;
    Button bt_receiver_mail,bt_receiver_scan,bt_final_logout;
    GifImageView gifimg;
    String amount=null;
    private Resources mResources;
    private Configuration mOverrideConfiguration;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.payment_page, container, false);
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if(requestCode==111) {
             System.out.println("come back from mail-->called");
          //  iv_transaction_status.setVisibility(View.INVISIBLE);
            // iv_transaction_status.setImageResource(R.drawable.right);

               gifimg.setVisibility(View.VISIBLE);
               tv_receiver_msg.setText("Process Completed");
               tv_receiver_msg.setTextSize(25);
               bt_receiver_mail.setVisibility(View.INVISIBLE);
               bt_final_logout.setVisibility(View.VISIBLE);
               bt_receiver_scan.setVisibility(View.VISIBLE);


               new PrefManagerPayment(getContext()).updateTotal(0,"total_amount");
               new PrefManagerCart(getContext()).deleteAll();
               new PrefManagerPayment(getContext()).deleteAlll();
             DashboardActivity.sum_amount=0;

            // Glide.with(getContext()).load(R.drawable.right).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(iv_transaction_status);
             // Glide.with(getContext()).load(R.drawable.right).into(iv_transaction_status);
         }
         bt_receiver_scan.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 new IntentIntegrator((Activity)v.getContext()).setCaptureActivity(Barcodescanning2Activity.class).initiateScan();
             }
         });
        bt_final_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PrefManager(getApplicationContext()).deleteLoginDetails();
                new PrefManager(getApplicationContext()).deleteProfileDetails();
                new PrefManager(getApplicationContext()).deleteBeneficiarydetails();
                new PrefManagerPayment(getApplicationContext()).deleteAlll();

                if(new PrefManager(getApplicationContext()).isUserLogedOut()) {
                    Intent gotologin = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(gotologin);
                }
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Verifying please wait..."); // Setting Message
        progressDialog.setTitle("Payout Status"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        tv_receiver_email=(TextView)view.findViewById(R.id.tv_receiver_email);
        tv_receiver_status=(TextView)view.findViewById(R.id.tv_receiver_status);
        tv_receiver_msg=(TextView)view.findViewById(R.id.tv_receiver_msg);
        bt_receiver_mail=(Button)view.findViewById(R.id.bt_receiver_mail);
        bt_receiver_scan=(Button)view.findViewById(R.id.bt_final_startscan);
        bt_final_logout=(Button)view.findViewById(R.id.bt_final_logout);
        tv_receiver_email_title=(TextView)view.findViewById(R.id.tv_reciever_email_title);
        tv_receiver_status_title=(TextView)view.findViewById(R.id.tv_reciever_status_title);
        iv_transaction_status=(ImageView)view.findViewById(R.id.iv_gsaves_logo);
        gifimg=(GifImageView)view.findViewById(R.id.gif_img1);


        tv_receiver_status.setVisibility(View.INVISIBLE);
        tv_receiver_email.setVisibility(View.INVISIBLE);
        tv_receiver_status_title.setVisibility(View.INVISIBLE);
        tv_receiver_email_title.setVisibility(View.INVISIBLE);
        bt_final_logout.setVisibility(View.INVISIBLE);
        bt_receiver_scan.setVisibility(View.INVISIBLE);

        tv_receiver_msg.setVisibility(View.INVISIBLE);
        gifimg.setVisibility(View.INVISIBLE);



        Bundle b=getArguments();
        final String email=b.getString("benef_email");
        String item_id=b.getString("benef_code");
        int array_size;
        float sum_amount=0;
        try {

            //array_size = new PrefManagerCart(view.getContext()).getArrayList("discount_prices").size();
            array_size = Getproductdata.prodsSize();
        }catch(Exception e){
            array_size=0;
        }
        if(array_size!=0) {
            try {
               /* for (int i = 0; i < array_size; i++) {
                    sum_amount += Float.parseFloat(new PrefManagerCart(getContext()).getArrayList("discount_prices").get(i));
                }*/
                sum_amount=Float.parseFloat(new PrefManagerPayment(getContext()).getDiscountTotal());
            }catch(Exception e)
            {
                Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
           // amount.equals(new PrefManagerPayment(getContext()).getDiscountTotal());
        }
        else
        {
           //amount="0";
           sum_amount=0;
        }
       // System.out.println("actual fund"+sum_amount);

        sum_amount=roundfn(sum_amount);
        System.out.println("converted actual fund"+sum_amount);
        amount=String.valueOf(sum_amount);
        System.out.println("final amount:::"+amount+"array size::"+array_size+" "+new PrefManagerPayment(getContext()).getDiscountTotal());

        int count=(int)(Math.random());
        item_id=item_id+count;

        System.out.println("item id:::"+item_id);

        String currency="usd";
        String paypalid=b.getString("paypalid");
        String bearer="Bearer ";
        String batch_id=null;


       /* (PaypalApi.getClient().paymentdetails(paypalid)).enqueue(new Callback<PaymentDetailsResponse>() {
            @Override
            public void onResponse(Call<PaymentDetailsResponse> call, Response<PaymentDetailsResponse> response) {
              Toast.makeText(getContext(),"got response working",Toast.LENGTH_LONG).show();
              System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<PaymentDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });*/

        PayoutAmount pamt=new PayoutAmount();
        pamt.setCurrency("USD");
        pamt.setValue(amount);

        List<Item> item=new ArrayList<>();
        Item i=new Item();
        i.setAmount(pamt);
        i.setNote("Thanks for your patronage!");
        i.setReceiver(email);
        i.setRecipientType("EMAIL");
        i.setSenderItemId(item_id);
        item.add(i);


        SenderBatchHeader sbh=new SenderBatchHeader();
        sbh.setEmailMessage("You have received a payout! Thanks for using our service!");
        sbh.setEmailSubject("You have a payout from G-Saves!");
        sbh.setSenderBatchId(new StringBuilder().append("Payouts_").append(java.util.Calendar.getInstance().getTime()).toString());


        PayoutRequest pr=new PayoutRequest();
        pr.setItems(item);
        pr.setSenderBatchHeader(sbh);

        final String[] payout_batch_id = new String[1];
        final String[] batch_status = new String[1];



        //Arrays.asList().parallelStream().map();
        PaypalApi.getClient().payoutrequest(pr).enqueue(new Callback<com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutResponse>() {
            @Override
            public void onResponse(Call<com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutResponse> call, Response<com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutResponse> response) {
                System.out.println("upper result is : " + response.body());
              try {
                  BatchHeader b = response.body().getBatchHeader();
                  payout_batch_id[0] = b.getPayoutBatchId();
                  batch_status[0] = b.getBatchStatus();

              }catch (Exception e)
              {
                  Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
              }
                System.out.println("payout batch id : "+payout_batch_id[0]);
                System.out.println("batch status : "+batch_status[0]);
               if(response.isSuccessful()) {


                   new Thread(new Runnable() {
                       public void run() {
                           try {
                               Thread.sleep(6000);
                               payout_batchid = payout_batch_id[0];

//                               Toast.makeText(getContext(), "delay applied", Toast.LENGTH_LONG).show();
                               (PaypalApi.getClient().payoutstatus(payout_batchid)).enqueue(new Callback<PayoutStatusResponse>() {
                                   @Override
                                   public void onResponse(Call<PayoutStatusResponse> call, Response<PayoutStatusResponse> response) {
                                       System.out.println("result is : " + response.body());
                                       List<PayoutStatusresponseItem> sitem = response.body().getItems();

                                       for (PayoutStatusresponseItem i : sitem) {
                                           System.out.println("Transaction id: " + i.getTransactionId());
                                           tv_receiver_email.setVisibility(View.VISIBLE);
                                           tv_receiver_email.setText(email);
                                           tv_receiver_status.setVisibility(View.VISIBLE);
                                           tv_receiver_email_title.setVisibility(View.VISIBLE);
                                           tv_receiver_status_title.setVisibility(View.VISIBLE);
                                           if (i.getErrors() != null) {
                                               Errors e = i.getErrors();
                                               System.out.println("error name:" + e.getName());
                                               System.out.println("error msg:" + e.getMessage());
                                               tv_receiver_status.setText(e.getName());
                                               tv_receiver_msg.setVisibility(View.VISIBLE);
                                               tv_receiver_msg.setText(e.getMessage());
                                               bt_receiver_mail.setEnabled(false);
                                               if (e.getName().equals("RECEIVER_UNREGISTERED")) {
                                                   bt_receiver_mail.setEnabled(true);
                                               }
                                           } else {
                                               tv_receiver_status.setText(response.body().getBatchHeader().getBatchStatus());
                                               tv_receiver_msg.setVisibility(View.VISIBLE);
                                               tv_receiver_msg.setText("This receiver got amount in their Paypal Wallet. You'll need to request them to sign in PayPal to receive the payment.");
                                           }
                                       }

                               }



                                   @Override
                                   public void onFailure(Call<PayoutStatusResponse> call, Throwable t) {
                                       System.out.println(t.getMessage());

                                   }
                               });

                           } catch (Exception e) {
                               e.printStackTrace();
                               progressDialog.dismiss();
                           }
                           progressDialog.dismiss();
                       }
                   }).start();


               }
               else
               {
                   Toast.makeText(getApplicationContext(),"Server Not Responding",Toast.LENGTH_LONG).show();
                   progressDialog.dismiss();
               }


            }

            @Override
            public void onFailure(Call<com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


      bt_receiver_mail.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent(Intent.ACTION_SEND, Uri.fromParts("mailto","address@example.com", null));
            //  Intent i = new Intent(Intent.ACTION_SEND);
              i.setType("message/rfc822");
              i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
              i.putExtra(Intent.EXTRA_SUBJECT, "You have a payout from G-Saves!");
              i.putExtra(Intent.EXTRA_TEXT   , "G-Saves Payout has sent you $"+amount+" through PayPal. To claim your payment go to https://www.paypal.com");
              try {

                  startActivityForResult(Intent.createChooser(i, "Send mail..."),111);


              } catch (android.content.ActivityNotFoundException ex) {
                  Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
              }
          }
      });




    }

    private float roundfn(float sum_amount) {
        BigDecimal bd = new BigDecimal(sum_amount);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();

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
