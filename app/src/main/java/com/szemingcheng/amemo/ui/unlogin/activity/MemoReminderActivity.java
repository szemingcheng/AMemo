package com.szemingcheng.amemo.ui.unlogin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.presenter.Imp.MemoDetailActivityPresentImp;
import com.szemingcheng.amemo.presenter.MemoDetailActivityPresent;
import com.szemingcheng.amemo.utils.TimeUtils;
import com.szemingcheng.amemo.view.MemoDetailActivityView;

import static com.szemingcheng.amemo.ui.unlogin.activity.MemoDetailActivity.NOTE_SELECT;

/**
 * Created by szemingcheng on 2017/5/26.
 * 没做好
 * 编辑提醒类笔记
 */

public class MemoReminderActivity extends AppCompatActivity implements MemoDetailActivityView {

    public final static String VIEW_MEMO_MODE = "VIEW_MEMO_MODE";
    public final static String CREATE_MEMO_MODE = "CREATE_NOTE_MODE";
    public final static String VIEW_DELETE_MODE = "VIEW_DELETE_MODE";

    private EditText title;
    private TextView notebk_title;
    private ImageView information;
    private Memo memo;
    private Toolbar toolbar;
    private EditText memo_edit;
    private String come_from;
    private String notebk_title_view_mode;
    private MemoDetailActivityPresent memoDetailActivityPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_memo_reminder);
        memoDetailActivityPresent = new MemoDetailActivityPresentImp(this);
        final Intent intent = getIntent();
        come_from = intent.getExtras().getString("comefrom");

        toolbar = (Toolbar)findViewById(R.id.toolbar_in_memo_detail);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        title = (EditText) findViewById(R.id.memo_detail_title);
        notebk_title = (TextView) findViewById(R.id.memo_detail_notebk);
        information = (ImageView)findViewById(R.id.memo_detail_information);
        memo_edit = (EditText)findViewById(R.id.editor);
        switch (come_from){

            case CREATE_MEMO_MODE:
                initViewOnCreateMode();
                memo=new Memo();
                break;
            case VIEW_MEMO_MODE:
                Long id = intent.getExtras().getLong("id");
                memoDetailActivityPresent.load_memo_detail(id);
                break;
            case VIEW_DELETE_MODE:
                Long id2 = intent.getExtras().getLong("id");
                memoDetailActivityPresent.load_memo_detail(id2);
                Toast.makeText(MemoReminderActivity.this,"删除的笔记不可编辑",Toast.LENGTH_LONG).show();
                break;
        }
        toolbar.setNavigationOnClickListener(OnNavigationListener);

    }
    private Toolbar.OnClickListener OnNavigationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        switch (come_from){
            case CREATE_MEMO_MODE:
                menu.findItem(R.id.memo_detail_help).setVisible(true);
                break;
            case VIEW_MEMO_MODE:
                menu.findItem(R.id.memo_detail_edit).setVisible(true);
                menu.findItem(R.id.memo_detail_delete).setVisible(true);
                break;
            case VIEW_DELETE_MODE:
                menu.findItem(R.id.memo_detail_restore).setVisible(true);
                menu.findItem(R.id.memo_detail_remove).setVisible(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memo_detail_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.memo_detail_delete){
            showDeleteDialog();
            return true;
        }
        if (id == R.id.memo_detail_remove){
            showRemoveDialog();
            return true;
        }
        if (id == R.id.memo_detail_restore){
            showRestoreDialog();
            return true;
        }
        if (id == R.id.memo_detail_help){
            showHelperDialog();
            return true;
        }
        if (id == R.id.memo_detail_edit){

        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelperDialog() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MemoReminderActivity.this);
        final View dialogView = LayoutInflater.from(MemoReminderActivity.this)
                .inflate(R.layout.layout_memo_edit_helper,null);
        customizeDialog.setTitle("帮助");
        customizeDialog.setView(dialogView);
        customizeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        customizeDialog.show();
    }

    private void showRestoreDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MemoReminderActivity.this);
        normalDialog.setTitle("恢复");
        normalDialog.setMessage("笔记将会恢复到您的笔记本中");
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                memoDetailActivityPresent.restore_memo(memo.get_ID());

            }
        });
        // 显示
        normalDialog.show();
    }

    private void showRemoveDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MemoReminderActivity.this);
        normalDialog.setTitle("彻底删除");
        normalDialog.setMessage("彻底删除后将不能恢复该笔记");
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                memoDetailActivityPresent.remove_memo(memo.get_ID());

            }
        });
        // 显示
        normalDialog.show();
    }

    private void showDeleteDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MemoReminderActivity.this);
        normalDialog.setTitle("删除");
        normalDialog.setMessage("该笔记将会移到乐色桶");
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                memoDetailActivityPresent.delete_memo(memo.get_ID());

            }
        });
        // 显示
        normalDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case NOTE_SELECT:
                if (resultCode==RESULT_OK){
                    String title = data.getExtras().getString("title");
                    notebk_title.setText(title);
                    Long id = data.getExtras().getLong("id");
                    NoteBK noteBK = App.getAppcontext().getDaoSession().getNoteBKDao().load(id);
                    memo.setNoteBK(noteBK);
                }
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        save_memo();
    }
    private void save_memo() {
        boolean error = false;
        String content = memo_edit.getText().toString();
        String memo_title = title.getText().toString();
        String memo_notebk_title = notebk_title.getText().toString();
        if (TextUtils.isEmpty(content)&&TextUtils.isEmpty(memo_title)) {
            Toast.makeText(MemoReminderActivity.this, "不可以保存一条空笔记", Toast.LENGTH_SHORT).show();
            error = true;
            finish();
        }
        if (TextUtils.isEmpty(memo_notebk_title)){
            notebk_title.setError("点击选择笔记本！");
            error = true;
        }
        if (!error){
            if (come_from.equals(CREATE_MEMO_MODE)) {
                memo.setTitle(memo_title);
                memo.setContext(content);
                memoDetailActivityPresent.save_memo_detail(memo);
            }
            else if (come_from.equals(VIEW_MEMO_MODE)){
                if (content.equals(memo.getContext())&&memo_title.equals(memo.getTitle())
                        &&memo_notebk_title.equals(notebk_title_view_mode)){
                    finish();
                }
                else {
                    if (!content.equals(memo.getContext())) {
                        memo.setContext(memo_edit.getText().toString());
                    }
                    if (!memo_title.equals(memo.getTitle())) {
                        memo.setTitle(title.getText().toString());
                    }
                    memoDetailActivityPresent.update_memo_detail(memo);
                }
            }
            else {
                finish();
            }
        }
    }
    private void showInfomationDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MemoReminderActivity.this);
        normalDialog.setIcon(R.drawable.vector_drawable_i);
        normalDialog.setTitle("笔记信息");
        normalDialog.setMessage("创建时间："+ TimeUtils.getChatTimeStr(memo.getCreatat())+
                "\n最后修改时间："+TimeUtils.getChatTimeStr(memo.getUpdateat()));
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.show();
    }
    @Override
    public void initViewOnViewMode(Memo memo) {
        toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        title.setText(memo.getTitle());
        memo_edit.setText(memo.getContext());
        title.setSelected(false);
        information.setVisibility(View.VISIBLE);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfomationDialog();
            }
        });
        if (memo.getState()==Memo.IS_EXSIT) {
            notebk_title.setText(memo.getNoteBK().getTitle());
            notebk_title_view_mode = memo.getNoteBK().getTitle();
            title.addTextChangedListener(onTextChangedListener);
            notebk_title.addTextChangedListener(onTextChangedListener);
            notebk_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(MemoReminderActivity.this,NoteBKSelectDialogActivity.class);
                    startActivityForResult(intent1,NOTE_SELECT);
                }
            });
            this.memo = memo;
        }
        else{
            notebk_title.setText("已放入乐色桶！");
            title.setEnabled(false);
            memo_edit.setEnabled(false);
            this.memo = memo;
        }
    }

    private TextWatcher onTextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            toolbar.setNavigationIcon(R.drawable.vector_drawable_check);
        }
        @Override
        public void afterTextChanged(Editable s) {
            toolbar.setNavigationIcon(R.drawable.vector_drawable_check);
        }
    };
    @Override
    public void initViewOnCreateMode() {
        toolbar.setNavigationIcon(R.drawable.vector_drawable_check);
        notebk_title.setHint("请选择笔记本");
        notebk_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MemoReminderActivity.this,NoteBKSelectDialogActivity.class);
                startActivityForResult(intent1,NOTE_SELECT);
            }
        });
    }
    @Override
    public void showSaveMemoSuccess() {
        Toast.makeText(MemoReminderActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    public void showSaveMemoFail(String error) {
        Toast.makeText(MemoReminderActivity.this,error,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showDeleteSuccess() {
        Toast.makeText(MemoReminderActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showRestoreSuccess() {
        Toast.makeText(MemoReminderActivity.this,"恢复成功",Toast.LENGTH_SHORT).show();
        finish();
    }
}
