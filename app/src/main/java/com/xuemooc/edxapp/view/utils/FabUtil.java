package com.xuemooc.edxapp.view.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.custom.CheckableFrameLayout;

/**
 * Created by chaossss on 2015/9/22.
 */
public class FabUtil {
    private static final int ANIMATION_DURATION = 200;
    private static final int[] STATE_UNCHECKED = new int[]{};
    private static final int[] STATE_CHECKED = new int[]{android.R.attr.state_checked};

    private volatile static FabUtil fabUtil;

    private Context context;
    private boolean isAdded;

    private Handler handler;

    private FabUtil(Context context) {
        isAdded = false;
        this.context = context;
        handler = new Handler();
    }

    public static FabUtil getFabUtil(Context context){
        if(fabUtil == null){
            synchronized (FabUtil.class){
                if(fabUtil == null){
                    fabUtil = new FabUtil(context);
                }
            }
        }

        return fabUtil;
    }

    public void addCourse(CheckableFrameLayout fab) {
        isAdded = !isAdded;

        fab.setChecked(isAdded);

        ImageView iconView = (ImageView) fab.findViewById(R.id.class_detail_fab_icon);
        setOrAnimatePlusCheckIcon(iconView, isAdded);
        fab.setContentDescription(context.getString(isAdded
                ? R.string.class_detail_remove
                : R.string.class_detail_add));
    }

    private void setOrAnimatePlusCheckIcon(final ImageView imageView, boolean isCheck) {
        if (!hasL()) {
            compatSetOrAnimatePlusCheckIcon(imageView, isCheck);
            return;
        }

        Drawable drawable = imageView.getDrawable();
        if (!(drawable instanceof AnimatedStateListDrawable)) {
            drawable = context.getResources().getDrawable(R.drawable.add_schedule_fab_icon_anim);
            imageView.setImageDrawable(drawable);
        }
        imageView.setColorFilter(isCheck ?
                context.getResources().getColor(R.color.theme_accent_1) : Color.WHITE);

        imageView.setImageState(isCheck ? STATE_UNCHECKED : STATE_CHECKED, false);
        drawable.jumpToCurrentState();
        imageView.setImageState(isCheck ? STATE_CHECKED : STATE_UNCHECKED, false);
    }

    private static boolean hasL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    private void compatSetOrAnimatePlusCheckIcon(final ImageView imageView, boolean isCheck) {

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

        if (isCheck) {
            Animator outAnimator = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0f);
            outAnimator.setDuration(ANIMATION_DURATION / 2);
            outAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    imageView.setImageResource(imageResId);
                }
            });

            AnimatorSet inAnimator = new AnimatorSet();
            outAnimator.setDuration(ANIMATION_DURATION);
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
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(imageResId);
                }
            });
        }
    }
}
