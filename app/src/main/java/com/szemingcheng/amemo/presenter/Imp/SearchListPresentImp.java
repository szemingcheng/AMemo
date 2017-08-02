package com.szemingcheng.amemo.presenter.Imp;

import com.szemingcheng.amemo.entity.SearchResult;
import com.szemingcheng.amemo.model.Imp.SearchListModelImp;
import com.szemingcheng.amemo.model.SearchListModel;
import com.szemingcheng.amemo.presenter.SearchListPresent;
import com.szemingcheng.amemo.view.SearchListView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/8/2 15:33.
 */

public class SearchListPresentImp implements SearchListPresent {
    SearchListModel searchListModel;
    SearchListView searchListView;

    public SearchListPresentImp(SearchListView searchListView) {
        this.searchListModel = new SearchListModelImp();
        this.searchListView = searchListView;
    }

    @Override
    public void get_result(String keyword) {
        searchListModel.select_data(keyword,onDataFinishedListener);
    }

    private SearchListModel.OnDatFinishedListener onDataFinishedListener =
            new SearchListModel.OnDatFinishedListener() {
                @Override
                public void getDataFinish(List<SearchResult> searchResults) {
                    searchListView.loadLatestData(searchResults);
                }

                @Override
                public void onError(String error) {

                }
            };



}
