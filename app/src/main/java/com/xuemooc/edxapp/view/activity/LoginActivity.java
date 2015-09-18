package com.xuemooc.edxapp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

/**
 * Login Page Activity
 * Created by chaossss on 2015/8/7.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private Button login;
    private Button regist;

    private TextView forgetPsd;

    private EditText psd;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){
        login = (Button) findViewById(R.id.login_btn);
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
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                break;
        }
    }
}
