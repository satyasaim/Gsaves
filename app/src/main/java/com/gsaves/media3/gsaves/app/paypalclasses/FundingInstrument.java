package com.gsaves.media3.gsaves.app.paypalclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundingInstrument {
    @SerializedName("credit_card_token")
    @Expose
    private CreditCardToken creditCardToken;

    public CreditCardToken getCreditCardToken() {
        return creditCardToken;
    }

    public void setCreditCardToken(CreditCardToken creditCardToken) {
        this.creditCardToken = creditCardToken;
    }

}
