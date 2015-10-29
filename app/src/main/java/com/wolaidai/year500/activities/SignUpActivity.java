package com.wolaidai.year500.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wolaidai.year500.R;

/**
 * Created by danielzhang on 15/9/30.
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ((TextView)findViewById(R.id.tv_activity_title)).setText(R.string.sign_up_title);
        findViewById(R.id.iv_activity_back).setOnClickListener(this);

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
