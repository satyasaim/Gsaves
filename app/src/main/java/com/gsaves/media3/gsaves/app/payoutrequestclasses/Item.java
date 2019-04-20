package com.gsaves.media3.gsaves.app.payoutrequestclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("recipient_type")
    @Expose
    private String recipientType;
    @SerializedName("amount")
    @Expose
    private PayoutAmount amount;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("sender_item_id")
    @Expose
    private String senderItemId;
    @SerializedName("receiver")
    @Expose
    private String receiver;

    public String getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public PayoutAmount getAmount() {
        return amount;
    }

    public void setAmount(PayoutAmount amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSenderItemId() {
        return senderItemId;
    }

    public void setSenderItemId(String senderItemId) {
        this.senderItemId = senderItemId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

}
