package com.szemingcheng.amemo.ui.unlogin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.presenter.Imp.NoteBKListFragmentPresentImp;
import com.szemingcheng.amemo.presenter.NoteBKListFragmentPresent;
import com.szemingcheng.amemo.ui.unlogin.fragment.OnItemClickListener;
import com.szemingcheng.amemo.view.NoteBKListFragmentView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/5/23 14:03.
 */

public class NoteBKSelectDialogActivity extends Activity implements NoteBKListFragmentView,View.OnClickListener {

    private EditText editText;
    private RecyclerView memoDetailNotebkList;
    private NoteBKListFragmentPresent noteBKListFragmentPresent;
    private LayoutMemoDetailNotebkItemAdapter layoutMemoDetailNotebkItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_memo_detail_notebk_list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_in_memo_detail);
        toolbar.setTitle("选择笔记本");
        toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        toolbar.setNavigationOnClickListener(OnNavigationListener);
        noteBKListFragmentPresent = new NoteBKListFragmentPresentImp(this);
        findViewById(R.id.buttonAdd).setOnClickListener(this);
        editText = (EditText) findViewById(R.id.editNoteBK);
        editText.setSelected(false);
        memoDetailNotebkList=(RecyclerView) findViewById(R.id.memo_detail_notebk_list);
        layoutMemoDetailNotebkItemAdapter = new LayoutMemoDetailNotebkItemAdapter
                (NoteBKSelectDialogActivity.this, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("id"
                        ,layoutMemoDetailNotebkItemAdapter.getItemData(position).get_ID());
                intent.putExtra("title"
                        , layoutMemoDetailNotebkItemAdapter.getItemData(position).getTitle());
                NoteBKSelectDialogActivity.this.setResult(RESULT_OK,intent);
                finish();
            }
            @Override
            public void onMoreClick(View view, int position) {}
            @Override
            public void onItemLongClick(View view, int positon) {}
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(NoteBKSelectDialogActivity.this);
        memoDetailNotebkList.setLayoutManager(layoutManager);
        memoDetailNotebkList.setAdapter(layoutMemoDetailNotebkItemAdapter);
        noteBKListFragmentPresent.getMemo(App.getAppcontext().getUser_ID());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                String noteText = editText.getText().toString();
                editText.setText("");
                if (noteText == null || noteText.equals("")) {
                    Toast.makeText(getApplicationContext(),"输入正确的笔记本标题！",Toast.LENGTH_SHORT).show();
                } else {
                    NoteBK noteBK = new NoteBK();
                    noteBK.setTitle(noteText);
                    noteBKListFragmentPresent.add_NoteBK(noteBK);
                }
                break;
        }
    }

    @Override
    public void updateListView(List<NoteBK> noteBKs) {
        layoutMemoDetailNotebkItemAdapter.clear();
        layoutMemoDetailNotebkItemAdapter.setData(noteBKs);
        layoutMemoDetailNotebkItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingIcon() {

    }

    @Override
    public void hideLoadingIcon() {

    }

    @Override
    public void showRecyclerView() {

    }

    @Override
    public void hideRecyclerView() {

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void AddSuccess(NoteBK noteBK) {
        layoutMemoDetailNotebkItemAdapter.insertData(noteBK,0);
        noteBKListFragmentPresent.getMemo(App.getAppcontext().getUser_ID());
    }

    @Override
    public void showDeleteSuccess() {

    }

    @Override
    public void showRenameSuccess() {

    }

    private Toolbar.OnClickListener OnNavigationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}