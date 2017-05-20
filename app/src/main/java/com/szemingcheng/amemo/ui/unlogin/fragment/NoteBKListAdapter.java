package com.szemingcheng.amemo.ui.unlogin.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.NoteBK;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szemingcheng on 2017/5/20.
 */

public class NoteBKListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NoteBK> list=new ArrayList<>();
    private Context context;
    private final int EMPTY_TYPE = -1;
    public NoteBKListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NoteBK> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }
    public void clear() {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }
    public void removeDataItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void insertData(NoteBK noteBK, int position){
        list.add(position,noteBK);
        notifyItemInserted(position);
    }
    NoteBK getItemData(int position){
        return list == null ? null : list.size() < position ? null : list.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size()<=0) return EMPTY_TYPE;
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == EMPTY_TYPE ){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layput_empty, parent, false);
            EmptyViewHolder vh = new EmptyViewHolder(view);
            return vh;
        }
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_notebk_item, parent, false);
        NoteBKViewHolder vh = new NoteBKViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder){
            ((EmptyViewHolder)holder).emptyLayout.setVisibility(View.VISIBLE);
        }
        else {
            NoteBK noteBK = list.get(position);
            ((NoteBKViewHolder) holder).noteBookTitle.setText(noteBK.getTitle());
            ((NoteBKViewHolder) holder).memoQuantity.setText(String.valueOf((noteBK.getMemos()).size())+"条笔记");
            ((NoteBKViewHolder) holder).noteBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点了", Toast.LENGTH_SHORT).show();
                }
            });
            ((NoteBKViewHolder) holder).noteBookMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "more", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list != null&&list.size()!=0) {
            return list.size();
        } else if (list.size()==0){
            return 1;
        }
        else return 0;
    }

    private class NoteBKViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout noteBook;
        private TextView noteBookTitle;
        private TextView memoQuantity;
        private ImageView noteBookMore;

        public NoteBKViewHolder(View view) {
            super(view);
            noteBook = (LinearLayout) view.findViewById(R.id.note_book);
            noteBookTitle = (TextView) view.findViewById(R.id.note_book_title);
            memoQuantity = (TextView) view.findViewById(R.id.memo_quantity);
            noteBookMore = (ImageView) view.findViewById(R.id.note_book_more);
        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout emptyLayout;
        private TextView errorView;
        private EmptyViewHolder(View view) {
            super(view);
            emptyLayout = (FrameLayout) view.findViewById(R.id.empty_layout);
            errorView = (TextView) view.findViewById(R.id.empty_view);
        }
    }
}
