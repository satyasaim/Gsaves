package com.gsaves.media3.gsaves.app.paypalclasses;

import android.widget.Toast;

import com.gsaves.media3.gsaves.app.api.PaypalApi;
import com.gsaves.media3.gsaves.app.pojo.PaypalAuthenticateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Accesstokenclass {

    public static String getaccesstoken()
    {
        final String[] token = {null};

        (PaypalApi.getClient().authenticate("client_credentials")).enqueue(new Callback<PaypalAuthenticateResponse>() {
            @Override
            public void onResponse(Call<PaypalAuthenticateResponse> call, Response<PaypalAuthenticateResponse> response) {

                token[0] =response.body().getAccessToken();

            }

            @Override
            public void onFailure(Call<PaypalAuthenticateResponse> call, Throwable t) {
               System.out.println(t.getMessage());
            }
        });
        return token[0];
    }
}
