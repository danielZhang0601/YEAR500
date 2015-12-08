package com.wolaidai.year500.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wolaidai.year500.R;

/**
 * Created by danielzhang on 15/12/8.
 */
public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        findViewById(R.id.iv_activity_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.me_modify_password);
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
