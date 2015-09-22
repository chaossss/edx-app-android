package com.xuemooc.edxapp.view.utils;

import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.dd.CircularProgressButton;
import com.xuemooc.edxapp.view.consts.PBConst;

/**
 * Util used to help CircularProgressButton do its job
 * Created by chaossss on 2015/9/19.
 */
public class ProgressButtonUtil {
    private int progress;
    private boolean isProgressing;

    private CircularProgressButton pb;
    private ValueAnimator valueAnimator;

    public ProgressButtonUtil(CircularProgressButton pb){
        initParam();
        this.pb = pb;
    }

    private void initParam(){
        progress = 0;
        isProgressing = false;
    }

    public boolean isProgressing(){
        return isProgressing;
    }

    public void progress(){
        if(!isProgressing){
            isProgressing = true;
            valueAnimator = ValueAnimator.ofInt(1, 99, 1);
            valueAnimator.setDuration(2500);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    progress = ((Integer) animation.getAnimatedValue()).intValue();
                    pb.setProgress(progress);
                }
            });
            valueAnimator.start();
        }
    }

    public void updatePBState(PBConst state){
        if(state == PBConst.PB_LOGIN_SUCCESS || state == PBConst.PB_INIT){
            valueAnimator.end();
            pb.setProgress(state.getPBState());
        }else{
            valueAnimator.end();
            pb.setProgress(state.getPBState());
            pb.setErrorText(state.getPBString());
        }
    }
}
