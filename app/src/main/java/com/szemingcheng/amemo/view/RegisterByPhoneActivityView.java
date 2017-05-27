package com.szemingcheng.amemo.view;

import com.szemingcheng.amemo.ui.login.activity.RegisterByPhoneActivity;

/**
 * Created by Jaygren on 2017/5/26.
 */

public interface RegisterByPhoneActivityView {
    String getPhone();

    String getCode();

    void CodeReminder(String reminder);

    void CodeClickable(boolean b);

    void codeError();

    void codeSubmited();

    void codeSuccess();

    void phoneFocus();

    void Clare();

    RegisterByPhoneActivity getContext();
}
