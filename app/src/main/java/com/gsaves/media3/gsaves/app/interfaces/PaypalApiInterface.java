package com.gsaves.media3.gsaves.app.interfaces;

import com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutRequest;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutResponse;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutStatusResponse;
import com.gsaves.media3.gsaves.app.paypalclasses.PaymentDetailsResponse;
import com.gsaves.media3.gsaves.app.pojo.PaypalAuthenticateResponse;
import com.gsaves.media3.gsaves.app.pojo.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PaypalApiInterface {
    @Headers("Content-Type:application/x-www-form-urlencoded;charset=utf-8")
    @FormUrlEncoded // annotation used in POST type requests

    //@POST("/signup")
    @POST("/v1/oauth2/token")
        // API's endpoints
    Call<PaypalAuthenticateResponse> authenticate(@Field("grant_type") String grant_type);

    @Headers("Content-Type:application/json")

    // annotation used in POST type requests
    @GET("/v1/payments/payment/{id}")
    Call<PaymentDetailsResponse> paymentdetails(@Path("id") String id);

    @Headers("Content-Type:application/json")

    // annotation used in POST type requests
    @POST("/v1/payments/payouts")
    Call<PayoutResponse> payoutrequest(@Body PayoutRequest body);

    @Headers("Content-Type:application/json")
    // annotation used in POST type requests
    @GET("/v1/payments/payouts/{id}")
    Call<PayoutStatusResponse> payoutstatus(@Path("id") String id);




}
