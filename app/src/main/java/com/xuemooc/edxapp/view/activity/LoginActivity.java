package com.xuemooc.edxapp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xuemooc.edxapp.AppConst;
import com.xuemooc.edxapp.R;

/**
 * Login Page Activity
 * Created by chaossss on 2015/8/7.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private Button regist;
    private CircularProgressButton login;

    private TextView forgetPsd;

    private EditText psd;
    private EditText email;

    private AppConst.LoginConst loginState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initParam();
    }

    private void initView(){
        login = (CircularProgressButton) findViewById(R.id.login_btn);
        login.setIndeterminateProgressMode(true);
        regist = (Button) findViewById(R.id.login_regist);

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
        loginState = AppConst.LoginConst.LOGIN_INIT;
    }

    private void setLoginState(AppConst.LoginConst loginState){
        this.loginState = loginState;
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

                if (login.getProgress() == 0) {
                    loginState = AppConst.LoginConst.LOGIN_LOGINING;
                    login.setProgress(loginState.getLoginState());
                } else if (login.getProgress() != loginState.getLoginState()) {
                    login.setProgress(loginState.getLoginState());
                } else {
                    loginState = AppConst.LoginConst.LOGIN_SUCCESS;
                    finishLogining(loginState);
                }

                break;
        }
    }

    public void finishLogining(AppConst.LoginConst loginingState){
        int state = loginingState.getLoginState();

        switch(loginingState){
            case LOGIN_SUCCESS:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;

            default:
                login.setProgress(state);
                login.setErrorText(loginingState.getErrorInfo());
                break;
        }
    }
}
