package com.lafresh.smile2.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product implements Parcelable {
    public static final String TAG = Product.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("product_id")
    private int id = 0;
    private String product_name = "";
    private String store_id = "";
    private int price = 0;
    private int sale_price = 0;
    private String modified_date = "";
    private String freight_title = "";
    private String picture_url = "";
    private List<ProductPicture> picture_url_list;
    private String dealer_product_id = "";
    private String desc = "";
    private String store_name = "";
    private List<ProductSpec> spec_list;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getDealer_product_id() {
        return dealer_product_id;
    }

    public void setDealer_product_id(String dealer_product_id) {
        this.dealer_product_id = dealer_product_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getFreight_title() {
        return freight_title;
    }

    public void setFreight_title(String freight_title) {
        this.freight_title = freight_title;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public List<ProductPicture> getPicture_url_list() {
        return picture_url_list;
    }

    public void setPicture_url_list(List<ProductPicture> picture_url_list) {
        this.picture_url_list = picture_url_list;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public List<ProductSpec> getSpec_list() {
        return spec_list;
    }

    public void setSpec_list(List<ProductSpec> spec_list) {
        this.spec_list = spec_list;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        price = in.readInt();
        sale_price = in.readInt();
        picture_url = in.readString();
        product_name = in.readString();
        freight_title = in.readString();
        modified_date = in.readString();
        picture_url_list = in.createTypedArrayList(ProductPicture.CREATOR);
        dealer_product_id = in.readString();
        desc = in.readString();
        store_id = in.readString();
        store_name = in.readString();
        spec_list = in.createTypedArrayList(ProductSpec.CREATOR);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(price);
        dest.writeInt(sale_price);
        dest.writeString(picture_url);
        dest.writeString(product_name);
        dest.writeString(freight_title);
        dest.writeString(modified_date);
        dest.writeTypedList(picture_url_list);
        dest.writeString(desc);
        dest.writeString(dealer_product_id);
        dest.writeString(store_id);
        dest.writeString(store_name);
        dest.writeTypedList(spec_list);
    }
}

