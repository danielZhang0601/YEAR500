package com.wolaidai.year500.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.protocols.YearsAPI;
import com.wolaidai.year500.widgets.LoadingImageView;

import org.apache.http.Header;
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

    private ProgressDialog loadDialog;

    private boolean isCreate = false;
    private String collectionId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);

        liv_collection_detail_1 = (LoadingImageView)findViewById(R.id.liv_collection_detail_1);
        liv_collection_detail_2 = (LoadingImageView)findViewById(R.id.liv_collection_detail_2);
        liv_collection_detail_3 = (LoadingImageView)findViewById(R.id.liv_collection_detail_3);
        liv_collection_detail_4 = (LoadingImageView)findViewById(R.id.liv_collection_detail_4);
        liv_collection_detail_5 = (LoadingImageView)findViewById(R.id.liv_collection_detail_5);
        liv_collection_detail_6 = (LoadingImageView)findViewById(R.id.liv_collection_detail_6);

        collectionId = getIntent().getStringExtra(getString(R.string.collection_id));
        isCreate = getIntent().getBooleanExtra(getString(R.string.create_collection), false);

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
        loadDialog = new ProgressDialog(activityThis);
        loadDialog.setMessage(getString(R.string.detail_item_info_loading));
        loadDialog.setCancelable(false);
        loadDialog.show();
        YearsAPI.getCollectionDetail(activityThis, collectionId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                updateView(response);
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

    }


    private void updateView(JSONObject response) {

    }
}
