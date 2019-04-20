package com.gsaves.media3.gsaves.app.payoutrequestclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gsaves.media3.gsaves.app.paypalclasses.Link_;

import java.util.List;

public class PayoutStatusResponse {
    @SerializedName("batch_header")
    @Expose
    private PayoutBatchHeader batchHeader;
    @SerializedName("items")
    @Expose
    private List<PayoutStatusresponseItem> items = null;
    @SerializedName("links")
    @Expose
    private List<PayoutStatusLink_> links = null;

    public PayoutBatchHeader getBatchHeader() {
        return batchHeader;
    }

    public void setBatchHeader(PayoutBatchHeader batchHeader) {
        this.batchHeader = batchHeader;
    }

    public List<PayoutStatusresponseItem> getItems() {
        return items;
    }

    public void setItems(List<PayoutStatusresponseItem> items) {
        this.items = items;
    }

    public List<PayoutStatusLink_> getLinks() {
        return links;
    }

    public void setLinks(List<PayoutStatusLink_> links) {
        this.links = links;
    }

}
