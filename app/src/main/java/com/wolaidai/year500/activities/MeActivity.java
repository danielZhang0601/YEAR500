package com.wolaidai.year500.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wolaidai.year500.R;

/**
 * Created by danielzhang on 15/12/8.
 */
public class MeActivity extends BaseActivity implements View.OnClickListener {

    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MeActivity.class);
        if (null != bundle)
            intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        findViewById(R.id.iv_activity_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.main_button_me);
        findViewById(R.id.btn_me_account_info).setOnClickListener(this);
        findViewById(R.id.btn_me_modify_password).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_activity_back:
                onBackPressed();
                break;
            case R.id.btn_me_account_info:
                AccountInfoActivity.launch(activityThis, null);
                break;
            case R.id.btn_me_modify_password:
                break;
        }
    }
}
