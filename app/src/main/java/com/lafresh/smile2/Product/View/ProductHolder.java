package com.lafresh.smile2.Product.View;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lafresh.smile2.R;
import com.lafresh.smile2.Repository.Model.Merchant;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.SmileApplication;

import java.text.DecimalFormat;

public class ProductHolder extends RecyclerView.ViewHolder {
    public static final String TAG = ProductHolder.class.getSimpleName();

//    public ImageView img_merchant_left , img_merchant_right;
    public ImageView img_product_left , img_product_right;
    public TextView tv_store_name_left ,tv_product_name_left ,tv_price_left , tv_sale_price_left;
    public TextView tv_store_name_right ,tv_product_name_right ,tv_price_right , tv_sale_price_right;
    public ConstraintLayout layout_left , layout_right;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#,###");

    public ProductHolder(@NonNull View itemView) {
        super(itemView);
        img_product_left = itemView.findViewById(R.id.img_product_left);
        img_product_right = itemView.findViewById(R.id.img_product_right);
        tv_product_name_left = itemView.findViewById(R.id.tv_product_name_left);
        tv_product_name_right = itemView.findViewById(R.id.tv_product_name_right);
        tv_price_left = itemView.findViewById(R.id.tv_price_left);
        tv_price_right = itemView.findViewById(R.id.tv_price_right);
        tv_sale_price_left = itemView.findViewById(R.id.tv_sale_price_left);
        tv_sale_price_right = itemView.findViewById(R.id.tv_sale_price_right);
        layout_left = itemView.findViewById(R.id.constraintLayout_left);
        layout_right = itemView.findViewById(R.id.constraintLayout_right);
    }

    public void setImg_product_two(Product productleft , Product productright) {
        img_product_right.setVisibility(View.VISIBLE);
        tv_price_right.setVisibility(View.VISIBLE);
        tv_sale_price_right.setVisibility(View.VISIBLE);
        tv_product_name_right.setVisibility(View.VISIBLE);
        layout_right.setVisibility(View.VISIBLE);
        Glide.with(ProductFragment.getInstance()).load(SmileApplication.DOMAIN + productleft.getPicture_url()).into(img_product_left);
        Glide.with(ProductFragment.getInstance()).load(SmileApplication.DOMAIN + productright.getPicture_url()).into(img_product_right);
        tv_price_left.setText("NT$" + mDecimalFormat.format((double)productleft.getPrice()));
        tv_product_name_left.setText(productleft.getProduct_name());
        tv_sale_price_left.setText("NT$" + mDecimalFormat.format((double)productleft.getSale_price()));
        tv_price_left.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        img_product_left.setTag(R.id.img_product_left,productleft.getId());
        img_product_right.setTag(R.id.img_product_right,productright.getId());
        layout_left.setTag(R.id.constraintLayout_left,productleft.getId());
        layout_right.setTag(R.id.constraintLayout_right,productright.getId());
        tv_price_right.setText("NT$" + mDecimalFormat.format((double)productright.getPrice()));
        tv_product_name_right.setText(productright.getProduct_name());
        tv_sale_price_right.setText("NT$" + mDecimalFormat.format((double)productright.getSale_price()));
        tv_price_right.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void setImg_product_one(Product productleft) {
        layout_right.setVisibility(View.INVISIBLE);
        img_product_right.setVisibility(View.INVISIBLE);
        tv_price_right.setVisibility(View.INVISIBLE);
        tv_sale_price_right.setVisibility(View.INVISIBLE);
        tv_product_name_right.setVisibility(View.INVISIBLE);
        Glide.with(ProductFragment.getInstance()).load(SmileApplication.DOMAIN + productleft.getPicture_url()).into(img_product_left);
        img_product_left.setTag(R.id.img_product_left,productleft.getId());
        layout_left.setTag(R.id.constraintLayout_left,productleft.getId());
        tv_price_left.setText("NT$" + mDecimalFormat.format((double)productleft.getPrice()));
        tv_product_name_left.setText(productleft.getProduct_name());
        tv_sale_price_left.setText("NT$" + mDecimalFormat.format((double)productleft.getSale_price()));
        tv_price_left.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
