package com.szemingcheng.amemo.presenter.Imp;

import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.model.Imp.NoteBKListModelImp;
import com.szemingcheng.amemo.model.NoteBKListModel;
import com.szemingcheng.amemo.presenter.NoteBKListFragmentPresent;
import com.szemingcheng.amemo.view.NoteBKListFragmentView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/20.
 */

public class NoteBKListFragmentPresentImp implements NoteBKListFragmentPresent {
    private NoteBKListFragmentView noteBKListFragmentView;
    private NoteBKListModel noteBKListModel;


    public NoteBKListFragmentPresentImp(NoteBKListFragmentView noteBKListFragmentView) {
        this.noteBKListFragmentView = noteBKListFragmentView;
        this.noteBKListModel = new NoteBKListModelImp();
    }

    @Override
    public void getMemo(String user_id) {
        noteBKListModel.notebk_list(user_id,onDatafinishedlistener);
    }

    @Override
    public void pulltorefresh(String user_id) {
        noteBKListModel.notebk_list(user_id,onDatafinishedlistener);
    }

    @Override
    public void add_NoteBK(NoteBK noteBK) {
        noteBKListModel.notebk_add(noteBK,onRequestListener);
    }

    @Override
    public void delete_NoteBK(Long notebk_id) {
        noteBKListModel.notebk_delete(notebk_id,onRequestListener);
    }

    @Override
    public void update_NoteBK(NoteBK noteBK1,NoteBK noteBK) {
        noteBKListModel.notebk_save(noteBK1,noteBK,onRequestListener);
    }

    private NoteBKListModel.OnDataFinishedListener onDatafinishedlistener = new NoteBKListModel.OnDataFinishedListener() {
        @Override
        public void getDataFinish(List<NoteBK> noteBKs) {
            noteBKListFragmentView.hideLoadingIcon();
            noteBKListFragmentView.updateListView(noteBKs);
        }

        @Override
        public void onError(String error) {
            noteBKListFragmentView.hideLoadingIcon();
            noteBKListFragmentView.hideRecyclerView();
        }
    };
    private NoteBKListModel.OnRequestListener onRequestListener = new NoteBKListModel.OnRequestListener() {
        @Override
        public void onSuccess() {
            noteBKListFragmentView.showDeleteSuccess();
        }

        @Override
        public void onRenameSuccess() {
            noteBKListFragmentView.showRenameSuccess();
        }

        @Override
        public void onSuccess(NoteBK noteBK) {
            noteBKListFragmentView.AddSuccess(noteBK);
        }

        @Override
        public void onError(String error) {
            noteBKListFragmentView.showError(error);
        }
    };
}
