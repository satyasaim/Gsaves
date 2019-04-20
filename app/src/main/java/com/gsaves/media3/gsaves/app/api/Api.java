  package com.gsaves.media3.gsaves.app.api;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gsaves.media3.gsaves.app.interfaces.ApiInterface;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

  public class Api {
      private static Retrofit retrofit = null;
      private static Context context2;
      public static void getapiContext(Context context)
      {
          context2=context;

      }

      public static ApiInterface getClient() {
          Gson gson = new GsonBuilder()
                  .setLenient()
                  .create();


          HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
          logging.setLevel(HttpLoggingInterceptor.Level.BODY);

          int cacheSize = 10 * 1024 * 1024; // 10 MB

          Cache cache = new Cache(context2.getCacheDir(), cacheSize);





          OkHttpClient okHttpClient = new OkHttpClient.Builder()
                  .cache(cache).addInterceptor(logging).build();



          // change your base URL
          if (retrofit==null) {
            // retrofit=new Retrofit.Builder().baseUrl("http://104.236.67.117:4400/").addConverterFactory(GsonConverterFactory.create()).build();
              retrofit=new Retrofit.Builder().baseUrl("http://96.125.162.228/").addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

          }
          //Creating object for our interface
          ApiInterface api = retrofit.create(ApiInterface.class);

          return api; // return the APIInterface object
      }


  }
