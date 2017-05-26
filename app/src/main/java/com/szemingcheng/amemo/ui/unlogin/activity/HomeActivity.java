package com.szemingcheng.amemo.ui.unlogin.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.ui.login.activity.LoginActivity;
import com.szemingcheng.amemo.ui.unlogin.fragment.MemoListFragment;
import com.szemingcheng.amemo.ui.unlogin.fragment.NoteBKListFragment;
import com.szemingcheng.amemo.ui.unlogin.fragment.SettingFragment;
import com.szemingcheng.amemo.ui.unlogin.fragment.TrashListFragment;
import com.szemingcheng.amemo.utils.PreferencesUtils;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String MEMOLIST_FRAGMENT = "memolist";
    private static final String NOTEBKLIST_FRAGMENT ="notebklist";
    private static final String TRASHLIST_FRAGMENT = "trashlist";
    private static final String SETTING_FRAGMENT = "setting";
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    View header;
    TextView user_name;
    ImageView user_avatar;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //侧滑菜单
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //抽屉事件绑定
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        header=navigationView.inflateHeaderView(R.layout.nav_header_home);
        user_avatar=(ImageView)header.findViewById(R.id.default_avatar);
        user_name = (TextView)header.findViewById(R.id.onscreen_name);
        user_avatar.setImageResource(R.drawable.vector_drawable_defualt_avatar);

        if(!PreferencesUtils.islogin(App.getAppcontext(),PreferencesUtils.LOGINED,false)){
            user_name.setText("请登录");
        }
        else{
        userid=PreferencesUtils.getUserId(App.getAppcontext(),PreferencesUtils.USERID,"VISITOR");
            user_name.setText(userid);
        }

        user_avatar.setOnClickListener(onLoginListener);
        user_name.setOnClickListener(onLoginListener);
        navigationView.setNavigationItemSelectedListener(this);
        Resources resource=getBaseContext().getResources();
        ColorStateList csl=resource.getColorStateList(R.color.navigation_menu_item_color);
        navigationView.setItemTextColor(csl);
        navigationView.setItemIconTintList(csl);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new MemoListFragment()).commit();
            navigationView.getMenu().getItem(0).setChecked(true).setCheckable(true);
        }

    }

    private View.OnClickListener onLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(HomeActivity.this, "登录啊老铁",Toast.LENGTH_SHORT ).show();
            PreferencesUtils.logined(App.getAppcontext(),PreferencesUtils.LOGINED,false);
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    //菜单项操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //侧滑栏的点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        ItemSelected(id);

        item.setChecked(true);
        item.setCheckable(true);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //菜单项选择事件
    private void ItemSelected(int id) {
        switch (id) {
            case R.id.nav_memo_all:
                replaceFragment(R.id.fragment,new MemoListFragment(),MEMOLIST_FRAGMENT);
                break;
            case R.id.nav_note_books:
                replaceFragment(R.id.fragment,new NoteBKListFragment(),NOTEBKLIST_FRAGMENT);
                break;
            case R.id.nav_trash:
                replaceFragment(R.id.fragment,new TrashListFragment(),TRASHLIST_FRAGMENT);
                break;
            case R.id.nav_setting:
                replaceFragment(R.id.fragment,new SettingFragment(),SETTING_FRAGMENT);
                break;
        }
    }

    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            // 设置tag
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // 这里要设置tag，上面也要设置tag
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            // 存在则弹出在它上面的所有fragment，并显示对应fragment
            getSupportFragmentManager().popBackStack(tag, 0);
        }
    }
}
