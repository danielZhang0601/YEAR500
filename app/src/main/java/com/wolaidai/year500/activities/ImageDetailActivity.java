package com.wolaidai.year500.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wolaidai.year500.R;
import com.wolaidai.year500.protocols.YearsAPI;

import org.apache.http.Header;

/**
 * Created by danielzhang on 15/12/7.
 */
public class ImageDetailActivity extends BaseActivity implements View.OnClickListener {

    private ProgressBar pb_image_detail;
    private ImageView iv_image_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        findViewById(R.id.iv_activity_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_activity_title)).setText(R.string.image_detail_title);
        ((TextView) findViewById(R.id.tv_activity_done_text)).setText(R.string.image_detail_done_text);
        findViewById(R.id.rl_activity_done).setOnClickListener(this);
        pb_image_detail = (ProgressBar) findViewById(R.id.pb_image_detail);
        iv_image_detail = (ImageView) findViewById(R.id.iv_image_detail);
        YearsAPI.getImage(activityThis, getIntent().getStringExtra(getString(R.string.collection_image_url)), new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                pb_image_detail.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                if (statusCode == 200) {
                    BitmapFactory bitmapFactory = new BitmapFactory();
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inJustDecodeBounds = true;
                    bitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);
                    opt.inSampleSize = Math.max(opt.outWidth / (iv_image_detail.getWidth() == 0 ? 200 : iv_image_detail.getWidth()), opt.outHeight / (iv_image_detail.getHeight() == 0 ? 200 : iv_image_detail.getHeight()));
                    opt.inJustDecodeBounds = false;
                    Bitmap bitmap = bitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);
                    iv_image_detail.setImageBitmap(bitmap);
                    iv_image_detail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
                pb_image_detail.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_activity_back:
                onBackPressed();
                break;
            case R.id.rl_activity_done:
                //跳转到相机
                break;
        }
    }
}
