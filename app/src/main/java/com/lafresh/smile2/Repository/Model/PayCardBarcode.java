package com.lafresh.smile2.Repository.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PayCardBarcode implements Serializable {
    @SerializedName("TransNo")
    public String transNo;

    @SerializedName("MemberId")
    public String memberId;

    @SerializedName("CardToken")
    public String cardToken;

    @SerializedName("isRedeem")
    public String isRedeem;

    @SerializedName("WalletDefineCode")
    public String walletDefineCode;

    @SerializedName("ActivityCode")
    public String activityCode;

    @SerializedName("Barcode")
    public String barCode;

    @SerializedName("Duration")
    public String duration;

    @SerializedName("ExpDate")
    public String expDate;

    @SerializedName("BankNo")
    public String bankNo;

    @SerializedName("CardNumber")
    public String cardNumber;

    @SerializedName("PaymentType")
    public String paymentType;

    @SerializedName("ExBarcode")
    public String exBarcode;
}
