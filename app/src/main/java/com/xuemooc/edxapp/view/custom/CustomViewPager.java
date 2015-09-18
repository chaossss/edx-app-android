package com.xuemooc.edxapp.view.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by chaossss on 2015/8/31.
 */
public class CustomViewPager extends ViewPager {
    private boolean isEnableScroll = false;

    @Override
    public void scrollTo(int x, int y) {
        if(isEnableScroll){
            super.scrollTo(x, y);
        }
    }

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
