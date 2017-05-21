package com.szemingcheng.amemo.presenter.Imp;

import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.model.Imp.MemoListModelImp;
import com.szemingcheng.amemo.model.MemoListModel;
import com.szemingcheng.amemo.presenter.MemoListFragmentPresent;
import com.szemingcheng.amemo.view.MemoListFragmentView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/16.
 */

public class MemoListFragmentPresentImp implements MemoListFragmentPresent {
   private MemoListModel memoListModel;
   private MemoListFragmentView memoListFragmentView;

    public MemoListFragmentPresentImp(MemoListFragmentView memoListFragmentView) {
        this.memoListFragmentView = memoListFragmentView;
        this.memoListModel = new MemoListModelImp();
    }

    @Override
    public void getMemo(String user_id) {
        memoListModel.memo_all(user_id,onDataFinishedListener);
    }

    @Override
    public void pulltorefresh(String user_id) {
        memoListModel.memo_all(user_id,onDataFinishedListener);
    }

    @Override
    public void getMemo_notebk(String note_title) {
        memoListModel.memo_in_notebk(note_title,onDataFinishedListener);
    }

    @Override
    public void pulltorefresh_notebk(String note_id) {
        memoListModel.memo_in_notebk(note_id,onDataFinishedListener);
    }

    private MemoListModel.OnDataFinishedListener  onDataFinishedListener = new MemoListModel.OnDataFinishedListener() {
        @Override
        public void getDataFinish(List<Memo> memos) {
            memoListFragmentView.showRecyclerView();
            memoListFragmentView.hideLoadingIcon();
            memoListFragmentView.updateListView(memos);
        }

        @Override
        public void onError(String error) {
            memoListFragmentView.hideLoadingIcon();
            memoListFragmentView.hideRecyclerView();
        }
    };
}
