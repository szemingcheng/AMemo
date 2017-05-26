package com.szemingcheng.amemo.ui.login.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.presenter.Imp.Login_RegisterActivityPresentImp;
import com.szemingcheng.amemo.view.RegisterByPhoneActivityView;

public class RegisterByPhoneActivity extends AppCompatActivity implements RegisterByPhoneActivityView,View.OnClickListener {
    EditText phone_edit, code_edit;
    Button code_bn, register;
    RegisterByPhoneActivity context;
    Login_RegisterActivityPresentImp registerActivityPresentImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_phone);
        init();
    }

    private void init() {
        phone_edit = (EditText) findViewById(R.id.phone_edit);
        code_edit = (EditText) findViewById(R.id.code_edit);
        code_bn = (Button) findViewById(R.id.code_bn);
        register = (Button) findViewById(R.id.registerbyPhone);
        code_bn.setOnClickListener(this);
        register.setOnClickListener(this);
        registerActivityPresentImp=new Login_RegisterActivityPresentImp(RegisterByPhoneActivity.this);
        registerActivityPresentImp.initSMSSDK("2");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.code_bn:
                if(registerActivityPresentImp.matchRegisterByPhoneActiviryRegex()){
                    registerActivityPresentImp.SMSSDKStart("2");
                }
                break;
            case R.id.registerbyPhone:
                if(registerActivityPresentImp.matchRegisterByPhoneActiviryRegex()) {
                    registerActivityPresentImp.SMSSDKSubmitVerificationCode("2");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public String getPhone() {
        return phone_edit.getText().toString();
    }

    @Override
    public String getCode() {
        return code_edit.getText().toString();
    }

    @Override
    public void CodeReminder(String reminder) {
         code_bn.setText(reminder);
    }

    @Override
    public void CodeClickable(boolean b) {
        code_bn.setClickable(b);
    }

    @Override
    public void codeError() {
        Toast.makeText(getApplicationContext(), "验证码错误",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void codeSubmited() {
        Toast.makeText(getApplicationContext(), "验证码已经发送",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void codeSuccess() {
        Toast.makeText(getApplicationContext(), "提交验证码成功",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void phoneFocus() {
        Toast.makeText(this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();
        phone_edit.setText("");
        phone_edit.requestFocus();
    }

    @Override
    public void Clare() {
        phone_edit.setText("");
        code_edit.setText("");
        phone_edit.requestFocus();
    }

    @Override
    public RegisterByPhoneActivity getContext() {
        return context=this;
    }
}
