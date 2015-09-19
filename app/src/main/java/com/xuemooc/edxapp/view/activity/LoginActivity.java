package com.xuemooc.edxapp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.utils.ProgressButtonUtil;

/**
 * Login Page Activity
 * Created by chaossss on 2015/8/7.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private Button regist;

    private ProgressButtonUtil pbUtil;
    private CircularProgressButton login;

    private TextView forgetPsd;

    private EditText psd;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initParam();
    }

    private void initView(){
        regist = (Button) findViewById(R.id.login_regist);
        login = (CircularProgressButton) findViewById(R.id.login_btn);

        forgetPsd = (TextView) findViewById(R.id.login_forget_psd);

        psd = (EditText) findViewById(R.id.login_userpsd_input);
        email = (EditText) findViewById(R.id.login_username_input);

        setClickListener();
    }

    private void setClickListener(){
        login.setOnClickListener(this);
        regist.setOnClickListener(this);
        forgetPsd.setOnClickListener(this);
    }

    private void initParam(){
        pbUtil = new ProgressButtonUtil(login);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_forget_psd:
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
                break;

            case R.id.login_regist:
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
                break;

            case R.id.login_btn:
                String psdStr = psd.getText().toString();
                String uidStr = email.getText().toString();

                if(!pbUtil.isProgressing()){
                    pbUtil.progress();
                }else{
                    pbUtil.finishProgress();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

                break;
        }
    }
}
