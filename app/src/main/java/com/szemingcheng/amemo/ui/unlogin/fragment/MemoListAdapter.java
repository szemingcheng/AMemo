package com.szemingcheng.amemo.ui.unlogin.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szemingcheng on 2017/5/17.
 */

public class MemoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TXT_VIEW = 1;
    private final int PIC_VIEW = 2;
    private final int REMINDER_VIEW = 3;
    private final int EMPTY_TYPE = -1;
    private OnItemClickListener onItemClickListener;
    private Context mcontext;
    private List<Memo> list=new ArrayList<>();

     public MemoListAdapter(Context context, OnItemClickListener onItemClickListener) {
        mcontext = context;
        this.onItemClickListener = onItemClickListener;
        Log.i("adapter","adapter assigned");
    }

     public void setData(List<Memo> data) {
         Log.i("adapter","set data:"+data.size());
        list = data;
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

    public void insertData(Memo memo,int position){
        list.add(position,memo);
        notifyItemInserted(position);
    }

    public Memo getItemData(int position){
        return list == null ? null : list.size() < position ? null : list.get(position);
    }


    @Override
    public int getItemViewType(int position) {
        if (list.size()<=0) return EMPTY_TYPE;
        if (list.get(position).getType() == Memo.TYPE_TXT) {
            return TXT_VIEW;
        } else if (list.get(position).getType() == Memo.TYPE_CAM) {
            return PIC_VIEW;
        } else if (list.get(position).getType() == Memo.TYPE_REMINDER) {
            return REMINDER_VIEW;
        } else {
            Log.i("adapter","default item view type:"+super.getItemViewType(position));
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Log.i("adapter","create view holder,view item:"+viewType);
        if (viewType == TXT_VIEW) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_memo_item, parent, false);
            TextViewHolder vh = new TextViewHolder(view,onItemClickListener);

            return vh;
        } else if (viewType == PIC_VIEW) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_memo_item_pic_txt, parent, false);
            PicViewHolder vh = new PicViewHolder(view,onItemClickListener);
            return vh;
        } else if (viewType == REMINDER_VIEW)  {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_memo_item_reminder, parent, false);
            ReminderViewHolder vh = new ReminderViewHolder(view,onItemClickListener);
            return vh;
        }
        else if (viewType == EMPTY_TYPE){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layput_empty, parent, false);
            EmptyViewHolder vh = new EmptyViewHolder(view,onItemClickListener);
            return vh;
        }
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_error,parent,false);
            ErrorViewHolder vh = new ErrorViewHolder(view,onItemClickListener);
            return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.i("adapter","bind view holder:"+holder);
        if (holder instanceof TextViewHolder) {
            Memo memo = list.get(position);
            ((TextViewHolder)holder).memoTitle.setText(memo.getTitle());
            ((TextViewHolder)holder).memoContent.setText(memo.getMemotxt());
            ((TextViewHolder)holder).memoUpdateat.setText(TimeUtils.getChatTimeStr(memo.getUpdateat()));

        } else if (holder instanceof PicViewHolder) {
            Memo memo = list.get(position);
            ((PicViewHolder) holder).memoTitle.setText(memo.getTitle());
            ((PicViewHolder) holder).memoContent.setText(memo.getMemotxt());
            ((PicViewHolder) holder).memoUpdateat.setText(TimeUtils.getChatTimeStr(memo.getUpdateat()));
            ((PicViewHolder)holder).memoPic.setImageResource(Integer.parseInt(memo.getPic()));
        } else if (holder instanceof ReminderViewHolder) {
            Memo memo = list.get(position);
            ((ReminderViewHolder) holder).memoTitle.setText(memo.getTitle());
            ((ReminderViewHolder) holder).memoContent.setText(memo.getMemotxt());
            ((ReminderViewHolder) holder).memoreminderDate.setText(TimeUtils.getChatTimeStr(memo.getReminder_date()));
        }
        else if (holder instanceof EmptyViewHolder){
            ((EmptyViewHolder)holder).emptyLayout.setVisibility(View.VISIBLE);
        }
        else {
            ((ErrorViewHolder)holder).emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
       // Log.i("adapter","item count:"+list.size());
        if (list != null&&list.size()!=0) {
            return list.size();
        } else if (list.size()>=0){
            return 1;
        }
        else return 0;
    }

    private class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout aMemoItem;
        private TextView memoTitle;
        private TextView memoContent;
        private TextView memoUpdateat;
        private OnItemClickListener onItemClickListener;

        private TextViewHolder(View view, OnItemClickListener onItemClickListener1) {
            super(view);
            onItemClickListener = onItemClickListener1;
            aMemoItem = (LinearLayout) view.findViewById(R.id.memo_item);
            memoTitle = (TextView) view.findViewById(R.id.memo_title);
            memoContent = (TextView) view.findViewById(R.id.memo_content);
            memoUpdateat = (TextView) view.findViewById(R.id.update_at);
            aMemoItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onItemClickListener) {
                this.onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    private class PicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private LinearLayout aMemoItem;
            private TextView memoTitle;
            private TextView memoContent;
            private ImageView memoPic;
            private TextView memoUpdateat;
            private OnItemClickListener onItemClickListener;

            private PicViewHolder(View view, OnItemClickListener onItemClickListener1) {
                super(view);
                onItemClickListener = onItemClickListener1;
                aMemoItem = (LinearLayout) view.findViewById(R.id.memo_item);
                memoTitle = (TextView) view.findViewById(R.id.memo_title);
                memoContent = (TextView) view.findViewById(R.id.memo_content);
                memoPic = (ImageView) view.findViewById(R.id.memo_pic);
                memoUpdateat = (TextView) view.findViewById(R.id.update_at);
                aMemoItem.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    this.onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        }
    private class ReminderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private LinearLayout aMemoItem;
            private TextView memoTitle;
            private TextView memoContent;
            private TextView memoreminderDate;
            private OnItemClickListener onItemClickListener;

            private ReminderViewHolder(View view, OnItemClickListener onItemClickListener1) {
                super(view);
                onItemClickListener = onItemClickListener1;
                aMemoItem = (LinearLayout) view.findViewById(R.id.memo_item);
                memoTitle = (TextView) view.findViewById(R.id.memo_title);
                memoContent = (TextView) view.findViewById(R.id.memo_content);
                memoreminderDate = (TextView) view.findViewById(R.id.memo_reminder_date);
                aMemoItem.setOnClickListener(this);
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
    private class ErrorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FrameLayout emptyLayout;
        private TextView errorView;
        private OnItemClickListener onItemClickListenerl;

        private ErrorViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            onItemClickListenerl = onItemClickListener;
            emptyLayout = (FrameLayout) view.findViewById(R.id.error_layout);
            errorView = (TextView) view.findViewById(R.id.error_view);
        }

        @Override
        public void onClick(View v) {
            if (null != onItemClickListenerl) {
                onItemClickListenerl.onItemClick(v, getAdapterPosition());
            }
        }
    }
    }
