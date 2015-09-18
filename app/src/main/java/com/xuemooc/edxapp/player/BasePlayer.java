package com.xuemooc.edxapp.player;

import android.media.MediaPlayer;

import com.xuemooc.edxapp.player.base.IPlayer;

/**
 * Base Player
 * Created by chaossss on 2015/9/17.
 */
public class BasePlayer extends MediaPlayer implements IPlayer {
    private int bufferedPercent;
    private int lastPlayPosition;

    private boolean isLoadedFromLocal;

    private String videoUri;
    private String videoTitle;

    private PlayState playState;

    public enum PlayState{
        PLAYING, RESTART, PAUSE, BUFFERING, ERROR, BUFFERED
    }

    public BasePlayer(){
        initPlayer();
    }

    private void initPlayer(){
        bufferedPercent = 0;
        lastPlayPosition = 0;
        isLoadedFromLocal = false;
        playState = PlayState.RESTART;
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void prev() {

    }

    @Override
    public void next() {

    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void restart() {
        super.reset();
    }
}
