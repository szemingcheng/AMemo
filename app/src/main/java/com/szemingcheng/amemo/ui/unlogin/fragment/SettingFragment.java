package com.szemingcheng.amemo.ui.unlogin.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.presenter.Imp.SettingFragmentPresentImp;
import com.szemingcheng.amemo.presenter.SettingFragmentPresent;
import com.szemingcheng.amemo.ui.unlogin.activity.setting.SettingDeatilActivity;
import com.szemingcheng.amemo.ui.unlogin.activity.setting.UserInfoActivity;
import com.szemingcheng.amemo.utils.PreferencesUtils;
import com.szemingcheng.amemo.view.SettingFragmentView;

/**
 * Created by szemingcheng on 2017/5/15.
 */

public class SettingFragment extends Fragment implements SettingFragmentView {
    public View mView;
    ImageView user_avatar;
    SettingFragmentPresent settingFragmentPresent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingFragmentPresent = new SettingFragmentPresentImp(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.setting_avatar).setOnClickListener(onClickListener);
        getActivity().findViewById(R.id.setting_back).setOnClickListener(onClickListener);
        getActivity().findViewById(R.id.setting_phone).setOnClickListener(onClickListener);
        getActivity().findViewById(R.id.setting_userinfo).setOnClickListener(onClickListener);
        getActivity().findViewById(R.id.setting_about).setOnClickListener(onClickListener);
        getActivity().findViewById(R.id.setting_help).setOnClickListener(onClickListener);
        getActivity().findViewById(R.id.setting_sync).setOnClickListener(onClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_setting, container, false);
        if(PreferencesUtils.islogin(App.getAppcontext(),PreferencesUtils.LOGINED,false)){
            mView.findViewById(R.id.setting_logined).setVisibility(View.VISIBLE);
            user_avatar = (ImageView) mView.findViewById(R.id.default_avatar);
            user_avatar.setImageResource(R.drawable.user1);
        }else {
            mView.findViewById(R.id.setting_logined).setVisibility(View.GONE);
        }

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.setting_back:
                    _exit();
                    break;
                case R.id.setting_userinfo:
                    getActivity().startActivity(new Intent(getActivity(), UserInfoActivity.class));
                    break;
                case R.id.setting_phone:
                    Intent intent = new Intent(getActivity(), SettingDeatilActivity.class);
                    intent.putExtra("comefrom", SettingDeatilActivity.PHONE);
                    startActivity(intent);
                    break;
                case R.id.setting_avatar:
                case R.id.setting_about:
                case R.id.setting_help:
                case R.id.setting_sync:
                    showMessageAlertDialog();
                    break;
            }
        }
    };

    @Override
    public void _exit() {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
        View exitView = View.inflate(getActivity(), R.layout.layout_alertdialog_exit, null);
        normalDialog.setView(exitView);
        normalDialog.show();
        exitView.findViewById(R.id.exit1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingFragmentPresent._exit();
            }
        });
        exitView.findViewById(R.id.exit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingFragmentPresent._delete();
            }
        });
    }

    @Override
    public void showMessageAlertDialog() {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
        normalDialog.setMessage("该功能尚在开发，敬请期待" +
                "\n～(￣▽￣～)(～￣▽￣)～       ");
        normalDialog.show();
    }

    @Override
    public Activity getactivity() {
        return getActivity();
    }


}
