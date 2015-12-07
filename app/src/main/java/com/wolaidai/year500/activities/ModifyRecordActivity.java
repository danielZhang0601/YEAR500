package com.wolaidai.year500.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.protocols.YearsAPI;

import org.apache.http.Header;

/**
 * Created by danielzhang on 15/12/3.
 */
public class ModifyRecordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_modify_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_text);
        findViewById(R.id.iv_activity_back).setOnClickListener(this);
        findViewById(R.id.rl_activity_done).setVisibility(View.VISIBLE);
        findViewById(R.id.rl_activity_done).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.detail_item_record);
        ((TextView) findViewById(R.id.tv_activity_done_text)).setText(R.string.modify_title_done_text);
        et_modify_text = (EditText) findViewById(R.id.et_modify_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        et_modify_text.setText(getIntent().getStringExtra(getString(R.string.collection_record)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_activity_back:
                onBackPressed();
                break;
            case R.id.rl_activity_done:
                updateRecord();
                break;
        }
    }

    private void updateRecord() {
//        YearsAPI.updateRecord(activityThis, getIntent().getStringExtra(getString(R.string.collection_id)), getString(R.string.detail_item_record), et_modify_text.getText().toString(), new TextHttpResponseHandler() {
        YearsAPI.updateRecord(activityThis, "S1440165538460", getString(R.string.detail_item_record), et_modify_text.getText().toString(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(activityThis, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Toast.makeText(activityThis, s, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

}
