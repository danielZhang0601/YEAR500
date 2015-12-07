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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by danielzhang on 15/12/3.
 */
public class ModifyStoryActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_modify_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_text);
        findViewById(R.id.iv_activity_back).setOnClickListener(this);
        findViewById(R.id.rl_activity_done).setVisibility(View.VISIBLE);
        findViewById(R.id.rl_activity_done).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.detail_item_story);
        ((TextView) findViewById(R.id.tv_activity_done_text)).setText(R.string.modify_title_done_text);
        et_modify_text = (EditText) findViewById(R.id.et_modify_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        et_modify_text.setText(getIntent().getStringExtra(getString(R.string.collection_story)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_activity_back:
                onBackPressed();
                break;
            case R.id.rl_activity_done:
                updateStory();
                break;
        }
    }

    private void updateStory() {
        List<String> stroies = Arrays.asList(et_modify_text.getText().toString().split("\n"));
//        YearsAPI.updateRecord(activityThis, getIntent().getStringExtra(getString(R.string.collection_story)), getString(R.string.detail_item_story), stroies, new TextHttpResponseHandler() {
        YearsAPI.updateRecord(activityThis, "S1440165538460", getString(R.string.detail_item_story), stroies, new TextHttpResponseHandler() {
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
