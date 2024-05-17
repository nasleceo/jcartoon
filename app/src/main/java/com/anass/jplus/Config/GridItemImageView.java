package com.anass.jplus.Config;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class GridItemImageView extends AppCompatImageView {


    public GridItemImageView(Context context) {
        super(context);
    }

    public GridItemImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridItemImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }
}