package com.xuemooc.edxapp.player.listener;

/**
 * Interface that responses play state
 * Created by chaossss on 2015/9/17.
 */
public interface IPlayerListener {
    void onPlayVideoStop();
    void onPlayVideoNext();
    void onPlayVideoPrev();
    void onPlayVideoStart();
    void onPlayVideoRestart();
    void onPlayVideoFinished();
}
