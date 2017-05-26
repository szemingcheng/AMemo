package com.szemingcheng.amemo.utils;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.szemingcheng.amemo.R;

/**
 * Created by Jaygren on 2017/5/23.
 */

public class TitleLayout extends LinearLayout implements View.OnClickListener {
    Button titleBack;

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
                ((Activity) getContext()).finish();
                break;
            default:
                break;
        }
    }
}
