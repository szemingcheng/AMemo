package com.szemingcheng.amemo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import java.io.File;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by Jaygren on 2017/5/16.
 */

public interface EditorUtilInterface {

    //打开相册
    public void openAlbum(FragmentActivity fragmentActivity);

    //获取展示照片的所保存uri
    public File outputImage(File CacheDir, String path);

    //4.4以下系统使用这个方法处理图片
    public void handleImageBeforeKitKat(Intent data, File outputImage, File CacheDir, String path, RichEditor mEditor, Context context);

    //4.4及以上的系统使用方法处理图片
    @TargetApi(19)
    public void handleImageOnKitKat(Intent data, File outputImage, File CacheDir, String path, RichEditor mEditor, Context context);



    //获取图片
    String getImapagePath(Uri uri, String selection, Context context);

    //展示图片
    public void displayImage(String imagePath, File outputImage, File CacheDir, String path, RichEditor mEditor, Context context);

    //复制照片
    public void copyFile(String oldPath, String newPath);

}
