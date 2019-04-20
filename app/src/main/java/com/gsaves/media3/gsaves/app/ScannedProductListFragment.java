package com.gsaves.media3.gsaves.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class ScannedProductListFragment extends Fragment {
    View view;
    TextView scan_code;
    FragmentActivity fact;
    Intent mintent;
    Fragment f;
    public ScannedProductListFragment()
    {




    }
    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        fact=this.getActivity();
        super.onActivityResult(requestCode, resultCode, data);
        final String result="test";
        if ((requestCode == 49374) && (resultCode == Activity.RESULT_OK)) {
            // recreate your fragment here
            // f = new ScannedProductListFragment();
           // FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            //ft.detach(f).attach(f).commit();
            callback(data);
            Log.d("ok working123","working");
       /* if (requestCode == 49374) {
            // Do your job
           // tvResult.setText("Result Code = " + resultCode);
           // Toast.makeText(fact.getApplicationContext(),"working",Toast.LENGTH_LONG).show();
            Log.d("working123","working");

            f = new ScannedProductListFragment();
            if (f != null) {


               f.startActivity(data);
                *//*FragmentTransaction ft = fact.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.lay_homeframe, f, "product details");
                ft.addToBackStack(null);
                ft.commit();*//*


            }
        }*/

          /*  AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(fact.getApplicationContext(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(fact.getApplicationContext());
            }
            builder.setTitle("Scan Result")
                    .setMessage("Scanned result is " + result)
                    .setPositiveButton("Copy result", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            ClipboardManager clipboard = (ClipboardManager) fact.getSystemService(CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Scan Result", result);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(fact.getApplicationContext(), "Result copied to clipboard" + result, Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            dialog.dismiss();
                        }
                    })
                    .show(); */
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.scanned_product, container, false);
        Log.d("Activitycalled","activity working");
        //String value = getArguments().getString("contents");
       // f.startActivityForResult(mintent, REQUEST_CODE);
        //scan_code = (TextView)view.findViewById(R.id.tv_scanned_code);
        //scan_code.setText(value);

        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Intent intent = new Intent(getActivity(), LoginActivity.class);
        //getActivity().startActivityForResult(intent, 49374);
        Log.d("Activitycalled","activity working");



    }

    @Override
    public void startActivityForResult(Intent data, int requestCode) {
      //  super.startActivityForResult(data, requestCode);
        callback(data);
        Log.d("ok working123","working");
    }

    public  void callback(Intent data)
    {

        Log.d("ok working123","constructor working");
        f = new ScannedProductListFragment();
     /*  getFragmentManager().beginTransaction()
                .detach(f)
                .attach(f)
                .commit();*/
     Toast.makeText(f.getActivity().getApplicationContext(),"call back method is working",Toast.LENGTH_LONG).show();
    }



}
