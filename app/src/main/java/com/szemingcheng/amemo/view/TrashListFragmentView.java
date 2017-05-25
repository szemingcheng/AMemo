package com.szemingcheng.amemo.view;

import com.szemingcheng.amemo.entity.Memo;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/25.
 */

public interface TrashListFragmentView {
    void updateListView(List<Memo> memos);

    void showRecyclerView();

    void hideRecyclerView();
}
