package com.szemingcheng.amemo.presenter.Imp;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.model.Imp.SettingDeatilModelImp;
import com.szemingcheng.amemo.model.Imp.UserModelImp;
import com.szemingcheng.amemo.model.SettingDeatilModel;
import com.szemingcheng.amemo.model.UserModel;
import com.szemingcheng.amemo.presenter.SettingDeatilActivityPresent;
import com.szemingcheng.amemo.ui.unlogin.activity.setting.UserInfoActivity;
import com.szemingcheng.amemo.utils.HttpUtils;
import com.szemingcheng.amemo.utils.PreferencesUtils;
import com.szemingcheng.amemo.utils.RegexUtils;
import com.szemingcheng.amemo.view.SettingDeatilActivityView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Jaygren on 2017/5/29.
 */

public class SettingDeatilActivityPresentImp implements SettingDeatilActivityPresent {
    RequestBody requestBody;
    com.szemingcheng.amemo.entity.Response Response;
    SettingDeatilActivityView settingDeatilActivityView;
    SettingDeatilModel settingDeatilModel;
    UserModel userModel;
    Handler handler = new Handler();
    User user;

    public  SettingDeatilActivityPresentImp(SettingDeatilActivityView settingDeatilActivityView){
        this.settingDeatilActivityView=settingDeatilActivityView;
        settingDeatilModel=new SettingDeatilModelImp();
        userModel=new UserModelImp();
    }
    @Override
    public boolean matchUseridRegex(String onstreenname) {
        if (RegexUtils.OnscreenNameRegex(onstreenname)==0){
         settingDeatilActivityView.OnScreamClare();
            return false;
        }
        return true;
    }

    @Override
    public boolean matchPasswdRegex(String passwd) {
        if(RegexUtils.PasswdRegex(passwd)==0){
            settingDeatilActivityView.PasswdClare();
            return  false;
        }
        return true;
    }

    @Override
    public boolean matchPhonseRegex(String phone) {
        if(RegexUtils.PhoneRegex(phone)==0){
            settingDeatilActivityView.PhoneClare();
            return  false;
        }
        return true;
    }

    @Override
    public void initViewOnScreenName() {
    settingDeatilActivityView.initViewOnScreenName();
    }

    @Override
    public void initViewPasswd() {
    settingDeatilActivityView.initViewPasswd();
    }

    @Override
    public void initViewPhone() {
        settingDeatilActivityView.initViewPhone();
    }

    @Override
    public void initViewCam() {
        settingDeatilActivityView.initViewCam(settingDeatilActivityView.getToolbar().getMenu());
    }

    @Override
    public void getJson(String step) {
        switch (step){
            case "4":
                Log.i("password",settingDeatilActivityView.getPasswd());
                requestBody = new FormBody.Builder()
                        .add("password", settingDeatilActivityView.getPasswd())
                        .add("phone", settingDeatilActivityView.getPhone())
                        .add("step", step)
                        .build();
                break;
            case "6":
                requestBody=new FormBody.Builder()
                        .add("onscreen_name", settingDeatilActivityView.getOnScreenName())
                        .add("phone", settingDeatilActivityView.getPhone())
                        .add("step", step)
                        .build();
                break;
            case "7":
                requestBody=new FormBody.Builder()
                        .add("userid",App.getAppcontext().getUser_ID())
                        .add("phone", settingDeatilActivityView.getPhoneNumber())
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
                switch (Response.getResCode()){
                    case "4":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(settingDeatilActivityView.getContext(),"密码修改成功！",Toast.LENGTH_SHORT).show();
                                settingDeatilActivityView.getContext().startActivity(new Intent(settingDeatilActivityView.getContext(), UserInfoActivity.class));
                                settingDeatilActivityView.getContext().finish();
                            }
                        });
                        break;
                    case "6":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(settingDeatilActivityView.getContext(),"昵称修改成功！",Toast.LENGTH_SHORT).show();
                                user=new User(settingDeatilActivityView.getID(),Response.getUserid(),null,Response.getPhone(),Response.getOnscreen_name(),null,User.TYPE_USER);
                                settingDeatilModel.user_onscreenname_update(user);
                                PreferencesUtils.putOnscreen_name(App.getAppcontext(), PreferencesUtils.ONSCREENNAME,Response.getOnscreen_name());
                                settingDeatilActivityView.getContext().startActivity(new Intent(settingDeatilActivityView.getContext(), UserInfoActivity.class));
                                settingDeatilActivityView.getContext().finish();
                            }
                        });
                        break;
                    case "7":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(settingDeatilActivityView.getContext(),"手机号码修改成功！",Toast.LENGTH_SHORT).show();
                                user=new User(settingDeatilActivityView.getID(),Response.getUserid(),null,Response.getPhone(),Response.getOnscreen_name(),null,User.TYPE_USER);
                                settingDeatilModel.user_onscreenname_update(user);
                                settingDeatilActivityView.getContext().startActivity(new Intent(settingDeatilActivityView.getContext(), UserInfoActivity.class));
                                settingDeatilActivityView.getContext().finish();
                            }
                        });
                        break;
                    case "-5":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                settingDeatilActivityView.PhoneClare();
                                Toast.makeText(settingDeatilActivityView.getContext(),"该号码已存在",Toast.LENGTH_SHORT).show();
                            }
                        });
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
}
