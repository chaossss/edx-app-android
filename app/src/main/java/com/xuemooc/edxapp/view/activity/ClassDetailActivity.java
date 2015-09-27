package com.xuemooc.edxapp.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.custom.CheckableFrameLayout;
import com.xuemooc.edxapp.view.utils.FabUtil;
import com.xuemooc.edxapp.view.utils.SystemBarTintManager;

/**
 * Created by chaossss on 2015/9/17.
 */
public class ClassDetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks, View.OnClickListener{
    private View previewImg;
    private Toolbar toolbar;
    private CheckableFrameLayout fab;
    private ObservableScrollView mScrollView;

    private int parallaxImgHeight;
    private FabUtil fabUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        initView();
        initParam();
    }

    private void initView(){
        initStatusBar();
        initToolBar();

        previewImg = findViewById(R.id.class_detail_image);

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        fab = (CheckableFrameLayout) findViewById(R.id.class_detail_fab);
        fab.setOnClickListener(this);
    }

    private void initStatusBar(){
        SystemBarTintManager sm = new SystemBarTintManager(this);
        sm.setStatusBarTintEnabled(true);
        sm.setStatusBarTintResource(R.color.colorPrimaryDark);
    }

    private void initToolBar(){
        toolbar = (Toolbar)findViewById(R.id.class_detail_toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
    }

    private void initParam(){
        fabUtil = FabUtil.getFabUtil();
        parallaxImgHeight = getResources().getDimensionPixelSize(R.dimen.activity_detail_toolbar_height);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / parallaxImgHeight);
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(previewImg, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.class_detail_fab:
                fabUtil.addCourse(this, fab);
                break;

            default:
                finish();
                break;
        }
    }
}
