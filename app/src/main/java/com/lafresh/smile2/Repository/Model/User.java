package com.lafresh.smile2.Repository.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class User {
    public static final String TAG = User.class.getSimpleName();
    public static final String MEMBER_GROUP_VIP = "vip";
    public static final String MEMBER_GROUP_NORMAL = "normal";

    @SerializedName("ID")
    private int id;
    @SerializedName("member_id")
    private int member_id;
    private String account;
    private String name;
    private String birthday;
    private String mobile;
    private String group;
    private String email;

    private String gender;
    private String createDateTime;
    private String bonus_points;
    private String prepaid_amount;

    @SerializedName("staff_quota")
    private String staffQuota;

    @SerializedName("departure_date")
    private String departureDate;
    @SerializedName("staff_mobile")
    private String staffMobile;

    @SerializedName("pos_membercard_code")
    private String pos_membercard_code;
    @SerializedName("membership_src")
    private String membershipSrc;
    @SerializedName("membership_card_src")
    private String membershipCardSrc;

    @SerializedName("city_code")
    private String citycode;
    @SerializedName("area_code")
    private String areacode;
    @SerializedName("address")
    private String address;
    @SerializedName("bonus_expired_soon")
    private String bonusexpiredsoon;

    @SerializedName("verify_code")
    private String verify_code;

    private List<String> topic;

    public int getMember() {
        return member_id;
    }

    public void setMember(int memberid) {
        this.member_id = memberid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoint() {
        return bonus_points;
    }

    public void setPoint(String point) {
        this.bonus_points = point;
    }

    public String getQuota() {
        return staffQuota;
    }

    public void setQuota(String quota) {
        this.staffQuota = quota;
    }

    public String getDeparture() {
        return departureDate;
    }

    public void setDeparture(String departure) {
        this.departureDate = departure;
    }

    public String getStaffMobile() {
        return staffMobile;
    }

    public void setStaffMobile(String Mobile) {
        this.staffMobile = Mobile;
    }

    public String getprePaidAmount() {
        return prepaid_amount;
    }

    public void setPrepaidAmount(String prepaid_amount) {
        this.prepaid_amount = prepaid_amount;
    }

    public String getMembershipCode() {
        return pos_membercard_code;
    }

    public void setMembershipCode(String membershipCode) {
        this.pos_membercard_code = membershipCode;
    }

    public String getMembershipSrc() {
        return membershipSrc;
    }

    public void setMembershipSrc(String membershipSrc) {
        this.membershipSrc = membershipSrc;
    }

    public String getMembershipCardSrc() {
        return membershipCardSrc;
    }

    public void setMembershipCardSrc(String membershipCardSrc) {
        this.membershipCardSrc = membershipCardSrc;
    }

    public String getBonusexpiredsoon() {
        return bonusexpiredsoon;
    }

    public void setBonusexpiredsoon(String bonusexpiredsoon) {
        this.bonusexpiredsoon = bonusexpiredsoon;
    }

    public List<String> getTopic() {
        return topic;
    }

    public void setTopic(List<String> topic) {
        this.topic = topic;
    }

//    public boolean isVipUser() {
//        if (group.equals(MEMBER_GROUP_NORMAL)) {
//            return false;
//        }
//        if (group.equals(MEMBER_GROUP_VIP)) {
//            return true;
//        }
//
//        return false;
//    }


    public String getVerifyCode() {
        return verify_code;
    }

    public void setVerifyCode(String verify_code) {
        this.verify_code = verify_code;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", member_id);
            jsonObject.put("member_id", member_id);
            jsonObject.put("account", mobile);
            jsonObject.put("name", name);
            jsonObject.put("birthday", birthday);
            jsonObject.put("mobile", mobile);
            jsonObject.put("gender", gender);
            jsonObject.put("email", email);
            jsonObject.put("staff_quota", staffQuota);
            jsonObject.put("departure_date", departureDate);
            jsonObject.put("staff_mobile", staffMobile);

            jsonObject.put("city_code", citycode);
            jsonObject.put("area_code", areacode);
            jsonObject.put("address", address);

            jsonObject.put("prepaid_amount", prepaid_amount);
//            jsonObject.put("group", group);
//            jsonObject.put("createDateTime", createDateTime);
            jsonObject.put("point", bonus_points);
            jsonObject.put("membership_code", pos_membercard_code);
            jsonObject.put("bonus_expired_soon", bonusexpiredsoon);
//            jsonObject.put("membership_src", membershipSrc);
//            jsonObject.put("membership_card_src", membershipCardSrc);
//            jsonObject.put("topic", topic);
            jsonObject.put("verify_code", verify_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
