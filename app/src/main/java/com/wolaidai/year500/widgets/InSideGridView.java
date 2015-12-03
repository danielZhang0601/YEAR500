package com.wolaidai.year500.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by danielzhang on 15/12/3.
 */
public class InSideGridView extends GridView {
    public InSideGridView(Context context) {
        super(context);
    }

    public InSideGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InSideGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
