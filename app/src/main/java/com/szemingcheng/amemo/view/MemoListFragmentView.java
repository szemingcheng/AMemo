package com.szemingcheng.amemo.view;

import com.szemingcheng.amemo.entity.Memo;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/16.
 */

public interface MemoListFragmentView {
   void updateListView(List<Memo> memos);

     void showLoadingIcon();

     void hideLoadingIcon();

     void showRecyclerView();

     void hideRecyclerView();


}
