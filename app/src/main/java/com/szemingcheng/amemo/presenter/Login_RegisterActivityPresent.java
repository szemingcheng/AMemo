package com.szemingcheng.amemo.presenter;

/**
 * Created by Jaygren on 2017/5/24.
 */

public interface Login_RegisterActivityPresent {
    boolean matchLoginActivityReges(String userid, String password);

    boolean matchChangePasswordActivityReges(String password1, String password2);

    boolean matchRegisterActivityReges(String userid, String password1, String password2, String phone);

    boolean matchRegisterByPhoneActiviryRegex();

    void getJson(String step);

    void parseJSON(String jsonDate);

    void LoadUserInfo();

    void CheckUserid();

    void initSMSSDK(String step);

    void SMSSDKSubmitVerificationCode(String step);

    void SMSSDKStart(String step);
}
