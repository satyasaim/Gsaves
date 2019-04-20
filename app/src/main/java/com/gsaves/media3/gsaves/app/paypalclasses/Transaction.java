package com.gsaves.media3.gsaves.app.paypalclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Transaction {
    @SerializedName("amount")
    @Expose
    private Amount amount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("related_resources")
    @Expose
    private List<RelatedResource> relatedResources = null;

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RelatedResource> getRelatedResources() {
        return relatedResources;
    }

    public void setRelatedResources(List<RelatedResource> relatedResources) {
        this.relatedResources = relatedResources;
    }
}
