package com.szemingcheng.amemo.utils;

import com.szemingcheng.amemo.entity.Response;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Jaygren on 2017/5/24.
 */

public class HttpUtils {
    /**
     * okhttp3统一入口
     * @param requestBody 数据源
     * @param callback 回调数据
     */

    private static Response Response;
    public static void sendRequest( RequestBody requestBody, okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .post(requestBody)
                .url("http://jaygrenyu.oicp.io:41026/AMemo/AMemo.php")
                .build();
        //回调
        client.newCall(request).enqueue(callback);
    }
}
