package com.szemingcheng.amemo.ui.unlogin.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.ui.unlogin.fragment.MemoListFragment;
import com.szemingcheng.amemo.ui.unlogin.fragment.SettingFragment;
import com.szemingcheng.amemo.ui.unlogin.fragment.TrashListFragment;
import com.szemingcheng.amemo.ui.unlogin.fragment.notebk.NoteBKListFragment;
import com.szemingcheng.amemo.view.HomeActivityView;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeActivityView {
    private static final String MEMOLIST_FRAGMENT = "memolist";
    private static final String NOTEBKLIST_FRAGMENT ="notebklist";
    private static final String TRASHLIST_FRAGMENT = "trashlist";
    private static final String SETTING_FRAGMENT = "setting";
    private static final String MEMOLIST_IN_NB_FRAGMENT="memolistinb";
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    View header;
    TextView user_name;
    ImageView user_avatar;
    public FloatingActionMenu mfloatingActionButton;
    public FloatingActionButton memo_cam;
    public FloatingActionButton memo_pic;
    public FloatingActionButton memo_reminder;
    public FloatingActionButton memo_txt;
    private long mExitTime = 0;
    private boolean come_from_menu = false;
    String Fragment_tag=MEMOLIST_FRAGMENT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("所有笔记");
        setSupportActionBar(toolbar);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        header=navigationView.inflateHeaderView(R.layout.nav_header_home);
        user_avatar=(ImageView)header.findViewById(R.id.default_avatar);
        user_name = (TextView)header.findViewById(R.id.onscreen_name);
        user_avatar.setImageResource(R.drawable.vector_drawable_defualt_avatar);
        user_name.setText("请登录");
        user_avatar.setOnClickListener(onLoginListener);
        user_name.setOnClickListener(onLoginListener);
        navigationView.setNavigationItemSelectedListener(this);
        Resources resource=getBaseContext().getResources();
        ColorStateList csl=resource.getColorStateList(R.color.navigation_menu_item_color);
        navigationView.setItemTextColor(csl);
        navigationView.setItemIconTintList(csl);
        mfloatingActionButton = (FloatingActionMenu)findViewById(R.id.fab_menu);
        memo_cam = (FloatingActionButton)findViewById(R.id.menu_item_camera);
        memo_pic = (FloatingActionButton)findViewById(R.id.menu_item_pic);
        memo_reminder = (FloatingActionButton)findViewById(R.id.menu_item_reminder);
       // mfloatingActionButton.hideMenuButton(false);
        mfloatingActionButton.showMenuButton(true);
        memo_cam.setOnClickListener(onClickListener);
        memo_pic.setOnClickListener(onClickListener);
        memo_reminder.setOnClickListener(onClickListener);
        createCustomAnimation();
        mfloatingActionButton.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mfloatingActionButton.isOpened()) {
                    come_from_menu = true;
                    Intent intent = new Intent();
                    intent.setAction("com.activity.MemoDetailActivity");
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(MemoDetailActivity.CREATE_MEMO_MODE,come_from_menu);
                    intent.putExtra(MemoDetailActivity.CREATE_MEMO_MODE,bundle);
                    startActivity(intent);
                }
                mfloatingActionButton.toggle(true);
            }
        });
        mfloatingActionButton.setClosedOnTouchOutside(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new MemoListFragment()).commit();
            navigationView.getMenu().getItem(0).setChecked(true).setCheckable(true);
        }

    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_item_camera:
                    Toast.makeText(HomeActivity.this, memo_cam.getLabelText(), Toast.LENGTH_SHORT).show();
                    mfloatingActionButton.toggle(false);
                    break;
                case R.id.menu_item_pic:
                    Toast.makeText(HomeActivity.this, memo_pic.getLabelText(), Toast.LENGTH_SHORT).show();
                    mfloatingActionButton.toggle(false);
                    break;
                case R.id.menu_item_reminder:
                    Toast.makeText(HomeActivity.this, memo_reminder.getLabelText(), Toast.LENGTH_SHORT).show();
                    mfloatingActionButton.toggle(false);
                    break;
            }
        }
    };
    private View.OnClickListener onLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(HomeActivity.this, "登录啊老铁",Toast.LENGTH_SHORT ).show();
        }
    };

    @Override
    public void onBackPressed() {
        final int stackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (stackEntryCount==0){
            _exit();
        }
        else {
//            final String tagName = getSupportFragmentManager().getBackStackEntryAt(stackEntryCount - 2).getName();
            super.onBackPressed();
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (Fragment_tag){
            case MEMOLIST_FRAGMENT:
                menu.findItem(R.id.sort_by).setVisible(true);
                menu.findItem(R.id.nav_setting).setVisible(true);
                break;
            case NOTEBKLIST_FRAGMENT:
                menu.findItem(R.id.nav_setting).setVisible(true);
                menu.findItem(R.id.sort_by).setVisible(false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memo_list_fragment_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort_by) {
            Toast.makeText(HomeActivity.this,"排序",Toast.LENGTH_SHORT).show();
            return true;
        }
       else if (id==R.id.nav_setting){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_memo_all:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment,new MemoListFragment(),MEMOLIST_FRAGMENT).commit();

                getSupportActionBar().setTitle("所有笔记");
                break;
            case R.id.nav_note_books:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment,new NoteBKListFragment(),NOTEBKLIST_FRAGMENT).commit();
//                replaceFragment(R.id.fragment,new NoteBKListFragment(),NOTEBKLIST_FRAGMENT);
                getSupportActionBar().setTitle("笔记本");
                break;
            case R.id.nav_trash:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment,new TrashListFragment(),TRASHLIST_FRAGMENT).commit();
//                replaceFragment(R.id.fragment,new TrashListFragment(),TRASHLIST_FRAGMENT);
                getSupportActionBar().setTitle("乐色桶");
                break;
            case R.id.nav_setting:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment,new SettingFragment(),SETTING_FRAGMENT).commit();
//                replaceFragment(R.id.fragment,new SettingFragment(),SETTING_FRAGMENT);
                getSupportActionBar().setTitle("设置");
                break;
        }
        item.setChecked(true);
        item.setCheckable(true);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void replaceFragment(int containerViewId, Fragment fragment, String tag) {
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
    private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
             mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public FloatingActionMenu getMfloatingActionButton() {
        return mfloatingActionButton;
    }

    public void setMfloatingActionButton(FloatingActionMenu mfloatingActionButton) {
        this.mfloatingActionButton = mfloatingActionButton;
    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(mfloatingActionButton.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(mfloatingActionButton.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(mfloatingActionButton.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(mfloatingActionButton.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mfloatingActionButton.getMenuIconView().setImageResource(mfloatingActionButton.isOpened()
                        ? R.drawable.vector_drawable_pen_memo : R.drawable.fab_add);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));
        mfloatingActionButton.setIconToggleAnimatorSet(set);
    }

    @Override
    public void fragment_callback(Bundle arg) {
        Fragment_tag = arg.getString("fragment");
    }
}
