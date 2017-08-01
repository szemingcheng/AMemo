package com.szemingcheng.amemo.presenter.Imp;

import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.model.Imp.MemoListModelImp;
import com.szemingcheng.amemo.model.MemoListModel;
import com.szemingcheng.amemo.presenter.MemoListPresent;
import com.szemingcheng.amemo.view.MemoListView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/16.
 */

public class MemoListPresentImp implements MemoListPresent {
   private MemoListModel memoListModel;
   private MemoListView memoListView;

    public MemoListPresentImp(MemoListView memoListView) {
        this.memoListView = memoListView;
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
    public void getMemo_notebk(String notebk_id) {
        memoListModel.memo_in_notebk(notebk_id,onDataFinishedListener);
    }

    @Override
    public void pulltorefresh_notebk(String note_id) {
        memoListModel.memo_in_notebk(note_id,onDataFinishedListener);
    }

    @Override
    public void delete_memo(Long memo_id) {
        memoListModel.memo_delete(memo_id,onRequestListener);
    }

    private MemoListModel.OnDataFinishedListener  onDataFinishedListener = new MemoListModel.OnDataFinishedListener() {
        @Override
        public void getDataFinish(List<Memo> memos) {
            memoListView.showRecyclerView();
            memoListView.updateListView(memos);
        }

        @Override
        public void onError(String error) {
            memoListView.hideRecyclerView();
        }
    };

    private MemoListModel.OnRequestListener onRequestListener = new MemoListModel.OnRequestListener() {
        @Override
        public void onSuccess() {
            memoListView.showSuccess();
        }

        @Override
        public void onError(String error) {
            memoListView.showError(error);
        }
    };
}
