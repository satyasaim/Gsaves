package com.gsaves.media3.gsaves.app.paypalclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditCardToken {
    @SerializedName("credit_card_id")
    @Expose
    private String creditCardId;
    @SerializedName("payer_id")
    @Expose
    private String payerId;
    @SerializedName("external_customer_id")
    @Expose
    private String externalCustomerId;
    @SerializedName("last4")
    @Expose
    private String last4;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("expire_month")
    @Expose
    private String expireMonth;
    @SerializedName("expire_year")
    @Expose
    private String expireYear;

    public String getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getExternalCustomerId() {
        return externalCustomerId;
    }

    public void setExternalCustomerId(String externalCustomerId) {
        this.externalCustomerId = externalCustomerId;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(String expireMonth) {
        this.expireMonth = expireMonth;
    }

    public String getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(String expireYear) {
        this.expireYear = expireYear;
    }
}
