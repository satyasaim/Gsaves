package com.gsaves.media3.gsaves.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.api.BarcodeApi;
import com.gsaves.media3.gsaves.app.barcodeclasses.BarcodeResponse;
import com.gsaves.media3.gsaves.app.barcodeclasses.Product;
import com.gsaves.media3.gsaves.app.barcodeclasses.Store;
import com.gsaves.media3.gsaves.app.interfaces.AsyncTaskCompleteListener;
import com.gsaves.media3.gsaves.app.pojo.Qrcoderesponse;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment f;
    Bundle bundle;
    int vid;
    String first_name=null;
    private TextView prof_name;
  private ImageView prof_img;
    String imgurl;
    private boolean change_fragment=false;
    IntentResult result;
    Intent data;
    int requestCode;
    public static final String FRAGMENT_TAG = "single";
    Button bt_scannreport;

    ArrayList<String> prod_name=null;
    ArrayList<String> discount_price=null;
    ArrayList<String> item_remove=null;
    static float sum_amount;

    static int count;
    static boolean comeback=false;

    /*DashboardActivity() {
        prod_name = new ArrayList<String>();
        discount_price = new ArrayList<String>();
        item_remove = new ArrayList();


    }
*/

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
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getApplicationContext(), "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {

                change_fragment=true;
                Log.d("requestcode",""+requestCode);
                this.data=data;
                this.requestCode=requestCode;
               // setResult(Activity.RESULT_OK);
               // f = this.getSupportFragmentManager().findFragmentById(R.id.frag_prod);;
               // f = new ScannedProductListFragment();
              // f.onActivityResult(requestCode, resultCode, data);

               // ActivityResultBus.getInstance().postQueue(new ActivityResultEvent(requestCode, resultCode, data));
                //f.startActivityForResult(data,requestCode);


                if(change_fragment)
                {

                    Log.d("workingreturnback",""+requestCode);
                   /* (Api.getClient().getproductdata(result.getContents())).enqueue(new Callback<Qrcoderesponse>() {
                        @Override
                        public void onResponse(Call<Qrcoderesponse> call, Response<Qrcoderesponse> response) {


                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            intent.putExtra("frag_status",change_fragment);
                            intent.putExtra("prod_name",response.body().getData().getProductName());
                            intent.putExtra("prod_price",response.body().getData().getProductPrice());
                            intent.putExtra("prod_discount",response.body().getData().getProductDiscount());
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Qrcoderesponse> call, Throwable t) {
                         Toast.makeText(getApplicationContext(),"Cannot retrieve product data",Toast.LENGTH_SHORT).show();
                        }
                    });*/

                    (BarcodeApi.getClient().getProductData(result.getContents(),"y","0i0pd6p2542nrbpl7nyjilh8ppyn21")).enqueue(new Callback<BarcodeResponse>() {
                        @Override
                        public void onResponse(Call<BarcodeResponse> call, Response<BarcodeResponse> response) {
                            //System.out.println(response.body());
                            if(response.code() == 429) {
                                Toast.makeText(getApplicationContext(),"Server Not Responding",Toast.LENGTH_LONG).show();
                            }

                            if(response.body()==null)
                            {
                                System.out.println("No product Available");

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashboardActivity.this);
                                // Setting Alert Dialog Title
                                alertDialogBuilder.setTitle("Product Availability Status!!!");
                                // Icon Of Alert Dialog
                                alertDialogBuilder.setIcon(R.drawable.report_problem);
                                // Setting Alert Dialog Message
                                alertDialogBuilder.setMessage("No Product Available.Try Again...");
                                alertDialogBuilder.setCancelable(true);
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                                final AlertDialog finalAlertDialog = alertDialog;
                                new Thread(new Runnable() {
                                    public void run() {

                                        try {
                                            Thread.sleep(5000);



                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        finalAlertDialog.dismiss();
                                    }
                                }).start();
                            }
                            else
                            {

                                List<Product> product = response.body().getProducts();
                                List<Store> stores;
                                List<String> image;
                                String productname = null;
                                String productprice = null;
                                String productdis = null;
                                String productmaxprice = null;
                                String productimag = null;
                                String store1 = null;
                                String store2 = null;

                                try {
                                    for (Product i : product) {
                                        productname = i.getProductName();
                                        image = i.getImages();
                                        productimag = image.get(0);

                                        System.out.println("Product name:" + i.getProductName());
                                        System.out.println("Product image:" + productimag);


                                        stores = i.getStores();
                                        int count = 0;
                                        if (stores.size() != 0) {
                                            for (Store j : stores) {
                                                count++;
                                                if (count == 1) {
                                                    productprice = j.getStorePrice();

                                                    store1 = j.getStoreName();
                                                    System.out.println("store name:" + store1);
                                                    System.out.println("product url name:" + j.getProductUrl());
                                                    System.out.println("store price:" + j.getStorePrice());
                                                    System.out.println("currency symbol:" + j.getCurrencySymbol());
                                                    //break;
                                                }
                                                if (count == stores.size()) {
                                                    store2 = j.getStoreName();
                                                    productmaxprice = j.getStorePrice();
                                                    System.out.println("last store name:" + store2);
                                                    System.out.println("last product url name:" + j.getProductUrl());
                                                    System.out.println("last store price:" + productmaxprice);
                                                    System.out.println("last currency symbol:" + j.getCurrencySymbol());
                                                    break;
                                                }


                                            }
                                        } else if (stores.size() == 0) {
                                            Toast.makeText(getApplicationContext(), "Store Details and prices are not available for this product.Sorry for the inconvenience.", Toast.LENGTH_LONG).show();
                                            productmaxprice = "0";
                                            productprice = "0";
                                        }
                                    }


                                    if (productmaxprice.equals(productprice)) {
                                        productdis = productmaxprice;
                                    } else {
                                        productdis = String.valueOf(Float.parseFloat(productmaxprice) - Float.parseFloat(productprice));
                                        System.out.println("Discount:" + productdis);
                                    }
                                } catch(Exception e)
                                {
                                    System.out.println(e.getMessage());
                                }
                                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                intent.putExtra("frag_status", change_fragment);
                                intent.putExtra("prod_name", productname);
                                intent.putExtra("prod_img", productimag);
                                intent.putExtra("store1", store1);
                                intent.putExtra("prod_price", productprice);
                                intent.putExtra("store2", store2);
                                intent.putExtra("prod_maxprice", productmaxprice);
                                intent.putExtra("prod_discount", productdis);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<BarcodeResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Cannot retrieve product data",Toast.LENGTH_SHORT).show();
                        }
                    });








                }

                Toast.makeText(getApplicationContext(),"fragment status"+change_fragment,Toast.LENGTH_LONG).show();

               // showResultDialogue(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }

//Paypal payment result
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        //System.out.println(confirm.toJSONObject().toString(4));
                        // System.out.println(confirm.getPayment().toJSONObject()
                        //  .toString(4));

                        System.out.println(confirm.getProofOfPayment().toJSONObject().toString(4));
                        JSONObject resultjson=confirm.getProofOfPayment().toJSONObject();
                        Log.d("paypalresponseid",resultjson.getString("id"));
                        Toast.makeText(getApplicationContext(), "Amount debited successfully",
                                Toast.LENGTH_LONG).show();



                        setContentView(R.layout.activity_dashboard3);


                        Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
                        final NavigationView navigationView3 = (NavigationView) findViewById(R.id.nav_view3);
                        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
                        stub.setLayoutResource(R.layout.home_dashboard);


                        View inflated2 = stub.inflate();
                        View hView = navigationView3.getHeaderView(0);

                        Menu m = navigationView3.getMenu();

                        for (int i = 0; i < m.size(); i++) {
                            MenuItem mi2 = m.getItem(i);

                            //for aapplying a font to subMenu ...
                            SubMenu subMenu2 = mi2.getSubMenu();
                            if (subMenu2 != null && subMenu2.size() > 0) {
                                for (int j = 0; j < subMenu2.size(); j++) {
                                    MenuItem subMenuItem = subMenu2.getItem(j);
                                    applyFontToMenuItem(subMenuItem);
                                }
                            }

                            //the method we have create in activity
                            applyFontToMenuItem(mi2);
                        }
                        DrawerLayout drawer3 = (DrawerLayout) findViewById(R.id.drawer_layout3);

                        prof_name = (TextView) hView.findViewById(R.id.prof_name);
                        prof_name.setText(new PrefManager(getApplicationContext()).getFirstName());
                        prof_img = (ImageView) hView.findViewById(R.id.prof_pic);
                        imgurl = new PrefManager(getApplicationContext()).getImage();
                        if (!imgurl.isEmpty()) {
                            Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
                        }


                        drawer3.setFitsSystemWindows(true);
                        drawer3.requestDisallowInterceptTouchEvent(true);
                        ActionBarDrawerToggle toggle2 = new ActionBarDrawerToggle(
                                DashboardActivity.this, drawer3, toolbar3, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                        drawer3.addDrawerListener(toggle2);
                        toggle2.syncState();

                        drawer3.closeDrawer(GravityCompat.START);
                        f=new BeneficiariesFragment_test();
                        if (f != null) {
                            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.lay_homeframe,f, "Terms and conditions");
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                        navigationView3.setNavigationItemSelectedListener(DashboardActivity.this);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
                count=0;
                comeback=true;
              /*  RecyclerView rvv_wallet_items=(RecyclerView)findViewById(R.id.rv_wallet_items);
             CustomAdapterCartItem customAdaptercartitems = new CustomAdapterCartItem(DashboardActivity.this, new PrefManagerCart(DashboardActivity.this).getArrayList("product_names"), new PrefManagerCart(DashboardActivity.this).getArrayList("discount_prices"),  new PrefManagerCart(DashboardActivity.this).getArrayList("item_remove"));
                rvv_wallet_items.setAdapter(customAdaptercartitems);*/
               setContentView(R.layout.activity_dashboard3);


                Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
                final NavigationView navigationView3 = (NavigationView) findViewById(R.id.nav_view3);
                ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
                stub.setLayoutResource(R.layout.home_dashboard);


                View inflated2 = stub.inflate();
                View hView = navigationView3.getHeaderView(0);

                Menu m = navigationView3.getMenu();

                for (int i = 0; i < m.size(); i++) {
                    MenuItem mi2 = m.getItem(i);

                    //for aapplying a font to subMenu ...
                    SubMenu subMenu2 = mi2.getSubMenu();
                    if (subMenu2 != null && subMenu2.size() > 0) {
                        for (int j = 0; j < subMenu2.size(); j++) {
                            MenuItem subMenuItem = subMenu2.getItem(j);
                            applyFontToMenuItem(subMenuItem);
                        }
                    }

                    //the method we have create in activity
                    applyFontToMenuItem(mi2);
                }
                DrawerLayout drawer3 = (DrawerLayout) findViewById(R.id.drawer_layout3);

                prof_name = (TextView) hView.findViewById(R.id.prof_name);
                prof_name.setText(new PrefManager(getApplicationContext()).getFirstName());
                prof_img = (ImageView) hView.findViewById(R.id.prof_pic);
                imgurl = new PrefManager(getApplicationContext()).getImage();
                if (!imgurl.isEmpty()) {
                    Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
                }


                drawer3.setFitsSystemWindows(true);
                drawer3.requestDisallowInterceptTouchEvent(true);
                ActionBarDrawerToggle toggle2 = new ActionBarDrawerToggle(
                        DashboardActivity.this, drawer3, toolbar3, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer3.addDrawerListener(toggle2);
                toggle2.syncState();

                drawer3.closeDrawer(GravityCompat.START);
                Bundle b=new Bundle();
                b.putString("comeback","true");

                f=new WalletFragment();
                f.setArguments(b);
                if (f != null) {
                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.lay_homeframe, f, "Wallet fragment");
                    ft.addToBackStack(null);
                    ft.commit();
                }
                navigationView3.setNavigationItemSelectedListener(DashboardActivity.this);
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
                count=0;
                comeback=true;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                f=new BarcodescannerFragment();
                                if (f != null) {

                                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setMessage("Invalid Amount cannot Proceed further for Payment ! Please scan product and add to wallet to continue for payment.").setPositiveButton("ok", dialogClickListener)
                        .setNegativeButton("", dialogClickListener).show();

                setContentView(R.layout.activity_dashboard3);
                Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
                final NavigationView navigationView3 = (NavigationView) findViewById(R.id.nav_view3);
                ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
                stub.setLayoutResource(R.layout.home_dashboard);
                /*ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) stub.getLayoutParams();
                params.setMargins(0,50,0,0);
                stub.setLayoutParams(params);*/
                View inflated2 = stub.inflate();
                View hView = navigationView3.getHeaderView(0);

                Menu m = navigationView3.getMenu();

                for (int i = 0; i < m.size(); i++) {
                    MenuItem mi2 = m.getItem(i);

                    //for aapplying a font to subMenu ...
                    SubMenu subMenu2 = mi2.getSubMenu();
                    if (subMenu2 != null && subMenu2.size() > 0) {
                        for (int j = 0; j < subMenu2.size(); j++) {
                            MenuItem subMenuItem = subMenu2.getItem(j);
                            applyFontToMenuItem(subMenuItem);
                        }
                    }

                    //the method we have create in activity
                    applyFontToMenuItem(mi2);
                }
                DrawerLayout drawer3 = (DrawerLayout) findViewById(R.id.drawer_layout3);

                prof_name = (TextView) hView.findViewById(R.id.prof_name);
                prof_name.setText(new PrefManager(getApplicationContext()).getFirstName());
                prof_img = (ImageView) hView.findViewById(R.id.prof_pic);
                imgurl = new PrefManager(getApplicationContext()).getImage();
                if (!imgurl.isEmpty()) {
                    Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
                }


                drawer3.setFitsSystemWindows(true);
                drawer3.requestDisallowInterceptTouchEvent(true);
                ActionBarDrawerToggle toggle2 = new ActionBarDrawerToggle(
                        DashboardActivity.this, drawer3, toolbar3, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer3.addDrawerListener(toggle2);
                toggle2.syncState();

                drawer3.closeDrawer(GravityCompat.START);
                f=new BarcodescannerFragment();
                if (f != null) {
                    final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                    ft.addToBackStack(null);
                    ft.commit();
                }
                navigationView3.setNavigationItemSelectedListener(DashboardActivity.this);
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
                        Toast.makeText(getApplicationContext(),
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
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }


    //method to construct dialogue with scan results
    public void showResultDialogue(final String result) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + result)
                .setPositiveButton("Copy result", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(DashboardActivity.this, "Result copied to clipboard"+result, Toast.LENGTH_SHORT).show();

                       // (R.layout.activity_dashboard);
                       /* f=new ScannedProductListFragment();
                      *//*  f = DashboardActivity.this.getSupportFragmentManager().findFragmentById(R.id.frag_prod);

                        FragmentTransaction ft =DashboardActivity.this.getSupportFragmentManager().beginTransaction();
                        ft.attach(f);

                        ft.addToBackStack(null);
                        ft.commit();*//*

                        FragmentManager fm1 = DashboardActivity.this.getSupportFragmentManager();
                        FragmentTransaction transaction = fm1.beginTransaction();

                        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.add(R.id.lay_homeframe, f);
                       // transaction.addToBackStack(null);// replace a Fragment with Frame Layout
                        transaction.commit();*/


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();


    }
    @Override
    public void onResume() {
        super.onResume();



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);





        bundle=getIntent().getExtras();

        boolean fstatus = false;
        if(true) {
            if (bundle.getBoolean("frag_status")) {

                fstatus = bundle.getBoolean("frag_status");
            }
        }

        if(fstatus)
       {
          final String product_name=bundle.getString("prod_name");
           final String prod_price=bundle.getString("prod_price");
           final String prod_discount=bundle.getString("prod_discount");
           final String prod_maxprice=bundle.getString("prod_maxprice");
           final String prod_img=bundle.getString("prod_img");
           final String store1=bundle.getString("store1");
           final String store2=bundle.getString("store2");

           new GenericAsyncTask(getApplicationContext(),new AsyncTaskCompleteListener()
           {


               @Override
               public void onTaskComplete(Object result) {






                  // Toast.makeText(getApplicationContext(), "button click fragmenttt status recall !!!--> true", Toast.LENGTH_LONG).show();

                    Log.d("testing", "testing");


                   setContentView(R.layout.activity_dashboard3);
                   Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbar3);

                   final NavigationView navigationView3 = (NavigationView) findViewById(R.id.nav_view3);
                   ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
                   stub.setLayoutResource(R.layout.scanned_product);
                   View inflated2 = stub.inflate();

                   TextView tvv_code=(TextView)findViewById(R.id.tv_code);
                   tvv_code.setText(product_name);
                   TextView tvv_prod_price=(TextView)findViewById(R.id.tv_prod_price);
                   tvv_prod_price.setText(prod_price);
                   TextView tvv_prod_price2=(TextView)findViewById(R.id.tv_prod_price2);
                   tvv_prod_price2.setText(prod_maxprice);
                   TextView tvv_store1=(TextView)findViewById(R.id.tv_storename);
                   tvv_store1.setText(store1);
                   TextView tvv_store2=(TextView)findViewById(R.id.tv_storename2);
                   tvv_store2.setText(store2);
                   ImageView ivv_product=(ImageView)findViewById(R.id.iv_product);
                  try {

                      if (!prod_img.isEmpty()) {
                          Picasso.with(getApplicationContext()).load(prod_img).resize(500, 500).into(ivv_product);
                      }
                  }catch (Exception e)
                  {
                      System.out.println(e.getMessage());
                  }

                   TextView tvv_prod_discount=(TextView)findViewById(R.id.tv_prod_discount);
                   tvv_prod_discount.setText(prod_discount);

                   Button btt_scan_code=(Button)findViewById(R.id.bt_scan_code);
                   Button btt_wallet=(Button)findViewById(R.id.bt_add_wallet);


                   View hView = navigationView3.getHeaderView(0);

                   Menu m = navigationView3.getMenu();

                   for (int i = 0; i < m.size(); i++) {
                       MenuItem mi2 = m.getItem(i);

                       //for aapplying a font to subMenu ...
                       SubMenu subMenu2 = mi2.getSubMenu();
                       if (subMenu2 != null && subMenu2.size() > 0) {
                           for (int j = 0; j < subMenu2.size(); j++) {
                               MenuItem subMenuItem = subMenu2.getItem(j);
                               applyFontToMenuItem(subMenuItem);
                           }
                       }

                       //the method we have create in activity
                       applyFontToMenuItem(mi2);
                   }
                   DrawerLayout drawer3 = (DrawerLayout) findViewById(R.id.drawer_layout3);

                   prof_name = (TextView) hView.findViewById(R.id.prof_name);
                   prof_name.setText(new PrefManager(getApplicationContext()).getFirstName());
                   prof_img = (ImageView) hView.findViewById(R.id.prof_pic);
                   imgurl = new PrefManager(getApplicationContext()).getImage();
                   if (!imgurl.isEmpty()) {
                       Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
                   }


                   drawer3.setFitsSystemWindows(true);
                   drawer3.requestDisallowInterceptTouchEvent(true);
                   ActionBarDrawerToggle toggle2 = new ActionBarDrawerToggle(
                           DashboardActivity.this, drawer3, toolbar3, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                   drawer3.addDrawerListener(toggle2);
                   toggle2.syncState();

                   drawer3.closeDrawer(GravityCompat.START);


                   vid = 2;

                   btt_scan_code.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           new IntentIntegrator(DashboardActivity.this).setCaptureActivity(Barcodescanning2Activity.class).initiateScan();
                       }
                   });



                   btt_wallet.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                           count++;
                           setContentView(R.layout.activity_dashboard3);
                           Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbar3);


                           final NavigationView navigationView3 = (NavigationView) findViewById(R.id.nav_view3);
                           ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
                           //stub.setLayoutResource(R.layout.product_wallet);
                           stub.setLayoutResource(R.layout.home_dashboard);
                           ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) stub.getLayoutParams();
                params.setMargins(0,75,0,0);
                stub.setLayoutParams(params);
                           View inflated2 = stub.inflate();
                           Button btt_wallet_scan_code=(Button)findViewById(R.id.bt_wallet_scan_code);
                           Button btt_wallet_pay=(Button)findViewById(R.id.bt_wallet_pay);

                           final RecyclerView rvv_wallet_items=(RecyclerView)findViewById(R.id.rv_wallet_items);
                           final TextView tvv_delete_allcartitems=(TextView)findViewById(R.id.tv_delete_allcartitems);
                           final TextView tvv_wallet_code=(TextView)findViewById(R.id.tv_wallet_code);


                           View hView = navigationView3.getHeaderView(0);
                           Menu m = navigationView3.getMenu();

                           for (int i = 0; i < m.size(); i++) {
                               MenuItem mi2 = m.getItem(i);

                               //for aapplying a font to subMenu ...
                               SubMenu subMenu2 = mi2.getSubMenu();
                               if (subMenu2 != null && subMenu2.size() > 0) {
                                   for (int j = 0; j < subMenu2.size(); j++) {
                                       MenuItem subMenuItem = subMenu2.getItem(j);
                                       applyFontToMenuItem(subMenuItem);
                                   }
                               }

                               //the method we have create in activity
                               applyFontToMenuItem(mi2);
                           }
                           DrawerLayout drawer3 = (DrawerLayout) findViewById(R.id.drawer_layout3);

                           prof_name = (TextView) hView.findViewById(R.id.prof_name);
                           prof_name.setText(new PrefManager(getApplicationContext()).getFirstName());
                           prof_img = (ImageView) hView.findViewById(R.id.prof_pic);
                           imgurl = new PrefManager(getApplicationContext()).getImage();
                           if (!imgurl.isEmpty()) {
                               Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
                           }


                           drawer3.setFitsSystemWindows(true);
                           drawer3.requestDisallowInterceptTouchEvent(true);
                           ActionBarDrawerToggle toggle2 = new ActionBarDrawerToggle(
                                   DashboardActivity.this, drawer3, toolbar3, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                           drawer3.addDrawerListener(toggle2);
                           toggle2.syncState();

                           drawer3.closeDrawer(GravityCompat.START);







                          /* vid=2;
                           if(comeback==true) {
                               Toast.makeText(getApplicationContext(), "recall", Toast.LENGTH_LONG).show();
                              sum_amount=0;

                               *//*prod_name=new PrefManagerCart(DashboardActivity.this).getArrayList("product_names");
                               discount_price=new PrefManagerCart(DashboardActivity.this).getArrayList("discount_prices");
                               item_remove=new PrefManagerCart(DashboardActivity.this).getArrayList("item_remove");*//*


                               prod_name.add(product_name);
                               discount_price.add(prod_discount);
                               item_remove.add(String.valueOf(R.drawable.delete));
                               for(int i=0;i<discount_price.size();i++) {
                                   sum_amount += roundfn(Float.parseFloat(discount_price.get(i)));

                               }
                           }
                           else {


                              prod_name=new ArrayList<>();
                               discount_price=new ArrayList<>();
                               item_remove=new ArrayList<>();
                               prod_name.add(product_name);
                               discount_price.add(prod_discount);
                               item_remove.add(String.valueOf(R.drawable.delete));
                               for(int i=0;i<discount_price.size();i++) {
                                   sum_amount += roundfn(Float.parseFloat(discount_price.get(i)));

                               }
                           }

                           Log.d("count tessst val",""+count);


                         if(count==1) {
                             new PrefManagerCart(DashboardActivity.this).saveArrayList(prod_name, "product_names");
                             new PrefManagerCart(DashboardActivity.this).saveArrayList(discount_price, "discount_prices");
                             new PrefManagerCart(DashboardActivity.this).saveArrayList(item_remove, "item_remove");

                             System.out.println("total sum in first scan"+sum_amount);

                         }
                           System.out.println("array size"+prod_name.size());
                           if((count>1) && (prod_name.size()==1))
                           {

                               System.out.println("test included count > 1 and prod_name.size=1:"+prod_name.size());
                               if(prod_name.size()==0)
                               {
                                   System.out.println("test when prod_name.size=0"+prod_name.size());
                                sum_amount=0;
                                    *//*prod_name.add(product_name);
                                   discount_price.add(prod_discount);
                                   item_remove.add(String.valueOf(R.drawable.delete));
*//*
                               }


                               //for(int i=0;i<discount_price.size();i++) {
                                   //sum_amount += roundfn(Float.parseFloat(discount_price.get(i)));

                               //}

                               new PrefManagerCart(DashboardActivity.this).saveArrayList(prod_name, "product_names");
                               new PrefManagerCart(DashboardActivity.this).saveArrayList(discount_price, "discount_prices");
                               new PrefManagerCart(DashboardActivity.this).saveArrayList(item_remove, "item_remove");
                           }

                           GridLayoutManager gridLayoutManagerfaq = new GridLayoutManager(DashboardActivity.this,1);
                           gridLayoutManagerfaq.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                           rvv_wallet_items.setLayoutManager(gridLayoutManagerfaq);


                           final CustomAdapterCartItem customAdaptercartitems = new CustomAdapterCartItem(DashboardActivity.this, new PrefManagerCart(DashboardActivity.this).getArrayList("product_names"), new PrefManagerCart(DashboardActivity.this).getArrayList("discount_prices"),  new PrefManagerCart(DashboardActivity.this).getArrayList("item_remove"));


                           if(count>1) {
                               customAdaptercartitems.insertItem(DashboardActivity.this, customAdaptercartitems.getPosition(), product_name, prod_discount, String.valueOf(R.drawable.delete));

                           }

                           System.out.println("array size"+prod_name.size());

                           rvv_wallet_items.setAdapter(customAdaptercartitems); // set the Adapter to RecyclerView

                           rvv_wallet_items.addItemDecoration(new SimpleDividerItemDecoration(DashboardActivity.this));

                           tvv_wallet_code.setText(String.valueOf(sum_amount));

                           new PrefManagerPayment(getApplicationContext()).saveDiscountTotal(sum_amount,"total_amount");

                                       if (customAdaptercartitems.delstatus()) {

                                           tvv_wallet_code.setText(String.valueOf(customAdaptercartitems.getUpdatedamount()));
                                       }

                           if(customAdaptercartitems.delstatus())
                           {

                               tvv_wallet_code.setText(String.valueOf(customAdaptercartitems.getUpdatedamount()));
                           }

                           tvv_delete_allcartitems.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   count=0;
                                   sum_amount=0;

                                   CustomAdapterCartItem customAdaptercartitemsdel = new CustomAdapterCartItem(DashboardActivity.this, new PrefManagerCart(DashboardActivity.this).getArrayList("product_names"), new PrefManagerCart(DashboardActivity.this).getArrayList("discount_prices"),  new PrefManagerCart(DashboardActivity.this).getArrayList("item_remove"));
                                   customAdaptercartitemsdel.deleteAll(DashboardActivity.this);
                                   rvv_wallet_items.setAdapter(customAdaptercartitemsdel);
                                   tvv_wallet_code.setText(String.valueOf(sum_amount));

                                  // new PrefManagerCart(getApplicationContext()).deleteAll();
                                   new PrefManagerPayment(getApplicationContext()).saveDiscountTotal(sum_amount,"total_amount");



                               }
                           });


                           btt_wallet_scan_code.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   new IntentIntegrator(DashboardActivity.this).setCaptureActivity(Barcodescanning2Activity.class).initiateScan();
                               }
                           });

                           btt_wallet_pay.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {



                                   DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {

                                   switch (which){
                                       case DialogInterface.BUTTON_POSITIVE:
                                           //Yes button clicked
                                           Intent intent = new Intent(getApplicationContext(), PayPalService.class);
                                           intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                                           c=new ContextWrapper(getApplicationContext());
                                           c.startService(intent);
                                           getPayment();
                                           break;

                                       case DialogInterface.BUTTON_NEGATIVE:
                                           //No button clicked
                                           gotofragment();

                                           break;
                                   }
                               }

                                       private void gotofragment() {
                                           setContentView(R.layout.activity_dashboard3);
                                           Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
                                           final NavigationView navigationView3 = (NavigationView) findViewById(R.id.nav_view3);
                                           ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
                                           stub.setLayoutResource(R.layout.home_dashboard);
                                           View inflated2 = stub.inflate();
                                           View hView = navigationView3.getHeaderView(0);

                                           Menu m = navigationView3.getMenu();

                                           for (int i = 0; i < m.size(); i++) {
                                               MenuItem mi2 = m.getItem(i);

                                               //for aapplying a font to subMenu ...
                                               SubMenu subMenu2 = mi2.getSubMenu();
                                               if (subMenu2 != null && subMenu2.size() > 0) {
                                                   for (int j = 0; j < subMenu2.size(); j++) {
                                                       MenuItem subMenuItem = subMenu2.getItem(j);
                                                       applyFontToMenuItem(subMenuItem);
                                                   }
                                               }

                                               //the method we have create in activity
                                               applyFontToMenuItem(mi2);
                                           }
                                           DrawerLayout drawer3 = (DrawerLayout) findViewById(R.id.drawer_layout3);

                                           prof_name = (TextView) hView.findViewById(R.id.prof_name);
                                           prof_name.setText(new PrefManager(getApplicationContext()).getFirstName());
                                           prof_img = (ImageView) hView.findViewById(R.id.prof_pic);
                                           imgurl = new PrefManager(getApplicationContext()).getImage();
                                           if (!imgurl.isEmpty()) {
                                               Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
                                           }


                                           drawer3.setFitsSystemWindows(true);
                                           drawer3.requestDisallowInterceptTouchEvent(true);
                                           ActionBarDrawerToggle toggle2 = new ActionBarDrawerToggle(
                                                   DashboardActivity.this, drawer3, toolbar3, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                                           drawer3.addDrawerListener(toggle2);
                                           toggle2.syncState();

                                           drawer3.closeDrawer(GravityCompat.START);
                                           f=new WalletFragment();
                                           if (f != null) {
                                               final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                               ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                                               ft.addToBackStack(null);
                                               ft.commit();
                                           }
                                           navigationView3.setNavigationItemSelectedListener(DashboardActivity.this);
                                       }
                                   };

                           AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                           builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                   .setNegativeButton("No", dialogClickListener).show();


                               }

                               private void getPayment() {
                                   String total=new PrefManagerPayment(getApplicationContext()).getDiscountTotal();
                                   if(total.isEmpty())
                                   {
                                       total="0";
                                       new PrefManagerPayment(getApplicationContext()).updateTotal(Float.parseFloat(total),"total_amount");
                                       //total="0.1";
                                   }

                                   final Dialog dialog = new Dialog(DashboardActivity.this);
                                   dialog.setContentView(R.layout.discount_options);
                                   Button bt_100 = (Button) dialog.findViewById(R.id.bt_100);
                                   Button bt_75 = (Button) dialog.findViewById(R.id.bt_75);
                                   Button bt_50 = (Button) dialog.findViewById(R.id.bt_50);
                                   Button bt_25 = (Button) dialog.findViewById(R.id.bt_25);
                                   Button bt_dis_cancel = (Button) dialog.findViewById(R.id.bt_discount_cancel);
                                   Button bt_dis_ok = (Button) dialog.findViewById(R.id.bt_discount_ok);
                                   final EditText et_custom_discount=(EditText)dialog.findViewById(R.id.et_discount_custome);
                                   final TextView tv_final_dis_amount=(TextView)dialog.findViewById(R.id.tv_final_dis_amount);
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




                               }
                               private void proceedtopay(String s) {
                                   System.out.println("calculated val:: "+s);
                                   thingToBuy = new PayPalPayment(new BigDecimal(s), "USD",
                                           "Amount for Beneficiary", PayPalPayment.PAYMENT_INTENT_SALE);
                                   Intent intent = new Intent(getApplicationContext(),
                                           PaymentActivity.class);

                                   intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                                   startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                               }

                               private String cal_dis_option(String fnTotal, float percentage) {
                                   String cal_dis=null;
                                   float f=roundfn(Float.parseFloat(fnTotal));
                                   float p=percentage;
                                   f=f-f*(p/100);
                                   cal_dis=String.valueOf(f);
                                   return cal_dis;

                               }
                           });*/

                           Getproductdata gp=new Getproductdata(product_name,prod_discount,String.valueOf(R.drawable.delete),getApplicationContext(),count);
                           gp.addProduct();

                           f=new WalletFragment();
                           if (f != null) {
                               final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                               ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                               ft.addToBackStack(null);
                               ft.commit();
                           }
                           navigationView3.setNavigationItemSelectedListener(DashboardActivity.this);

                       }

                       private float roundfn(float v) {
                           BigDecimal bd = new BigDecimal(v);
                           bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                           return bd.floatValue();
                       }
                   });


                   navigationView3.setNavigationItemSelectedListener(DashboardActivity.this);



               }






           }).execute();






       }






    vid=0;

        setContentView(R.layout.activity_dashboard);


        Button bt_scan=(Button)findViewById(R.id.bt_start_scan);
         bt_scan.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 /*Intent intent = new Intent(getApplicationContext(), BarcodescanningActivity.class);
                 startActivity(intent);*/
                 new IntentIntegrator(DashboardActivity.this).setCaptureActivity(Barcodescanning2Activity.class).initiateScan();
             }
         });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        prof_name=(TextView)hView.findViewById(R.id.prof_name);
        prof_img=(ImageView)hView.findViewById(R.id.prof_pic);
        prof_name.setText(new PrefManager(getApplicationContext()).getFirstName());

        imgurl=new PrefManager(getApplicationContext()).getImage();


        if(!imgurl.isEmpty()) {
            Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
        }

        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            prof_img=(ImageView)hView.findViewById(R.id.prof_pic);
            imgurl=new PrefManager(getApplicationContext()).getImage();


            if(!imgurl.isEmpty()) {
                Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
            }
        }


        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }



            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
//        prof_name=(TextView)drawer.findViewById(R.id.prof_name);
 //       prof_name.setText(new PrefManager(getApplicationContext()).getFirstName());
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        setContentView(R.layout.activity_dashboard2);
        ViewStub stub=(ViewStub)findViewById(R.id.layout_stub2);
        stub.setLayoutResource(R.layout.home_dashboard);
        View inflated4 = stub.inflate();



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        setContentView(R.layout.activity_dashboard2);
        ViewStub stub=(ViewStub)findViewById(R.id.layout_stub2);
        stub.setLayoutResource(R.layout.home_dashboard);
        View inflated2 = stub.inflate();

        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view2);
        final View hView =  navigationView2.getHeaderView(0);

        Menu m = navigationView2.getMenu();

        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        DrawerLayout drawer2 = (DrawerLayout) findViewById(R.id.drawer_layout2);

        prof_name=(TextView)hView.findViewById(R.id.prof_name);
        prof_name.setText(new PrefManager(getApplicationContext()).getFirstName());
        prof_img=(ImageView)hView.findViewById(R.id.prof_pic);
        imgurl=new PrefManager(getApplicationContext()).getImage();


        if(!imgurl.isEmpty()) {
            Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
        }


        drawer2.setFitsSystemWindows(true);
        drawer2.requestDisallowInterceptTouchEvent(true);
        ActionBarDrawerToggle toggle2 = new ActionBarDrawerToggle(
                this, drawer2, toolbar2, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer2.addDrawerListener(toggle2);
        toggle2.syncState();



        drawer2.closeDrawer(GravityCompat.START);




        /*EditText et_prof1=(EditText)findViewById(R.id.et_prof_name);
        et_prof1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
             Toast.makeText(DashboardActivity.this,"test",Toast.LENGTH_LONG).show();
            }
        });*/
        vid=1;

       navigationView2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {



           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {


              // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
               int id = menuitem.getItemId();
               DrawerLayout drawer2 = (DrawerLayout) findViewById(R.id.drawer_layout2);
               //drawer2.setFitsSystemWindows(true);
               //drawer2.requestDisallowInterceptTouchEvent(true);


               drawer2.closeDrawer(GravityCompat.START);






               if (id == R.id.nav_home) {

                       f = new BarcodescannerFragment();

                   //f=new ScannedProductListFragment();


                   Toast.makeText(getApplicationContext(),"testing",Toast.LENGTH_LONG).show();
                   // Handle the camera action
               } else if (id == R.id.nav_profile) {

                   f=new ProfileFragment();
                   if(bundle!=null) {
                       f.setArguments(bundle);
                   }

                  // Toast.makeText(getApplicationContext(),"testing2",Toast.LENGTH_LONG).show();
               } else if (id == R.id.nav_beneficiaries) {
                   f=new BeneficiariesFragment();
                 //  Toast.makeText(getApplicationContext(),"testing3",Toast.LENGTH_LONG).show();
               }   else if (id == R.id.nav_wallet) {
               f=new WalletFragment();
               //  Toast.makeText(getApplicationContext(),"testing3",Toast.LENGTH_LONG).show();
           /*}  else if (id == R.id.nav_cards) {
                   f=new CardsFragment();*/
                  // Toast.makeText(getApplicationContext(),"testing4",Toast.LENGTH_LONG).show();

               } else if (id == R.id.nav_agents) {
                   f=new AuthorizedAgents();

               } else if (id == R.id.nav_settings) {
                   f=new SettingsFragment();
                   Toast.makeText(getApplicationContext(),"testing5",Toast.LENGTH_LONG).show();

               }else if (id == R.id.nav_logout) {
                   new PrefManager(getApplicationContext()).deleteLoginDetails();
                   new PrefManager(getApplicationContext()).deleteProfileDetails();
                   new PrefManager(getApplicationContext()).deleteBeneficiarydetails();
                   new PrefManagerPayment(getApplicationContext()).deleteAlll();
                   new PrefManagerCart(getApplicationContext()).deleteAll();

                   if (new PrefManager(getApplicationContext()).isUserLogedOut()) {
                       Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                       startActivity(intent);
                   }
                   finish();

               }

               if (f != null) {
                   FragmentManager fm1 = DashboardActivity.this
                           .getSupportFragmentManager();
                   FragmentTransaction transaction = fm1.beginTransaction();
                   //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                   transaction.replace(R.id.lay_homeframe, f);
                   transaction.addToBackStack(null);// replace a Fragment with Frame Layout
                   prof_img=(ImageView)hView.findViewById(R.id.prof_pic);
                   imgurl=new PrefManager(getApplicationContext()).getImage();


                   if(!imgurl.isEmpty()) {
                       Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
                   }
                   transaction.commit(); // commit the changes



                   drawer2.closeDrawers();
                  // f.getView().setFocusableInTouchMode(true);
                   //f.getView().requestFocus();
                   onBackPressed();


                   return true;
               }

               return false;


           }


       });




        int id = item.getItemId();

        if (id == R.id.nav_home) {
            f=new BarcodescannerFragment();
           // f=new ScannedProductListFragment();
            Toast.makeText(getApplicationContext(),"testing",Toast.LENGTH_LONG).show();
         // Handle the camera action
        } else if (id == R.id.nav_profile) {

            f=new ProfileFragment();
            Toast.makeText(getApplicationContext(),"testing2",Toast.LENGTH_LONG).show();
            if(bundle!=null) {
                f.setArguments(bundle);
            }
        } else if (id == R.id.nav_beneficiaries) {
            f=new BeneficiariesFragment();

        }   else if (id == R.id.nav_wallet) {
            f=new WalletFragment();

  /*  } else if (id == R.id.nav_cards) {
            f=new CardsFragment();*/
        } else if (id == R.id.nav_agents) {
            f=new AuthorizedAgents();

        } else if (id == R.id.nav_settings) {
            f=new SettingsFragment();
        }else if (id == R.id.nav_logout) {
            new PrefManager(getApplicationContext()).deleteLoginDetails();
            new PrefManager(getApplicationContext()).deleteProfileDetails();
            new PrefManager(getApplicationContext()).deleteBeneficiarydetails();
            new PrefManagerPayment(getApplicationContext()).deleteAlll();
            new PrefManagerCart(getApplicationContext()).deleteAll();
            CustomAdapterCartItem customAdaptercartitemsdel = new CustomAdapterCartItem(getApplicationContext(), new PrefManagerCart(getApplicationContext()).getArrayList("product_names"), new PrefManagerCart(getApplicationContext()).getArrayList("discount_prices"),  new PrefManagerCart(getApplicationContext()).getArrayList("item_remove"));
            customAdaptercartitemsdel.deleteAll(getApplicationContext());
            customAdaptercartitemsdel.notifyDataSetChanged();
            if (new PrefManager(getApplicationContext()).isUserLogedOut()) {
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            finish();

        }

        if (f != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.lay_homeframe, f);
            transaction.addToBackStack(null);// replace a Fragment with Frame Layout
            prof_img=(ImageView)hView.findViewById(R.id.prof_pic);
            imgurl=new PrefManager(getApplicationContext()).getImage();


            if(!imgurl.isEmpty()) {
                Picasso.with(hView.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(prof_img);
            }
            transaction.commit(); // commit the changes
            drawer2.closeDrawers();
            return true;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d("vidstatus",""+vid);
       if(vid==1) {
           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
           if (drawer.isDrawerOpen(GravityCompat.START)) {
               drawer.closeDrawer(GravityCompat.START);
           } else {
               //super.onBackPressed();
               f=new BarcodescannerFragment();
               FragmentManager fm1 = DashboardActivity.this
                       .getSupportFragmentManager();
               FragmentTransaction transaction = fm1.beginTransaction();
               //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
               transaction.replace(R.id.lay_homeframe, f);
               transaction.addToBackStack(null);// replace a Fragment with Frame Layout

           }
       }else if(vid==2) {
           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
           if (drawer.isDrawerOpen(GravityCompat.START)) {
               drawer.closeDrawer(GravityCompat.START);
           } else {
               super.onBackPressed();

           }
       }else if(vid==0) {
           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           if (drawer.isDrawerOpen(GravityCompat.START)) {
               drawer.closeDrawer(GravityCompat.START);
           } else {
              Intent intent=new Intent(DashboardActivity.this,DashboardActivity.class);
              startActivity(intent);
              finish();

           }
       }









    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(),"font/droidserif_bold.ttf");

        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void onClickscanbutton(View v)
    {
if(v==bt_scannreport)
{
   // bt_scannreport.performClick();
}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
