package com.szemingcheng.amemo.ui.unlogin.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.dao.UserDao;
import com.szemingcheng.amemo.db.DBUtils;
import com.szemingcheng.amemo.db.UserHelper;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.presenter.Imp.SettingDeatilActivityPresentImp;
import com.szemingcheng.amemo.presenter.SettingDeatilActivityPresent;
import com.szemingcheng.amemo.utils.RegexUtils;
import com.szemingcheng.amemo.view.SettingDeatilActivityView;

public class SettingDeatilActivity extends AppCompatActivity implements SettingDeatilActivityView{
    private Toolbar toolbar;
    public final static String ONSCREENNAME = "ONSCREENNAME";
    public final static String PASSWORD="PASSWORD";
    public final static String AVATAR="AVATAR";
    public final static String PHONE="PHONE";
    private String come_from;
    static String CAHNGPASSWORD = "4";
    static String CAHNGONSCREENNAME = "6";
    static String CAHNGPHONE = "7";
    User user=new User();
    UserHelper userHelper= DBUtils.getUserDriverHelper();
    ImageView picture;
    EditText  change_onscreenname,change_passwd,change_phonenumber;
    TextView change_onscreenname_reminder,change_passwd_reminder,change_phonenumber_reminder,change_phonenumber_show;
    SettingDeatilActivityPresent settingDeatilActivityPresent;
    Button changed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_deatil);
        settingDeatilActivityPresent=new SettingDeatilActivityPresentImp(this);
        user=userHelper.queryBuilder().where(UserDao.Properties.User_id.eq(App.getAppcontext().getUser_ID())).unique();
        picture=(ImageView)findViewById(R.id.picture);
        change_onscreenname=(EditText)findViewById(R.id.change_onscreenname);
        change_passwd=(EditText)findViewById(R.id.change_passwd);
        change_phonenumber=(EditText)findViewById(R.id.change_phonenumber);
        change_phonenumber_reminder=(TextView)findViewById(R.id.change_phonenumber_reminder);
        change_onscreenname_reminder=(TextView)findViewById(R.id.change_onscreenname_reminder);
        change_phonenumber_show=(TextView)findViewById(R.id.change_phonenumber_show);
        change_passwd_reminder=(TextView)findViewById(R.id.change_passwd_reminder);
        changed=(Button)findViewById(R.id.changed);
        final Intent intent=getIntent();
        come_from=intent.getExtras().getString("comefrom");
        toolbar = (Toolbar) findViewById(R.id.toolbar_in_setting);
        toolbar.setTitle("");
        toolbar.setNavigationIcon
                (R.drawable.vector_drawable_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener
                (OnNavigationListener);
    }

    private Toolbar.OnClickListener OnNavigationListener = new
            View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (come_from){
            case ONSCREENNAME:
                settingDeatilActivityPresent.initViewOnScreenName();
                break;
            case PASSWORD:
              settingDeatilActivityPresent.initViewPasswd();
                break;
            case AVATAR:
                settingDeatilActivityPresent.initViewCam();
                break;
            case PHONE:
                settingDeatilActivityPresent.initViewPhone();
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.avatar_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                switch (come_from){
                    case  ONSCREENNAME:
                        if(settingDeatilActivityPresent.matchUseridRegex(getOnScreenName())){
                        settingDeatilActivityPresent.getJson(CAHNGONSCREENNAME);
                        break;
                        }
                        else
                        break;
                    case PASSWORD:
                        if(settingDeatilActivityPresent.matchPasswdRegex(getPasswd())){
                            settingDeatilActivityPresent.getJson(CAHNGPASSWORD);
                            break;
                        }else
                        break;
                    case PHONE:
                        if(settingDeatilActivityPresent.matchPhonseRegex(getPhoneNumber())){
                            settingDeatilActivityPresent.getJson(CAHNGPHONE);
                            break;
                        }
                        break;
                }
                break;
            case R.id.avatar_album:
                Toast.makeText(SettingDeatilActivity.this,"按了avatar_album",Toast.LENGTH_SHORT).show();
                break;
            case R.id.avatar_camera:
                Toast.makeText(SettingDeatilActivity.this,"按了avatar_camera",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onClickLisenter=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
      change_phonenumber.setVisibility(View.VISIBLE);
      change_phonenumber.addTextChangedListener(onTextChangedListener);
        }
    };

    private TextWatcher onTextChangedListener =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            toolbar.getMenu().findItem(R.id.save).setVisible(true);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            toolbar.getMenu().findItem(R.id.save).setVisible(true);
        }
    };

    @Override
    public String getOnScreenName() {
        return change_onscreenname.getText().toString();
    }

    @Override
    public String getPasswd() {
        return change_passwd.getText().toString();
    }

    @Override
    public String getPhone() {
        return user.getPhone();
    }

    @Override
    public String getPhoneNumber() {
        return change_phonenumber.getText().toString();
    }

    @Override
    public Long getID() {
        return user.get_ID();
    }

    @Override
    public void initViewOnScreenName() {
        toolbar.setTitle("更改昵称");
        change_onscreenname.setVisibility(View.VISIBLE);
        change_onscreenname_reminder.setVisibility(View.VISIBLE);
        change_onscreenname.setText(user.getOnscreen_name());
        change_onscreenname.addTextChangedListener(onTextChangedListener);
    }

    @Override
    public void initViewPasswd() {
        toolbar.setTitle("更改密码");
        change_passwd.setVisibility(View.VISIBLE);
        change_passwd_reminder.setVisibility(View.VISIBLE);
        change_passwd.addTextChangedListener(onTextChangedListener);
    }

    @Override
    public void initViewCam(Menu menu) {
        menu.findItem(R.id.avatar_album).setVisible(true);
        menu.findItem(R.id.avatar_camera).setVisible(true);
    }

    @Override
    public void initViewPhone() {
        toolbar.setTitle("更换手机号码");
        change_phonenumber_reminder.setVisibility(View.VISIBLE);
        change_phonenumber_show.setVisibility(View.VISIBLE);
        change_phonenumber_show.setText(RegexUtils.phoneNumberReplace(user.getPhone()));
        changed.setVisibility(View.VISIBLE);
        changed.setOnClickListener(onClickLisenter);
    }

    @Override
    public SettingDeatilActivity getContext() {
        return SettingDeatilActivity.this;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void OnScreamClare() {
        change_onscreenname.setText("");
        Toast.makeText(this,"用户昵称格式不正确",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void PasswdClare() {
        change_passwd.setText("");
        Toast.makeText(this,"密码格式不正确",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void PhoneClare() {
        change_phonenumber.setText("");
        Toast.makeText(this,"手机号码格式不正确",Toast.LENGTH_SHORT).show();
    }
}