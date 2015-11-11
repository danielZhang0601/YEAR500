package com.wolaidai.year500.networks;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

@SuppressWarnings("deprecation")
public class ZAsyncHttpClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setTimeout(5000);
        // client.setSSLSocketFactory(sslSocketFactory);
    }

    public static void setCookieStore(PersistentCookieStore store) {
        client.setCookieStore(store);
    }

    /**
     * 异步get方法
     *
     * @param context         传人上下文 用于取消
     * @param url             请求地址
     * @param responseHandler 网络请求返回数据解析Handler
     */
    public static void get(Context context, String url, ResponseHandlerInterface responseHandler) {
        client.get(context, url, responseHandler);
    }

    /**
     * 异步post方法
     *
     * @param context         传入上下文 用于取消请求
     * @param url             请求地址
     * @param json            请求json字符串
     * @param responseHandler 网络请求返回数据解析Handler
     */
    public static void post(Context context, String url, String json, ResponseHandlerInterface responseHandler) {
        StringEntity entity = null;
        try {
            entity = new StringEntity(json, "UTF-8");
            String contentType = "application/json";
            client.post(context, url, entity, contentType, responseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void cancel(Context context, boolean mayInterruptIfRunning) {
        client.cancelRequests(context, mayInterruptIfRunning);
    }

}
