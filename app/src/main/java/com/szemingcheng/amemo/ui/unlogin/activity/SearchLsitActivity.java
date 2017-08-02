package com.szemingcheng.amemo.ui.unlogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.SearchResult;
import com.szemingcheng.amemo.presenter.Imp.SearchListPresentImp;
import com.szemingcheng.amemo.presenter.SearchListPresent;
import com.szemingcheng.amemo.ui.unlogin.fragment.OnItemClickListener;
import com.szemingcheng.amemo.view.SearchListView;

import java.util.List;

/**
 * Created by szemingcheng on 2017/8/2 15:44.
 */

public class SearchLsitActivity extends AppCompatActivity implements SearchListView,View.OnClickListener {

    private EditText editText;
    private RecyclerView searchList;
    private TextView keyword;
    private SearchListPresent searchListPresent;
    private SearchListAdapter searchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_in_memo_detail);
        toolbar.setTitle("搜索");
        toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        toolbar.setNavigationOnClickListener(OnNavigationListener);
        searchListPresent = new SearchListPresentImp(this);
        findViewById(R.id.buttonAdd).setOnClickListener(this);
        editText = (EditText) findViewById(R.id.editNoteBK);
        editText.setSelected(false);
        keyword = (TextView) findViewById(R.id.search_keyword);
        searchList = (RecyclerView) findViewById(R.id.search_list);
        searchListAdapter = new SearchListAdapter(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                switch (searchListAdapter.getItemData(position).getType()){
                    case SearchResult.TYPE_NOTEBK:
                        intent.setAction("com.activity.MemoListInNBActivity");
                        intent.putExtra("notebk_id",Long.toString(searchListAdapter.getItemData(position).get_id()));
                        intent.putExtra("notebk_title",searchListAdapter.getItemData(position).getTitle());
                        startActivity(intent);
                        break;
                    case SearchResult.TYPE_MEMO:
                        intent.setAction("com.activity.MemoDetailActivity");
                        intent.putExtra("comefrom", MemoDetailActivity.VIEW_MEMO_MODE);
                        intent.putExtra("id",searchListAdapter.getItemData(position).get_id());
                        startActivity(intent);
                        break;
                }
            }
            @Override
            public void onMoreClick(View view, int position) {}
            @Override
            public void onItemLongClick(View view, int positon) {}
        },SearchLsitActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchLsitActivity.this);
        searchList.setLayoutManager(layoutManager);
        searchList.setAdapter(searchListAdapter);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                String noteText = editText.getText().toString();
                editText.setText("");
                if (noteText == null || noteText.equals("")) {
                    Toast.makeText(getApplicationContext(),"输入正确的搜索内容！",Toast.LENGTH_SHORT).show();
                } else {
                    keyword.setText("包含“"+noteText+"”的结果有....");
                    keyword.setVisibility(View.VISIBLE);
                    searchListPresent.get_result(noteText);
                }
                break;
        }
    }
    @Override
    public void loadLatestData(List<SearchResult> searchResults) {
        searchListAdapter.clear();
        searchListAdapter.setData(searchResults);
        searchListAdapter.notifyDataSetChanged();
    }
    private Toolbar.OnClickListener OnNavigationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
