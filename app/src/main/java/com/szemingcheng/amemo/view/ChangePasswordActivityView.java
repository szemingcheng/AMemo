package com.szemingcheng.amemo.view;

import com.szemingcheng.amemo.ui.login.activity.ChangePasswordActivity;

/**
 * Created by Jaygren on 2017/5/25.
 */

public interface ChangePasswordActivityView {

    String getPassword1();

    String getpassword2();

    String getCode();

    void PasswordMatchRegex();

    String getPhone();

    void codeError();

    void codeSubmited();

    void codeSuccess();

    void Password1RequestFocus();

    void Password2RequestFocus();

    void Clare();

    void ChangedSuccess();

    void CodeReminder(String reminder);

    void CodeClickable(boolean b);

    ChangePasswordActivity getContext();
}
