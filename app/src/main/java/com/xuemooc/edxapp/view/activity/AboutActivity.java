package com.xuemooc.edxapp.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xuemooc.edxapp.R;

/**
 * Created by chaossss on 2015/8/7.
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initToolbar();
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.about_us_toolbar);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setTitle("");
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
