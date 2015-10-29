package com.wolaidai.year500.activities;

import android.os.Bundle;
import android.os.Handler;

import com.wolaidai.year500.R;

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
                startActivity(WelcomeActivity.class);
                activityThis.finish();
            }
        },1500);
    }
}
