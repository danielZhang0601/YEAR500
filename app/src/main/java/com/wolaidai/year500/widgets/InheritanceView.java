package com.wolaidai.year500.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by danielzhang on 15/11/12.
 */
public class InheritanceView extends View {
    public InheritanceView(Context context) {
        super(context);
    }

    public InheritanceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InheritanceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
