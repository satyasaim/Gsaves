package com.gsaves.media3.gsaves.app;

import com.gsaves.media3.gsaves.app.api.PaypalApi;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.Errors;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutStatusResponse;
import com.gsaves.media3.gsaves.app.payoutrequestclasses.PayoutStatusresponseItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payoutstatusfinalcall {
String id;
    Payoutstatusfinalcall(String id)
    {
        this.id=id;

    }


}
