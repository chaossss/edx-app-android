package com.xuemooc.edxapp.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.custom.SlideSwitch;

/**
 * 设置页面
 * Created by chaossss on 2015/8/5.
 */
public class SettingsActivity extends Activity implements View.OnClickListener,SlideSwitch.SlideListener{
    private TextView back;

    private Button loginOut;

    private SlideSwitch wifiDownloadSwitch;
    private SlideSwitch selectDownloadPosSwitch;

    private RelativeLayout aboutUs;
    private RelativeLayout checkUpdate;
    private RelativeLayout feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initView();
    }

    private void initView(){
        initHeader();

        loginOut = (Button)findViewById(R.id.settings_login_out);

        wifiDownloadSwitch = (SlideSwitch)findViewById(R.id.settings_wifi_download_only_switch);
        selectDownloadPosSwitch = (SlideSwitch)findViewById(R.id.settings_select_download_video_location_switch);

        aboutUs = (RelativeLayout)findViewById(R.id.settings_about_us);
        feedback = (RelativeLayout) findViewById(R.id.settings_feedback);
        checkUpdate = (RelativeLayout)findViewById(R.id.settings_check_update);

        initListener();
    }

    private void initHeader(){
        back = (TextView)findViewById(R.id.header_back);
        back.setVisibility(View.VISIBLE);
    }

    private void initListener(){
        back.setOnClickListener(this);
        loginOut.setOnClickListener(this);

        wifiDownloadSwitch.setSlideListener(this);
        wifiDownloadSwitch.setOnClickListener(this);

        selectDownloadPosSwitch.setSlideListener(this);
        selectDownloadPosSwitch.setOnClickListener(this);

        aboutUs.setOnClickListener(this);
        feedback.setOnClickListener(this);
        checkUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_login_out:
                break;

            case R.id.settings_wifi_download_only_switch:
                if(wifiDownloadSwitch.isOpen()){
                    wifiDownloadSwitch.setState(false);
                }else{
                    wifiDownloadSwitch.setState(true);
                }
                break;

            case R.id.settings_select_download_video_location_switch:
                if(selectDownloadPosSwitch.isOpen()){
                    selectDownloadPosSwitch.setState(false);
                }else{
                    selectDownloadPosSwitch.setState(true);
                }
                break;

            case R.id.settings_about_us:
                break;

            case R.id.settings_check_update:
                break;

            case R.id.settings_feedback:
                break;

            case R.id.header_back:
                this.finish();
                break;
        }
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }
}
