package com.wolaidai.year500.activities;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.protocols.YearsAPI;
import com.wolaidai.year500.utils.YearDynasty;

import org.apache.http.Header;

/**
 * Created by danielzhang on 15/12/3.
 */
public class ModifyBaseInfoActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatSpinner acs_modify_base_dynasty;
    private EditText et_modify_base_length;
    private EditText et_modify_base_width;
    private EditText et_modify_base_height;
    private EditText et_modify_base_weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_base_info);
        findViewById(R.id.iv_activity_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.modify_title);
        ((TextView) findViewById(R.id.tv_activity_done_text)).setText(R.string.modify_title_done_text);
        findViewById(R.id.rl_activity_done).setOnClickListener(this);
        acs_modify_base_dynasty = (AppCompatSpinner) findViewById(R.id.acs_modify_base_dynasty);
        et_modify_base_length = (EditText) findViewById(R.id.et_modify_base_length);
        et_modify_base_width = (EditText) findViewById(R.id.et_modify_base_width);
        et_modify_base_height = (EditText) findViewById(R.id.et_modify_base_height);
        et_modify_base_weight = (EditText) findViewById(R.id.et_modify_base_weight);
        ArrayAdapter<String> dynastyAdapter = new ArrayAdapter(activityThis, android.R.layout.simple_spinner_item, YearDynasty.getInstance().getNameList());
        acs_modify_base_dynasty.setAdapter(dynastyAdapter);
        updateSpinner();
        et_modify_base_length.setText(getIntent().getStringExtra(getString(R.string.collection_length)));
        et_modify_base_width.setText(getIntent().getStringExtra(getString(R.string.collection_width)));
        et_modify_base_height.setText(getIntent().getStringExtra(getString(R.string.collection_height)));
        et_modify_base_weight.setText(getIntent().getStringExtra(getString(R.string.collection_weight)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_activity_back:
                onBackPressed();
                break;
            case R.id.rl_activity_done:
                updateBaseInfo();
                break;
        }
    }

    private void updateSpinner() {
        String dynasty = getIntent().getStringExtra(getString(R.string.collection_dynasty));
        if (null != dynasty) {
            dynasty = dynasty.trim();
            for (int i = 0; i < YearDynasty.getInstance().getNameList().size(); i++) {
                if (YearDynasty.getInstance().getNameList().get(i).equals(dynasty))
                    acs_modify_base_dynasty.setSelection(i, true);
            }
        }
    }

    private void updateBaseInfo() {
        YearsAPI.updateBaseInfo(activityThis, getIntent().getStringExtra(getString(R.string.collection_id)),
                YearDynasty.getInstance().getNameByIndex(acs_modify_base_dynasty.getSelectedItemPosition()),
                et_modify_base_length.getText().toString(), et_modify_base_width.getText().toString(),
                et_modify_base_height.getText().toString(), et_modify_base_weight.getText().toString(),
                new TextHttpResponseHandler() {

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
