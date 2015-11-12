package com.wolaidai.year500.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wolaidai.year500.R;

import org.apache.http.Header;

/**
 * Created by danielzhang on 15/11/9.
 */
public class LoadingImageView extends LinearLayout {

    private Context mContext;
    private ImageView iv_loading_image_view;
    private ProgressBar pb_loading_progress_bar;

    private AsyncHttpResponseHandler asyncHttpResponseHandler;

    public LoadingImageView(Context context) {
        this(context, null);

    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        asyncHttpResponseHandler = new ImageAsyncHttpResponseHandler();
        initView();
    }

    public AsyncHttpResponseHandler getAsyncHttpResponseHandler() {
        return asyncHttpResponseHandler;
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.widget_loading_image_view, this);
        iv_loading_image_view = (ImageView) findViewById(R.id.iv_loading_image_view);
        pb_loading_progress_bar = (ProgressBar) findViewById(R.id.pb_loading_progress_bar);
        iv_loading_image_view.setVisibility(View.INVISIBLE);
        pb_loading_progress_bar.setVisibility(View.VISIBLE);
    }

    public class ImageAsyncHttpResponseHandler extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
            if (statusCode == 200)
                setImage(bytes);
            else
                hideImage();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
            hideImage();
        }
    }

    private void setImage(byte[] bytes) {

        //防止 OOM
        BitmapFactory bitmapFactory = new BitmapFactory();
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        bitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);
        opt.inSampleSize = Math.max(opt.outWidth / getWidth(), opt.outHeight / getHeight());
        opt.inJustDecodeBounds = false;
        Bitmap bitmap = bitmapFactory.decodeByteArray(bytes, 0, bytes.length, opt);
        iv_loading_image_view.setImageBitmap(bitmap);
        iv_loading_image_view.setVisibility(View.VISIBLE);
        pb_loading_progress_bar.setVisibility(View.INVISIBLE);
    }

    public void setNull() {
        iv_loading_image_view.setVisibility(View.INVISIBLE);
        pb_loading_progress_bar.setVisibility(View.INVISIBLE);
    }

    private void hideImage() {
        iv_loading_image_view.setVisibility(View.INVISIBLE);
        pb_loading_progress_bar.setVisibility(View.VISIBLE);
    }
}
