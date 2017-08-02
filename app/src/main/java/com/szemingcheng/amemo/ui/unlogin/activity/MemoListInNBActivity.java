package com.szemingcheng.amemo.ui.unlogin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.presenter.Imp.MemoListPresentImp;
import com.szemingcheng.amemo.presenter.MemoListPresent;
import com.szemingcheng.amemo.ui.unlogin.fragment.memo.MemoListAdapter;
import com.szemingcheng.amemo.ui.unlogin.fragment.OnItemClickListener;
import com.szemingcheng.amemo.view.MemoListView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/8/1.
 */

public class MemoListInNBActivity extends AppCompatActivity implements MemoListView {
    public RecyclerView mRecyclerView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private MemoListPresent memoListPresent;
    private MemoListAdapter memoListAdapter;
    private FrameLayout memolist;
    List<Memo> data;
    String notebk_title;
    String notebk_id;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_memo_list_in_notebk);
        memoListPresent = new MemoListPresentImp(this);
        final Intent intent = getIntent();
        notebk_id = intent.getExtras().getString("notebk_id");
        notebk_title = intent.getExtras().getString("notebk_title");
        mToolbar = (Toolbar)findViewById(R.id.toolbar_in_memo_list_in_nb);
        mToolbar.setNavigationIcon(R.drawable.vector_drawable_back_white);
        mToolbar.setTitle(notebk_title);
        setSupportActionBar(mToolbar);
        memolist = (FrameLayout)findViewById(R.id.memo_list_in_nb);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        memoListAdapter = new MemoListAdapter(MemoListInNBActivity.this, new OnItemClickListener() {
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
                showMultiBtnDialog(memoListAdapter.getItemData(positon),positon);
            }
        });
        mRecyclerView = (RecyclerView)memolist.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (data!=null) data.clear();
                        memoListPresent.pulltorefresh_notebk(notebk_id);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(App.getAppcontext(), "更新了...", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(MemoListInNBActivity.this.getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(memoListAdapter);
        Log.i("setadapter","recyclerview assigned");
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                memoListPresent.getMemo_notebk(notebk_id);
            }
        });
        mToolbar.setNavigationOnClickListener(OnNavigationListener);
    }
    private Toolbar.OnClickListener OnNavigationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           finish();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        memoListPresent.getMemo_notebk(notebk_id);
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
                new AlertDialog.Builder(MemoListInNBActivity.this);
        normalDialog.setTitle(memo.getTitle()).setMessage("您想进行什么操作呢？");
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
