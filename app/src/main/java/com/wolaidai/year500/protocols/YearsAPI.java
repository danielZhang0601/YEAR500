package com.wolaidai.year500.protocols;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wolaidai.year500.networks.ZAsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danielzhang on 15/10/8.
 */
public class YearsAPI {

    public static String BASE_URL = "http://172.30.7.126:53333/500nian";

    public static void reg(Context context, String account, String password, AsyncHttpResponseHandler responseHandler) {
        String url = String.format("%s/userreg?userphone=%s&userpwd=%s&useremail=%s&userequ1=%s",
                BASE_URL, account, password, "", "");
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void login(Context context, String account, String password, AsyncHttpResponseHandler responseHandler) {
        String url = String.format("%s/userlogin?userphone=%s&userpwd=%s", BASE_URL, account, password);
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void logout(Context context, String account, AsyncHttpResponseHandler responseHandler) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userphone", account);
            ZAsyncHttpClient.post(context, BASE_URL + "?action=userlogout", jsonObject.toString(), responseHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getDynasts(Context context, AsyncHttpResponseHandler responseHandler) {
        String url = String.format("%s/getdynastydict", BASE_URL);
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void getCollections(Context context, String userId, String account, AsyncHttpResponseHandler responseHandler) {
        String url = String.format("%s/usersouhome?userid=%s&userphone=%s", BASE_URL, userId, account);
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void getImage(Context context, String url, AsyncHttpResponseHandler responseHandler) {
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void getCollectionDetail(Context context, String collectionId, AsyncHttpResponseHandler responseHandler) {
        String url = String.format("%s/souinfo?souid=%s", BASE_URL, collectionId);
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void addCollection() {
//        /createsou?userid=U1440139979366
    }

    public static void updateCollectionByType(Context context, String collectionId, String typeId, AsyncHttpResponseHandler responseHandler) {
        String url = String.format("%s/souchargetype?id=%s&souvenirtypeid=%s", BASE_URL, collectionId, typeId);
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void deleteCollection(Context context, String collectionId, AsyncHttpResponseHandler responseHandler) {
        String url = String.format("%s/deletesou?souid=%s", BASE_URL, collectionId);
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void addCollectionType(Context context, String typeName, String typeId, AsyncHttpResponseHandler responseHandler) {
//        /createsoutype?soutypename=珠子&userid=U1440139979366
        String url = String.format("%s/createsoutype?soutypename=%s&userid=%s", BASE_URL, typeName, typeId);
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void updateCollectionType(Context context, String typeId, String typeName, AsyncHttpResponseHandler responseHandler) {
//        /updatesoutype?id=ST1440144370686&typename=玉器
        String url = String.format("%s/updatesoutype?id=%s&typename=玉器", BASE_URL, typeId, typeName);
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void deleteCollectionType(Context context, String typeId, AsyncHttpResponseHandler responseHandler) {
//        /deletesoutype?soutypeid=ST1440256367658
        String url = String.format("%s/deletesou?souid=%s", BASE_URL, typeId);
        ZAsyncHttpClient.get(context, url, responseHandler);
    }

    public static void findColletion() {

    }

}
