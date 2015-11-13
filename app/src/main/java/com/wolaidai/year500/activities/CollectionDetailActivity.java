package com.wolaidai.year500.activities;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.protocols.YearsAPI;
import com.wolaidai.year500.widgets.LoadingImageView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by danielzhang on 15/11/10.
 */
public class CollectionDetailActivity extends BaseActivity implements View.OnClickListener {

    private LoadingImageView liv_collection_detail_1;
    private LoadingImageView liv_collection_detail_2;
    private LoadingImageView liv_collection_detail_3;
    private LoadingImageView liv_collection_detail_4;
    private LoadingImageView liv_collection_detail_5;
    private LoadingImageView liv_collection_detail_6;
    private EditText et_collection_detail_length;
    private EditText et_collection_detail_width;
    private EditText et_collection_detail_height;
    private EditText et_collection_detail_weight;
    private EditText et_collection_detail_dynasty;
    private EditText et_collection_detail_record;
    private EditText et_collection_detail_story;
    private LinearLayout ll_collection_detail_scroll_root;

    private ProgressDialog loadDialog;

    private boolean isCreate = true;
    private String collectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);

        findViewById(R.id.iv_activity_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.detail_title);

        liv_collection_detail_1 = (LoadingImageView) findViewById(R.id.liv_collection_detail_1);
        liv_collection_detail_2 = (LoadingImageView) findViewById(R.id.liv_collection_detail_2);
        liv_collection_detail_3 = (LoadingImageView) findViewById(R.id.liv_collection_detail_3);
        liv_collection_detail_4 = (LoadingImageView) findViewById(R.id.liv_collection_detail_4);
        liv_collection_detail_5 = (LoadingImageView) findViewById(R.id.liv_collection_detail_5);
        liv_collection_detail_6 = (LoadingImageView) findViewById(R.id.liv_collection_detail_6);
        ll_collection_detail_scroll_root = (LinearLayout) findViewById(R.id.ll_collection_detail_scroll_root);
        et_collection_detail_length = (EditText) findViewById(R.id.et_collection_detail_length);
        et_collection_detail_width = (EditText) findViewById(R.id.et_collection_detail_width);
        et_collection_detail_height = (EditText) findViewById(R.id.et_collection_detail_height);
        et_collection_detail_weight = (EditText) findViewById(R.id.et_collection_detail_weight);
        et_collection_detail_dynasty = (EditText) findViewById(R.id.et_collection_detail_dynasty);
        et_collection_detail_record = (EditText) findViewById(R.id.et_collection_detail_record);
        et_collection_detail_story = (EditText) findViewById(R.id.et_collection_detail_story);

        collectionId = getIntent().getStringExtra(getString(R.string.collection_id));
        isCreate = getIntent().getBooleanExtra(getString(R.string.create_collection), true);

        if (isCreate) {
            liv_collection_detail_1.setNull();
            liv_collection_detail_2.setNull();
            liv_collection_detail_3.setNull();
            liv_collection_detail_4.setNull();
            liv_collection_detail_5.setNull();
            liv_collection_detail_6.setNull();
        }
        liv_collection_detail_1.setOnClickListener(this);
        liv_collection_detail_2.setOnClickListener(this);
        liv_collection_detail_3.setOnClickListener(this);
        liv_collection_detail_4.setOnClickListener(this);
        liv_collection_detail_5.setOnClickListener(this);
        liv_collection_detail_6.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ll_collection_detail_scroll_root.requestFocus();
        if (null != collectionId && collectionId.isEmpty())
            return;
        loadDialog = new ProgressDialog(activityThis);
        loadDialog.setMessage(getString(R.string.detail_item_info_loading));
        loadDialog.setCancelable(false);
        loadDialog.show();
        YearsAPI.getCollectionDetail(activityThis, collectionId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString(getString(R.string.json_error)).equals(getString(R.string.json_false)))
                        updateView(response.getJSONObject(getString(R.string.json_result)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onFinish() {
                super.onFinish();
                loadDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v instanceof LoadingImageView) {
            int[] location = new int[2];
            ll_collection_detail_scroll_root.getLocationOnScreen(location);
            ((LoadingImageView) v).scale(ll_collection_detail_scroll_root.getWidth(), getWindowManager().getDefaultDisplay().getHeight() - location[1]);
        } else {
            switch (v.getId()) {
                case R.id.iv_activity_back:
                    onBackPressed();
                    break;
            }
        }
    }


    private void updateView(JSONObject response) {
        try {
            String length = response.getJSONObject(getString(R.string.json_sou_base_info)).getString(getString(R.string.json_length));
            String width = response.getJSONObject(getString(R.string.json_sou_base_info)).getString(getString(R.string.json_width));
            String height = response.getJSONObject(getString(R.string.json_sou_base_info)).getString(getString(R.string.json_height));
            String weight = response.getJSONObject(getString(R.string.json_sou_base_info)).getString(getString(R.string.json_weight));
            String dynastyname = response.getJSONObject(getString(R.string.json_sou_base_info)).getString(getString(R.string.json_dynasty_name));
            et_collection_detail_length.setText(length + getString(R.string.detail_item_detail_cm));
            et_collection_detail_width.setText(width + getString(R.string.detail_item_detail_cm));
            et_collection_detail_height.setText(height + getString(R.string.detail_item_detail_cm));
            et_collection_detail_weight.setText(weight + getString(R.string.detail_item_detail_g));
            et_collection_detail_dynasty.setText(dynastyname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
