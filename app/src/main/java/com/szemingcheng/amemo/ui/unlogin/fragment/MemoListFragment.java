package com.szemingcheng.amemo.ui.unlogin.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.szemingcheng.amemo.R;

/**
 * Created by szemingcheng on 2017/5/15.
 */

public class MemoListFragment extends Fragment {
    public  Context mContext;
    public  View mView;
    private LinearLayout mdateheader;
    public RecyclerView mRecyclerView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public FrameLayout mEmptypage;
    public TextView mdate;
    public FloatingActionMenu mfloatingActionButton;
    public FloatingActionButton memo_cam;
    public FloatingActionButton memo_reminder;
    public FloatingActionButton memo_txt;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_memo_list_fragment,container,false);
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.recyclerview);
        mdateheader = (LinearLayout)mView.findViewById(R.id.sticky_header);
        mdate = (TextView)mdateheader.findViewById(R.id.date_sort);
        mSwipeRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.swipe_refresh_widget);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mfloatingActionButton = (FloatingActionMenu) view.findViewById(R.id.fab_menu);
        memo_cam = (FloatingActionButton)view.findViewById(R.id.menu_item_camera);
        memo_reminder = (FloatingActionButton)view.findViewById(R.id.menu_item_reminder);
        mfloatingActionButton.setClosedOnTouchOutside(true);
        mfloatingActionButton.hideMenuButton(false);
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mfloatingActionButton.showMenuButton(true);
        memo_cam.setOnClickListener(onClickListener);
        memo_reminder.setOnClickListener(onClickListener);
        createCustomAnimation();
        mfloatingActionButton.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mfloatingActionButton.isOpened()) {
                    Toast.makeText(getActivity(), mfloatingActionButton.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
                }
                mfloatingActionButton.toggle(true);
            }
        });
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
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.menu_item_camera:
                    Toast.makeText(getActivity(), memo_cam.getLabelText(), Toast.LENGTH_SHORT).show();
                    mfloatingActionButton.toggle(false);
                    break;
                case R.id.menu_item_reminder:
                    Toast.makeText(getActivity(), memo_reminder.getLabelText(), Toast.LENGTH_SHORT).show();
                    mfloatingActionButton.toggle(false);
                    break;

            }
        }
    };
}
