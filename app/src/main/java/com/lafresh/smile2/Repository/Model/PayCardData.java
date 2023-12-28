package com.lafresh.smile2.Repository.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PayCardData implements Serializable {
    @SerializedName("TransNo")
    public String transNo;

    @SerializedName("MemberId")
    public String member_id;

    @SerializedName("Cards")
    public List<PayCard> cards;

    @SerializedName("ApposId")
    public String apposId;

    @SerializedName("ResultCode")
    public String resultCode;

    @SerializedName("ResultMessage")
    public String resultMessage;

    @SerializedName("RequestRandom")
    public String requestRandom;

    @SerializedName("RtnCode")
    public String rtn_code;
}
