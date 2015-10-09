package com.xuemooc.edxapp.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.custom.CheckableFrameLayout;
import com.xuemooc.edxapp.view.utils.FabUtil;
import com.xuemooc.edxapp.view.utils.SystemBarTintManager;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by chaossss on 2015/9/17.
 */
public class ClassDetailActivity extends AppCompatActivity implements ObservableScrollViewCallbacks, View.OnClickListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener{
    private Toolbar toolbar;

    private Uri uri;
    private ProgressBar pb;
    private VideoView videoView;
    private TextView downloadRateView, loadRateView;
    private String path = "http://www.modrails.com/videos/passenger_nginx.mov";

    private CheckableFrameLayout fab;
    private ObservableScrollView mScrollView;

    private int parallaxImgHeight;
    private FabUtil fabUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!LibsChecker.checkVitamioLibs(this)){
            return;
        }
        setContentView(R.layout.activity_class_detail);

        initView();
        initParam();
    }

    private void initView(){
        initStatusBar();
        initToolBar();
        initVideoView();
        initUI();
    }

    private void initStatusBar(){
        SystemBarTintManager sm = new SystemBarTintManager(this);
        sm.setStatusBarTintEnabled(true);
        sm.setStatusBarTintResource(R.color.colorPrimaryDark);
    }

    private void initToolBar(){
        toolbar = (Toolbar)findViewById(R.id.class_detail_toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
    }
    private void initUI(){
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);

        fab = (CheckableFrameLayout) findViewById(R.id.class_detail_fab);
        fab.setOnClickListener(this);
    }

    private void initVideoView(){
        pb = (ProgressBar) findViewById(R.id.class_detail_video_view_pb);
        videoView = (VideoView) findViewById(R.id.class_detail_video_view_player);
        loadRateView = (TextView) findViewById(R.id.class_detail_video_view_pb_load_rate);
        downloadRateView = (TextView) findViewById(R.id.class_detail_video_view_pb_download_rate);

        uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.setOnInfoListener(this);
        videoView.setOnBufferingUpdateListener(this);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
            }
        });
    }

    private void initParam(){
        fabUtil = FabUtil.getFabUtil();
        parallaxImgHeight = getResources().getDimensionPixelSize(R.dimen.activity_detail_toolbar_height);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what){
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if(videoView.isPlaying()){
                    videoView.pause();
                    loadRateView.setText("");
                    downloadRateView.setText("");
                    pb.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);
                    downloadRateView.setVisibility(View.VISIBLE);
                }
                break;

            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_ZOOM, 0);
                videoView.start();
                pb.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                break;

            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }

        return true;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / parallaxImgHeight);
        toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(videoView, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.class_detail_fab:
                fabUtil.addCourse(this, fab);
                break;

            default:
                finish();
                break;
        }
    }
}
