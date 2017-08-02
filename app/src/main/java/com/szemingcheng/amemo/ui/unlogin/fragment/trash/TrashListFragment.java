package com.szemingcheng.amemo.ui.unlogin.fragment.trash;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.presenter.Imp.TrashListFragmentPresentImp;
import com.szemingcheng.amemo.presenter.TrashListFragmentPresent;
import com.szemingcheng.amemo.ui.unlogin.activity.HomeActivity;
import com.szemingcheng.amemo.ui.unlogin.activity.MemoDetailActivity;
import com.szemingcheng.amemo.ui.unlogin.fragment.memo.MemoListAdapter;
import com.szemingcheng.amemo.ui.unlogin.fragment.OnItemClickListener;
import com.szemingcheng.amemo.view.HomeActivityView;
import com.szemingcheng.amemo.view.TrashListFragmentView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/15.
 */

public class TrashListFragment extends Fragment implements TrashListFragmentView {
    public  View mView;
    public RecyclerView mRecyclerView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public FrameLayout mEmptyLayout;
    public TextView mErrorMessage;
    private TrashListFragmentPresent trashListFragmentPresent;
    private MemoListAdapter memoListAdapter;
    List<Memo> data;
    HomeActivityView homeActivityView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trashListFragmentPresent = new TrashListFragmentPresentImp(this);
        homeActivityView = ((HomeActivity)getActivity());
        Bundle bundle = new Bundle();
        bundle.putString("fragment","memolist");
        homeActivityView.fragment_callback(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_memo_list_fragment,container,false);
        mEmptyLayout = (FrameLayout)mView.findViewById(R.id.empty_layout);
        mErrorMessage = (TextView)mEmptyLayout.findViewById(R.id.empty_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        memoListAdapter = new MemoListAdapter(getActivity().getApplication(), new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setAction("com.activity.MemoDetailActivity");
                intent.putExtra("comefrom", MemoDetailActivity.VIEW_DELETE_MODE);
                intent.putExtra("id",memoListAdapter.getItemData(position).get_ID());
                startActivity(intent);
            }
            @Override
            public void onMoreClick(View view, int position) {

            }
            @Override
            public void onItemLongClick(View view, int positon) {
                showMultiBtnDialog(memoListAdapter.getItemData(positon));
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
                        trashListFragmentPresent.pulltorefresh(App.getAppcontext().getUser_ID());
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
                trashListFragmentPresent.getMemo(App.getAppcontext().getUser_ID());
            }
        });
        return mView;
    }

    private void showMultiBtnDialog(final Memo memo) {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setTitle("笔记 "+memo.getTitle()).setMessage("您想进行什么操作呢？");
        normalDialog.setPositiveButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
        normalDialog.setNeutralButton("彻底删除",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        trashListFragmentPresent.memo_delete(memo.get_ID());
                    }
                });
        normalDialog.setNegativeButton("恢复", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                trashListFragmentPresent.memo_restore(memo.get_ID());
            }
        });
        // 创建实例并显示
        normalDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (data!=null) data.clear();
                trashListFragmentPresent.pulltorefresh(App.getAppcontext().getUser_ID());
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        getActivity().getMenuInflater().inflate(R.menu.memo_activity_menu,menu);
    }

    @Override
    public void updateListView(List<Memo> memos) {
        memoListAdapter.clear();
        memoListAdapter.setData(memos);
        memoListAdapter.notifyDataSetChanged();
        Log.i("setadapter","update List view");
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

    @Override
    public void showSuccess() {
        Toast.makeText(App.getAppcontext(),"操作成功",Toast.LENGTH_SHORT).show();
        trashListFragmentPresent.getMemo(App.getAppcontext().getUser_ID());
    }

    @Override
    public void showError(String error) {
        Toast.makeText(App.getAppcontext(),error,Toast.LENGTH_SHORT).show();
    }
}
