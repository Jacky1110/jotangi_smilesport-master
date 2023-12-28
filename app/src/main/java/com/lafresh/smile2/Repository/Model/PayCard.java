package com.lafresh.smile2.Repository.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PayCard implements Serializable {
    @SerializedName("CardName")
    public String name;

    @SerializedName("CardNumber")
    public String number;

    @SerializedName("CardToken")
    public String token;

    @SerializedName("BankNo")
    public String bankNo;

    @SerializedName("CardType")
    public String cardType;

    public Boolean defaultCard = false;
}
