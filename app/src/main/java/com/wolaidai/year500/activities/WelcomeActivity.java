package com.wolaidai.year500.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wolaidai.year500.R;
import com.wolaidai.year500.utils.SharedPreferencesHelper;

/**
 * Created by danielzhang on 15/10/28.
 */
public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager vp_welcome_image;
    private LinearLayout ll_welcome_dots;
    private Button btn_welcome_sign_in_or_up;

    private int[] imgIdArray;
    private ImageView[] mImageViews;
    private ImageView[] dots;
    private ImageView dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        vp_welcome_image = (ViewPager) findViewById(R.id.vp_welcome_image);
        ll_welcome_dots = (LinearLayout) findViewById(R.id.ll_welcome_dots);
        btn_welcome_sign_in_or_up = (Button) findViewById(R.id.btn_welcome_sign_in_or_up);
        btn_welcome_sign_in_or_up.setOnClickListener(this);

        imgIdArray = new int[]{ R.mipmap.page3, R.mipmap.page4, R.mipmap.page5 };

        mImageViews = new ImageView[imgIdArray.length];
        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
        }

        dots = new ImageView[mImageViews.length];
        for (int i = 0; i < mImageViews.length; i++) {
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // 设置每个小圆点距离左边的间距
            margin.setMargins(20, 0, 0, 0);
            dot = new ImageView(activityThis);
            // 设置每个小圆点的宽高
            dot.setLayoutParams(new ViewGroup.LayoutParams(15, 15));
            dots[i] = dot;
            if (i == 0) {
                // 默认选中第一张图片
                dots[i].setBackgroundResource(R.mipmap.circle);
            } else {
                // 其他图片都设置未选中状态
                dots[i].setBackgroundResource(R.mipmap.dotnot);
            }
            ll_welcome_dots.addView(dots[i], margin);
        }
        // 设置Adapter
        vp_welcome_image.setAdapter(new ViewPagerAdapter());
        // 设置监听，主要是设置点点的背景
        vp_welcome_image.addOnPageChangeListener(WelcomeActivity.this);
        // 设置ViewPager的默认项
        vp_welcome_image.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == mImageViews.length - 1)
            btn_welcome_sign_in_or_up.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageSelected(int position) {
        // 遍历数组让当前选中图片下的小圆点设置颜色
        for (int i = 0; i < dots.length; i++) {
            if (i == position)
                dots[i].setBackgroundResource(R.mipmap.circle);
            else
                dots[i].setBackgroundResource(R.mipmap.dotnot);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_welcome_sign_in_or_up:
                SharedPreferencesHelper.putBoolean(activityThis, getString(R.string.app_name), getString(R.string.is_first_use), false);
                startActivity(SignInActivity.class);
                activityThis.finish();
                break;
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageViews.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                container.addView(mImageViews[position % mImageViews.length], 0);
            } catch (Exception e) {
            }
            return mImageViews[position % mImageViews.length];
        }
    }
}
