package com.szemingcheng.amemo.ui.unlogin.fragment.notebk;

import android.content.DialogInterface;
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
import android.widget.EditText;
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
import com.szemingcheng.amemo.view.HomeActivityView;
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
    private NoteBKListFragmentPresent noteBKListFragmentPresent;
    private NoteBKListAdapter noteBKListAdapter;
    List<NoteBK> data;
    HomeActivityView homeActivityView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteBKListFragmentPresent = new NoteBKListFragmentPresentImp(this);
        homeActivityView = ((HomeActivity)getActivity());
        Bundle bundle = new Bundle();
        bundle.putString("fragment","notebklist");
        homeActivityView.fragment_callback(bundle);
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
                MemoListInNBFragment memoListInNBFragment =
                        MemoListInNBFragment.newInstance(noteBKListAdapter.getItemData(position).getTitle(),
                                noteBKListAdapter.getItemData(position).getNotebk_id());
                ((HomeActivity)getActivity()).replaceFragment
                        (R.id.fragment,memoListInNBFragment,"memolistinb");
            }
            @Override
            public void onMoreClick(View view, int position) {
                showMultiBtnDialog(noteBKListAdapter.getItemData(position));
            }
            @Override
            public void onItemLongClick(View view, int positon) {

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
                        noteBKListFragmentPresent.pulltorefresh(App.getAppcontext().getUser_ID());
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
                noteBKListFragmentPresent.getMemo(App.getAppcontext().getUser_ID());
            }
        });
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        noteBKListFragmentPresent.getMemo(App.getAppcontext().getUser_ID());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    }
    @Override
    public void hideLoadingIcon() {
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
    public void showError(String error) {
        Toast.makeText(App.getAppcontext(),error,Toast.LENGTH_SHORT).show();

    }
    @Override
    public void AddSuccess(NoteBK noteBK) {

    }

    @Override
    public void showSuccess() {
        Toast.makeText(App.getAppcontext(),"删除成功",Toast.LENGTH_SHORT).show();
        noteBKListFragmentPresent.getMemo(App.getAppcontext().getUser_ID());
    }

    private void showMultiBtnDialog(final NoteBK noteBK){
        final EditText editText = new EditText(getActivity());
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setTitle("笔记本 "+noteBK.getTitle());
        normalDialog.setMessage("您想进行什么操作呢？\n笔记本不为空时，不能进行删除操作");
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
                        noteBKListFragmentPresent.delete_NoteBK(noteBK.get_ID());
                    }
                });
//        normalDialog.setNegativeButton("修改", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        normalDialog.show();
    }
}
