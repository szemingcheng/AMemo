package com.szemingcheng.amemo.model.Imp;

import android.util.Log;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.dao.MemoDao;
import com.szemingcheng.amemo.dao.NoteBKDao;
import com.szemingcheng.amemo.dao.UserDao;
import com.szemingcheng.amemo.db.DBUtils;
import com.szemingcheng.amemo.db.MemoHelper;
import com.szemingcheng.amemo.db.NoteBKHelper;
import com.szemingcheng.amemo.db.UserHelper;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.entity.SearchResult;
import com.szemingcheng.amemo.entity.User;
import com.szemingcheng.amemo.model.SearchListModel;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szemingcheng on 2017/8/2 15:09.
 */

public class SearchListModelImp implements SearchListModel {
    private NoteBKHelper noteBKHelper = DBUtils.getNoteBKDriverHelper();
    private MemoHelper memoHelper = DBUtils.getMemoDriverHelper();
    private UserHelper userHelper = DBUtils.getUserDriverHelper();
    List<NoteBK> noteBKs = new ArrayList<>();
    List<Memo> memos = new ArrayList<>();
    List<SearchResult> searchResults = new ArrayList<>();
    @Override
    public void select_data(String keyword, OnDatFinishedListener onDatFinishedListener) {
        String userid = App.getAppcontext().getUser_ID();
        WhereCondition notebk_titleWhereCondition = NoteBKDao.Properties.Title.like("%" + keyword + "%");
        WhereCondition memo_titleWhereCondition = MemoDao.Properties.Title.like("%" + keyword + "%");
        Log.i("search","keyword:"+keyword);
        if (userid.equals("")){
            noteBKs = noteBKHelper.queryBuilder().where(notebk_titleWhereCondition,
                    NoteBKDao.Properties.User_id.eq(1L)).list();
            memos = memoHelper.queryBuilder().where(memo_titleWhereCondition,
                    MemoDao.Properties.User_ID.eq(1L)).list();
            Log.i("search","notebks:"+noteBKs.size());

            for (NoteBK noteBK:noteBKs){
                if (noteBKs.size()>0) {
                    SearchResult sr = new SearchResult(Long.parseLong(noteBK.getNotebk_id()),
                            noteBK.getTitle(), SearchResult.TYPE_NOTEBK);
                    searchResults.add(sr);
                }
            }
            for (Memo memo:memos){
                if (memos.size()>0) {
                    SearchResult sr = new SearchResult(memo.get_ID(),
                            memo.getTitle(), SearchResult.TYPE_MEMO);
                    searchResults.add(sr);
                }
            }
            onDatFinishedListener.getDataFinish(searchResults);
        }
        else {
            User user = userHelper.queryBuilder()
                    .where(UserDao.Properties.User_id.eq(App.getAppcontext().getUser_ID()))
                    .unique();
            Long user_id = user.get_ID();
            noteBKs = noteBKHelper.queryBuilder().where(notebk_titleWhereCondition,
                    NoteBKDao.Properties.User_id.eq(user_id)).list();
            memos = memoHelper.queryBuilder().where(memo_titleWhereCondition,
                    NoteBKDao.Properties.User_id.eq(user_id)).list();
            for (NoteBK noteBK:noteBKs){
                SearchResult sr = new SearchResult(noteBK.get_ID(),
                        noteBK.getTitle(),SearchResult.TYPE_NOTEBK);
                searchResults.add(sr);
            }
            for (Memo memo:memos){
                SearchResult sr = new SearchResult(memo.get_ID(),
                        memo.getTitle(),SearchResult.TYPE_MEMO);
                searchResults.add(sr);
            }
            onDatFinishedListener.getDataFinish(searchResults);
        }
        Log.i("search","searchresults:"+searchResults.size());
    }
}
