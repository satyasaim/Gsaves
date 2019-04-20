package com.gsaves.media3.gsaves.app.payoutrequestclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PayoutStatusresponseItem {
    @SerializedName("payout_item_id")
    @Expose
    private String payoutItemId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("payout_item_fee")
    @Expose
    private PayoutItemFee payoutItemFee;
    @SerializedName("payout_batch_id")
    @Expose
    private String payoutBatchId;
    @SerializedName("payout_item")
    @Expose
    private PayoutItem payoutItem;
    @SerializedName("time_processed")
    @Expose
    private String timeProcessed;
    @SerializedName("errors")
    @Expose
    private Errors errors;
    @SerializedName("links")
    @Expose
    private List<Link> links = null;

    public String getPayoutItemId() {
        return payoutItemId;
    }

    public void setPayoutItemId(String payoutItemId) {
        this.payoutItemId = payoutItemId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public PayoutItemFee getPayoutItemFee() {
        return payoutItemFee;
    }

    public void setPayoutItemFee(PayoutItemFee payoutItemFee) {
        this.payoutItemFee = payoutItemFee;
    }

    public String getPayoutBatchId() {
        return payoutBatchId;
    }

    public void setPayoutBatchId(String payoutBatchId) {
        this.payoutBatchId = payoutBatchId;
    }

    public PayoutItem getPayoutItem() {
        return payoutItem;
    }

    public void setPayoutItem(PayoutItem payoutItem) {
        this.payoutItem = payoutItem;
    }

    public String getTimeProcessed() {
        return timeProcessed;
    }

    public void setTimeProcessed(String timeProcessed) {
        this.timeProcessed = timeProcessed;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
