package com.gsaves.media3.gsaves.app;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class SettingsFragment extends Fragment {
    View view;
    FragmentActivity fact;
    Intent mintent;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private NotificationManager mNotificationManager;
    private LocationManager mLocationManager;
    Fragment f=null;

    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     Button bt_logout=(Button)view.findViewById(R.id.bt_logout);
        final Switch current_loc=(Switch)view.findViewById(R.id.switch1);
        final Switch notifications=(Switch)view.findViewById(R.id.switch2);
        TextView tv_chng_pwd=(TextView)view.findViewById(R.id.tv_chng_pwd);
        TextView tv_dispute=(TextView)view.findViewById(R.id.tv_dispute);
        TextView tv_contactus=(TextView)view.findViewById(R.id.tv_contactus);

        mNotificationManager=(NotificationManager)view.getContext().getSystemService(NOTIFICATION_SERVICE);


        tv_chng_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=new TermsandconditionsFragment();
                if (f != null) {

                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.lay_homeframe,f, "Terms and conditions");
                    ft.addToBackStack(null);
                    ft.commit();




                }
            }
        });

        tv_dispute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=new AboutusFragment();
                if (f != null) {

                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
        tv_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=new ContactUsFragment();
                if (f != null) {

                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.lay_homeframe, f, "Terms and conditions");
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });
     bt_logout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             new PrefManager(getApplicationContext()).deleteLoginDetails();
             new PrefManager(getApplicationContext()).deleteProfileDetails();
             new PrefManager(getApplicationContext()).deleteBeneficiarydetails();
             new PrefManagerPayment(getApplicationContext()).deleteAlll();
             new PrefManagerCart(getApplicationContext()).deleteAll();
             CustomAdapterCartItem customAdaptercartitemsdel = new CustomAdapterCartItem(v.getContext(), new PrefManagerCart(v.getContext()).getArrayList("product_names"), new PrefManagerCart(v.getContext()).getArrayList("discount_prices"),  new PrefManagerCart(v.getContext()).getArrayList("item_remove"));
             customAdaptercartitemsdel.deleteAll(v.getContext());
             customAdaptercartitemsdel.notifyDataSetChanged();

             if(new PrefManager(getApplicationContext()).isUserLogedOut()) {
                 Intent gotologin = new Intent(view.getContext(), LoginActivity.class);
                 startActivity(gotologin);
             }
         }
     });

            current_loc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(current_loc.isChecked()) {
                      /* mLocationManager=(LocationManager)view.getContext().getSystemService(Context.LOCATION_SERVICE);
                        Toast.makeText(getContext(), "current loc enabled" , Toast.LENGTH_SHORT).show();

                         checkLocationPermission();*/
                       statusCheck();

                    }
                    else
                   {
                       startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                   }
                    }

            });

            notifications.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(notifications.isChecked())
                    {
                        changeInterruptionFiler(NotificationManager.INTERRUPTION_FILTER_NONE);
                    }
                    else
                    {
                        changeInterruptionFiler(NotificationManager.INTERRUPTION_FILTER_ALL);
                    }
                }

                private void changeInterruptionFiler(int interruptionFilter) {
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){ // If api level minimum 23
            /*
                boolean isNotificationPolicyAccessGranted ()
                    Checks the ability to read/modify notification policy for the calling package.
                    Returns true if the calling package can read/modify notification policy.
                    Request policy access by sending the user to the activity that matches the
                    system intent action ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS.

                    Use ACTION_NOTIFICATION_POLICY_ACCESS_GRANTED_CHANGED to listen for
                    user grant or denial of this access.

                Returns
                    boolean

            */
                        // If notification policy access granted for this package
                        if(mNotificationManager.isNotificationPolicyAccessGranted()){
                /*
                    void setInterruptionFilter (int interruptionFilter)
                        Sets the current notification interruption filter.

                        The interruption filter defines which notifications are allowed to interrupt
                        the user (e.g. via sound & vibration) and is applied globally.

                        Only available if policy access is granted to this package.

                    Parameters
                        interruptionFilter : int
                        Value is INTERRUPTION_FILTER_NONE, INTERRUPTION_FILTER_PRIORITY,
                        INTERRUPTION_FILTER_ALARMS, INTERRUPTION_FILTER_ALL
                        or INTERRUPTION_FILTER_UNKNOWN.
                */

                            // Set the interruption filter
                            mNotificationManager.setInterruptionFilter(interruptionFilter);
                        }else {
                /*
                    String ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS
                        Activity Action : Show Do Not Disturb access settings.
                        Users can grant and deny access to Do Not Disturb configuration from here.

                    Input : Nothing.
                    Output : Nothing.
                    Constant Value : "android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS"
                */
                            // If notification policy access not granted for this package
                            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                            startActivity(intent);
                        }
                    }

                }
            });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "permission granted" , Toast.LENGTH_SHORT).show();


                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
            case 0:  Toast.makeText(getContext(), "current loc not enabled:" , Toast.LENGTH_SHORT).show();
                return;
        }
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
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

