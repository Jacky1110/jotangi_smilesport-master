package com.lafresh.smile2.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.lafresh.smile2.Repository.Model.User;

public class SharedUtils {
    public static final String TAG = SharedUtils.class.getSimpleName();
    private final String INIT = "init";
    private final String FIREBASE_TOKEN = "firebase_token";
    private final String FIRST_OPEN = "first_open";
    private final String FIRST_OPEN_STORE_LIST = "first_open_store_list";

    private final String ACCOUNT_INFO = "account_info";
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";

    private static SharedUtils sharedUtils;

    public static SharedUtils getInstance() {
        if (sharedUtils == null) {
            sharedUtils = new SharedUtils();
        }

        return sharedUtils;
    }

    private SharedUtils() {
    }

    public boolean getUserFirstOpenApp(Context context) {
        return context.getSharedPreferences(INIT, Context.MODE_PRIVATE).getBoolean(FIRST_OPEN, true);
    }

    public void setUserOpenApp(Context context) {
        context.getSharedPreferences(INIT, Context.MODE_PRIVATE).edit().putBoolean(FIRST_OPEN, false).apply();
    }

    public String getFirebaseToken(Context context) {
        return context.getSharedPreferences(INIT, Context.MODE_PRIVATE).getString(FIREBASE_TOKEN, "");
    }


    public void setFirebaseToken(Context context, String token) {
        context.getSharedPreferences(INIT, Context.MODE_PRIVATE).edit().putString(FIREBASE_TOKEN, token).apply();
    }

    public void saveAccountInfo(Context context, String account, String password) {
        context.getSharedPreferences(ACCOUNT_INFO, Context.MODE_PRIVATE).edit()
                .putString(ACCOUNT, account)
                .putString(PASSWORD, password)
                .apply();
    }

    public void saveUserID(Context context, String member_id){
        context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).edit()
                .putString("MemberID", member_id)
                .apply();
    }

    public void saveBusinessSaleID(Context context, String business_sale_id){
        context.getSharedPreferences("BusinessSaleID", Context.MODE_PRIVATE).edit()
                .putString("BusinessSaleID", business_sale_id)
                .apply();
    }

    public void saveShoppingCartCount(Context context, String shoppingcartcount){
        context.getSharedPreferences("ShoppingCartCount", Context.MODE_PRIVATE).edit()
                .putString("ShoppingCartCount", shoppingcartcount)
                .apply();
    }

    public void saveUser(Context context, User user) {
        SharedPreferences.Editor editor = context.getSharedPreferences(User.TAG, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(User.TAG, json);
        editor.apply();
    }

    public void saveVerifyCode(Context context, String verifyState){
        context.getSharedPreferences("VerifyCode", Context.MODE_PRIVATE).edit()
                .putString("VerifyCode", verifyState)
                .apply();
    }

    public void savePayCode(Context context, String payCode){
        context.getSharedPreferences("PayCode", Context.MODE_PRIVATE).edit()
                .putString("PayCode",payCode)
                .apply();
    }

    public void saveDefaultCardNumber(Context context, String cardNum){
        context.getSharedPreferences("DefaultCardNumber",Context.MODE_PRIVATE).edit()
                .putString("DefaultCardNumber",cardNum)
                .apply();
    }

    public void removeAllLocalData(Context context) {
        context.getSharedPreferences(User.TAG, Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences(ACCOUNT_INFO, Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("BusinessSaleID", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("ShoppingCartCount", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("config", Context.MODE_PRIVATE).edit().clear().apply();
        context.getSharedPreferences("VerifyCode", Context.MODE_PRIVATE).edit().clear().apply();
    }
    public User getUser(Context context) {
        String json = context.getSharedPreferences(User.TAG, Context.MODE_PRIVATE).getString(User.TAG, "");
        User user = new Gson().fromJson(json, User.class);
        if (user != null && user.getMember()!=0) {
            return user;
        }
        return null;
    }

    public String getUserAccount(Context context) {
        return context.getSharedPreferences(ACCOUNT_INFO, Context.MODE_PRIVATE).getString(ACCOUNT, "");
    }

    public String getUserID(Context context) {
        return context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
    }

    public String getBusinessSaleID(Context context) {
        return context.getSharedPreferences("BusinessSaleID", Context.MODE_PRIVATE).getString("BusinessSaleID", "");
    }

    public String getShoppingCartCount(Context context) {
        return context.getSharedPreferences("ShoppingCartCount", Context.MODE_PRIVATE).getString("ShoppingCartCount", "");
    }

    public String getUserPassword(Context context) {
        return context.getSharedPreferences(ACCOUNT_INFO, Context.MODE_PRIVATE).getString(PASSWORD, "");
    }

    public String getVerifyCode(Context context) {
        return context.getSharedPreferences("VerifyCode", Context.MODE_PRIVATE).getString("VerifyCode", "");
    }

    public String getPayCode(Context context){
        return context.getSharedPreferences("PayCode", Context.MODE_PRIVATE).getString("PayCode","");
    }

    public String getDefaultCardNumber(Context context){
        return context.getSharedPreferences("DefaultCardNumber",Context.MODE_PRIVATE).getString("DefaultCardNumber","");
    }
}
