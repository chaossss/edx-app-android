package com.xuemooc.edxapp.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.xuemooc.edxapp.R;
import com.xuemooc.edxapp.view.custom.CheckableFrameLayout;
import com.xuemooc.edxapp.view.fragment.ClassDetailCommentFragment;
import com.xuemooc.edxapp.view.fragment.ClassDetailDirectoryFragment;
import com.xuemooc.edxapp.view.fragment.ClassDetailInfoFragment;
import com.xuemooc.edxapp.view.utils.FabUtil;
import com.xuemooc.edxapp.view.utils.SystemBarTintManager;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by chaossss on 2015/9/17.
 */
public class ClassDetailActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, ViewPager.OnPageChangeListener, android.support.v7.widget.Toolbar.OnMenuItemClickListener{
    private Toolbar toolbar;

    private Uri uri;
    private ProgressBar pb;
    private VideoView videoView;
    private TextView downloadRateView, loadRateView;
    private String path = "http://www.modrails.com/videos/passenger_nginx.mov";

    private CheckableFrameLayout fab;

    private ViewPager pager;
    private SmartTabLayout tab;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_class_detail, menu);
        return true;
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
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(this);
    }
    private void initUI(){
        pager = (ViewPager) findViewById(R.id.class_detail_pager);
        tab = (SmartTabLayout) findViewById(R.id.class_detail_tab);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.class_detail_info, ClassDetailInfoFragment.class)
                .add(R.string.class_detail_directory, ClassDetailDirectoryFragment.class)
                .add(R.string.class_detail_comment, ClassDetailCommentFragment.class)
                .create()
        );

        pager.setAdapter(adapter);
        tab.setViewPager(pager);
        tab.setOnPageChangeListener(this);

        fab = (CheckableFrameLayout) findViewById(R.id.class_detail_fab);
        fab.setOnClickListener(this);
    }

    private void initVideoView(){
        pb = (ProgressBar) findViewById(R.id.class_detail_video_view_pb);
        videoView = (VideoView) findViewById(R.id.class_detail_video_view_player);
        loadRateView = (TextView) findViewById(R.id.class_detail_video_view_pb_load_rate);
        downloadRateView = (TextView) findViewById(R.id.class_detail_video_view_pb_download_rate);

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

        RelativeLayout root = (RelativeLayout) findViewById(R.id.class_detail_video_view);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!videoView.isPlaying()){
                    uri = Uri.parse(path);
                    videoView.setVideoURI(uri);
                    pb.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);
                    downloadRateView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initParam(){
        fabUtil = FabUtil.getFabUtil();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position == 0){
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
