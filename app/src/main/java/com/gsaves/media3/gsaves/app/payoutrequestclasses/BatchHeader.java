package com.gsaves.media3.gsaves.app.payoutrequestclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BatchHeader {
    @SerializedName("payout_batch_id")
    @Expose
    private String payoutBatchId;
    @SerializedName("batch_status")
    @Expose
    private String batchStatus;
    @SerializedName("sender_batch_header")
    @Expose
    private SenderBatchHeader senderBatchHeader;

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

    public SenderBatchHeader getSenderBatchHeader() {
        return senderBatchHeader;
    }

    public void setSenderBatchHeader(SenderBatchHeader senderBatchHeader) {
        this.senderBatchHeader = senderBatchHeader;
    }
}
