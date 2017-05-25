package com.szemingcheng.amemo.ui.unlogin.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.szemingcheng.amemo.App;
import com.szemingcheng.amemo.R;
import com.szemingcheng.amemo.entity.Memo;
import com.szemingcheng.amemo.entity.NoteBK;
import com.szemingcheng.amemo.presenter.Imp.MemoDetailActivityPresentImp;
import com.szemingcheng.amemo.presenter.MemoDetailActivityPresent;
import com.szemingcheng.amemo.utils.Editor;
import com.szemingcheng.amemo.view.MemoDetailActivityView;

import java.io.File;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by szemingcheng on 2017/5/17.
 */

public class MemoDetailActivity extends AppCompatActivity implements MemoDetailActivityView {
    private RichEditor mEditor;
    private Editor editor;
    public final static String VIEW_MEMO_MODE = "VIEW_MEMO_MODE";
    public final static String CREATE_MEMO_MODE = "CREATE_NOTE_MODE";
    public final static String VIEW_DELETE_MODE = "VIEW_DELETE_MODE";
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int NOTE_SELECT = 3;
    private Uri imageUri;
    public  File outputImage;
    private HorizontalScrollView horizontalScrollView;
    private EditText title;
    private TextView notebk_title;
    private Memo memo;
    private Toolbar toolbar;
    private String come_from;
    private String notebk_title_view_mode;
    private MemoDetailActivityPresent memoDetailActivityPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_memo_detail);
        memoDetailActivityPresent = new MemoDetailActivityPresentImp(this);
        final Intent intent = getIntent();
        come_from = intent.getExtras().getString("comefrom");
         toolbar = (Toolbar)findViewById(R.id.toolbar_in_memo_detail);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        title = (EditText) findViewById(R.id.memo_detail_title);
        notebk_title = (TextView) findViewById(R.id.memo_detail_notebk);

        horizontalScrollView = (HorizontalScrollView)findViewById(R.id.edit_tool);
            editor = new Editor();
            mEditor = (RichEditor) findViewById(R.id.editor);
            initEditor();
            mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
                @Override
                public void onTextChange(String text) {
                    toolbar.setNavigationIcon(R.drawable.vector_drawable_check);
                }
            });
            initAction();
        switch (come_from){

            case CREATE_MEMO_MODE:
                initViewOnCreateMode();
                memo=new Memo();
                break;
            case VIEW_MEMO_MODE:
                Long id = intent.getExtras().getLong("id");
                memoDetailActivityPresent.load_memo_detail(id);
                Toast.makeText(MemoDetailActivity.this,"长按显示编辑工具栏",Toast.LENGTH_LONG).show();
                break;
            case VIEW_DELETE_MODE:
               Long id2 = intent.getExtras().getLong("id");
                memoDetailActivityPresent.load_memo_detail(id2);
                Toast.makeText(MemoDetailActivity.this,"删除的笔记不可编辑",Toast.LENGTH_LONG).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.memo_detail_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.memo_detail_delete){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initEditor() {
        mEditor.setEditorHeight(800);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("单击此输入内容....");
    }

    private void initAction() {
        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });


        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建File对象，用于存储拍照后的图片
                outputImage = editor.outputImage(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");

                //7.0以上，使用FileProvider.getUriForFile调用Uri地址
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(MemoDetailActivity.this, "nullteam.richeditortest.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }

                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        findViewById(R.id.action_insert_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查权限
                if (ContextCompat.checkSelfPermission(MemoDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请sd卡运行权限
                    ActivityCompat.requestPermissions(MemoDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    editor.openAlbum(MemoDetailActivity.this);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //将拍摄的照片显示出来
                    String outputImagePath = outputImage.getPath();
                    mEditor.insertImage(outputImagePath + "\" style=\"width:100%;", "alt");
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上的系统使用方法处理图片
                        editor.handleImageOnKitKat(data,outputImage,getExternalCacheDir(),System.currentTimeMillis() + ".jpg",mEditor,this);
                    } else {
                        //4.4以下系统使用这个方法处理图片
                        editor.handleImageBeforeKitKat(data,outputImage,getExternalCacheDir(),System.currentTimeMillis() + ".jpg",mEditor,this);
                    }
                }
                break;
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
        String content = mEditor.getHtml();
        String memo_title = title.getText().toString();
        String memo_notebk_title = notebk_title.getText().toString();
        if (TextUtils.isEmpty(content)&&TextUtils.isEmpty(memo_title)) {
            Toast.makeText(MemoDetailActivity.this, "不可以保存一条空笔记", Toast.LENGTH_SHORT).show();
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
                        memo.setContext(mEditor.getHtml());
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
    @Override
    public void initViewOnViewMode(Memo memo) {
        toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
        notebk_title.setText(memo.getNoteBK().getTitle());
        notebk_title_view_mode = memo.getNoteBK().getTitle();
        title.setText(memo.getTitle());
        mEditor.setHtml(memo.getContext());
        title.setSelected(false);
        if (memo.getState()==Memo.IS_EXSIT) {
            mEditor.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    horizontalScrollView.setVisibility(View.VISIBLE);
                    return true;
                }
            });
            title.addTextChangedListener(onTextChangedListener);
            notebk_title.addTextChangedListener(onTextChangedListener);
            notebk_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(MemoDetailActivity.this,NoteBKSelectDialogActivity.class);
                    startActivityForResult(intent1,NOTE_SELECT);
                }
            });
            this.memo = memo;
        }
        else{
            title.setEnabled(false);
            mEditor.setInputEnabled(false);
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
        horizontalScrollView.setVisibility(View.VISIBLE);
        notebk_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MemoDetailActivity.this,NoteBKSelectDialogActivity.class);
                startActivityForResult(intent1,NOTE_SELECT);
            }
        });
    }

    @Override
    public void showSaveMemoDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MemoDetailActivity.this).create();
        alertDialog.setTitle("");
    }
    @Override
    public void showSaveMemoSuccess() {
        Toast.makeText(MemoDetailActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    public void showSaveMemoFail(String error) {
        Toast.makeText(MemoDetailActivity.this,error,Toast.LENGTH_SHORT).show();
    }

}
