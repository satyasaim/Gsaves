package com.gsaves.media3.gsaves.app.interfaces;

import com.gsaves.media3.gsaves.app.pojo.Addauthorizeagentresponse;
import com.gsaves.media3.gsaves.app.pojo.AddbeneficiaryResponse;
import com.gsaves.media3.gsaves.app.pojo.AuthorisedagentslistResponse;
import com.gsaves.media3.gsaves.app.pojo.BeneficiarieslistResponse;
import com.gsaves.media3.gsaves.app.pojo.CardslistResponse;
import com.gsaves.media3.gsaves.app.pojo.ContactUsResponse;
import com.gsaves.media3.gsaves.app.pojo.CreatecardResponse;
import com.gsaves.media3.gsaves.app.pojo.Deleteagentresponse;
import com.gsaves.media3.gsaves.app.pojo.Deletebeneficiaryresponse;
import com.gsaves.media3.gsaves.app.pojo.Deletecardresponse;
import com.gsaves.media3.gsaves.app.pojo.ForgotpasswordResponse;
import com.gsaves.media3.gsaves.app.pojo.ImageUploadResponse;
import com.gsaves.media3.gsaves.app.pojo.LoginResponse;
import com.gsaves.media3.gsaves.app.pojo.PayoutResponse;
import com.gsaves.media3.gsaves.app.pojo.Qrcoderesponse;
import com.gsaves.media3.gsaves.app.pojo.SignUpResponse;
import com.gsaves.media3.gsaves.app.pojo.UpdateProfileResponse;
import com.gsaves.media3.gsaves.app.pojo.Updateagentresponse;
import com.gsaves.media3.gsaves.app.pojo.UpdatebeneficiaryDataResponse;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

@SuppressWarnings("ALL")
public interface ApiInterface {

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded // annotation used in POST type requests
    //@POST("/signup")
    @POST("/Luis/app-registers")
        // API's endpoints
    Call<SignUpResponse> registration(@Field("user_name") String name,
                                      @Field("mobile_number") String mobile,
                                      @Field("email") String email,
                                      @Field("password") String password

    );

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded // annotation used in POST type requests
    //@POST("/signup")
    @POST("/Luis/app-profile-update/{id}")
    Call<UpdateProfileResponse> updateprofile(@Path("id") String id,
                                              @Field("first_name") String user_name,
                                              @Field("mobile_number") String mobile,
                                              @Field("address") String address,
                                              @Field("contact_address") String cont_address,
                                              @Field("profile_image") String profile_img,
                                              @Field("last_name") String last_name,
                                              @Field("password") String password
                                              );


   // @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
   // @FormUrlEncoded // annotation used in POST type requests
    //@POST("/signup")
   // @Multipart
    @POST("/Luis/app-profile-image-upload")
//Call<ImageUploadResponse> uploadimage(@Part MultipartBody.Part profile_image);
    Call<ImageUploadResponse> uploadimage(@Body RequestBody profile_image);


    /**
     * @param email
     * @param password
     * @return
     */
    // In registration method @Field used to set the keys and String data type is representing its a string type value and callback is used to get the response from api and it will set it in our POJO class
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    //@POST("/Lotus/app-login")
  // @POST("/login")
    @POST("/Luis/app-login")
    Call<LoginResponse> signin(@Field("email") String email,
                               @Field("password") String password);


    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    //@FormUrlEncoded
    @POST("/Luis/app-getunivercity")
    Call<BeneficiarieslistResponse> getbeneficiaries();


    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    //@FormUrlEncoded
    @POST("/Luis/app-get-card-payment")
    Call<CardslistResponse> getcardlist();

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("/Luis/app-univercity")
    Call<AddbeneficiaryResponse> addbeneficiary(@Field("univercity_name") String univercity_name,
                                                @Field("collage_name") String collage_name,
                                                @Field("mobile_number") String mobile_number,
                                                @Field("location") String location,
                                                @Field("email") String email,
                                                @Field("univercity_code") String univercity_code,
                                                @Field("address") String address,
                                                @Field("user_id") String userid);

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("/Luis/app-univercity-update/{id}")
    Call<UpdatebeneficiaryDataResponse> updatebeneficiary(@Path("id") String id,
                                                          @Field("univercity_name") String univercity_name,
                                                          @Field("collage_name") String collage_name,
                                                          @Field("email") String email,
                                                          @Field("mobile_number") String mobile_number,
                                                          @Field("location") String location,
                                                          @Field("univercity_code") String univercity_code,
                                                          @Field("address") String address,
                                                          @Field("user_id") String userid);

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    //@FormUrlEncoded
    @POST("/Luis/app-univercity-delete/{id}")
    Call<Deletebeneficiaryresponse> deletebeneficiary(@Path("id") String id);





    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("/Luis/app-forgot-password")
    Call<ForgotpasswordResponse> forgotpassword(@Field("email") String email);


    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    //@FormUrlEncoded
    @POST("/Luis/app-card-payment-delete/{id}")
    Call<Deletecardresponse> deletecard(@Path("id") String id);

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("/Luis/app-manage-qrcods")
    Call<Qrcoderesponse> getproductdata(@Field("brcode_number") String qrcode);

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("/Luis/app-card-payment")
    Call<CreatecardResponse> createcard(@Field("card_name") String card_name,
                                        @Field("card_number") String card_number,
                                        @Field("branch_location") String branch_location,
                                        @Field("card_ifsc") String card_ifsc,
                                        @Field("user_id") String user_id,
                                        @Field("year") String year,
                                        @Field("month") String month);

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("/Luis/app-contactus")
    Call<ContactUsResponse> contactus(@Field("name") String contact_name,
                                      @Field("email") String contact_email,
                                      @Field("mobile_number") String contact_mobile,
                                      @Field("message") String contact_msg);

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("/Luis/app-payouts")
    Call<PayoutResponse> payout(@Field("email") String payout_email,
                                @Field("item_id") String payout_itemid,
                                @Field("set_value") String payout_setval,
                                @Field("set_currency") String payout_setcur);



 @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
 //@FormUrlEncoded
 @GET("/Luis/app-authorised-agency")
 Call<AuthorisedagentslistResponse> getauthagents();


 @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
 //@FormUrlEncoded
 @POST("/Luis/app-authorised-agency-delete/{id}")
 Call<Deleteagentresponse> deleteagent(@Path("id") String id);


 @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
 @FormUrlEncoded
 @POST("/Luis/app-authorised")
 Call<Addauthorizeagentresponse> addauthagent(@Field("authorised_name") String authorised_name,
                                              @Field("mobile_number") String mobile_number,
                                              @Field("location") String location,
                                              @Field("email") String email,
                                              @Field("address") String address,
                                              @Field("user_id") String user_id
                                              );

 @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
 @FormUrlEncoded
 @POST("/Luis/app-authorised-agency-update/{id}")
 Call<Updateagentresponse> updateagent(@Path("id") String id,
                                       @Field("authorised_name") String authorised_name,
                                       @Field("mobile_number") String mobile_number,
                                       @Field("location") String location,
                                       @Field("email") String email,
                                       @Field("address") String address

                                       );


}
