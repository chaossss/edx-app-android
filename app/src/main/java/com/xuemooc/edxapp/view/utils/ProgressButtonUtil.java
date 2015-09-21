package com.xuemooc.edxapp.view.utils;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.dd.CircularProgressButton;

/**
 * Util used to help CircularProgressButton do its job
 * Created by chaossss on 2015/9/19.
 */
public class ProgressButtonUtil {
    private int progress;
    private boolean isProgressing;

    private CircularProgressButton pb;
    private ValueAnimator valueAnimator;

    public enum PBConst implements IpbConst{
        PB_INIT(0),
        PB_LOGINING(50),
        PB_WRONG_PSD("密码错误", -1),
        PB_BAD_WEB("网络环境差", -1),
        PB_LOGIN_SUCCESS("登录成功",100),
        PB_SUBMIT_SUCCESS("提交成功", 100),
        PB_INVALID_USER("无效的用户名", -1);

        private int state;
        private String info;

        PBConst(int state){
            this(null, state);
        }

        PBConst(String info, int state){
            this.info = info;
            this.state = state;
        }

        @Override
        public int getPBState() {
            return this.state;
        }

        @Override
        public String getPBString() {
            return this.info;
        }
    }

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

    public void finishProgress(PBConst state){
        if(isProgressing){
            isProgressing = false;
            if(state != PBConst.PB_LOGIN_SUCCESS){
                Log.v("login", "fail");
                valueAnimator.end();
                pb.setProgress(state.getPBState());
                pb.setErrorText(state.getPBString());
            }else{
                Log.v("login", "success");
                valueAnimator.end();
            }
        }
    }
}
