package com.gsaves.media3.gsaves.app.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QrcodeData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("brcode_number")
    @Expose
    private String brcodeNumber;
    @SerializedName("brcode_type")
    @Expose
    private String brcodeType;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_discount")
    @Expose
    private String productDiscount;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrcodeNumber() {
        return brcodeNumber;
    }

    public void setBrcodeNumber(String brcodeNumber) {
        this.brcodeNumber = brcodeNumber;
    }

    public String getBrcodeType() {
        return brcodeType;
    }

    public void setBrcodeType(String brcodeType) {
        this.brcodeType = brcodeType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
