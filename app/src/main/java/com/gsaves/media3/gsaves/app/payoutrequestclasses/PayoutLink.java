package com.gsaves.media3.gsaves.app.payoutrequestclasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayoutLink {
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("rel")
    @Expose
    private String rel;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("encType")
    @Expose
    private String encType;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEncType() {
        return encType;
    }

    public void setEncType(String encType) {
        this.encType = encType;
    }

}
