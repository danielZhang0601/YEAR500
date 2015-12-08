package com.wolaidai.year500.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wolaidai.year500.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private int backPressedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.iv_activity_back).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.main_title);
        // init view
        findViewById(R.id.btn_main_mine).setOnClickListener(this);
        findViewById(R.id.btn_main_me).setOnClickListener(this);
        findViewById(R.id.btn_main_show).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backPressedCount = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_me:
                MeActivity.launch(v.getContext(), null);
                break;
            case R.id.btn_main_show:
                Toast.makeText(activityThis, R.string.to_be_continued, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_main_mine:
                MineActivity.launch(v.getContext(), null);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedCount++ > 0)
            super.onBackPressed();
        else
            Toast.makeText(activityThis, R.string.main_back_press_warning, Toast.LENGTH_SHORT).show();
    }
}
