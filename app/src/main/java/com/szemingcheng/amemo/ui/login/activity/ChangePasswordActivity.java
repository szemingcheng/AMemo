package com.szemingcheng.amemo.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.presenter.Imp.Login_RegisterActivityPresentImp;
import com.szemingcheng.amemo.view.ChangePasswordActivityView;

import static com.szemingcheng.amemo.App.activityList;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordActivityView, View.OnClickListener {
    EditText change_password1, change_password2, code_edit;
    Button getCode, change_password;
    String password1, password2, phone,code;
    ChangePasswordActivity context;
    Login_RegisterActivityPresentImp changePasswordActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //activityList列表
        activityList.add(ChangePasswordActivity.this);
        init();
    }

    private void init() {
        //获取phone
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");

        change_password1 = (EditText) findViewById(R.id.change_password1);
        change_password2 = (EditText) findViewById(R.id.change_password2);
        code_edit = (EditText) findViewById(R.id.code_edit);
        getCode = (Button) findViewById(R.id.getCode);
        change_password = (Button) findViewById(R.id.change_password);
        getCode.setOnClickListener(this);
        change_password.setOnClickListener(this);
        changePasswordActivity=new Login_RegisterActivityPresentImp(ChangePasswordActivity.this);
        changePasswordActivity.initSMSSDK("1");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_password:
                changePasswordActivity.SMSSDKSubmitVerificationCode("1");
                break;
            case R.id.getCode:
                changePasswordActivity.SMSSDKStart("1");
                break;
            default:
                break;
        }
    }

    @Override
    public String getPassword1() {
        password1 = change_password1.getText().toString();
        return password1;
    }

    @Override
    public String getpassword2() {
        password2 = change_password2.getText().toString();
        return password2;
    }

    @Override
    public String getCode() {
        code=code_edit.getText().toString();
        return code;
    }

    @Override
    public void PasswordMatchRegex() {
        Toast.makeText(ChangePasswordActivity.this, "密码格式不符(6-15个任意单词字符)",
                Toast.LENGTH_SHORT).show();
        change_password1.setText("");
        change_password2.setText("");
        change_password1.requestFocus();
    }

    @Override
    public String getPhone() {
        return phone;
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
    public void Password1RequestFocus() {
        Toast.makeText(ChangePasswordActivity.this, "密码不能为空，请重新输入!",
                Toast.LENGTH_SHORT).show();
        change_password1.setText("");
        change_password1.requestFocus();
    }

    @Override
    public void Password2RequestFocus() {
        Toast.makeText(ChangePasswordActivity.this, "密码不能为空，请重新输入!",
                Toast.LENGTH_SHORT).show();
        change_password1.setText("");
        change_password2.requestFocus();
    }

    @Override
    public void Clare() {
        change_password1.setText("");
        change_password2.setText("");
        change_password1.requestFocus();
    }

    @Override
    public void ChangedSuccess() {
        Toast.makeText(ChangePasswordActivity.this, "修改成功",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void CodeReminder(String reminder) {
        getCode.setText(reminder);
    }

    @Override
    public void CodeClickable(boolean b) {
        getCode.setClickable(b);
    }

    @Override
    public ChangePasswordActivity getContext() {
        return  context=this;
    }
}
