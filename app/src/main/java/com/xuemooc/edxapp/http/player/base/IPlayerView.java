package com.xuemooc.edxapp.http.player.base;

/**
 * Created by chaossss on 2015/9/17.
 */
public interface IPlayerView {
    void setPreview();
    void setToolBar();
    void setFullScreen();
    void setController();
    void setTitle(String title);
    void setBufferPercentage(int percent);
}
