package com.gsaves.media3.gsaves.app;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.gsaves.media3.gsaves.app.api.Api;
import com.gsaves.media3.gsaves.app.pojo.Data;

import com.gsaves.media3.gsaves.app.pojo.ImageUploadResponse;
import com.gsaves.media3.gsaves.app.pojo.UpdateProfileResponse;
import com.squareup.picasso.Picasso;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import static android.view.View.*;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.gsaves.media3.gsaves.app.DashboardActivity.*;

public class ProfileFragment extends Fragment {
    View view;
    Bundle bundle;
    String fname,lname,email,mobile,address1,address2,profileid;
   static String imgurl;
    private Button
            upload,
            cancel;
    private Bitmap bitmap;
    private ProgressDialog
            dialog;
    Uri imageUri;
    private static final int PICK_IMAGE = 3;
    private static final int PICK_Camera_IMAGE = 2;
    public static final int
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    ImageView imagev;
    Cursor cursor;
    int count=0;
    String status="gallery";
    Uri selectedImageUri = null;
    String filePath = null;
    String old_password;
    String new_password;
    String fb_filepath=null;
    int fbstatus=0;
    Fragment f=null;






    MediaPlayer
            mp=new MediaPlayer();


    Context context;
    public ProfileFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                100);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
       // return true;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        TextView tv_change_pwd=(
                TextView)view.findViewById(R.id.tv_change_pwd);
        Button bt_save_prof=(
                Button)view.findViewById(R.id.bt_card_register);
        imagev=(
                ImageView)view.findViewById(R.id.imageView3);




        final EditText et_profname=(
                EditText)view.findViewById(R.id.et_prof_name);
        final TextView tv_profname=(
                TextView)view.findViewById(R.id.tv_profile_name);
        final EditText et_proflname=(
                EditText)view.findViewById(R.id.et_card_name);
        final EditText et_profemail=(
                EditText)view.findViewById(R.id.et_card_id);
        final EditText et_mobile=(
                EditText)view.findViewById(R.id.et_card_cvv);
        final EditText et_address1=(
                EditText)view.findViewById(R.id.et_profile_address);
        final EditText et_address2=(
                EditText)view.findViewById(R.id.et_profile_zip);




        final EditText[] change_pwd = new EditText[1];
        final EditText[] new_pwd = new EditText[1];
        final EditText[] confirm_new_pwd = new EditText[1];


        String logintype="normal";
        bundle = this.getArguments();

       fname=new PrefManager(getApplicationContext()).getFirstName();
       lname=new PrefManager(getApplicationContext()).getLastName();
       email=new PrefManager(getApplicationContext()).getEmail();
       mobile=new PrefManager(getApplicationContext()).getMobile();
       address1=new PrefManager(getApplicationContext()).getAddress1();
       address2=new PrefManager(getApplicationContext()).getAddress2();
       profileid=new PrefManager(getApplicationContext()).getId();
       imgurl=new PrefManager(getApplicationContext()).getImage();



        et_profname.setText(fname);
        tv_profname.setText(fname);
        et_proflname.setText(lname);
        et_profemail.setText(email);
        et_mobile.setText(mobile);
        et_address1.setText(address1);
        et_address2.setText(address2);


        if(!imgurl.isEmpty()) {
            Picasso.with(view.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(imagev);
        }




        if(bundle.getString("login_type")!=null) {
            logintype = bundle.getString("login_type");
        }
        if(logintype.
                equals("fb")) {
            fbstatus=1;
        fname = bundle.getString("first_name");
        lname = bundle.getString("last_name");
        email = bundle.getString("email");
            imgurl=bundle.getString("image_url");
            mobile=bundle.getString("mobile_number");
            address1=bundle.getString("address");
            address2=bundle.getString("contact_address");
            profileid=bundle.getString("id");

         Log.d("received", fname);
          et_profname.setText(fname);
          tv_profname.setText(fname);
          et_proflname.setText(lname);
          et_profemail.setText(email);
          et_mobile.setText(mobile);
          et_address1.setText(address1);
          et_address2.setText(address2);
          if(!imgurl.isEmpty()) {
             Picasso.with(view.getContext()).load(imgurl).resize(200,200).into(imagev);
          }


          /*  try {
                Uri uri=Uri.parse(imgurl);
                URL url = new URL(uri.toString());
                String imag_url=url.toString();
                //String fb_filepath = getPath(imag_url);
                decodeFile(url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/


        }

          bt_save_prof.setOnClickListener(new OnClickListener()  {
              @Override
              public void onClick(View v) {
                  if(filePath==null)
                  {
                      if(new PrefManager(getApplicationContext()).getImage().isEmpty()) {
                          Picasso.with(view.getContext()).load("http://96.125.162.228/Luis/uploads/profile@2x.png").resize(200, 200).into(imagev);
                          filePath="http://96.125.162.228/Luis/uploads/profile@2x.png";
                      }
                      else {
                          Picasso.with(view.getContext()).load("http://96.125.162.228/Luis/uploads/" + imgurl).resize(200, 200).into(imagev);
                          filePath=imgurl;

                      }


                  }


                      final File file = new File(filePath);

                  Log.d("filepath",""+filePath);
                      final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                      progressDialog.setCancelable(false); // set cancelable to false
                      progressDialog.setMessage("Please Wait"); // set message
                      progressDialog.show(); // show progress dialog

                  Log.d("filename",""+file.getName());




                      Api.getapiContext(getApplicationContext());

                      (Api.getClient().updateprofile(profileid, et_profname.getText().toString().trim(), et_mobile.getText().toString().trim(), et_address1.getText().toString().trim(), et_address2.getText().toString().trim(), file.getName(), et_proflname.getText().toString().trim(),new_password)).enqueue(new Callback<UpdateProfileResponse>() {
                          @Override
                          public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                              Toast.makeText(getApplicationContext(), response.body().getStatus(), Toast.LENGTH_LONG).show();
                              progressDialog.dismiss();
                              Data data = response.body().getData();

                            if(data!=null) {
                                et_profname.setText(data.getFirstName());
                                et_proflname.setText(data.getLastName());
                                et_profemail.setText(data.getEmail());
                                et_mobile.setText(data.getMobileNumber());
                                et_address1.setText(data.getAddress());
                                et_address2.setText(data.getContactAddress());

                                old_password = data.getPassword();
                            }
if(data==null)
{
    et_profname.setText(fname);
    tv_profname.setText(fname);
    et_proflname.setText(lname);
    et_profemail.setText(email);
    et_mobile.setText(mobile);
    et_address1.setText(address1);
    et_address2.setText(address2);
}
                              if (!data.getProfileImage().isEmpty()) {
                               Picasso.with(view.getContext()).load("http://96.125.162.228/Luis/uploads/" + data.getProfileImage()).resize(200, 200).into(imagev);

                              }
                              else {
                                  imagev.setImageBitmap(bitmap);
                                  imagev.setMaxHeight(200);
                                  imagev.setMaxWidth(200);
                              }


                              new PrefManager(getApplicationContext()).saveImage(file.getName());







                              Toast.makeText(getApplicationContext(), et_profname.getText(), Toast.LENGTH_SHORT).show();

                              if (bitmap != null) {


                                  String token = "testtoken";
                                 // Picasso.with(view.getContext()).load(filePath).resize(200, 200).into(imagev);
                                  Toast.makeText(getApplicationContext(), "original file path:" + filePath, Toast.LENGTH_SHORT).show();
//Create a file object using file path

                                  // Create a request body with file and image media type
                     /* RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
                      // Create MultipartBody.Part using file request-body,file name and part name
                      MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);
                      //Create request body with text description and text media type
                      RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
*/

                                  RequestBody tokenRequest = RequestBody.create(MediaType.parse("text/plain"), token);
                                  RequestBody type = RequestBody.create(MediaType.parse("text/plain"), true + "");


                                  MultipartBody.Builder builder = new MultipartBody.Builder();
                                  builder.setType(MultipartBody.FORM);
                                  if(fbstatus==1) {
                                      builder.addFormDataPart("profile_image", "fbprofilepic", RequestBody.create(MediaType.parse("multipart/form-data"), fb_filepath));
                                  }
                                  else
                                  {
                                      builder.addFormDataPart("profile_image", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
                                  }
                                  //  MultipartBody.Part filePart = MultipartBody.Part.createFormData("profile_image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                                  MultipartBody filePart = builder.build();
                                  Api.getClient().uploadimage(filePart).enqueue(new Callback<ImageUploadResponse>() {
                                      @Override
                                      public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                                          Toast.makeText(getApplicationContext(), "Image uloaded" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                                          Log.d("image loaded url", "" + response.body().getUrl());

                                      }

                                      @Override
                                      public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                                          Toast.makeText(getApplicationContext(), "Image upload failed", Toast.LENGTH_SHORT).show();
                                      }
                                  });
                              }
                              new PrefManager(getApplicationContext()).saveProfileDetails(et_profname.getText().toString(), et_proflname.getText().toString(), et_profemail.getText().toString(), et_mobile.getText().toString(), et_address1.getText().toString(), et_address2.getText().toString(), file.getName(),new_password);
                          }


                          @Override
                          public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                              Log.d("user updation failed", t.getStackTrace().

                                      toString());

                              Log.i("RM", t.getMessage());
                              progressDialog.dismiss();
                          }
                      });


              }
          });
        new_password=new PrefManager(getContext()).getPassword();

          tv_change_pwd.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                  final Dialog mdialog = new Dialog(v.getContext());
                  mdialog.setContentView(R.layout.change_pwd);

                  Button bt_chg_pwd_ok=(

                          Button)mdialog.findViewById(R.id.bt_chg_pwd_ok);
                  Button bt_chg_pwd_cancel=(

                          Button)mdialog.findViewById(R.id.bt_chg_pwd_cancel);

                   change_pwd[0] =(EditText)mdialog.findViewById(R.id.change_pwd);
                    new_pwd[0] =(EditText)mdialog.findViewById(R.id.et_new_pwd);
                   confirm_new_pwd[0] =(EditText)mdialog.findViewById(R.id.et_confirm_new_pwd);

                //  Toast.makeText(mdialog.getContext(),""+old_password,Toast.LENGTH_LONG).show();

                  change_pwd[0].setText(new PrefManager(mdialog.getContext()).getPassword());






                  bt_chg_pwd_ok.setOnClickListener(new OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          if(new_pwd[0].getText().toString().equals(confirm_new_pwd[0].getText().toString()))
                          {
                              new_password=confirm_new_pwd[0].getText().toString();
                              Toast.makeText(v.getContext(),"Password changed Successfully !",Toast.LENGTH_LONG).show();
                          }
                          else
                          {
                              Toast.makeText(v.getContext(),"Confirm password doesn't match",Toast.LENGTH_LONG).show();
                          }
                          if(new_pwd[0].getText().toString()==null||confirm_new_pwd[0].getText().toString()==null)
                          {
                              confirm_new_pwd[0].setText(new PrefManager(v.getContext()).getPassword());

                          }
                          mdialog.dismiss();
                      }
                  });

                  bt_chg_pwd_cancel.setOnClickListener(new OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          mdialog.dismiss();
                      }
                  });
                  mdialog.show();
              }
          });
       /* if(new_pwd[0].getText().toString()==null||confirm_new_pwd[0].getText().toString()==null)
        {
            confirm_new_pwd[0].setText(new PrefManager(getContext()).getPassword());
        }*/

        tv_profname.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;

                final Dialog mdialog = new Dialog(v.getContext());
                mdialog.setContentView(R.layout.image_options);

                Button bt_imgoption_ok=(

                        Button)mdialog.findViewById(R.id.bt_imgoption_ok);
                Button bt_imgoption_cancel=(

                        Button)mdialog.findViewById(R.id.bt_imgoption_cancel);
                final RadioButton camera=(

                        RadioButton)mdialog.findViewById(R.id.rdbut_camera);
                final RadioButton gallery=(

                        RadioButton)mdialog.findViewById(R.id.rdbut_gallery);
                gallery.setChecked(true);
                bt_imgoption_cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mdialog.dismiss();
                    }
                });
                bt_imgoption_ok.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {





                        Log.d("countval",""+count);

                        if(gallery.isChecked()) {

                            mdialog.dismiss();

                            //  if (bitmap == null) {
                            if (

                                    true) {
                                Toast.makeText(getApplicationContext(),
                                        "Please select image", Toast.LENGTH_SHORT).show();
                                try {
                                    Intent gintent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                    //gintent.setType("image/*");
                                    //gintent.setAction(Intent.ACTION_GET_CONTENT);
                                    //startActivityForResult(
                                    //      Intent.createChooser(gintent, "Select Picture"),
                                    //    PICK_IMAGE);*/
                                    startActivityForResult(gintent, PICK_IMAGE);


                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(),
                                            e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                    Log.e(e.getClass().getName(), e.getMessage(), e);
                                }
                            } else {
                                dialog = ProgressDialog.show(view.getContext(), "Uploading",
                                        "Please wait...", true);
                                // new ImageUploadTask().execute();
                            }
                        }

                        if(camera.isChecked())
                        {

                            if( ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                                    requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                                            5);
                                }
                            }


                            Intent intent;
                            if(true){
                                mdialog.dismiss();
                                //define the file-name to save photo taken by Camera activity
                                String fileName = "new-photo-name.jpg";
                                //create parameters for Intent with filename

                                ContentValues values = new ContentValues();
                                values.put(android.provider.MediaStore.Images.Media.TITLE, fileName);
                                values.put(android.provider.MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                                Log.d("camera entered",""+Build.VERSION.SDK_INT);

                                //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                // intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                // Here, thisActivity is the current activity
                                if (ContextCompat.checkSelfPermission(

                                        getActivity(),
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    // Permission is not granted
                                    // Should we show an explanation?
                                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                        // Show an explanation to the user *asynchronously* -- don't block
                                        // this thread waiting for the user's response! After the user
                                        // sees the explanation, try again to request the permission.
                                        showDialog("External storage", getApplicationContext(),
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                    } else {
                                        // No explanation needed; request the permission
                                        ActivityCompat.requestPermissions(getActivity(),
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                1024);

                                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                        // app-defined int constant. The callback method gets the
                                        // result of the request.
                                        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                    }
                                } else {
                                    // Permission has already been granted
                                    imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                }



                                Log.d("camera entered2",""+imageUri);
                                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,imageUri);
                                intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                startActivityForResult(intent, PICK_Camera_IMAGE);



                            }

                        }

                    }
                });

                mdialog.show();








    }
        });
          imagev.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                  count++;

                  final Dialog mdialog = new Dialog(v.getContext());
                  mdialog.setContentView(R.layout.image_options);

                  Button bt_imgoption_ok=(

                          Button)mdialog.findViewById(R.id.bt_imgoption_ok);
                  Button bt_imgoption_cancel=(

                          Button)mdialog.findViewById(R.id.bt_imgoption_cancel);
                  final RadioButton camera=(

                          RadioButton)mdialog.findViewById(R.id.rdbut_camera);
                  final RadioButton gallery=(

                          RadioButton)mdialog.findViewById(R.id.rdbut_gallery);
                 gallery.setChecked(true);
                  bt_imgoption_cancel.setOnClickListener(new OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          mdialog.dismiss();
                      }
                  });
                  bt_imgoption_ok.setOnClickListener(new OnClickListener() {
                      @Override
                      public void onClick(View v) {





                          Log.d("countval",""+count);

                          if(gallery.isChecked()) {

                              mdialog.dismiss();

                            //  if (bitmap == null) {
                                  if (

                                          true) {
                                  Toast.makeText(getApplicationContext(),
                                          "Please select image", Toast.LENGTH_SHORT).show();
                                  try {
                                      Intent gintent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                      //gintent.setType("image/*");
                                      //gintent.setAction(Intent.ACTION_GET_CONTENT);
                                      //startActivityForResult(
                                        //      Intent.createChooser(gintent, "Select Picture"),
                                          //    PICK_IMAGE);*/
                                      startActivityForResult(gintent, PICK_IMAGE);


                                  } catch (Exception e) {
                                      Toast.makeText(getApplicationContext(),
                                              e.getMessage(),
                                              Toast.LENGTH_LONG).show();
                                      Log.e(e.getClass().getName(), e.getMessage(), e);
                                  }
                              } else {
                                  dialog = ProgressDialog.show(view.getContext(), "Uploading",
                                          "Please wait...", true);
                                  // new ImageUploadTask().execute();
                              }
                          }

                          if(camera.isChecked())
                          {

                              if( ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                                      requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                                              5);
                                  }
                              }


                             Intent intent;
                               if(

                                       true){
                                  mdialog.dismiss();
                                  //define the file-name to save photo taken by Camera activity
                                  String fileName = "new-photo-name.jpg";
                                  //create parameters for Intent with filename

                                  ContentValues values = new ContentValues();
                                  values.put(android.provider.MediaStore.Images.Media.TITLE, fileName);
                                  values.put(android.provider.MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                                   Log.d("camera entered",""+Build.VERSION.SDK_INT);

                                   //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                                   intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                  // intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                   // Here, thisActivity is the current activity
                                   if (ContextCompat.checkSelfPermission(

                                           getActivity(),
                                           Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                           != PackageManager.PERMISSION_GRANTED) {

                                       // Permission is not granted
                                       // Should we show an explanation?
                                       if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                                               Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                           // Show an explanation to the user *asynchronously* -- don't block
                                           // this thread waiting for the user's response! After the user
                                           // sees the explanation, try again to request the permission.
                                           showDialog("External storage", getApplicationContext(),
                                                   Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                       } else {
                                           // No explanation needed; request the permission
                                           ActivityCompat.requestPermissions(getActivity(),
                                                   new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                  1024);

                                           // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                           // app-defined int constant. The callback method gets the
                                           // result of the request.

                                           imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                       }
                                   } else {
                                       // Permission has already been granted
                                       imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                   }



                                Log.d("camera entered2",""+imageUri);
                                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,imageUri);
                                intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                startActivityForResult(intent, PICK_Camera_IMAGE);



                              }

                              }

                      }
                  });

                  mdialog.show();




              }

          });




        }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                1024);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
            case PICK_IMAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_IMAGE);
                } else {
                    Toast.makeText(view.getContext(),"Did n't allow the app to access gallery",Toast.LENGTH_SHORT).show();
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
            case PICK_Camera_IMAGE:
                Intent intent;
                String fileName = "new-photo-name.jpg";
                ContentValues values = new ContentValues();
                values.put(android.provider.MediaStore.Images.Media.TITLE, fileName);
                values.put(android.provider.MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                Log.d("camera entered",""+Build.VERSION.SDK_INT);

                 //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                imageUri = getActivity().

                        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


                Log.d("camera entered2",""+imageUri);
                //create new Intent
                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,imageUri);
                intent.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY, 1);



                startActivityForResult(intent,PICK_Camera_IMAGE);

                break;

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = data.getData();
                    Log.d("dataimg",""+selectedImageUri);

                    try {

                        InputStream is =getApplicationContext().getContentResolver().openInputStream(data.getData());

                       // uploadImage(getBytes(is));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if(selectedImageUri != null){
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);



                if (selectedImagePath != null) {
                    filePath = selectedImagePath;

                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;

                } else {
                    Toast.makeText(getApplicationContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }


                Log.d("filePath",filePath);

                if (filePath != null) {
                    decodeFile(filePath);


                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }
    }





    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        cursor = view.getContext().getContentResolver().query(uri, projection, null, null, null);

            if (cursor != null) {

                // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
                // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);

            } else
                  return null;




    }

    public void decodeFile(String filePath)  {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;


        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 3;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
         Log.d("invoked","called"+imgurl);
         if(fbstatus==1)
         {
             try {
                 ParcelFileDescriptor parcelFileDescriptor =getApplicationContext().getContentResolver().openFileDescriptor(Uri.parse(imgurl), "r");
                 FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);


                 parcelFileDescriptor.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }

         }
         else {
             bitmap = BitmapFactory.decodeFile(filePath, o2);
         }

        imagev.setImageBitmap(bitmap);





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
                    //Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
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