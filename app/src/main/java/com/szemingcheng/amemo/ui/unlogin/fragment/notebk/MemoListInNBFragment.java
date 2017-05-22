package com.szemingcheng.amemo.ui.unlogin.fragment.notebk;

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
import android.widget.FrameLayout;
import android.widget.Toast;

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
    private MemoListFragmentPresent memoListFragmentPresent;
    private MemoListAdapter memoListAdapter;
    private FrameLayout memolist;
    List<Memo> data;
    String notebk_title;
    Toolbar mToolbar;
    public static MemoListInNBFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString("note_title", title);
        MemoListInNBFragment fragment = new MemoListInNBFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memoListFragmentPresent = new MemoListFragmentPresentImp(this);
        notebk_title = getArguments().getString("note_title");
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
                Toast.makeText(App.getAppcontext(),"点了",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onMoreClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int positon) {

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