package com.szemingcheng.amemo.presenter.Imp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.manager.DaoManger;
import com.szemingcheng.amemo.model.Imp.UserModelImp;
import com.szemingcheng.amemo.model.UserModel;
import com.szemingcheng.amemo.presenter.Login_RegisterActivityPresent;
import com.szemingcheng.amemo.ui.login.activity.ChangePasswordActivity;
import com.szemingcheng.amemo.ui.login.activity.LoginActivity;
import com.szemingcheng.amemo.ui.unlogin.activity.HomeActivity;
import com.szemingcheng.amemo.utils.HttpUtils;
import com.szemingcheng.amemo.utils.PreferencesUtils;
import com.szemingcheng.amemo.utils.RegexUtils;
import com.szemingcheng.amemo.view.ChangePasswordActivityView;
import com.szemingcheng.amemo.view.LoginActivityView;
import com.szemingcheng.amemo.view.RegisterActivityView;
import com.szemingcheng.amemo.view.RegisterByPhoneActivityView;

import java.io.IOException;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Jaygren on 2017/5/24.
 */

public class Login_RegisterActivityPresentImp implements Login_RegisterActivityPresent {
    LoginActivityView loginActivityView;
    ChangePasswordActivityView changePasswordActivityView;
    RegisterActivityView registerActivityView;
    RegisterByPhoneActivityView registerByPhoneActivityView;
    UserModelImp userModel;
    com.szemingcheng.amemo.entity.Response Response;
    RequestBody requestBody;
    User user;
    static String CAHNGPASSWORD = "4";
    static String REGISTERBYPHONE = "5";
    int i = 60; // 设置短信验证提示时间为60s

    Handler handler = new Handler();

    public Login_RegisterActivityPresentImp(LoginActivityView loginActivityView) {
        this.loginActivityView = loginActivityView;
        userModel = new UserModelImp();
        LoadUserInfo();
    }

    public Login_RegisterActivityPresentImp(ChangePasswordActivityView changePasswordActivityView) {
        this.changePasswordActivityView = changePasswordActivityView;
        userModel = new UserModelImp();
    }

    public Login_RegisterActivityPresentImp(RegisterActivityView registerActivityView) {
        this.registerActivityView = registerActivityView;
        userModel = new UserModelImp();
    }

    public  Login_RegisterActivityPresentImp(RegisterByPhoneActivityView registerByPhoneActivityView){
        this.registerByPhoneActivityView=registerByPhoneActivityView;
        userModel=new UserModelImp();
    }
    @Override
    public boolean matchLoginActivityReges(String userid, String password) {
        if (userid.equals("")) {
            loginActivityView.UseridRequestFocus();
            return false;
        } else {
            if (password.equals("")) {
                loginActivityView.PasswordRequestFocus();
                return false;
            }
        }
        if (RegexUtils.UseridRegex(userid) == 0) {
            loginActivityView.UseridUnMatchRegex();
            return false;
        } else {
            if (RegexUtils.PasswdRegex(password) == 0) {
                loginActivityView.PasswordUnMatchRegex();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean matchChangePasswordActivityReges(String password1, String password2) {
        if (password1.equals("")) {
            changePasswordActivityView.Password1RequestFocus();
            return false;
        }
        if (password2.equals("")) {
            changePasswordActivityView.Password2RequestFocus();
            return false;
        }
        if (!password1.equals(password2)) {
            changePasswordActivityView.Clare();
            return false;
        }
        if (RegexUtils.PasswdRegex(password1) == 0) {
            changePasswordActivityView.PasswordMatchRegex();
            return false;
        }
        return true;
    }

    @Override
    public boolean matchRegisterActivityReges(String userid, String password1, String password2, String phone) {
        if (userid.equals("")) {
            registerActivityView.UseridFocus();
            return false;
        }
        if (password1.equals("")) {
            registerActivityView.Password1Focus();
            return false;
        }
        if (password2.equals("")) {
            registerActivityView.Password2Focus();
            return false;
        }
        if (!password1.equals(password2)) {
            registerActivityView.PasswordCompared();
            return false;
        }
        if (phone.equals("")) {
            registerActivityView.PhoneFocus();
            return false;
        }
        if (RegexUtils.UseridRegex(userid) == 0) {
            registerActivityView.UseridMatchRegex();
            return false;
        }
        if (RegexUtils.PasswdRegex(password1) == 0) {
            registerActivityView.PasswdMatchRegex();
            return false;
        }
        if (RegexUtils.PhoneRegex(phone) == 0) {
            registerActivityView.PhoneMatchRegex();
            return false;
        }
        return true;
    }

    @Override
    public boolean matchRegisterByPhoneActiviryRegex() {
        if (RegexUtils.PhoneRegex(registerByPhoneActivityView.getPhone()) == 0) {
            registerByPhoneActivityView.phoneFocus();
            return false;
        }
        return true;
    }

    @Override
    public void getJson(String step) {
        switch (step) {
            case "1":
            case "3":
                requestBody = new FormBody.Builder()
                        .add("userid", loginActivityView.getUserid())
                        .add("password", loginActivityView.getPassword())
                        .add("step", step)
                        .build();
                break;
            case "2":
                requestBody = new FormBody.Builder()
                        .add("userid", registerActivityView.getUserid())
                        .add("password", registerActivityView.getPassword1())
                        .add("phone", registerActivityView.getPhone())
                        .add("step", step)
                        .build();
                break;
            case "4":
                requestBody = new FormBody.Builder()
                        .add("password", changePasswordActivityView.getPassword1())
                        .add("phone", changePasswordActivityView.getPhone())
                        .add("step", step)
                        .build();
                break;
            case  "5":
                requestBody = new FormBody.Builder()
                        .add("phone",registerByPhoneActivityView.getPhone())
                        .add("step", step)
                        .build();
                break;
        }
        HttpUtils.sendRequest(requestBody, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseData = response.body().string();
                System.out.println("responseData" + responseData);
                parseJSON(responseData);
                System.out.println("ResponseCode:" + Response.getResCode());
                switch (Response.getResCode()) {
                    case "1":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                userModel.user_check(Response.getUserid(), new UserModel.UseridexistedListener() {
                                    @Override
                                    public void Useidexisted(String userid) {
                                        PreferencesUtils.putUserid(App.getAppcontext(), PreferencesUtils.USERID, Response.getUserid(),
                                                PreferencesUtils.PHONE, Response.getPhone(), PreferencesUtils.ONSCREENNAME, Response.getOnscreen_name(),
                                                PreferencesUtils.PASSWORD, loginActivityView.getPassword());
                                        //表示已登录
                                        PreferencesUtils.logined(App.getAppcontext(), PreferencesUtils.LOGINED, true);
                                    }

                                    @Override
                                    public void UseridUnexited(String userid) {
                                        PreferencesUtils.putUserid(App.getAppcontext(), PreferencesUtils.USERID, Response.getUserid(),
                                                PreferencesUtils.PHONE, Response.getPhone(), PreferencesUtils.ONSCREENNAME, Response.getOnscreen_name(),
                                                PreferencesUtils.PASSWORD, loginActivityView.getPassword());
                                        //表示为新用户
                                        PreferencesUtils.putBoolean(App.getAppcontext(), PreferencesUtils.FIRST_START, true);
                                        //表示已登录
                                        PreferencesUtils.logined(App.getAppcontext(), PreferencesUtils.LOGINED, true);
                                        DaoManger.getDaoSession(Response.getUserid());
                                    }
                                });
                                loginActivityView.getContext().startActivity(new Intent(loginActivityView.getContext(), HomeActivity.class));
                                loginActivityView.getContext().finish();
                            }
                        });
                        break;
                    case "2":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(loginActivityView.getContext(), ChangePasswordActivity.class);
                                intent.putExtra("phone", Response.getPhone());
                                loginActivityView.getContext().startActivity(intent);
                            }
                        });
                        break;
                    case "3":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                PreferencesUtils.putUserid(App.getAppcontext(), PreferencesUtils.USERID, registerActivityView.getUserid(),
                                        PreferencesUtils.PHONE, registerActivityView.getPhone(), PreferencesUtils.ONSCREENNAME, registerActivityView.getUserid(),
                                        PreferencesUtils.PASSWORD, registerActivityView.getPassword1());
                                //表示为新用户
                                PreferencesUtils.putBoolean(App.getAppcontext(), PreferencesUtils.FIRST_START, true);
                                //表示已登录
                                PreferencesUtils.logined(App.getAppcontext(), PreferencesUtils.LOGINED, true);
                                DaoManger.getDaoSession(Response.getUserid());
                                registerActivityView.getContext().startActivity((new Intent(registerActivityView.getContext(), HomeActivity.class)));
                            }
                        });
                        break;
                    case "4":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                PreferencesUtils.putUserid(App.getAppcontext(), PreferencesUtils.USERID, Response.getUserid(),
                                        PreferencesUtils.PHONE, Response.getPhone(), PreferencesUtils.ONSCREENNAME, Response.getOnscreen_name(),
                                        PreferencesUtils.PASSWORD, changePasswordActivityView.getPassword1());
                                changePasswordActivityView.ChangedSuccess();
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                changePasswordActivityView.getContext().startActivity(new Intent(changePasswordActivityView.getContext(), LoginActivity.class));
                                changePasswordActivityView.getContext().finish();
                            }
                        });
                        break;
                    case  "5":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                userModel.user_check(Response.getUserid(), new UserModel.UseridexistedListener() {
                                    @Override
                                    public void Useidexisted(String userid) {
                                        PreferencesUtils.putUserid(App.getAppcontext(), PreferencesUtils.USERID, Response.getUserid(),
                                                PreferencesUtils.PHONE, Response.getPhone(), PreferencesUtils.ONSCREENNAME, Response.getOnscreen_name(),
                                                PreferencesUtils.PASSWORD,"123456");
                                        //表示已登录
                                        PreferencesUtils.logined(App.getAppcontext(), PreferencesUtils.LOGINED, true);
                                    }

                                    @Override
                                    public void UseridUnexited(String userid) {
                                        PreferencesUtils.putUserid(App.getAppcontext(), PreferencesUtils.USERID, Response.getUserid(),
                                                PreferencesUtils.PHONE, Response.getPhone(), PreferencesUtils.ONSCREENNAME, Response.getOnscreen_name(),
                                                PreferencesUtils.PASSWORD,"123456");
                                        //表示为新用户
                                        PreferencesUtils.putBoolean(App.getAppcontext(), PreferencesUtils.FIRST_START, true);
                                        //表示已登录
                                        PreferencesUtils.logined(App.getAppcontext(), PreferencesUtils.LOGINED, true);
                                        DaoManger.getDaoSession(Response.getUserid());
                                    }
                                });
                                registerByPhoneActivityView.getContext().startActivity(new Intent(registerByPhoneActivityView.getContext(), HomeActivity.class));
                                registerByPhoneActivityView.getContext().finish();
                            }
                        });
                        break;
                    case "0":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                loginActivityView.clear();
                            }
                        });
                        break;
                    case "-2":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                registerActivityView.UserInfoClare();
                            }
                        });
                        break;
                    case "-5":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                userModel.user_check(Response.getUserid(), new UserModel.UseridexistedListener() {
                                    @Override
                                    public void Useidexisted(String userid) {
                                        PreferencesUtils.putUserid(App.getAppcontext(), PreferencesUtils.USERID, Response.getUserid(),
                                                PreferencesUtils.PHONE, Response.getPhone(), PreferencesUtils.ONSCREENNAME, Response.getOnscreen_name(),
                                                PreferencesUtils.PASSWORD,null);
                                        //表示已登录
                                        PreferencesUtils.logined(App.getAppcontext(), PreferencesUtils.LOGINED, true);
                                    }

                                    @Override
                                    public void UseridUnexited(String userid) {
                                        PreferencesUtils.putUserid(App.getAppcontext(), PreferencesUtils.USERID, Response.getUserid(),
                                                PreferencesUtils.PHONE, Response.getPhone(), PreferencesUtils.ONSCREENNAME, Response.getOnscreen_name(),
                                                PreferencesUtils.PASSWORD,null);
                                        //表示为新用户
                                        PreferencesUtils.putBoolean(App.getAppcontext(), PreferencesUtils.FIRST_START, true);
                                        //表示已登录
                                        PreferencesUtils.logined(App.getAppcontext(), PreferencesUtils.LOGINED, true);
                                        DaoManger.getDaoSession(Response.getUserid());
                                    }
                                });
                                registerByPhoneActivityView.getContext().startActivity(new Intent(registerByPhoneActivityView.getContext(), HomeActivity.class));
                                registerByPhoneActivityView.getContext().finish();
                            }
                        });
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void parseJSON(String jsonDate) {
        Gson gson = new Gson();
        List<com.szemingcheng.amemo.entity.Response> responseList = gson.fromJson(jsonDate, new TypeToken<List<com.szemingcheng.amemo.entity.Response>>() {
        }.getType());
        for (com.szemingcheng.amemo.entity.Response response : responseList) {
            Response = userModel.loadresponse(response.getResCode(), response.getMessage(), response.getPassword(), response.getUserid(), response.getPhone(), response.getOnscreen_name());
        }
    }


    @Override
    public void LoadUserInfo() {
        user = PreferencesUtils.getUserInfo(App.getAppcontext(), PreferencesUtils.USERID, PreferencesUtils.PHONE, PreferencesUtils.ONSCREENNAME, PreferencesUtils.PASSWORD);
            loginActivityView.setUserInfo(user.getUser_id(), user.getPasswrd());
    }

    @Override
    public void CheckUserid() {
        if (loginActivityView.getUserid().equals("")) {
            loginActivityView.CheckUserid();
        }
        return;
    }

    // 启动短信验证sdk
    @Override
    public void initSMSSDK(final String step) {
        switch (step){
            case "1":
                SMSSDK.initSDK(changePasswordActivityView.getContext(), "1e35a107e0ce9", "6572486ba037c6a8db439a4cb7f49908");
                break;
            case "2":
                SMSSDK.initSDK(registerByPhoneActivityView.getContext(), "1e35a107e0ce9", "6572486ba037c6a8db439a4cb7f49908");
                break;
        }
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                //主线程判断
                switch (step) {
                    case "1":
                        Smssdkhandler1.sendMessage(msg);
                        break;
                    case "2":
                        Smssdkhandler2.sendMessage(msg);
                        break;
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); // 注册回调监听接口
    }

    /**
     * 这个东西很重要，它是短信验证的管理器
     *
     * 被注释的千万不要删掉，否则就无效
     * @param step
     */
    @Override
    public void SMSSDKSubmitVerificationCode(String step) {
        switch (step) {
            case "1":
                if (matchChangePasswordActivityReges(changePasswordActivityView.getPassword1(), changePasswordActivityView.getpassword2())) {
                    //测试用
                    getJson(CAHNGPASSWORD);
                   // SMSSDK.submitVerificationCode("86", changePasswordActivityView.getPhone(), changePasswordActivityView.getCode());
                }
                break;
            case "2":
                //测试用
                getJson(REGISTERBYPHONE);
                //SMSSDK.submitVerificationCode("86", registerByPhoneActivityView.getPhone(), registerByPhoneActivityView.getCode());
                break;
        }
    }

    @Override
    public void SMSSDKStart(final String step) {
        switch (step) {
            case "1":
                SMSSDK.getVerificationCode("86", changePasswordActivityView.getPhone()); // 调用sdk发送短信验证
                changePasswordActivityView.CodeClickable(false);// 设置按钮不可点击 显示倒计时
                changePasswordActivityView.CodeReminder("重新发送（" + i + ")");
                break;
            case "2":
                SMSSDK.getVerificationCode("86", registerByPhoneActivityView.getPhone()); // 调用sdk发送短信验证
                registerByPhoneActivityView.CodeClickable(false);// 设置按钮不可点击 显示倒计时
                registerByPhoneActivityView.CodeReminder("重新发送（" + i + ")");
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (i = 60; i > 0; i--) {
                    switch (step) {
                        case "1":
                            Smssdkhandler1.sendEmptyMessage(-9);
                            break;
                        case "2":
                            Smssdkhandler2.sendEmptyMessage(-9);
                            break;
                    }
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);// 线程休眠实现读秒功能
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                switch (step) {
                    case "1":
                        Smssdkhandler1.sendEmptyMessage(-8);// 在60秒后重新显示为获取验证码
                        break;
                    case "2":
                        Smssdkhandler2.sendEmptyMessage(-8);// 在60秒后重新显示为获取验证码
                        break;
                }
            }
        }).start();
    }


    Handler Smssdkhandler1 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                changePasswordActivityView.CodeReminder("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                changePasswordActivityView.CodeReminder("获取验证码");
                changePasswordActivityView.CodeClickable(true);  // 设置可点击
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;

                System.out.println(event);
                System.out.println(result);
                System.out.println(data);

                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        changePasswordActivityView.codeSuccess();

                        // 验证成功
                        getJson(CAHNGPASSWORD);
                        Log.e("注册", "成功！");
                        changePasswordActivityView.getContext().startActivity(new Intent(changePasswordActivityView.getContext(), LoginActivity.class));
                        changePasswordActivityView.getContext().finish();
                        // 成功跳转之后销毁当前页面

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        changePasswordActivityView.codeSubmited();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
                if (result == SMSSDK.RESULT_ERROR) {
                    changePasswordActivityView.codeError();
                }
            }
        }
    };
    Handler Smssdkhandler2 = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                registerByPhoneActivityView.CodeReminder("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                registerByPhoneActivityView.CodeReminder("获取验证码");
                registerByPhoneActivityView.CodeClickable(true);  // 设置可点击
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;


                System.out.println(event);
                System.out.println(result);
                System.out.println(data);

                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        registerByPhoneActivityView.codeSuccess();

                        // 验证成功
                        getJson(REGISTERBYPHONE);
                        Log.e("注册", "成功！");
                        registerByPhoneActivityView.getContext().finish();
                        // 成功跳转之后销毁当前页面

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        registerByPhoneActivityView.codeSubmited();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
                if (result == SMSSDK.RESULT_ERROR) {
                    registerByPhoneActivityView.codeError();
                }
            }
        }
    };
}