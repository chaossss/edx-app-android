package com.xuemooc.edxapp.http.player.listener;

/**
 * Created by chaossss on 2015/9/17.
 */
public interface IPlayerViewListener {
    void onSetPreview();
    void onFullScreen();
    void onBuffer(int percent);
}
