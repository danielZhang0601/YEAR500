package com.wolaidai.year500.protocols;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wolaidai.year500.networks.ZAsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danielzhang on 15/10/8.
 */
public class YearsAPI {

    public static final String BASE_URL = "http://172.30.7.126:8080/500nian";

    public static void reg(Context context, String account, String password, JsonHttpResponseHandler responseHandler) {
        String url = String.format("%s/userreg?userphone=%s&userpwd=%s&useremail=%s&userequ1=%s",
               BASE_URL, account, password, "", "");
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void login(Context context, String account, String password, JsonHttpResponseHandler responseHandler) {
        String url = String.format("%s/userlogin?userphone=%s&userpwd=%s", BASE_URL, account, password);
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void logout(Context context, String account, JsonHttpResponseHandler responseHandler) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userphone", account);
            ZAsyncHttpClient.post(context, BASE_URL + "?action=userlogout", jsonObject.toString(), responseHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getCollections(Context context, String json, JsonHttpResponseHandler responseHandler) {

    }

    public static void getCollectionDetail() {

    }

    public static void addCollection() {

    }

    public static void updateCollection() {

    }

    public static void deleteCollection() {

    }

    public static void addCollectionType() {

    }

    public static void updateCollectionType() {

    }

    public static void deleteCollectionType() {

    }

    public static void findColletion() {

    }

}
