package com.szemingcheng.amemo.ui.unlogin.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

class NoteItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Memo> notes = new ArrayList<>();
    private Context context;
    private OnItemClickListener monItemClickListener;
    NoteItemAdapter(Context mcontext, OnItemClickListener onItemClickListener) {
        this.context=mcontext;
        monItemClickListener=onItemClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_memo_item, parent, false);
        NoteViewHolder vh = new NoteViewHolder(v,monItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Memo mnote=notes.get(position);
        ((NoteViewHolder)holder).memoTitle.setText(mnote.getTitle());
        ((NoteViewHolder)holder).memoContent.setText(mnote.getMemotxt());
        ((NoteViewHolder)holder).updateAt.setText(TimeUtils.getChatTimeStr(mnote.getUpdateat()));

    }
    @Override
    public int getItemCount() {
        if (notes != null) {
            return notes.size();
        } else {
            return 0;
        }
    }
     void addAll(List<Memo> notes) {
       this.notes = notes;
         this.notifyDataSetChanged();
    }
    void clear() {
        int size = notes.size();
        notes.clear();
        notifyItemRangeRemoved(0, size);
    }
    public void remove(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }
    public void insert(Memo note, int position) {
        notes.add(position, note);
        notifyItemInserted(position);
    }
    public Memo getItemData(int position) {
        return notes == null ? null : notes.size() < position ? null : notes.get(position);
    }

    private class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       private OnItemClickListener onItemClickListener;
        private LinearLayout memoItem;
        private TextView memoTitle;
        private TextView updateAt;
        private TextView memoContent;

       private   NoteViewHolder(View view,OnItemClickListener onItemClickListener) {
            super(view);
           this.onItemClickListener = onItemClickListener;
            memoItem = (LinearLayout) view.findViewById(R.id.memo_item);
            memoTitle = (TextView) view.findViewById(R.id.memo_title);
            updateAt = (TextView) view.findViewById(R.id.update_at);
            memoContent = (TextView) view.findViewById(R.id.memo_content);
           memoItem.setOnClickListener(this);
        }

       @Override
       public void onClick(View v) {
           if (null!=onItemClickListener){
               this.onItemClickListener.onItemClick(v, getAdapterPosition());
           }
       }
   }
}
