package com.szemingcheng.amemo.presenter.Imp;

import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.model.Imp.MemoListModelImp;
import com.szemingcheng.amemo.model.MemoListModel;
import com.szemingcheng.amemo.presenter.TrashListFragmentPresent;
import com.szemingcheng.amemo.view.TrashListFragmentView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/25.
 */

public class TrashListFragmentPresentImp implements TrashListFragmentPresent {
    private MemoListModel memoListModel;
    private TrashListFragmentView trashListFragmentView;

    public TrashListFragmentPresentImp(TrashListFragmentView trashListFragmentView) {
        this.trashListFragmentView = trashListFragmentView;
        this.memoListModel = new MemoListModelImp();
    }

    @Override
    public void getMemo(String user_id) {
        memoListModel.memo_in_trash(user_id,listener);
    }

    @Override
    public void pulltorefresh(String user_id) {
        memoListModel.memo_in_trash(user_id,listener);
    }

    @Override
    public void memo_restore(Long memo_id) {
        memoListModel.memo_restore(memo_id,onRequestListener);
    }

    @Override
    public void memo_delete(Long memo_id) {
        memoListModel.memo_remove(memo_id,onRequestListener);
    }

    private MemoListModel.OnDataFinishedListener listener = new MemoListModel.OnDataFinishedListener() {
        @Override
        public void getDataFinish(List<Memo> memos) {
            trashListFragmentView.showRecyclerView();
            trashListFragmentView.updateListView(memos);
        }

        @Override
        public void onError(String error) {
            trashListFragmentView.hideRecyclerView();
        }
    };

    private MemoListModel.OnRequestListener onRequestListener = new MemoListModel.OnRequestListener() {
        @Override
        public void onSuccess() {
            trashListFragmentView.showSuccess();
        }

        @Override
        public void onError(String error) {
            trashListFragmentView.showError(error);
        }
    };

}
