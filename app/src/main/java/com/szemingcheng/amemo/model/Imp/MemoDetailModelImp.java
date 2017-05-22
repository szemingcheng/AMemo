package com.szemingcheng.amemo.model.Imp;

import com.szemingcheng.amemo.db.DBUtils;
import com.szemingcheng.amemo.db.MemoHelper;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.model.MemoDetailModel;

/**
 * Created by szemingcheng on 2017/5/23.
 */

public class MemoDetailModelImp implements MemoDetailModel {
    private MemoHelper memoHelper = DBUtils.getMemoDriverHelper();
    @Override
    public void get_memo_detail(Memo memo, OnDataFinishedListener onDataFinishedListener) {
        onDataFinishedListener.getDataFinish(memo);
    }

    @Override
    public void save_memo(Memo memo, OnRequestListener onRequestListener) {
        if (memo.getTitle().isEmpty()&&memo.getMemotxt().isEmpty()){
            onRequestListener.onError("不能保存空笔记哟~");
        }
        else if(memo.getNoteBK().get_ID()==null){
            onRequestListener.onError("请选择笔记本！");
        }
        else {
            memoHelper.save(memo);
            onRequestListener.onSuccess();
        }
    }
}
