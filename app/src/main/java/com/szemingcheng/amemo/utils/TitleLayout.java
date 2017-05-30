package com.szemingcheng.amemo.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;

/**
 * Created by Jaygren on 2017/5/23.
 */

public class TitleLayout extends LinearLayout implements View.OnClickListener {
    Button titleBack;
    private long mExitTime = 0;

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        titleBack = (Button) findViewById(R.id.title_back);
        titleBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                _exit();
                break;
            default:
                break;
        }
    }

    private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(getContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            App.getAppcontext().delete();
        }
    }
}
