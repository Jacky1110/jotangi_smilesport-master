package com.lafresh.smile2.Repository.ApiRepository;

import android.util.Log;

import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Repository.AbsApiCallback;
import com.lafresh.smile2.Repository.ApiService.MemberApiService;
import com.lafresh.smile2.Repository.Model.MemberMessage;
import com.lafresh.smile2.Repository.Model.OrderProduct;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.SmileApplication;
import com.lafresh.smile2.Utils.ApiUtils;
import com.lafresh.smile2.Utils.SharedUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.lafresh.smile2.Constant.ApiConstant.*;
import static com.lafresh.smile2.Order.View.ReturnListFragment.STATUS_RETURN;

import org.json.JSONObject;

public class MemberRepository extends ApiRepository {
    public static final String TAG = MemberRepository.class.getSimpleName();

    private static MemberRepository memberRepository;

    public static MemberRepository getInstance() {
        if (memberRepository == null) {
            memberRepository = new MemberRepository();
        }
        return memberRepository;
    }

    private MemberRepository() {
        super();
    }

    public void register(final User user, final String password, final AbsApiCallback callback) {

        Map<String, String> map = new HashMap<>();
        map.put("accountno", user.getAccount());
        map.put("password", password);
        map.put("name", user.getName());
        map.put("birthday", user.getBirthday());
        map.put("email", "");
        //map.put("gender", user.getGender());
        map.put("isApp", "true");
        map.put("machine_type", "android"); //原生自行寫死，安卓傳”android”，蘋果傳”iOS”
        map.put("firebase_token", SharedUtils.getInstance().getFirebaseToken(SmileApplication.getInstance()));
        Log.d(TAG, "firebase_token: " + SharedUtils.getInstance().getFirebaseToken(SmileApplication.getInstance()));

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postRegister(requestBody).enqueue(callback);
    }

    public void login(final String account, final String password, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("accountno", account);
        map.put("password", password);
        map.put("isApp","true");
        map.put("machine_type", "android"); //原生自行寫死，安卓傳”android”，蘋果傳”iOS”
        map.put("firebase_token", SharedUtils.getInstance().getFirebaseToken(SmileApplication.getInstance()));

        Log.d(TAG, "FirebaseToken: " + SharedUtils.getInstance().getFirebaseToken(SmileApplication.getInstance()));

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postLogin(requestBody).enqueue(callback);
    }

    public void logout(final String member_id , final AbsApiCallback callback) {
        Log.e(TAG,"logout");
        Map<String, String> map = new HashMap<>();
        map.put("member_id", member_id);
        map.put("isApp", "true");

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postLogout(requestBody).enqueue(callback);
    }

    public void getNewsDetailApi(final String news_id , final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("news_id", news_id);
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetNewsDetail(requestBody).enqueue(callback);
    }

    public void checkAccount(final String account , final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("accountno", account);
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postCheckAccount(requestBody).enqueue(callback);
    }

    public void resendSms(final String account , final String code , final String function_name, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("accountno", account);
        map.put("smsverifycode", code);
        map.put("isApp", "true");
        map.put("functionname",function_name);

        Log.d(TAG, "smsverifycode: " + code);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postResendSms(requestBody).enqueue(callback);
    }

    public void verifySms(final String member_id ,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", member_id);
        Log.d(TAG, "modified_user: " + member_id);
        map.put("member_id", member_id);
        Log.d(TAG, "member_id: " + member_id);
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postVerifySms(requestBody).enqueue(callback);
    }

//    public void forgetPasswordSms(final String cellphone, final AbsApiCallback callback) {
//        Map<String, String> map = new HashMap<>();
//        map.put("modified_user", cellphone);
//        map.put("account", cellphone);
//        map.put("account", cellphone);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
//        retrofit.create(MemberApiService.class).postForgetPassword(requestBody).enqueue(callback);
//    }

    public void verifyForgetPassword(final String cellphone, final String password, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("accountno", cellphone);
        map.put("password", password);
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).patchVerifyForgetPassword(requestBody).enqueue(callback);
    }

    public void getMemberInfo(String member_id ,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", member_id);
        map.put("member_id", member_id);
        //map.put("functionname", "membercard_normal");
        map.put("functionname", "membercard");
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMemberInfo(requestBody).enqueue(callback);
    }

    public void getPropertyData(final String sMebmer_id , final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", sMebmer_id);
        map.put("isApp", "true");
        map.put("functionname", "fulladdress");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetPropertyList_AddressData(requestBody).enqueue(callback);
    }

    public void getCarouselList(final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetCarouselList(requestBody).enqueue(callback);
    }

    public void getMerchantList(final String keyword ,final String business_sale_id ,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("business_sale_id" ,business_sale_id);
        map.put("isApp", "true");
        map.put("category_name", keyword);
        Log.e(TAG,""+map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetMerchantList(requestBody).enqueue(callback);
    }

    public void getProductTypeList(final int store_id ,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("isApp", "true");
        map.put("store_id", Integer.toString(store_id));
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetProductTypeList(requestBody).enqueue(callback);
    }

    public void getLotList(final String mebmer_id,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("isApp", "true");
        map.put("modified_user", mebmer_id);
        map.put("mebmer_id", mebmer_id);
        map.put("functionname", "lotlist");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetLotList(requestBody).enqueue(callback);
    }

//    public void getShoppingCartCount(final String mebmer_id ,final AbsApiCallback callback) {
//        Map<String, String> map = new HashMap<>();
//        map.put("isApp", "true");
//        map.put("member_id", mebmer_id);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
//        retrofit.create(MemberApiService.class).GetShoppingCartCount(requestBody).enqueue(callback);
//    }

    public void getProductList(final String  business_sale_id ,final String sort ,final String store_id , final String product_type_id, final String product_name,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("isApp", "true");
        map.put("store_id", store_id);
        map.put("product_type_id", product_type_id);
        map.put("functionname", "productlist");
        map.put("product_name", product_name);
        map.put("SortMode", sort);
        map.put("sales_type","");
        map.put("business_sale_id",business_sale_id);
        Log.e(TAG,"map : " + map.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetProductList(requestBody).enqueue(callback);
    }

    public void getProductDetail(final String business_sale_id ,final String member_id, final String product_id ,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("product_id", product_id);
        map.put("isApp", "true");
        map.put("functionname", "productdetail");
        map.put("member_id",member_id);
        map.put("business_sale_id",business_sale_id);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetProductDetail(requestBody).enqueue(callback);
    }

    public void getLotDetail(final int lot_id ,final String member_id ,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", member_id);
        map.put("member_id", member_id);
        map.put("isApp", "true");
        map.put("functionname", "lotdetail");
        map.put("lot_id",Integer.toString(lot_id));
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetLotDetail(requestBody).enqueue(callback);
    }

    public void joinDrawLots(final int lot_id , final String cif_code , final String prod_id, final String spec_name ,final String member_id ,final String receipt_no,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", member_id);
        map.put("member_id", member_id);
        map.put("isApp", "true");
        map.put("lot_product_id", prod_id);
        map.put("lot_spec_name", spec_name);
        map.put("cif_id", cif_code);
        map.put("lot_id",Integer.toString(lot_id));
        map.put("receipt_no",receipt_no);
        Log.e(TAG , map.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).JoinDrawLots(requestBody).enqueue(callback);
    }

    public void getBusinessCode(final String sale_password ,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("sale_password", sale_password);
        map.put("isApp", "true");
        Log.e(TAG,map.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).GetBusinessCode(requestBody).enqueue(callback);
    }

    public void addShoppingCart(final String business_sale_id ,final  String sale_type ,final String member_id  , final String product_id , final String spec_id , final String quantity, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        Log.e(TAG,"addShoppingCart");
        map.put("member_id", member_id);
        map.put("product_id", product_id);
        map.put("spec_id", spec_id);
        map.put("quantity", quantity);
        map.put("isApp", "true");
        map.put("sales_type",sale_type);
        map.put("modified_user",member_id);
        map.put("business_sale_id",business_sale_id);

        Log.e(TAG , map.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).AddShoppingCart(requestBody).enqueue(callback);
    }

    public void updatePassword(final String member_id,final String oldPassword, final String newPassword, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", member_id);
        map.put("member_id", member_id);
        map.put("password", newPassword);
        map.put("oldpassword", oldPassword);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).patchUpdatePassword(requestBody).enqueue(callback);
    }

    public void updateMemberInfo(final String sMebmer_id , final String city_code , final String area_code , final String address , final String email , final String birth, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", sMebmer_id);
        map.put("member_id", sMebmer_id);
//        map.put("gender", gender);
        map.put("birthday", birth);
        map.put("city_code", city_code);
        map.put("area_code", area_code);
        map.put("address", address);
        map.put("email", email);
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).putUpdateMember(requestBody).enqueue(callback);
    }

    public void getMemberPointHistory(final String date ,final String member_id ,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", member_id);
        map.put("member_id", member_id);
        map.put("modified_date_y", date);
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMemberPointHistory(requestBody).enqueue(callback);
    }

    public void getMemberMessage(final String member_id ,final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", member_id);
        map.put("functionname", "apppushlist");
        map.put("member_id", member_id);
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMemberMessageList(requestBody).enqueue(callback);

    }

    public void getAboutData(final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("isApp", "true");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postAboutData(requestBody).enqueue(callback);
    }

    public void getFaqData(final String faq_id , final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("isApp", "true");
        map.put("faq_id",faq_id);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postFaqData(requestBody).enqueue(callback);
    }

    public void getReadMessage(final AbsApiCallback callback) {
        getMemberToken(ACTION_GET_READ_MESSAGE_LIST, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String code) {
                Map<String, String> map = new HashMap<>();
                map.put("code", code);

                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
                retrofit.create(MemberApiService.class).postGetReadMessageList(requestBody).enqueue(callback);
            }
        }, callback);
    }

    public void updateMessageStatus(final String mebmer_id ,final int messageId, final String status, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user",mebmer_id);
        map.put("member_id",mebmer_id);
        map.put("isApp","true");
        map.put("push_id", Integer.toString(messageId));
        map.put(status, "Y");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postUpdateMessageStatus(requestBody).enqueue(callback);
    }

    public void getOrders(final AbsApiCallback callback) {
        getMemberToken(ACTION_GET_ORDERS, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String code) {
                Map<String, String> map = new HashMap<>();
                map.put("code", code);

                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
                retrofit.create(MemberApiService.class).postGetMemberOrders(requestBody).enqueue(callback);
            }
        }, callback);
    }

//    public void getMailBadge(final String mebmer_id , final AbsApiCallback callback) {
//        Map<String, String> map = new HashMap<>();
//        map.put("modified_user",mebmer_id);
//        map.put("member_id",mebmer_id);
//        map.put("isApp","true");
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
//        retrofit.create(MemberApiService.class).postGetMailBadge(requestBody).enqueue(callback);
//    }

//    public void getMessageBadge(final String mebmer_id , final AbsApiCallback callback) {
//        Map<String, String> map = new HashMap<>();
//        map.put("modified_user",mebmer_id);
//        map.put("member_id",mebmer_id);
//        map.put("isApp","true");
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
//        retrofit.create(MemberApiService.class).postGetMessageBadge(requestBody).enqueue(callback);
//    }

    public void getBadgeNumber(final String mebmer_id , final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user",mebmer_id);
        map.put("member_id",mebmer_id);
        map.put("isApp","true");

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetBadgeNumber(requestBody).enqueue(callback);
    }

    public void returnProducts(final ArrayList<OrderProduct> products, final String name, final String address,
                               final String mobile, final String email, final int returnType, final AbsApiCallback callback) {
        getMemberToken(ACTION_RETURN_ORDER, new BaseContract.ValueCallback<String>() {
            @Override
            public void onValueCallback(int task, String code) {
                Map<String, String> map = new HashMap<>();
                map.put("code", code);
                map.put("oid", products.get(0).getOrderId());
                map.put("name", name);
                map.put("address", address);
                map.put("mobile", mobile);
                map.put("email", email);
                map.put("type", returnType == STATUS_RETURN ? "return" : "exchange");

                StringBuilder productBuilder = new StringBuilder();
                for (OrderProduct orderProduct : products) {
                    productBuilder.append(orderProduct.getProduct().getProductNo()).append(",");
                }
                map.put("product", productBuilder.substring(0, productBuilder.length() - 1));
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
                retrofit.create(MemberApiService.class).postReturnOrder(requestBody).enqueue(callback);
            }
        }, callback);
    }

    public void getMessageList(final String member_id, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", member_id);
        map.put("isApp", "true");
        map.put("member_id", member_id);
        Log.e(TAG,"getMessageList  map : " + map.toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postGetMessageList(requestBody).enqueue(callback);
    }

    public void addMemberContact(final String member_id ,final String message, final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", member_id);
        map.put("isApp", "true");
        map.put("member_id", member_id);
        map.put("message_content", message);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).postAddMemberContact(requestBody).enqueue(callback);
    }

    public void updateReadMessage(final String member_id , final AbsApiCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("modified_user", member_id);
        map.put("isApp", "true");
        map.put("member_id", member_id);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), ApiUtils.getEncodeStringParams(map));
        retrofit.create(MemberApiService.class).updateReadMessage(requestBody).enqueue(callback);
    }

    public void getCardPageList(final String account_id,final String DeviceOS, final AbsApiCallback callback){
        Log.d("ddd","getcardpagelist");
        Map<String, String> map = new HashMap<>();
        map.put("account_id", account_id);
        map.put("DeviceOS", DeviceOS);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), new JSONObject(map).toString());
        Log.d("ddd",new JSONObject(map).toString());
        retrofit.create(MemberApiService.class).getCardPageList(requestBody).enqueue(callback);
    }

    public void getBarcode(final String account_id,final String deviceOS,final String cardToken,final AbsApiCallback callback){
        Log.d("ddd","getBarcode");
        Map<String, String> map = new HashMap<>();
        map.put("account_id", account_id);
        map.put("DeviceOS", deviceOS);
        map.put("card_token", cardToken);
        map.put("is_redeem", "N");
        map.put("WalletDefineCode", "");
        map.put("ActivityCode", "");
        map.put("CarrierType", "");
        map.put("CarrierCredit", "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), new JSONObject(map).toString());
        Log.d("ddd",new JSONObject(map).toString());
        retrofit.create(MemberApiService.class).getBarcode(requestBody).enqueue(callback);
    }

    public void deleteBindingCard(final String account_id,final String deviceOS,final String cardToken,final AbsApiCallback callback){
        Log.d("ddd","deleteBindingCard");
        Map<String, String> map = new HashMap<>();
        map.put("account_id", account_id);
        map.put("DeviceOS", deviceOS);
        map.put("card_token", cardToken);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), new JSONObject(map).toString());
        Log.d("ddd",new JSONObject(map).toString());
        retrofit.create(MemberApiService.class).deleteBindingCard(requestBody).enqueue(callback);
    }
}
