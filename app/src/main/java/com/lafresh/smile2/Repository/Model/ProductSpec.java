package com.lafresh.smile2.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductSpec implements Parcelable {
    public static final String TAG = ProductSpec.class.getSimpleName();

    @SerializedName("spec_id")
    private int id = 0;
    @SerializedName("spec_name")
    private String specname = "";
    private String stock = "";
    private int spec_qty = 0;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecname() {
        return specname;
    }

    public void setSpecname(String specname) {
        this.specname = specname;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String specname) {
        this.stock = stock;
    }

    public int getSpec_qty() {
        return spec_qty;
    }

    public void setSpec_qty(int spec_qty) {
        this.spec_qty = spec_qty;
    }

    protected ProductSpec(Parcel in) {
        id = in.readInt();
        specname = in.readString();
        stock = in.readString();
    }

    public static final Creator<ProductSpec> CREATOR = new Creator<ProductSpec>() {
        @Override
        public ProductSpec createFromParcel(Parcel in) {
            return new ProductSpec(in);
        }

        @Override
        public ProductSpec[] newArray(int size) {
            return new ProductSpec[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(specname);
        dest.writeString(stock);
    }
}
