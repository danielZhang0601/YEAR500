package com.wolaidai.year500.activities;

import android.os.Bundle;
import android.os.Handler;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.protocols.YearsAPI;
import com.wolaidai.year500.utils.SharedPreferencesHelper;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by danielzhang on 15/10/29.
 */
public class LaunchActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPreferencesHelper.getBoolean(activityThis, getString(R.string.app_name), getString(R.string.is_first_use) ,true)) {
                    startActivity(WelcomeActivity.class);
                    activityThis.finish();
                } else {
                    String account = SharedPreferencesHelper.getString(activityThis, getResources().getString(R.string.app_name), getString(R.string.account), "");
                    String password = SharedPreferencesHelper.getString(activityThis, getResources().getString(R.string.app_name), getString(R.string.password), "");
                    if (!account.isEmpty() && !password.isEmpty() && account.matches("^1[3|4|5|8][0-9]\\d{4,8}$") && password.matches("^[a-zA-Z0-9]{4,20}")) {
                        YearsAPI.login(activityThis, account, password, new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                startActivity(MainActivity.class);
                                activityThis.finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                startActivity(SignInActivity.class);
                                activityThis.finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                startActivity(SignInActivity.class);
                                activityThis.finish();
                            }
                        });
                    } else {
                        startActivity(SignInActivity.class);
                        activityThis.finish();
                    }
                }
            }
        },1500);
    }
}
