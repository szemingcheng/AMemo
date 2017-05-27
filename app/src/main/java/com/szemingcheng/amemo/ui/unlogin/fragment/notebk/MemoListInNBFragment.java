package com.szemingcheng.amemo.ui.unlogin.fragment.notebk;

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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.presenter.Imp.MemoListFragmentPresentImp;
import com.szemingcheng.amemo.presenter.MemoListFragmentPresent;
import com.szemingcheng.amemo.ui.unlogin.activity.MemoDetailActivity;
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
    private MemoListFragmentPresent memoListFragmentPresent;
    private MemoListAdapter memoListAdapter;
    private FrameLayout memolist;
    List<Memo> data;
    String notebk_title;
    String notebk_id;
    Toolbar mToolbar;
    public static MemoListInNBFragment newInstance(String title,String notebk_id){
        Bundle bundle = new Bundle();
        bundle.putString("note_title", title);
        bundle.putString("note_id",notebk_id);
        MemoListInNBFragment fragment = new MemoListInNBFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memoListFragmentPresent = new MemoListFragmentPresentImp(this);
        notebk_title = getArguments().getString("note_title");
        notebk_id = getArguments().getString("note_id");
        Log.i("fragment",notebk_title);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_memo_list_in_notebk_fragment,container,false);
        mToolbar = (Toolbar)mView.findViewById(R.id.toolbar_in_fragment);
        mToolbar.setTitle(notebk_title);
        memolist = (FrameLayout)mView.findViewById(R.id.memo_list_in_nb);
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
                showMultiBtnDialog(memoListAdapter.getItemData(positon),positon);
            }
        });
        Log.i("setadapter","assigned,context:"+getActivity());
        mRecyclerView = (RecyclerView)memolist.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (data!=null) data.clear();
                        memoListFragmentPresent.pulltorefresh_notebk(notebk_id);
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
                memoListFragmentPresent.getMemo_notebk(notebk_id);
            }
        });
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        memoListFragmentPresent.getMemo_notebk(notebk_id);
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
                        memoListFragmentPresent.delete_memo(memo.get_ID());
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