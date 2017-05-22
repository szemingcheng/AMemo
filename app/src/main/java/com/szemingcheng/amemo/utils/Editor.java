package com.szemingcheng.amemo.utils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by Jaygren on 2017/5/16.
 */

public class Editor implements EditorUtilInterface {

    @Override
    public void openAlbum(FragmentActivity fragmentActivity) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        //读取图片相册
        intent.setType("image/*");
        fragmentActivity.startActivityForResult(intent, 1);
    }

    @Override
    public File outputImage(File CacheDir, String path) {
        File outputImage = new File(CacheDir, path);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputImage;
    }


    @TargetApi(19)
    @Override
    public void handleImageOnKitKat(Intent data, File outputImage, File CacheDir, String path, RichEditor mEditor, Context context) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            //解析封装图片Uri格式
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImapagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, context);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads//public_downloads"), Long.valueOf(docId));
                imagePath = getImapagePath(contentUri, null, context);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImapagePath(uri, null, context);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath, outputImage, CacheDir, path, mEditor, context);//根据图片路径显示图片
    }

    @Override
    public void handleImageBeforeKitKat(Intent data, File outputImage, File CacheDir, String path, RichEditor mEditor, Context context) {
        Uri uri = data.getData();
        String imagePath = getImapagePath(uri, null, context);
        displayImage(imagePath, outputImage, CacheDir, path, mEditor, context);
    }

    @Override
    public String getImapagePath(Uri uri, String selection, Context context) {
        String path = null;
        Cursor custor = context.getContentResolver().query(uri, null, selection, null, null);
        if (custor != null) {
            if (custor.moveToFirst()) {
                path = custor.getString(custor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            custor.close();
        }
        return path;
    }

    @Override
    public void displayImage(String imagePath, File outputImage, File CacheDir, String path, RichEditor mEditor, Context context) {
        if (imagePath != null) {
            outputImage = outputImage(CacheDir, path);
            String outoutImagePath = outputImage.getPath();
            //复制照片
            copyFile(imagePath, outoutImagePath);
            mEditor.insertImage(outoutImagePath + "\" style=\"width:100%;", "alt");
        } else {
            Toast.makeText(context, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件不存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }
}
