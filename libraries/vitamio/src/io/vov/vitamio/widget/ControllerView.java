package io.vov.vitamio.widget;

import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import io.vov.vitamio.R;

/**
 * Created by chaossss on 2015/10/16.
 */
public class ControllerView {
    private TextView endTime;
    private TextView currTime;
    private TextView videoName;
    private SeekBar progressBar;
    private ImageButton pauseBtn;

    public ControllerView(View rootView) {
        pauseBtn = (ImageButton) rootView.findViewById(R.id.layout_controller_play_pause);

        progressBar = (SeekBar) rootView.findViewById(R.id.layout_controller_progress);
        progressBar.setMax(1000);

        endTime = (TextView) rootView.findViewById(R.id.layout_controller_end_time);
        currTime = (TextView) rootView.findViewById(R.id.layout_controller_time_current);
        videoName = (TextView) rootView.findViewById(R.id.layout_controller_file_name);
    }

    public SeekBar getProgressBar(){
        return progressBar;
    }

    public void setOnPauseBtnClickListener(View.OnClickListener onPauseBtnClickListener){
        pauseBtn.requestFocus();
        pauseBtn.setOnClickListener(onPauseBtnClickListener);
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener){
        progressBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    public void setCurrTime(String currTime){
        this.currTime.setText(currTime);
    }

    public void setEndTime(String endTime){
        this.endTime.setText(endTime);
    }

    public void setVideoName(String videoName){
        this.videoName.setText(videoName);
    }

    public void setEnable(boolean enabled){
        pauseBtn.setEnabled(enabled);
        progressBar.setEnabled(enabled);
    }

    public void setPauseState(int drawable){
        pauseBtn.setImageResource(drawable);
    }
}
