package com.szemingcheng.amemo.view;

import com.szemingcheng.amemo.entity.SearchResult;

import java.util.List;

/**
 * Created by szemingcheng on 2017/8/2 15:34.
 */

public interface SearchListView {
    void loadLatestData(List<SearchResult> searchResults);

}
