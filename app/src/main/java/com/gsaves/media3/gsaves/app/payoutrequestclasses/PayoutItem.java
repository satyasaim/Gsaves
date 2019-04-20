package com.gsaves.media3.gsaves.app.payoutrequestclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayoutItem {
    @SerializedName("recipient_type")
    @Expose
    private String recipientType;
    @SerializedName("amount")
    @Expose
    private Amount_ amount;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("receiver")
    @Expose
    private String receiver;
    @SerializedName("sender_item_id")
    @Expose
    private String senderItemId;
    @SerializedName("recipient_wallet")
    @Expose
    private String recipientWallet;

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public Amount_ getAmount() {
        return amount;
    }

    public void setAmount(Amount_ amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSenderItemId() {
        return senderItemId;
    }

    public void setSenderItemId(String senderItemId) {
        this.senderItemId = senderItemId;
    }

    public String getRecipientWallet() {
        return recipientWallet;
    }

    public void setRecipientWallet(String recipientWallet) {
        this.recipientWallet = recipientWallet;
    }

}
