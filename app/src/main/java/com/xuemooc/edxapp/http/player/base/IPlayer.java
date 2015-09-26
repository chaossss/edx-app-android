package com.xuemooc.edxapp.http.player.base;

/**
 * Interface that defines Player's behavior
 * Created by chaossss on 2015/9/17.
 */
public interface IPlayer {
    void stop();
    void prev();
    void next();
    void start();
    void pause();
    void restart();
}
