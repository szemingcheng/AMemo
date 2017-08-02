package com.szemingcheng.amemo.ui.unlogin.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.SearchResult;
import com.szemingcheng.amemo.ui.unlogin.fragment.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szemingcheng on 2017/8/2 15:54.
 */

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int MEMO_VIEW = 1;
    private final int NOTEBK_VIEW = 2;
    private final int EMPTY_TYPE = -1;
    private OnItemClickListener onItemClickListener;
    private Context mcontext;
    List<SearchResult> list = new ArrayList<>();
    public SearchListAdapter(OnItemClickListener onItemClickListener, Context mcontext) {
        this.onItemClickListener = onItemClickListener;
        this.mcontext = mcontext;
    }
    public void setData(List<SearchResult> data) {
        list = data;
        this.notifyDataSetChanged();
    }
    public void clear() {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }
    public SearchResult getItemData(int position){
        return list == null ? null : list.size() < position ? null : list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size()<=0) return EMPTY_TYPE;
        if (list.get(position).getType() == SearchResult.TYPE_NOTEBK) {
            return NOTEBK_VIEW;
        }
        else if (list.get(position).getType()==SearchResult.TYPE_MEMO){
            return MEMO_VIEW;
        }
        else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == NOTEBK_VIEW) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_search_result_notebk_item, parent, false);
            NoteBKViewHolder vh = new NoteBKViewHolder(view,onItemClickListener);

            return vh;
        } else if (viewType == MEMO_VIEW) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_search_result_memo_item, parent, false);
            MemoViewHolder vh = new MemoViewHolder(view,onItemClickListener);
            return vh;
        } else if (viewType == EMPTY_TYPE){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layput_empty, parent, false);
            EmptyViewHolder vh = new EmptyViewHolder(view,onItemClickListener);
            return vh;
        }
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layput_empty, parent, false);
        EmptyViewHolder vh = new EmptyViewHolder(view,onItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoteBKViewHolder){
            SearchResult sr = list.get(position);
            ((NoteBKViewHolder)holder).searchResultNotebk.setText(sr.getTitle());
        }else if (holder instanceof MemoViewHolder){
            SearchResult sr = list.get(position);
            ((MemoViewHolder)holder).searchResultMemo.setText(sr.getTitle());
        }else if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).emptyLayout.setVisibility(View.VISIBLE);
        }else {
            ((EmptyViewHolder) holder).emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (list != null&&list.size()!=0) {
            return list.size();
        } else if (list.size()>=0){
            return 1;
        }
        else return 0;
    }
    protected class NoteBKViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout noteBookSelectItem;
        private TextView searchResultNotebk;
        private OnItemClickListener onItemClickListener;
        public NoteBKViewHolder(View view,OnItemClickListener onItemClickListener1) {
            super(view);
            onItemClickListener = onItemClickListener1;
            noteBookSelectItem = (LinearLayout) view.findViewById(R.id.note_book_select_item);
            searchResultNotebk = (TextView) view.findViewById(R.id.search_result_notebk);
            noteBookSelectItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onItemClickListener) {
                this.onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    protected class MemoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout MemoSelectItem;
        private TextView searchResultMemo;
        private OnItemClickListener onItemClickListener;
        public MemoViewHolder(View view,OnItemClickListener onItemClickListener1) {
            super(view);
            onItemClickListener = onItemClickListener1;
            MemoSelectItem = (LinearLayout) view.findViewById(R.id.memo_select_item);
            searchResultMemo = (TextView) view.findViewById(R.id.search_result_memo);
            MemoSelectItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onItemClickListener) {
                this.onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    private class EmptyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FrameLayout emptyLayout;
        private TextView errorView;
        private OnItemClickListener onItemClickListenerl;

        private EmptyViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            onItemClickListenerl = onItemClickListener;
            emptyLayout = (FrameLayout) view.findViewById(R.id.empty_layout);
            errorView = (TextView) view.findViewById(R.id.empty_view);

        }

        @Override
        public void onClick(View v) {
            if (null != onItemClickListenerl) {
                onItemClickListenerl.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
