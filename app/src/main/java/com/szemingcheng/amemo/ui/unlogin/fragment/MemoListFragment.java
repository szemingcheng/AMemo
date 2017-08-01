package com.szemingcheng.amemo.ui.unlogin.fragment;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.presenter.Imp.MemoListPresentImp;
import com.szemingcheng.amemo.presenter.MemoListPresent;
import com.szemingcheng.amemo.ui.unlogin.activity.HomeActivity;
import com.szemingcheng.amemo.ui.unlogin.activity.MemoDetailActivity;
import com.szemingcheng.amemo.view.HomeActivityView;
import com.szemingcheng.amemo.view.MemoListView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/15.
 */

public class MemoListFragment extends Fragment implements MemoListView {
    public  View mView;
    public RecyclerView mRecyclerView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public FrameLayout mEmptyLayout;
    public TextView mErrorMessage;
    private MemoListPresent memoListPresent;
    private MemoListAdapter memoListAdapter;
    List<Memo> data;
    HomeActivityView homeActivityView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memoListPresent = new MemoListPresentImp(this);
        homeActivityView = ((HomeActivity)getActivity());
        Bundle bundle = new Bundle();
        bundle.putString("fragment","memolist");
        homeActivityView.fragment_callback(bundle);
        setHasOptionsMenu(true);
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
                intent.putExtra("comefrom", MemoDetailActivity.VIEW_MEMO_MODE);
                intent.putExtra("id",memoListAdapter.getItemData(position).get_ID());
                startActivity(intent);
            }
            @Override
            public void onMoreClick(View view, int position) {

            }
            @Override
            public void onItemLongClick(View view, int positon) {
                Memo memo = memoListAdapter.getItemData(positon);
                showMultiBtnDialog(memo,positon);
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
                        memoListPresent.pulltorefresh(App.getAppcontext().getUser_ID());
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(App.getAppcontext(), "更新完成", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(memoListAdapter);
        Log.i("setadapter","recyclerview assigned");
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                Log.i("用户的ID是", App.getAppcontext().getUser_ID());
                // memoListPresent.pulltorefresh(App.getAppcontext().getUser_ID());
                memoListPresent.pulltorefresh(App.getAppcontext().getUser_ID());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (data!=null) data.clear();
                memoListPresent.getMemo(App.getAppcontext().getUser_ID());
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        Toast.makeText(App.getAppcontext(),"删除成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(App.getAppcontext(),error,Toast.LENGTH_SHORT).show();
    }

    private void showMultiBtnDialog(final Memo memo, final int position){
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
        normalDialog.setNeutralButton("删除",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        memoListPresent.delete_memo(memo.get_ID());
                        memoListAdapter.removeDataItem(position);
                    }
                });
        normalDialog.setNegativeButton("查看", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("com.activity.MemoDetailActivity");
                intent.putExtra("comefrom", MemoDetailActivity.VIEW_MEMO_MODE);
                intent.putExtra("id",memo.get_ID());
                startActivity(intent);
            }
        });
        // 创建实例并显示
        normalDialog.show();
    }
}
