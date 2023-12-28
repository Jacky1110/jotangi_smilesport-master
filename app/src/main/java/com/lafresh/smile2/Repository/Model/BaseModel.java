package com.lafresh.smile2.Repository.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseModel<T> {
    public static final String TAG = BaseModel.class.getSimpleName();

    private String link;
    private String method;
    private int code;
    private String Message;
    private T Data;
    private boolean isSuccess;
    private String staffQuota;
    private String departureDate;

    private String staffMobile;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public T getItems() {
        return Data;
    }

    public void setItems(T Data) {
        this.Data = Data;
    }

    public String getQuota() {
        return staffQuota;
    }

    public void setQuota(String quota) {
        this.staffQuota = quota;
    }

    public String getDeparture() {
        return departureDate;
    }

    public void setDeparture(String departure) {
        this.departureDate = departure;
    }

    public String getStaffMobile() {
        return staffMobile;
    }

    public void setStaffMobile(String Mobile) {
        this.staffMobile = Mobile;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("link", link);
            jsonObject.put("method", method);
            jsonObject.put("code", code);
            jsonObject.put("isSuccess", isSuccess);
            jsonObject.put("Message", Message);
            jsonObject.put("Data", Data);
            jsonObject.put("staff_quota", staffQuota);
            Log.d(TAG, "staff_quota: "+ staffQuota);
            jsonObject.put("departure_date", departureDate);
            jsonObject.put("staff_mobile", staffMobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
