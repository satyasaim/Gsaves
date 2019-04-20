package com.gsaves.media3.gsaves.app.payoutrequestclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PayoutResponse {

    @SerializedName("batch_header")
    @Expose
    private BatchHeader batchHeader;
    @SerializedName("links")
    @Expose
    private List<PayoutLink> links = null;

    public BatchHeader getBatchHeader() {
        return batchHeader;
    }

    public void setBatchHeader(BatchHeader batchHeader) {
        this.batchHeader = batchHeader;
    }

    public List<PayoutLink> getLinks() {
        return links;
    }

    public void setLinks(List<PayoutLink> links) {
        this.links = links;
    }
}
