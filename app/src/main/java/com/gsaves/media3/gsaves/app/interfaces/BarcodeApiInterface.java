package com.gsaves.media3.gsaves.app.interfaces;

import com.gsaves.media3.gsaves.app.barcodeclasses.BarcodeResponse;
import com.gsaves.media3.gsaves.app.pojo.AddbeneficiaryResponse;
import com.gsaves.media3.gsaves.app.pojo.BeneficiarieslistResponse;
import com.gsaves.media3.gsaves.app.pojo.CardslistResponse;
import com.gsaves.media3.gsaves.app.pojo.ContactUsResponse;
import com.gsaves.media3.gsaves.app.pojo.CreatecardResponse;
import com.gsaves.media3.gsaves.app.pojo.Deletebeneficiaryresponse;
import com.gsaves.media3.gsaves.app.pojo.Deletecardresponse;
import com.gsaves.media3.gsaves.app.pojo.ForgotpasswordResponse;
import com.gsaves.media3.gsaves.app.pojo.ImageUploadResponse;
import com.gsaves.media3.gsaves.app.pojo.LoginResponse;
import com.gsaves.media3.gsaves.app.pojo.PayoutResponse;
import com.gsaves.media3.gsaves.app.pojo.Qrcoderesponse;
import com.gsaves.media3.gsaves.app.pojo.SignUpResponse;
import com.gsaves.media3.gsaves.app.pojo.UpdateProfileResponse;
import com.gsaves.media3.gsaves.app.pojo.UpdatebeneficiaryDataResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("ALL")
public interface BarcodeApiInterface {

    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
  //  @FormUrlEncoded // annotation used in POST type requests
    //@POST("/signup")
    @POST("/v2/products")
        // API's endpoints
    Call<BarcodeResponse> getProductData(@Query("barcode") String barcode,
                                         @Query("formatted") String format,
                                         @Query("key") String key);



}
