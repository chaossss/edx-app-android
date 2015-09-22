package com.xuemooc.edxapp.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.dd.CircularProgressButton;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.utils.ProgressButtonUtil;
import com.xuemooc.edxapp.view.utils.SystemBarTintManager;

/**
 * Created by chaossss on 2015/8/7.
 */
public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;

    private EditText feedback;

    private ProgressButtonUtil pbUtil;
    private CircularProgressButton submit;

    private StringBuilder feedbackSB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        init();
    }

    private void init(){
        initView();
        initParam();
        initToolbar();
        initStatusBar();
        setOnClickListener();
    }

    private void initView(){
        feedback = (EditText) findViewById(R.id.feedback_input);
        submit = (CircularProgressButton) findViewById(R.id.feedback_submit);
    }

    private void initParam(){
        feedbackSB = new StringBuilder();
        pbUtil = new ProgressButtonUtil(submit);
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.feedback_toolbar);
        toolbar.setTitle("");
    }

    private void initStatusBar(){
        SystemBarTintManager sm = new SystemBarTintManager(this);
        sm.setStatusBarTintEnabled(true);
        sm.setStatusBarTintResource(R.color.colorPrimaryDark);
    }

    private void setOnClickListener(){
        submit.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.feedback_submit:
                if(feedbackSB.length() != 0){
                    feedbackSB.delete(0, feedbackSB.length());
                }

                feedbackSB.append(feedback.getText().toString());

                if(!pbUtil.isProgressing()){
                    pbUtil.progress();
                }else{
                    pbUtil.updatePBState(ProgressButtonUtil.PBConst.PB_LOGIN_SUCCESS);
                    finish();
                }

                break;

            default:
                finish();
                break;
        }
    }
}
