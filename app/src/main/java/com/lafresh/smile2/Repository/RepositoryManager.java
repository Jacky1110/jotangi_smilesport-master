package com.lafresh.smile2.Repository;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lafresh.smile2.Base.BaseContract;
import com.lafresh.smile2.Base.BasePresenter;
import com.lafresh.smile2.HttpPost;
import com.lafresh.smile2.MemberBean;
import com.lafresh.smile2.Repository.ApiRepository.ApiRepository;
import com.lafresh.smile2.Repository.ApiRepository.MemberRepository;
import com.lafresh.smile2.Repository.Model.About;
import com.lafresh.smile2.Repository.Model.Address;
import com.lafresh.smile2.Repository.Model.BaseModel;
import com.lafresh.smile2.Repository.Model.BasePayCard;
import com.lafresh.smile2.Repository.Model.Carousel;
import com.lafresh.smile2.Repository.Model.DrawLots;
import com.lafresh.smile2.Repository.Model.Faq;
import com.lafresh.smile2.Repository.Model.MemberMessage;
import com.lafresh.smile2.Repository.Model.Merchant;
import com.lafresh.smile2.Repository.Model.Message;
import com.lafresh.smile2.Repository.Model.Order;
import com.lafresh.smile2.Repository.Model.OrderProduct;
import com.lafresh.smile2.Repository.Model.PayCard;
import com.lafresh.smile2.Repository.Model.PayCardBarcode;
import com.lafresh.smile2.Repository.Model.PayCardData;
import com.lafresh.smile2.Repository.Model.PointHistory;
import com.lafresh.smile2.Repository.Model.Product;
import com.lafresh.smile2.Repository.Model.ProductType;
import com.lafresh.smile2.Repository.Model.Store;
import com.lafresh.smile2.Repository.Model.User;
import com.lafresh.smile2.Utils.ApiUtils;
import com.lafresh.smile2.Utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.lafresh.smile2.Constant.ApiConstant.*;

import retrofit2.Call;
import retrofit2.Response;

public class RepositoryManager {
    public static final String TAG = RepositoryManager.class.getSimpleName();
    private Context context;
    private BasePresenter basePresenter;

    public RepositoryManager(Context context) {
        this.context = context;
    }

    public void setPresenter(BasePresenter basePresenter) {
        this.basePresenter = basePresenter;
    }

    public boolean getUserLogin() {
//        return (SharedUtils.getInstance().getUser(context) != null && SharedUtils.getInstance().getVerifyCode(context).equals("Y"));
        return (SharedUtils.getInstance().getUser(context) != null); //有簡訊認證還是會一直跳login先把驗證拿掉，不清楚為啥要加&& SharedUtils.getInstance().getVerifyCode(context).equals("Y") 這段
    }

    public void callRegisterApi(User user, String password, final BaseContract.ValueCallback<User> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().register(user, password, new ApiCallback<BaseModel<User>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<User> response) {
                super.onApiSuccess(response);
                Boolean isSuccess = response.getSuccess();
                String Message = response.getMessage();
                String[] sMessage = Message.split("\\|");
                if (isSuccess) {
                    valueCallback.onValueCallback(TASK_POST_REGISTER, response.getItems());
                } else {
                    errorCallback.onValueCallback(TASK_POST_REGISTER, sMessage[1]);
                }

            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);

            }
        });
    }

    public void callLoginApi(String account, String password, final BaseContract.ValueCallback<User> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().login(account, password, new ApiCallback<BaseModel<User>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<User> response) {
                super.onApiSuccess(response);

                SharedPreferences pref = context.getSharedPreferences("loginInfo", MODE_PRIVATE);
                pref.edit()
                        .putString("staff_quota", response.getItems().getQuota())
                        .putString("staff_mobile", response.getItems().getStaffMobile())
                        .apply();

                Boolean isSuccess = response.getSuccess();
                String Message = response.getMessage();
                String[] sMessage = Message.split("\\|");
                //sMessage[1] = L_0X004  <-簡訊尚未完成驗證  .  L_1X000  <-已完成驗證成功登入
                if (isSuccess) {
                    if (sMessage[0].equals("L_1X000")) {  // 登入成功
                        valueCallback.onValueCallback(TASK_POST_LOGIN, response.getItems());
                        RepositoryManager.this.saveUser(response.getItems());
                    } else if (sMessage[0].equals("L_0X004")) { // 尚未完成簡訊驗證
                        valueCallback.onValueCallback(209, response.getItems());
//                        RepositoryManager.this.saveUser(response.getItems());
                    } else { // 其他錯誤訊息
                        errorCallback.onValueCallback(500, sMessage[1]);
                    }
                } else {
                    errorCallback.onValueCallback(500, sMessage[1]);
                }
            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);
                errorCallback.onValueCallback(failBaseModel.getCode(), failBaseModel.getMessage());
            }
        });
    }

    public void callLogOutApi(final String member_id, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().logout(member_id, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                SharedUtils.getInstance().removeAllLocalData(context);
                valueCallback.onValueCallback(TASK_POST_LOGOUT, response.getMessage());
            }
        });
    }

    public void callGetMemberInfoApi(String member_id, final BaseContract.ValueCallback<User> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getMemberInfo(member_id, new ApiCallback<BaseModel<User>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<User> response) {
                super.onApiSuccess(response);
                RepositoryManager.this.saveUser(response.getItems());
                valueCallback.onValueCallback(TASK_POST_GET_MEMBER_INFO, response.getItems());
            }
        });
    }

    public void callResendSmsApi(final String account, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        int iRandom = new Random().nextInt(9999);
        String srandom;
        srandom = Integer.toString(iRandom);
        if (iRandom < 1000) {
            srandom = "0" + srandom;
        }
        context.getSharedPreferences("SmsCode", MODE_PRIVATE).edit()
                .putString("SmsCode", srandom)
                .apply();

        MemberRepository.getInstance().resendSms(account, srandom, "DoRegister", new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
//                valueCallback.onValueCallback(TASK_POST_RESEND_SMS, response.getMessage());
                valueCallback.onValueCallback(TASK_POST_RESEND_SMS, "簡訊驗證發送成功");
            }
        });
    }

    public void callVerifySmsApi(final String verifyCode, String member_id, final BaseContract.ValueCallback<String> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        String SmsCode = context.getSharedPreferences("SmsCode", MODE_PRIVATE).getString("SmsCode", "");
        if (verifyCode.equals(MemberBean.Global_Code) || verifyCode.equals(SmsCode)) {
            basePresenter.startCallApi();
            if (member_id.equals("0")) {
                member_id = "520661";
                MemberBean.member_id = member_id;
                MemberRepository.getInstance().verifySms(member_id, new ApiCallback<BaseModel>(basePresenter) {
                    @Override
                    public void onApiSuccess(BaseModel response) {
                        super.onApiSuccess(response);
                        Boolean isSuccess = response.getSuccess();
                        String Message = response.getMessage();
                        String[] sMessage = Message.split("\\|");
                        if (isSuccess) {
                            if (sMessage[0].equals("V_1X000") || sMessage[0].equals("V_1X001") ||
                                    sMessage[0].equals("V_0X001") || sMessage[0].equals("V_0X002")) {
                                valueCallback.onValueCallback(TASK_POST_VERIFY_SMS, sMessage[1]);
                            } else {
                                valueCallback.onValueCallback(TOTAL_ERROR_CODE, sMessage[1]);
                            }
                        } else {
                            valueCallback.onValueCallback(TOTAL_ERROR_CODE, sMessage[1]);
                        }
                    }

                    @Override
                    public void onApiFail(int errorCode, BaseModel failBaseModel) {
                        super.onApiFail(errorCode, failBaseModel);
                        errorCallback.onValueCallback(TASK_POST_VERIFY_SMS, failBaseModel.getMessage());
                    }
                });
            } else {
                MemberBean.member_id = member_id;
                MemberRepository.getInstance().verifySms(member_id, new ApiCallback<BaseModel>(basePresenter) {
                    @Override
                    public void onApiSuccess(BaseModel response) {
                        super.onApiSuccess(response);
                        Boolean isSuccess = response.getSuccess();
                        String Message = response.getMessage();
                        String[] sMessage = Message.split("\\|");
                        if (isSuccess) {
                            if (sMessage[0].equals("V_1X000") || sMessage[0].equals("V_1X001") ||
                                    sMessage[0].equals("V_0X001") || sMessage[0].equals("V_0X002")) {
                                valueCallback.onValueCallback(TASK_POST_VERIFY_SMS, sMessage[1]);
                            } else {
                                valueCallback.onValueCallback(TOTAL_ERROR_CODE, sMessage[1]);
                            }
                        } else {
                            valueCallback.onValueCallback(TOTAL_ERROR_CODE, sMessage[1]);
                        }
                    }

                    @Override
                    public void onApiFail(int errorCode, BaseModel failBaseModel) {
                        super.onApiFail(errorCode, failBaseModel);
                        errorCallback.onValueCallback(TASK_POST_VERIFY_SMS, failBaseModel.getMessage());
                    }
                });
            }
        }
    }

    public void callCheckAccountApi(String cellphone, final BaseContract.ValueCallback<String> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().checkAccount(cellphone, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                Boolean isSuccess = response.getSuccess();
                String Message = response.getMessage();
                String[] sMessage = Message.split("\\|");
                //sMessage[1] = L_0X006  <-無此會員資料，請先註冊帳號。  .  0X002  <-新增失敗
                //              1X000  <-操作成功1X002   .  1X002 <-新增成功
                if (isSuccess) {
                    valueCallback.onValueCallback(RESPONSE_CODE_LOGIN_VERIFIED_ERROR, sMessage[1]);
                } else {
                    errorCallback.onValueCallback(TOTAL_ERROR_CODE, sMessage[1]);
                }
            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);
                errorCallback.onValueCallback(failBaseModel.getCode(), failBaseModel.getMessage());
            }
        });
    }

    public void callForgetPsdSmsApi(String cellphone, final BaseContract.ValueCallback<String> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        basePresenter.startCallApi();
        int iRandom = new Random().nextInt(9999);
        String srandom;
        srandom = Integer.toString(iRandom);
        if (iRandom < 1000) {
            srandom = "0" + srandom;
        }
        context.getSharedPreferences("SmsCode", MODE_PRIVATE).edit()
                .putString("SmsCode", srandom)
                .apply();

        String functionName = "";
        if (cellphone.length() != 10) {

            functionName = "DoResetStaffPassword";

        } else {

            functionName = "DoResetPassword";
        }
        MemberRepository.getInstance().resendSms(cellphone, srandom, functionName, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                Boolean isSuccess = response.getSuccess();
                String[] aMessage = response.getMessage().split("\\|");
                //sMessage[1] = L_0X006  <-無此會員資料，請先註冊帳號  .  L_0X000  <-無此帳號
                if (isSuccess) {
                    valueCallback.onValueCallback(TASK_POST_FORGET_PASSWORD, aMessage[1]);
                } else {
                    errorCallback.onValueCallback(TASK_PATCH_UPDATE_PASSWORD, aMessage[1]);
                }
            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);
                errorCallback.onValueCallback(TASK_POST_FORGET_PASSWORD, failBaseModel.getMessage());
            }
        });
    }

    public void callVerifyForgetPsdApi(final String cellphone, final String password, String verifyCode, final BaseContract.ValueCallback<String> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        String SmsCode = context.getSharedPreferences("SmsCode", MODE_PRIVATE).getString("SmsCode", "");
        if (verifyCode.equals(MemberBean.Global_Code) || verifyCode.equals(SmsCode)) {
            basePresenter.startCallApi();
            MemberRepository.getInstance().verifyForgetPassword(cellphone, password, new ApiCallback<BaseModel>(basePresenter) {
                @Override
                public void onApiSuccess(BaseModel response) {
                    super.onApiSuccess(response);
                    Boolean isSuccess = response.getSuccess();
                    String[] aMessage = response.getMessage().split("\\|");
                    if (isSuccess) {
                        valueCallback.onValueCallback(TASK_POST_FORGET_PASSWORD, aMessage[1]);
                    } else {
                        errorCallback.onValueCallback(TASK_PATCH_UPDATE_PASSWORD, aMessage[1]);
                    }
                }

                @Override
                public void onApiFail(int errorCode, BaseModel failBaseModel) {
                    super.onApiFail(errorCode, failBaseModel);
                    errorCallback.onValueCallback(TASK_POST_FORGET_PASSWORD_VERIFY, failBaseModel.getMessage());
                }
            });
        }
    }

    public void callGetPropertyDataApi(final BaseContract.ValueCallback<List<Address>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getPropertyData(member_id, new ApiCallback<BaseModel<List<Address>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Address>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_PROPERTY_CODE, response.getItems());
            }
        });
    }

    public void callGetCarouselListApi(final BaseContract.ValueCallback<List<Carousel>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getCarouselList(new ApiCallback<BaseModel<List<Carousel>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Carousel>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_CAROUSE, response.getItems());
            }
        });
    }

    public void callGetMerchantListApi(final String keyword, final String business_sale_id, final BaseContract.ValueCallback<List<Merchant>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getMerchantList(keyword, business_sale_id, new ApiCallback<BaseModel<List<Merchant>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Merchant>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_MERCHANT, response.getItems());
            }
        });
    }

    public void callGetProductTypeListApi(final int store_id, final BaseContract.ValueCallback<List<ProductType>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getProductTypeList(store_id, new ApiCallback<BaseModel<List<ProductType>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<ProductType>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_PRODUCT_TYPE, response.getItems());
            }
        });
    }

    public void callGetLotListApi(final BaseContract.ValueCallback<List<DrawLots>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getLotList(member_id, new ApiCallback<BaseModel<List<DrawLots>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<DrawLots>> response) {
                super.onApiSuccess(response);
                if (response.getSuccess()) {
                    valueCallback.onValueCallback(TASK_POST_GET_LOT_LIST, response.getItems());
                } else {
                    valueCallback.onValueCallback(TOTAL_ERROR_CODE, response.getItems());
                }
            }
        });
    }

//    public void getShoppingCartCountApi(final BaseContract.ValueCallback<List<Object>> valueCallback) {
//        basePresenter.startCallApi();
//        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
//        MemberRepository.getInstance().getShoppingCartCount(member_id , new ApiCallback<BaseModel<List<Object>>>(basePresenter) {
//            @Override
//            public void onApiSuccess(BaseModel<List<Object>> response) {
//                super.onApiSuccess(response);
//                valueCallback.onValueCallback(TASK_POST_GET_SHOPPING_COUNT, response.getItems());
//            }
//        });
//    }

    public void callGetProductListApi(final String business_sale_id, final String sort, final String store_id, final String product_type_id, final String product_name, final BaseContract.ValueCallback<List<Product>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getProductList(business_sale_id, sort, store_id, product_type_id, product_name, new ApiCallback<BaseModel<List<Product>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Product>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_PRODUCT_LIST, response.getItems());
            }
        });
    }

    public void callGetProductDetailApi(final String business_sale_id, final String product_id, final BaseContract.ValueCallback<List<Product>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getProductDetail(business_sale_id, member_id, product_id, new ApiCallback<BaseModel<List<Product>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Product>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_PRODUCT_DETAIL, response.getItems());
            }
        });
    }

    public void callGetLotDetailApi(final int lot_id, final BaseContract.ValueCallback<DrawLots> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getLotDetail(lot_id, member_id, new ApiCallback<BaseModel<DrawLots>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<DrawLots> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_LOTDATA_DETAIL, response.getItems());
            }
        });
    }

    public void callJoinDrawLotsApi(final int lot_id, final String cif_code, final String prod_id, final String spec_name, final String receipt_no, final BaseContract.ValueCallback<BaseModel> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().joinDrawLots(lot_id, cif_code, prod_id, spec_name, member_id, receipt_no, new ApiCallback<BaseModel<Boolean>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                Log.e(TAG, response.toString());
                //valueCallback.onValueCallback(TASK_POST_JOIN_LOT, response.getSuccess());
                valueCallback.onValueCallback(TASK_POST_JOIN_LOT, response);
            }
        });
    }

    public void callGetBusinessCodeApi(final String sale_password, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getBusinessCode(sale_password, new ApiCallback<BaseModel<Map<String, String>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<Map<String, String>> response) {
                super.onApiSuccess(response);
                if (response.getSuccess()) {
                    Map<String, String> map = response.getItems();
                    if (map.containsKey("business_sale_id")) {
                        valueCallback.onValueCallback(TASK_POST_GET_BUSINESS_CODE, response.getItems().get("business_sale_id"));
                    } else {
                        valueCallback.onValueCallback(TASK_POST_GET_BUSINESS_CODE_FALSE, response.getMessage().split("\\|")[1]);
                    }
                } else {
                    valueCallback.onValueCallback(TASK_POST_GET_BUSINESS_CODE_FALSE, response.getMessage().split("\\|")[1]);
                }
            }
        });
    }


    public void callAddShoppingCattApi(final String business_sale_id, final String sale_type, final String product_id, final String spec_id, final String quantity, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().addShoppingCart(business_sale_id, sale_type, member_id, product_id, spec_id, quantity, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_ADD_SHOPPING_CART, response.getMessage());
            }
        });
    }

    public void callUpdatePasswordApi(String oldPassword, String newPassword, final BaseContract.ValueCallback<String> valueCallback, final BaseContract.ValueCallback<String> errorCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().updatePassword(member_id, oldPassword, newPassword, new ApiCallback<BaseModel<User>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<User> response) {
                super.onApiSuccess(response);
                Boolean isSuccess = response.getSuccess();
                String[] aMessage = response.getMessage().split("\\|");
                if (isSuccess) {
                    valueCallback.onValueCallback(TASK_PATCH_UPDATE_PASSWORD, aMessage[1]);
                } else {
                    errorCallback.onValueCallback(TASK_PATCH_UPDATE_PASSWORD, aMessage[1]);
                }
            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);
                errorCallback.onValueCallback(TASK_PATCH_UPDATE_PASSWORD, failBaseModel.getMessage());
            }
        });
    }

    public void callUpdateMemberInfoApi(String city_code, String area_code, String address, String email, String birth, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().updateMemberInfo(member_id, city_code, area_code, address, email, birth, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_PUT_UPDATE_MEMBER, response.getMessage().split("\\|")[1]);
            }
        });
    }


    public void callMemberPointHistoryApi(final String date, final BaseContract.ValueCallback<List<PointHistory>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getMemberPointHistory(date, member_id, new ApiCallback<BaseModel<List<PointHistory>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<PointHistory>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_MEMBER_POINT, response.getItems());
            }
        });
    }

    public void callMemberMessageListApi(final BaseContract.ValueCallback<List<MemberMessage>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getMemberMessage(member_id, new ApiCallback<BaseModel<List<MemberMessage>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<MemberMessage>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_MEMBER_MESSAGE_LIST, response.getItems());
            }
        });
    }

    public void callAboutDataApi(final BaseContract.ValueCallback<List<About>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getAboutData(new ApiCallback<BaseModel<List<About>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<About>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_ABOUT_DATA, response.getItems());
            }
        });
    }

    public void callFaqDataApi(final String faq_id, final BaseContract.ValueCallback<Faq> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getFaqData(faq_id, new ApiCallback<BaseModel<Faq>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<Faq> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_FAQ_DATA, response.getItems());
            }
        });
    }

    public void callUpdateMessageStatusApi(int messageId, String status, final BaseContract.ValueCallback<String> valueCallback) {
//        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().updateMessageStatus(member_id, messageId, status, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_UPDATE_MESSAGE_STATUS, response.getMessage());
            }
        });
    }

    public void callGetOrdersApi(final BaseContract.ValueCallback<List<Order>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getOrders(new ApiCallback<BaseModel<List<Order>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Order>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_MEMBER_ORDERS, response.getItems());
            }
        });
    }

//    public void callGetMailBadgeApi(final BaseContract.ValueCallback<String> valueCallback) {
////        basePresenter.startCallApi();
//        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
//        MemberRepository.getInstance().getMailBadge(member_id,new ApiCallback<BaseModel<List<Map<String, String>>>>(basePresenter) {
//            @Override
//            public void onApiSuccess(BaseModel<List<Map<String, String>>> response) {
//                super.onApiSuccess(response);
//                if (response.getSuccess()) {
//                    List<Map<String, String>> map = response.getItems();
//                    if (map.get(0).containsKey("unReadNum")) {
//                        valueCallback.onValueCallback(TASK_POST_GET_MAIL_BADGE, response.getItems().get(0).get("unReadNum"));
//                    } else {
//                        valueCallback.onValueCallback(TASK_POST_GET_MAIL_BADGE, "0");
//                    }
//                } else {
//                    valueCallback.onValueCallback(TASK_POST_GET_MAIL_BADGE, "0");
//                }
//            }
//        });
//    }

//    public void callGetMessageBadgeApi(final BaseContract.ValueCallback<String> valueCallback) {
////        basePresenter.startCallApi();
//        String member_id = context.getSharedPreferences("MemberID", Context.MODE_PRIVATE).getString("MemberID", "");
//        MemberRepository.getInstance().getMessageBadge(member_id,new ApiCallback<BaseModel<List<Map<String, String>>>>(basePresenter) {
//            @Override
//            public void onApiSuccess(BaseModel<List<Map<String, String>>> response) {
//                super.onApiSuccess(response);
////                if (response.getCode() == 200) {
//                if (response.getSuccess()) {
//                    List<Map<String, String>> map = response.getItems();
//                    if (map.get(0).containsKey("unReadNum")) {
//                        valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_BADGE, response.getItems().get(0).get("unReadNum"));
////                        valueCallback.onValueCallback(TASK_POST_GET_MAIL_BADGE, "");
//                    } else {
//                        valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_BADGE, "0");
//                    }
//                } else {
//                    valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_BADGE, "0");
//                }
//            }
//        });
//    }


    public void callGetBadgeNumberApi(final BaseContract.ValueCallback<String> valueCallback) {
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getBadgeNumber(member_id, new ApiCallback<BaseModel<List<Map<String, String>>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Map<String, String>>> response) {
                super.onApiSuccess(response);
                if (response.getSuccess()) {
                    List<Map<String, String>> map = response.getItems();
                    String messageUnreadNum = "0", pushUnreadNum = "0", cartTotalQuantity = "0";
                    if (map.get(0).containsKey("message_unread_num")) {
                        messageUnreadNum = response.getItems().get(0).get("message_unread_num");
                    }
                    if (map.get(0).containsKey("push_unread_num")) {
                        pushUnreadNum = response.getItems().get(0).get("push_unread_num");
                    }
                    if (map.get(0).containsKey("cart_total_quantity")) {
                        cartTotalQuantity = response.getItems().get(0).get("cart_total_quantity");

                    }
                    valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_BADGE, messageUnreadNum + "_" + pushUnreadNum + "_" + cartTotalQuantity);
                } else {
                    valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_BADGE, "0_0_0");
                }
            }
        });
    }

    public void callGetStoresApi(int area, List<Integer> typeList, final BaseContract.ValueCallback<List<Store>> valueCallback) {
        basePresenter.startCallApi();
        String sNum = "";
        for (Integer type : typeList) {
            if (sNum.equals("")) {
                sNum = Integer.toString(type);
            } else {
                sNum += "," + type;
            }
        }
        ApiRepository.getInstance().getStoreList(area, sNum, new ApiCallback<BaseModel<List<Store>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Store>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_LOCATION_LIST, response.getItems());
            }
        });

    }

    public void callReturnProductsApi(final ArrayList<OrderProduct> products, final String name, final String address,
                                      final String mobile, final String email, final int returnType, final BaseContract.ValueCallback<String> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().returnProducts(products, name, address, mobile, email, returnType, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_RETURN_ORDER_SUCCESS, response.getMessage());
            }

            @Override
            public void onApiFail(int errorCode, BaseModel failBaseModel) {
                super.onApiFail(errorCode, failBaseModel);
                valueCallback.onValueCallback(TASK_FAIL, failBaseModel.getMessage());
            }
        });
    }

    public void callGetMessageListApi(final BaseContract.ValueCallback<List<Message>> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().getMessageList(member_id, new ApiCallback<BaseModel<List<Message>>>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel<List<Message>> response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_GET_MESSAGE_LIST, response.getItems());
            }
        });
    }

    public void callSendMessageApi(String message, final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().addMemberContact(member_id, message, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_ADD_MEMBER_CONTACT, true);
            }
        });
    }

    public void callReadMessageApi(final BaseContract.ValueCallback<Boolean> valueCallback) {
        basePresenter.startCallApi();
        String member_id = context.getSharedPreferences("MemberID", MODE_PRIVATE).getString("MemberID", "");
        MemberRepository.getInstance().updateReadMessage(member_id, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onApiSuccess(BaseModel response) {
                super.onApiSuccess(response);
                valueCallback.onValueCallback(TASK_POST_READ_MESSAGE, true);
            }
        });
    }

    public void callGetCardPageListApi(String account_id, String DeviceOS, final BaseContract.ValueCallback<List<PayCard>> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getCardPageList(account_id, DeviceOS, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onResponse(Call call, Response response) {
                Gson gson = new Gson();
                BasePayCard<PayCardData> basePayCard = gson.fromJson(response.body().toString(), new TypeToken<BasePayCard<PayCardData>>() {
                }.getType());
                Log.d("ddd", response.body().toString());
                valueCallback.onValueCallback(TASK_GET_CARD_PAGE_LIST, basePayCard.Data.cards);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("ddd", t.getMessage());
            }
        });
    }

    public void callGetBarcode(String account_id, String deivceOS, String cardToken, final BaseContract.ValueCallback<PayCardBarcode> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().getBarcode(account_id, deivceOS, cardToken, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onResponse(Call call, Response response) {
                Gson gson = new Gson();
                BasePayCard<PayCardBarcode> basePayCard = gson.fromJson(response.body().toString(), new TypeToken<BasePayCard<PayCardBarcode>>() {
                }.getType());
                valueCallback.onValueCallback(TASK_GET_BARCODE, basePayCard.Data);
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.d("ddd", t.getMessage());
            }
        });
    }

    public void callDeleteBindingCard(String account_id, String deviceOS, String cardToken, final BaseContract.ValueCallback<PayCardBarcode> valueCallback) {
        basePresenter.startCallApi();
        MemberRepository.getInstance().deleteBindingCard(account_id, deviceOS, cardToken, new ApiCallback<BaseModel>(basePresenter) {
            @Override
            public void onResponse(Call call, Response response) {
                Gson gson = new Gson();
                BasePayCard<PayCardBarcode> basePayCard = gson.fromJson(response.body().toString(), new TypeToken<BasePayCard<PayCardBarcode>>() {
                }.getType());
                valueCallback.onValueCallback(TASK_DELETE_BINDING_CARD, basePayCard.Data);
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.d("ddd", t.getMessage());
            }
        });
    }

    public void saveAccountInfo(String account, String password) {
        SharedUtils.getInstance().saveAccountInfo(context, ApiUtils.encryption(account), ApiUtils.encryption(password));
    }

    public void saveUserID(String member_id) {
        SharedUtils.getInstance().saveUserID(context, member_id);
    }

    public void saveBusinessSaleID(String business_sale_id) {
        SharedUtils.getInstance().saveBusinessSaleID(context, business_sale_id);
    }

    public void saveShoppingCartCount(String Count) {
        SharedUtils.getInstance().saveShoppingCartCount(context, Count);
    }

    public void saveVerifyCode(String verifyCode) {
        SharedUtils.getInstance().saveVerifyCode(context, verifyCode);
    }

    public void savePayCode(String payCode) {
        SharedUtils.getInstance().savePayCode(context, payCode);
    }

    public void saveDefaultCardNumber(String cardNum) {
        SharedUtils.getInstance().saveDefaultCardNumber(context, cardNum);
    }

    public void saveUser(User user) {
        SharedUtils.getInstance().saveUser(context, user);
    }

    public User getUser() {
        return SharedUtils.getInstance().getUser(context);
    }

    public String getUserAccount() {
        String encodeAccount = SharedUtils.getInstance().getUserAccount(context);
        return ApiUtils.decryption(encodeAccount).trim();
    }

    public String getUserID() {
        String member_id = SharedUtils.getInstance().getUserID(context);
        return member_id;
    }

    public String getBusinessSaleID() {
        String business_sale_id = SharedUtils.getInstance().getBusinessSaleID(context);
        return business_sale_id;
    }

    public String getShoppingCartCount() {
        String shoppingcartcount = SharedUtils.getInstance().getShoppingCartCount(context);
        return shoppingcartcount;
    }

    public String getUserPassword() {
        String encodePassword = SharedUtils.getInstance().getUserPassword(context);
        return ApiUtils.decryption(encodePassword).trim();
    }

    public String getVerifyCode() {
        String verifyCode = SharedUtils.getInstance().getVerifyCode(context);
        return verifyCode;
    }

    public String getPayCode() {
        String payCode = SharedUtils.getInstance().getPayCode(context);
        return payCode;
    }

    public String getDefaultCardNumber() {
        String cardNumber = SharedUtils.getInstance().getDefaultCardNumber(context);
        return cardNumber;
    }
}
