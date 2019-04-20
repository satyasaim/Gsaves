package com.gsaves.media3.gsaves.app.pojo;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class LoginResponse {
    private String status;
    private String message;
    private JsonObject user;

    @SerializedName("data")
    @Expose
    private Data data;


    public String getStatus() {


        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {


        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonObject getUser() {
      /*user.toString()
                .replace("\"[", "[").replace("]\"", "]")
                .replace("\\\"{", "{").replace("}\\\"", "}")
                .replace("\\\\\\\"", "\"");
*/
        return user;
    }

    public void setUser(JsonObject user) {
        this.user = user;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}


