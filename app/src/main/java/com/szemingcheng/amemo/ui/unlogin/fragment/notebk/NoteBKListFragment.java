package com.szemingcheng.amemo.ui.unlogin.fragment.notebk;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.presenter.Imp.NoteBKListFragmentPresentImp;
import com.szemingcheng.amemo.presenter.NoteBKListFragmentPresent;
import com.szemingcheng.amemo.ui.unlogin.activity.HomeActivity;
import com.szemingcheng.amemo.ui.unlogin.fragment.OnItemClickListener;
import com.szemingcheng.amemo.view.NoteBKListFragmentView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/15.
 */

public class NoteBKListFragment extends Fragment implements NoteBKListFragmentView {
    public  View mView;
    public RecyclerView mRecyclerView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public FrameLayout mEmptyLayout;
    public TextView mErrorMessage;
//    public FloatingActionMenu mfloatingActionButton;
//    public FloatingActionButton memo_cam;
//    public FloatingActionButton memo_pic;
//    public FloatingActionButton memo_reminder;
//    public FloatingActionButton memo_txt;
    private NoteBKListFragmentPresent noteBKListFragmentPresent;
    private NoteBKListAdapter noteBKListAdapter;
    List<NoteBK> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteBKListFragmentPresent = new NoteBKListFragmentPresentImp(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_memo_list_fragment,container,false);
        mEmptyLayout = (FrameLayout)mView.findViewById(R.id.empty_layout);
        mErrorMessage = (TextView)mEmptyLayout.findViewById(R.id.empty_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        noteBKListAdapter = new NoteBKListAdapter(getActivity().getApplicationContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("note_book",noteBKListAdapter.getItemData(position).getTitle());
                MemoListInNBFragment memoListInNBFragment = new MemoListInNBFragment();
                memoListInNBFragment.setArguments(bundle);
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment,memoListInNBFragment,"memolistinb").commit();
                ((HomeActivity)getActivity()).replaceFragment(R.id.home_layout,memoListInNBFragment,"memolistinb");
            }

            @Override
            public void onMoreClick(View view, int position) {
                Toast.makeText(getActivity().getApplicationContext(),"点了more",Toast.LENGTH_SHORT).show();
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
                        noteBKListFragmentPresent.pulltorefresh("");
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(App.getAppcontext(), "更新了...", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(noteBKListAdapter);
        Log.i("setadapter","recyclerview assigned");
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                noteBKListFragmentPresent.getMemo("");
            }
        });
        return mView;
    }

//    public void onViewCreated(View view,Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mfloatingActionButton = (FloatingActionMenu) view.findViewById(R.id.fab_menu);
//        memo_cam = (FloatingActionButton)view.findViewById(R.id.menu_item_camera);
//        memo_pic = (FloatingActionButton)view.findViewById(R.id.menu_item_pic);
//        memo_reminder = (FloatingActionButton)view.findViewById(R.id.menu_item_reminder);
//        mfloatingActionButton.setClosedOnTouchOutside(true);
//        mfloatingActionButton.hideMenuButton(false);
//    }
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mfloatingActionButton.showMenuButton(true);
//        memo_cam.setOnClickListener(onClickListener);
//        memo_pic.setOnClickListener(onClickListener);
//        memo_reminder.setOnClickListener(onClickListener);
//        createCustomAnimation();
//        mfloatingActionButton.setOnMenuButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mfloatingActionButton.isOpened()) {
//                    Toast.makeText(getActivity(), mfloatingActionButton.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
//                }
//                mfloatingActionButton.toggle(true);
//            }
//        });
//    }
//    private void createCustomAnimation() {
//        AnimatorSet set = new AnimatorSet();
//
//        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(mfloatingActionButton.getMenuIconView(), "scaleX", 1.0f, 0.2f);
//        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(mfloatingActionButton.getMenuIconView(), "scaleY", 1.0f, 0.2f);
//
//        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(mfloatingActionButton.getMenuIconView(), "scaleX", 0.2f, 1.0f);
//        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(mfloatingActionButton.getMenuIconView(), "scaleY", 0.2f, 1.0f);
//
//        scaleOutX.setDuration(50);
//        scaleOutY.setDuration(50);
//
//        scaleInX.setDuration(150);
//        scaleInY.setDuration(150);
//
//        scaleInX.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                mfloatingActionButton.getMenuIconView().setImageResource(mfloatingActionButton.isOpened()
//                        ? R.drawable.vector_drawable_pen_memo : R.drawable.fab_add);
//            }
//        });
//
//        set.play(scaleOutX).with(scaleOutY);
//        set.play(scaleInX).with(scaleInY).after(scaleOutX);
//        set.setInterpolator(new OvershootInterpolator(2));
//        mfloatingActionButton.setIconToggleAnimatorSet(set);
//    }
//    private View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.menu_item_camera:
//                    Toast.makeText(getActivity(), memo_cam.getLabelText(), Toast.LENGTH_SHORT).show();
//                    mfloatingActionButton.toggle(false);
//                    break;
//                case R.id.menu_item_reminder:
//                    Toast.makeText(getActivity(), memo_reminder.getLabelText(), Toast.LENGTH_SHORT).show();
//                    mfloatingActionButton.toggle(false);
//                    break;
//                case R.id.memo_pic:
//                    Toast.makeText(getActivity(), memo_pic.getLabelText(), Toast.LENGTH_SHORT).show();
//                    mfloatingActionButton.toggle(false);
//            }
//        }
//    };
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

    @Override
    public void updateListView(List<NoteBK> noteBKs) {
        noteBKListAdapter.clear();
        noteBKListAdapter.setData(noteBKs);
        noteBKListAdapter.notifyDataSetChanged();
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
