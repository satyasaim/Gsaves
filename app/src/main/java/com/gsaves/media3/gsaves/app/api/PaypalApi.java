package com.gsaves.media3.gsaves.app.api;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gsaves.media3.gsaves.app.interfaces.ApiInterface;
import com.gsaves.media3.gsaves.app.interfaces.PaypalApiInterface;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Credentials;
import okhttp3.Dispatcher;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PaypalApi {
    private static Retrofit retrofit = null;
    private static Context context2;
    //private static String username="AUUumZ4l4t7UvAMDLqpdTFKZwt1vhzwHR2KefM5Jo6BABrdzNkycrMqptNxTzpZUp_j62jpO44w1Hyc8";
    //private static String password="EFM85yMJ0Q5buH4hgh50jyjfzh806ABUULTibdKpaBFbmBh72-k_hcjQKXFWKEV6A_yaAFAo9kjpUaxt";

    private static String username="AQrq7fVTVMqWgxZq3grJedgmoRAR8KN2nhqLPf2o5J07Ckw1uogEl8txW2OqIj4VQ0SH45VodAkRe3oh";
    private static String password="ELfZ6EgL-xFdlaqOmgZJq-AknFx_NqG1VF6LTuaD0oX0IPjU1PlGPdprFzkKr2X-wAM5UqryvsQ4YawY";
    private static String authToken=null;
    public static void getapiContext(Context context)
    {
        context2=context;

    }

    public static PaypalApiInterface getClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        int cacheSize = 10 * 1024 * 1024; // 10 MB

//        Cache cache = new Cache(context2.getCacheDir(), cacheSize);





       // OkHttpClient okHttpClient = new OkHttpClient.Builder()
         //       .cache(cache).addInterceptor(logging).build();



        //Basic Auth
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            authToken = Credentials.basic(username, password);
        }

        //Create a new Interceptor.
        Interceptor headerAuthorizationInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                okhttp3.Request request = chain.request();
                Headers headers = request.headers().newBuilder().add("Authorization", authToken).build();
                request = request.newBuilder().headers(headers).build();
                return chain.proceed(request);
            }
        };

//Add the interceptor to the client builder.
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(logging).addInterceptor(headerAuthorizationInterceptor).build();
       okHttpClient.dispatcher().setMaxRequests(3);


        // change your base URL
        if (retrofit==null) {
            // retrofit=new Retrofit.Builder().baseUrl("http://104.236.67.117:4400/").addConverterFactory(GsonConverterFactory.create()).build();
            retrofit=new Retrofit.Builder().baseUrl("https://api.sandbox.paypal.com/").addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        }
        //Creating object for our interface
        PaypalApiInterface api = retrofit.create(PaypalApiInterface.class);

        return api; // return the APIInterface object

    }


}
