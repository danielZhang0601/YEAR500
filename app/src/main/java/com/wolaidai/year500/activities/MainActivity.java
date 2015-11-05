package com.wolaidai.year500.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wolaidai.year500.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.iv_activity_back).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.main_title);
        // init view
        findViewById(R.id.btn_main_learn).setOnClickListener(this);
        findViewById(R.id.btn_main_mine).setOnClickListener(this);
        findViewById(R.id.btn_main_service).setOnClickListener(this);
        findViewById(R.id.btn_main_show).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_learn:
            case R.id.btn_main_service:
            case R.id.btn_main_show:
                Toast.makeText(activityThis, R.string.to_be_continued, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_main_mine:
                MineActivity.launch(v.getContext(), null);
                break;
        }
    }
}
