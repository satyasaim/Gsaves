package com.gsaves.media3.gsaves.app.pojo;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AuthorisedagentslistResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<AuthagentData> data = null;


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public List<AuthagentData> getData() {
        return data;
    }


    public void setData(List<AuthagentData> data) {
        this.data = data;
    }




}
