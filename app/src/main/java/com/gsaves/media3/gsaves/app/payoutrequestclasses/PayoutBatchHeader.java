package com.gsaves.media3.gsaves.app.payoutrequestclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayoutBatchHeader {
    @SerializedName("payout_batch_id")
    @Expose
    private String payoutBatchId;
    @SerializedName("batch_status")
    @Expose
    private String batchStatus;
    @SerializedName("time_created")
    @Expose
    private String timeCreated;
    @SerializedName("time_completed")
    @Expose
    private String timeCompleted;
    @SerializedName("sender_batch_header")
    @Expose
    private SenderBatchHeader senderBatchHeader;
    @SerializedName("amount")
    @Expose
    private Amount_ amount;
    @SerializedName("fees")
    @Expose
    private Fees fees;

    public String getPayoutBatchId() {
        return payoutBatchId;
    }

    public void setPayoutBatchId(String payoutBatchId) {
        this.payoutBatchId = payoutBatchId;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted(String timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public SenderBatchHeader getSenderBatchHeader() {
        return senderBatchHeader;
    }

    public void setSenderBatchHeader(SenderBatchHeader senderBatchHeader) {
        this.senderBatchHeader = senderBatchHeader;
    }

    public Amount_ getAmount() {
        return amount;
    }

    public void setAmount(Amount_ amount) {
        this.amount = amount;
    }

    public Fees getFees() {
        return fees;
    }

    public void setFees(Fees fees) {
        this.fees = fees;
    }
}
