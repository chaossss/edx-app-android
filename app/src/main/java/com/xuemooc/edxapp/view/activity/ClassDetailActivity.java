package com.xuemooc.edxapp.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.custom.CheckableFrameLayout;
import com.xuemooc.edxapp.view.utils.SystemBarTintManager;

/**
 * Created by chaossss on 2015/9/17.
 */
public class ClassDetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks, View.OnClickListener{
    private static final int[] STATE_CHECKED = new int[]{android.R.attr.state_checked};
    private static final int[] STATE_UNCHECKED = new int[]{};

    private View previewImg;
    private Toolbar toolbar;
    private CheckableFrameLayout fabLayout;
    private ObservableScrollView mScrollView;

    private Handler mHandler = new Handler();

    private boolean isAdded;
    private int parallaxImgHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        SystemBarTintManager sm = new SystemBarTintManager(this);
        sm.setStatusBarTintEnabled(true);
        sm.setStatusBarTintResource(R.color.colorPrimaryDark);

        initView();
        initParam();
    }

    private void initView(){
        previewImg = findViewById(R.id.image);

        toolbar = (Toolbar)findViewById(R.id.class_detail_toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        fabLayout = (CheckableFrameLayout) findViewById(R.id.class_detail_fab);
        fabLayout.setOnClickListener(this);
    }

    private void initParam(){
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
                isAdded = !isAdded;
                showStarred(isAdded, true);
                break;

            default:
                finish();
                break;
        }
    }

    private void showStarred(boolean starred, boolean allowAnimate) {
        isAdded = starred;

        fabLayout.setChecked(isAdded, allowAnimate);

        ImageView iconView = (ImageView) fabLayout.findViewById(R.id.class_detail_fab_icon);
        setOrAnimatePlusCheckIcon(iconView, starred, allowAnimate);
        fabLayout.setContentDescription(getString(starred
                ? R.string.class_detail_remove
                : R.string.class_detail_add));
    }

    private void setOrAnimatePlusCheckIcon(final ImageView imageView, boolean isCheck,
                                          boolean allowAnimate) {
        if (!hasL()) {
            compatSetOrAnimatePlusCheckIcon(imageView, isCheck, allowAnimate);
            return;
        }

        Drawable drawable = imageView.getDrawable();
        if (!(drawable instanceof AnimatedStateListDrawable)) {
            drawable = this.getResources().getDrawable(R.drawable.add_schedule_fab_icon_anim);
            imageView.setImageDrawable(drawable);
        }
        imageView.setColorFilter(isCheck ?
                this.getResources().getColor(R.color.theme_accent_1) : Color.WHITE);
        if (allowAnimate) {
            imageView.setImageState(isCheck ? STATE_UNCHECKED : STATE_CHECKED, false);
            drawable.jumpToCurrentState();
            imageView.setImageState(isCheck ? STATE_CHECKED : STATE_UNCHECKED, false);
        } else {
            imageView.setImageState(isCheck ? STATE_CHECKED : STATE_UNCHECKED, false);
            drawable.jumpToCurrentState();
        }
    }

    private static boolean hasL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    private void compatSetOrAnimatePlusCheckIcon(final ImageView imageView, boolean isCheck,
                                                boolean allowAnimate) {

        final int imageResId = isCheck
                ? R.drawable.add_schedule_button_icon_checked
                : R.drawable.add_schedule_button_icon_unchecked;

        if (imageView.getTag() != null) {
            if (imageView.getTag() instanceof Animator) {
                Animator anim = (Animator) imageView.getTag();
                anim.end();
                imageView.setAlpha(1f);
            }
        }

        if (allowAnimate && isCheck) {
            int duration = this.getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            Animator outAnimator = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0f);
            outAnimator.setDuration(duration / 2);
            outAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    imageView.setImageResource(imageResId);
                }
            });

            AnimatorSet inAnimator = new AnimatorSet();
            outAnimator.setDuration(duration);
            inAnimator.playTogether(
                    ObjectAnimator.ofFloat(imageView, View.ALPHA, 1f),
                    ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0f, 1f),
                    ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 0f, 1f)
            );

            AnimatorSet set = new AnimatorSet();
            set.playSequentially(outAnimator, inAnimator);
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    imageView.setTag(null);
                }
            });
            imageView.setTag(set);
            set.start();
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(imageResId);
                }
            });
        }
    }
}
