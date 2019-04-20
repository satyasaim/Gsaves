package com.gsaves.media3.gsaves.app.payoutrequestclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SenderBatchHeader {
    @SerializedName("sender_batch_id")
    @Expose
    private String senderBatchId;
    @SerializedName("email_subject")
    @Expose
    private String emailSubject;
    @SerializedName("email_message")
    @Expose
    private String emailMessage;

    public String getSenderBatchId() {
        return senderBatchId;
    }

    public void setSenderBatchId(String senderBatchId) {
        this.senderBatchId = senderBatchId;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

}
