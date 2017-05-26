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
import com.szemingcheng.amemo.view.RegisterActivityView;

public class RegisterActivity extends AppCompatActivity implements RegisterActivityView, View.OnClickListener {
    EditText register_username, register_password1, register_password2, register_phone;
    Button register, register_login;
    String username, password1, password2, phone;
    Login_RegisterActivityPresentImp registerActivityPresentImp;
    RegisterActivity context;
    String REGISTER="2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        register_username = (EditText) findViewById(R.id.register_username);
        register_password1 = (EditText) findViewById(R.id.register_password1);
        register_password2 = (EditText) findViewById(R.id.register_password2);
        register_phone = (EditText) findViewById(R.id.register_phone);
        register = (Button) findViewById(R.id.register);
        register_login = (Button) findViewById(R.id.register_login);
        register.setOnClickListener(this);
        register_login.setOnClickListener(this);
        registerActivityPresentImp=new Login_RegisterActivityPresentImp(RegisterActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                if(registerActivityPresentImp.matchRegisterActivityReges(getUserid(),getPassword1(),getPassword2(),getPhone())){
                    registerActivityPresentImp.getJson(REGISTER);
                }
                break;
            case R.id.register_login:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public String getUserid() {
        username=register_username.getText().toString();
        return username;
    }

    @Override
    public String getPassword1() {
        password1=register_password1.getText().toString();
        return  password1;
    }

    @Override
    public String getPassword2() {
        password2=register_password2.getText().toString();
        return password2;
    }

    @Override
    public String getPhone() {
        phone=register_phone.getText().toString();
        return phone;
    }

    @Override
    public void UseridFocus() {
        Toast.makeText(RegisterActivity.this, "用户名不能为空，请重新输入!",
                Toast.LENGTH_SHORT).show();
        register_username.setText("");
        register_username.requestFocus();
    }

    @Override
    public void Password1Focus() {
        Toast.makeText(RegisterActivity.this, "密码不能为空，请重新输入!",
                Toast.LENGTH_SHORT).show();
        register_password1.setText("");
        register_password1.requestFocus();
    }

    @Override
    public void Password2Focus() {
        Toast.makeText(RegisterActivity.this, "密码不能为空，请重新输入!",
                Toast.LENGTH_SHORT).show();
        register_password2.setText("");
        register_password2.requestFocus();
    }

    @Override
    public void PhoneFocus() {
        Toast.makeText(RegisterActivity.this, "手机号码不能为空",
                Toast.LENGTH_SHORT).show();
        register_phone.setText("");
        register_phone.requestFocus();
        return;
    }

    @Override
    public void UseridMatchRegex() {
        Toast.makeText(RegisterActivity.this, "用户名格式不正确!",
                Toast.LENGTH_SHORT).show();
        register_username.setText("");
        register_username.requestFocus();
    }

    @Override
    public void PasswdMatchRegex() {
        Toast.makeText(RegisterActivity.this, "密码格式不符(6-15个任意单词字符)",
                Toast.LENGTH_SHORT).show();
        register_password1.setText("");
        register_password2.setText("");
        register_password1.requestFocus();
    }

    @Override
    public void PhoneMatchRegex() {
        Toast.makeText(RegisterActivity.this, "手机格式不正确!",
                Toast.LENGTH_SHORT).show();
        register_phone.setText("");
        register_phone.requestFocus();
    }

    @Override
    public void UserInfoClare() {
        Toast.makeText(RegisterActivity.this, "该用户已存在",
                Toast.LENGTH_SHORT).show();
        register_username.setText("");
        register_phone.setText("");
        register_username.requestFocus();
    }

    @Override
    public void PasswordCompared() {
        Toast.makeText(RegisterActivity.this, "两次密码不一致",
                Toast.LENGTH_SHORT).show();
        register_password1.setText("");
        register_password2.setText("");
        register_password1.requestFocus();
    }

    @Override
    public RegisterActivity getContext() {
        return context=this;
    }
}
