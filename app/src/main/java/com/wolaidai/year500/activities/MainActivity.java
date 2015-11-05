package com.wolaidai.year500.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wolaidai.year500.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_main_mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.iv_activity_back).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.main_title);
        // init view
        btn_main_mine = (Button) findViewById(R.id.btn_main_mine);

        // init listener
        btn_main_mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_mine:
                MineActivity.launch(v.getContext(), null);
                break;
        }
    }
}
