package com.lafresh.smile2.Constant;

public class ApiConstant {
    public static final String ACTION_REGISTER = "register";
    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_RESEND_SMS = "resendSMS";
    public static final String ACTION_VERIFY_SMS = "verifySMS";
    public static final String ACTION_FORGET_PASSWORD = "forgetPassword";
    public static final String ACTION_FORGET_PASSWORD_VERIFY = "verifyForgetPassword";
    public static final String ACTION_REFRESH_MEMBER_INFO = "refreshMemberInfo";
    public static final String ACTION_UPDATE_PASSWORD = "updatePassword";
    public static final String ACTION_LOGOUT = "logout";
    public static final String ACTION_UPDATE_MEMBER = "updateMember";
    public static final String ACTION_MEMBER_POINT = "getMemberPoint";
    public static final String ACTION_MEMBER_MESSAGE = "getNotificationList";
    public static final String ACTION_GET_READ_MESSAGE_LIST = "getReadNotification";
    public static final String ACTION_UPDATE_MESSAGE_STATUS = "updNotificationStatus";
    public static final String ACTION_GET_ORDERS = "getMemberOrder";
    public static final String ACTION_GET_MAIL_BADGE = "getFrequencyInfo";
    public static final String ACTION_RETURN_ORDER = "returnOrder";
    public static final String ACTION_MEMBER_CONTACT = "getMemberContact";
    public static final String ACTION_ADD_MEMBER_CONTACT = "addMemberContact";

    public static final int TASK_FAIL = -1;
    public static final int TASK_POST_REGISTER = 1;
    public static final int TASK_POST_LOGIN = 2;
    public static final int TASK_POST_RESEND_SMS = 3;
    public static final int TASK_POST_VERIFY_SMS = 4;
    public static final int TASK_POST_FORGET_PASSWORD = 5;
    public static final int TASK_POST_FORGET_PASSWORD_VERIFY = 6;
    public static final int TASK_POST_GET_MEMBER_INFO = 7;
    public static final int TASK_PATCH_UPDATE_PASSWORD = 8;
    public static final int TASK_POST_LOGOUT = 9;
    public static final int TASK_PUT_UPDATE_MEMBER = 10;
    public static final int TASK_POST_GET_MEMBER_POINT = 11;
    public static final int TASK_POST_GET_MEMBER_MESSAGE_LIST = 12;
    public static final int TASK_POST_GET_READ_MESSAGE_LIST = 13;
    public static final int TASK_POST_UPDATE_MESSAGE_STATUS = 14;
    public static final int TASK_POST_GET_MEMBER_ORDERS = 15;
    public static final int TASK_POST_GET_MAIL_BADGE = 16;
    public static final int TASK_POST_GET_STORE_LIST = 17;
    public static final int TASK_POST_RETURN_ORDER_SUCCESS = 18;
    public static final int TASK_POST_GET_MESSAGE_LIST = 19;
    public static final int TASK_POST_ADD_MEMBER_CONTACT = 20;
    public static final int TASK_POST_GET_PROPERTY_CODE = 21;
    public static final int TASK_POST_GET_CAROUSE = 22;
    public static final int TASK_POST_GET_MERCHANT = 23;
    public static final int TASK_POST_GET_PRODUCT_TYPE = 24;
    public static final int TASK_POST_GET_PRODUCT_LIST = 25;
    public static final int TASK_POST_GET_PRODUCT_DETAIL = 26;
    public static final int TASK_POST_ADD_SHOPPING_CART = 27;
    public static final int TASK_POST_GET_SHOPPING_COUNT = 28;
    public static final int TASK_POST_GET_LOCATION_LIST = 29;
    public static final int TASK_POST_GET_LOTDATA_DETAIL = 30;
    public static final int TASK_POST_GET_ABOUT_DATA = 31;
    public static final int TASK_POST_GET_FAQ_DATA = 32;
    public static final int TASK_POST_GET_MESSAGE_BADGE = 33;
    public static final int TASK_POST_READ_MESSAGE = 34;
    public static final int TASK_POST_GET_BUSINESS_CODE = 35;
    public static final int TASK_POST_GET_BUSINESS_CODE_FALSE = 36;
    public static final int TASK_POST_GET_LOT_LIST = 37;
    public static final int TASK_POST_JOIN_LOT = 38;
    public static final int TASK_GET_CARD_PAGE_LIST = 39;
    public static final int TASK_GET_BARCODE = 40;
    public static final int TASK_DELETE_BINDING_CARD = 41;



    public static final int RESPONSE_CODE_LOGIN_VERIFIED_ERROR = 209;
    public static final int TOTAL_ERROR_CODE = 500;
}
