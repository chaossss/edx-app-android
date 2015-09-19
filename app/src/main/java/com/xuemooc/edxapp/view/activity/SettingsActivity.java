package com.xuemooc.edxapp.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.custom.SlideSwitch;
import com.xuemooc.edxapp.view.utils.SystemBarTintManager;

/**
 * 设置页面
 * Created by chaossss on 2015/8/5.
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;

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

        SystemBarTintManager sm = new SystemBarTintManager(this);
        sm.setStatusBarTintEnabled(true);
        sm.setStatusBarTintResource(R.color.colorPrimaryDark);

        initView();
    }

    private void initView(){
        initToolbar();

        loginOut = (Button)findViewById(R.id.settings_login_out);

        wifiDownloadSwitch = (SlideSwitch)findViewById(R.id.settings_wifi_download_only_switch);
        selectDownloadPosSwitch = (SlideSwitch)findViewById(R.id.settings_select_download_video_location_switch);

        aboutUs = (RelativeLayout)findViewById(R.id.settings_about_us);
        feedback = (RelativeLayout) findViewById(R.id.settings_feedback);
        checkUpdate = (RelativeLayout)findViewById(R.id.settings_check_update);

        initListener();
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_add_white_24dp);
        toolbar.setTitle(R.string.setting_title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
    }

    private void initListener(){
        loginOut.setOnClickListener(this);

        wifiDownloadSwitch.setOnClickListener(this);
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

            default:
                finish();
                break;
        }
    }
}
