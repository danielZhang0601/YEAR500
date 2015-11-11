package com.wolaidai.year500;

import android.app.Application;

import com.umeng.update.UmengUpdateAgent;

/**
 * Created by danielzhang on 15/9/30.
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UmengUpdateAgent.update(this);
    }
}
