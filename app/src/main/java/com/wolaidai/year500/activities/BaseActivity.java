package com.wolaidai.year500.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

/**
 * Created by danielzhang on 15/9/30.
 */
public class BaseActivity extends Activity {

    protected BaseActivity activityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityThis = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void startActivity(Class<?> toClass) {
        activityThis.startActivity(new Intent(activityThis, toClass));
    }

    public void startActivityForResult(Class<?> toClass, int requestCode) {
        activityThis.startActivityForResult(new Intent(activityThis, toClass), requestCode);
    }
}
