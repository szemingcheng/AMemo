package com.szemingcheng.amemo.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.presenter.Imp.Login_RegisterActivityPresentImp;
import com.szemingcheng.amemo.view.LoginActivityView;

import static com.szemingcheng.amemo.App.activityList;

public class LoginActivity extends AppCompatActivity implements LoginActivityView, View.OnClickListener {
    EditText login_username, login_password;
<<<<<<< HEAD
    TextView forget_password,login_register;
    Button login, login_register_byphone;
=======
    TextView forget_password;
    Button login, login_register, login_register_byphone;
>>>>>>> 2703da20c11591e192b590ca5e928911cca45848
    String userid, password;
    LoginActivity context;
    Login_RegisterActivityPresentImp loginActivityPresentImp;
    static String LONGSTEP="1";
    static String FORGRTPASSWORDSTEP="3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //activityList列表
        activityList.add(LoginActivity.this);
        init();
    }

    private void init() {
        //控件
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        forget_password = (TextView) findViewById(R.id.forget_password);
        login = (Button) findViewById(R.id.login);
<<<<<<< HEAD
        login_register = (TextView) findViewById(R.id.login_register);
=======
        login_register = (Button) findViewById(R.id.login_register);
>>>>>>> 2703da20c11591e192b590ca5e928911cca45848
        login_register_byphone = (Button) findViewById(R.id.login_register_byphone);
        login.setOnClickListener(this);
        login_register.setOnClickListener(this);
        login_register_byphone.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        loginActivityPresentImp=new Login_RegisterActivityPresentImp(LoginActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                if(loginActivityPresentImp.matchLoginActivityReges(login_username.getText().toString(),login_password.getText().toString())){
                    loginActivityPresentImp.getJson(LoginActivity.LONGSTEP);
                }
                break;
            case R.id.login_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.login_register_byphone:
                startActivity(new Intent(LoginActivity.this, RegisterByPhoneActivity.class));
                break;
            case R.id.forget_password:
                loginActivityPresentImp.CheckUserid();
                loginActivityPresentImp.getJson(LoginActivity.FORGRTPASSWORDSTEP);
                break;
            default:
                break;
        }
    }

    @Override
    public String getUserid() {
        userid = login_username.getText().toString();
        return userid;
    }

    @Override
    public String getPassword() {
        password = login_password.getText().toString();
        return password;
    }

    @Override
    public void setUserInfo(String userid,String password) {
        login_username.setText(userid);
        login_password.setText(password);
    }

    @Override
    public void clear() {
        login_username.setText("");
        login_password.setText("");
        login_username.requestFocus();
        Toast.makeText(LoginActivity.this, "登录失败!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void UseridRequestFocus() {
        Toast.makeText(LoginActivity.this, "用户名不能为空，请重新输入!",
                Toast.LENGTH_SHORT).show();
        login_username.setText("");
        login_username.requestFocus();
    }

    @Override
    public void PasswordRequestFocus() {
        Toast.makeText(LoginActivity.this, "密码不能为空，请重新输入!",
                Toast.LENGTH_SHORT).show();
        login_password.setText("");
        login_password.requestFocus();
    }

    @Override
    public void UseridUnMatchRegex() {
        Toast.makeText(LoginActivity.this, "用户名格式不正确!",
                Toast.LENGTH_SHORT).show();
        login_username.setText("");
        login_password.setText("");
        login_username.requestFocus();
    }

    @Override
    public void PasswordUnMatchRegex() {
        Toast.makeText(LoginActivity.this, "密码格式不符(6-15个任意单词字符)",
                Toast.LENGTH_SHORT).show();
        login_password.setText("");
        login_password.requestFocus();
    }

    @Override
    public void CheckUserid() {
            Toast.makeText(LoginActivity.this, "用户名不能为空!",
                    Toast.LENGTH_SHORT).show();
            login_username.requestFocus();
            return;
    }

    @Override
    public LoginActivity getContext() {
        return context=this;
    }


}
