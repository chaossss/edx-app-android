package com.xuemooc.edxapp.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xuemooc.edxapp.R;

/**
 * Created by chaossss on 2015/8/7.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private Button login;

    private TextView regist;
    private TextView forgetPsd;

    private EditText psd;
    private EditText uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){
        login = (Button) findViewById(R.id.login_btn);

        regist = (TextView) findViewById(R.id.header_button);
        regist.setVisibility(View.VISIBLE);

        forgetPsd = (TextView) findViewById(R.id.login_forget);

        psd = (EditText) findViewById(R.id.login_userpsd_input);
        uid = (EditText) findViewById(R.id.login_username_input);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.header_button:
                break;

            case R.id.login_forget:
                break;

            case R.id.login_btn:
                String uidStr = uid.getText().toString();
                String psdStr = psd.getText().toString();

                break;
        }
    }
}
