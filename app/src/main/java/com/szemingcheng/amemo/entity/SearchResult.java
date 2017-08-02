package com.szemingcheng.amemo.entity;

/**
 * Created by szemingcheng on 2017/8/2 16:28.
 */

public class SearchResult {
    public static final int TYPE_NOTEBK=0x21;
    public static final int TYPE_MEMO=0x22;
    private long _id;
    private String title;
    private int type;

    public SearchResult(long _id, String title, int type) {
        this._id = _id;
        this.title = title;
        this.type = type;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
