package com.xuemooc.edxapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.http.interfaces.ILogin;
import com.xuemooc.edxapp.http.util.LoginUtil;
import com.xuemooc.edxapp.view.utils.ProgressButtonUtil;

/**
 * Login Page Activity
 * Created by chaossss on 2015/8/7.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILogin{
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
                if(!pbUtil.isProgressing()){
                    pbUtil.progress();
                }

                Bundle data = new Bundle();
                Message msg = new Message();

                data.putString("uid", email.getText().toString());
                data.putString("psd", psd.getText().toString());

                msg.setData(data);
                msg.what = LoginUtil.SEND_INFO;

                LoginUtil.getLoginUtil(this).sendRequest(msg);

                break;
        }
    }

    @Override
    public void updateUI(Message msg) {
        Message temp = null;

        switch (msg.what){
            case LoginUtil.LOGIN_SUCCESS:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;

            case LoginUtil.LOGIN_SUCCESS_SHOW:
                showLoginState((ProgressButtonUtil.PBConst)msg.obj);

                temp = new Message();
                temp.what = LoginUtil.LOGIN_SUCCESS;
                LoginUtil.getLoginUtil(this).sendRequest(temp);
                break;

            case LoginUtil.LOGIN_ERROR_SHOW:
                showLoginState((ProgressButtonUtil.PBConst)msg.obj);

                temp = new Message();
                temp.what = LoginUtil.LOGIN_RESET;
                LoginUtil.getLoginUtil(this).sendRequest(temp);
                break;

            case LoginUtil.LOGIN_RESET:
                showLoginState((ProgressButtonUtil.PBConst)msg.obj);
                break;
        }
    }

    public void showLoginState(ProgressButtonUtil.PBConst loginState){
        pbUtil.finishProgress(loginState);
    }
}
