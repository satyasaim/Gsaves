package com.gsaves.media3.gsaves.app.paypalclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Amount_ {
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("currency")
    @Expose
    private String currency;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
