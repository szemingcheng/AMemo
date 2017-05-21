package com.szemingcheng.amemo.ui.unlogin.fragment.notebk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.presenter.Imp.MemoListFragmentPresentImp;
import com.szemingcheng.amemo.presenter.MemoListFragmentPresent;
import com.szemingcheng.amemo.ui.unlogin.fragment.MemoListAdapter;
import com.szemingcheng.amemo.ui.unlogin.fragment.OnItemClickListener;
import com.szemingcheng.amemo.view.MemoListFragmentView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/20.
 */

public class MemoListInNBFragment extends Fragment implements MemoListFragmentView {
    public View mView;
    public RecyclerView mRecyclerView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public FrameLayout mEmptyLayout;
    public TextView mErrorMessage;
    public FloatingActionMenu mfloatingActionButton;
    public FloatingActionButton memo_cam;
    public FloatingActionButton memo_pic;
    public FloatingActionButton memo_reminder;
    public FloatingActionButton memo_txt;
    private MemoListFragmentPresent memoListFragmentPresent;
    private MemoListAdapter memoListAdapter;
    List<Memo> data;
    String notebk_title;
    Toolbar mToolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memoListFragmentPresent = new MemoListFragmentPresentImp(this);
        notebk_title = getArguments().getString("note_book");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_memo_list_in_notebk_fragment,container,false);
        mToolbar = (Toolbar)mView.findViewById(R.id.toolbar_in_fragment);
        mEmptyLayout = (FrameLayout)mView.findViewById(R.id.empty_layout);
        mErrorMessage = (TextView)mEmptyLayout.findViewById(R.id.empty_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        memoListAdapter = new MemoListAdapter(getActivity().getApplication(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(App.getAppcontext(),"点了",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onMoreClick(View view, int position) {

            }
        });
        Log.i("setadapter","assigned,context:"+getActivity());
        mRecyclerView = (RecyclerView)mView.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (data!=null) data.clear();
                        memoListFragmentPresent.pulltorefresh_notebk(notebk_title);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(App.getAppcontext(), "更新了...", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(memoListAdapter);
        Log.i("setadapter","recyclerview assigned");
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                memoListFragmentPresent.getMemo_notebk(notebk_title);
            }
        });
        return mView;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mfloatingActionButton = (FloatingActionMenu) view.findViewById(R.id.fab_menu);
        memo_cam = (FloatingActionButton)view.findViewById(R.id.menu_item_camera);
        memo_pic = (FloatingActionButton)view.findViewById(R.id.menu_item_pic);
        memo_reminder = (FloatingActionButton)view.findViewById(R.id.menu_item_reminder);
        mfloatingActionButton.setClosedOnTouchOutside(true);
        mfloatingActionButton.hideMenuButton(false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mfloatingActionButton.showMenuButton(true);
        memo_cam.setOnClickListener(onClickListener);
        memo_pic.setOnClickListener(onClickListener);
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
                case R.id.memo_pic:
                    Toast.makeText(getActivity(), memo_pic.getLabelText(), Toast.LENGTH_SHORT).show();
                    mfloatingActionButton.toggle(false);
            }
        }
    };

    @Override
    public void updateListView(List<Memo> memos) {
        memoListAdapter.clear();
        memoListAdapter.setData(memos);
        memoListAdapter.notifyDataSetChanged();
        Log.i("setadapter","update List view");
    }
    @Override
    public void showLoadingIcon() {
        mSwipeRefreshLayout.setRefreshing(true);
    }
    @Override
    public void hideLoadingIcon() {
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showRecyclerView() {
        if (mSwipeRefreshLayout.getVisibility() != View.VISIBLE) {
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideRecyclerView() {
        if (mSwipeRefreshLayout.getVisibility() != View.GONE) {
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }
    }


}