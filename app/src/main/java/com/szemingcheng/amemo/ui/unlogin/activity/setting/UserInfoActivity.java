package com.szemingcheng.amemo.ui.unlogin.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.dao.UserDao;
import com.szemingcheng.amemo.db.DBUtils;
import com.szemingcheng.amemo.db.UserHelper;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.ui.unlogin.activity.HomeActivity;

public class UserInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    TextView onscreenname_show, userid_show;
    LinearLayout onscreenname, passwd;
    User user;
    UserHelper userhelp = DBUtils.getUserDriverHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        init();
    }

    private void init() {
        user = userhelp.queryBuilder().where(UserDao.Properties.User_id.eq(App.getAppcontext().getUser_ID())).unique();
        onscreenname_show = (TextView) findViewById(R.id.userinfo_onscreenname_show);
        userid_show = (TextView) findViewById(R.id.userinfo_userid_show);
        onscreenname = (LinearLayout) findViewById(R.id.userinfo_onscreenname);
        passwd = (LinearLayout) findViewById(R.id.userinfo_passwd);
        onscreenname.setOnClickListener(onclicklistener);
        passwd.setOnClickListener(onclicklistener);
        onscreenname_show.setText(user.getOnscreen_name());
        userid_show.setText(user.getUser_id());
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
                    startActivity(new Intent(UserInfoActivity.this, HomeActivity.class));
                    finish();
                }
            };

    View.OnClickListener onclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.userinfo_onscreenname:
                    Intent intent = new Intent(UserInfoActivity.this, SettingDeatilActivity.class);
                    intent.putExtra("comefrom", SettingDeatilActivity.ONSCREENNAME);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.userinfo_passwd:
                    Intent intent1 = new Intent(UserInfoActivity.this, SettingDeatilActivity.class);
                    intent1.putExtra("comefrom", SettingDeatilActivity.PASSWORD);
                    startActivity(intent1);
                    finish();
                    break;
            }
        }
    };
}
