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
public class AccountInfoActivity extends BaseActivity implements View.OnClickListener {

    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, AccountInfoActivity.class);
        if (null != bundle)
            intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        findViewById(R.id.iv_activity_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.me_account_info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_activity_back:
                onBackPressed();
                break;
        }
    }
}
