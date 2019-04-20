package com.gsaves.media3.gsaves.app.paypalclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payer {
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("funding_instruments")
    @Expose
    private List<FundingInstrument> fundingInstruments = null;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<FundingInstrument> getFundingInstruments() {
        return fundingInstruments;
    }

    public void setFundingInstruments(List<FundingInstrument> fundingInstruments) {
        this.fundingInstruments = fundingInstruments;
    }
}
