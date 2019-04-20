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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class BarcodescannerFragment extends Fragment {
    View view;
    Fragment f;
    public BarcodescannerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_dashboard_fragment, container, false);
        //Toast.makeText(view.getContext(), "Scan Cancelled", Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(view.getContext(), "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {

                //show dialogue with result
               showResultDialogue(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //method to construct dialogue with scan results
    public void showResultDialogue(final String result) {
       /* AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(view.getContext());
        }
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + result)
                .setPositiveButton("Copy result", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        ClipboardManager clipboard = (ClipboardManager)view.getContext().getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getContext(), "Result copied to clipboard", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();*/


        /*Intent productlist=new Intent(view.getContext(),BarcodescanResultActivity.class);
        productlist.putExtra("contents",result);
        startActivity(productlist);*/
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button bt_scan=(Button)view.findViewById(R.id.bt_start_scan);
        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*Intent intent = new Intent(view.getContext(), Barcodescanning2Activity.class);
                startActivity(intent);*/
                //initiate scan with our custom scan activity
                new IntentIntegrator((Activity) view.getContext()).setCaptureActivity(Barcodescanning2Activity.class).initiateScan();
            }
        });



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
