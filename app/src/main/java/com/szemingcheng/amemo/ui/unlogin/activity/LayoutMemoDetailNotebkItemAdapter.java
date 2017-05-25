package com.szemingcheng.amemo.ui.unlogin.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.ui.unlogin.fragment.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class LayoutMemoDetailNotebkItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NoteBK> list=new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public LayoutMemoDetailNotebkItemAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
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
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_memo_detail_notebk_item, parent, false);
        LayoutMemoDetailNotebkItemAdapter.NoteBKViewHolder vh =
                new LayoutMemoDetailNotebkItemAdapter.NoteBKViewHolder(view,onItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoteBK noteBK = list.get(position);
        ((LayoutMemoDetailNotebkItemAdapter.NoteBKViewHolder) holder)
                .noteBookTitle.setText(noteBK.getTitle());
//        ((LayoutMemoDetailNotebkItemAdapter.NoteBKViewHolder) holder)
//                .memoQuantity.setText(String.valueOf((noteBK.getMemos()).size())+"条笔记");

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
        private OnItemClickListener onItemClickListener;
        public NoteBKViewHolder(View view,OnItemClickListener onItemClickListener1) {
            super(view);
            onItemClickListener = onItemClickListener1;
            noteBook = (LinearLayout) view.findViewById(R.id.note_book_select_item);
            noteBookTitle = (TextView) view.findViewById(R.id.note_book_select_title);
            memoQuantity = (TextView) view.findViewById(R.id.note_book_select_quantity);
            noteBook.setOnClickListener(NBitemListener);
        }
        private View.OnClickListener NBitemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        };
    }

}
