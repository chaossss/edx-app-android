package com.xuemooc.edxapp.http.util;

import android.os.Message;

import com.xuemooc.edxapp.http.interfaces.IWebMessage;
import com.xuemooc.edxapp.utils.thread.FeedBackTask;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by chaossss on 2015/9/22.
 */
public class FeedBackUtil implements IWebMessage {
    private IWebMessage iWebMessage;
    private final Executor threadPool;

    private volatile static FeedBackUtil feedBackUtil;

    private static final int THREAD_POOL_SIZE = 2;

    private FeedBackUtil(IWebMessage iWebMessage) {
        this.iWebMessage = iWebMessage;
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static FeedBackUtil getFeedBackUtil(IWebMessage iWebMessage){
        if(feedBackUtil == null){
            synchronized (FeedBackUtil.class){
                if(feedBackUtil == null){
                    feedBackUtil = new FeedBackUtil(iWebMessage);
                }
            }
        }

        return feedBackUtil;
    }

    @Override
    public void sendRequest(Message msg) {
        FeedBackTask task = new FeedBackTask(iWebMessage);
        task.sendMsgToServer(msg);
        threadPool.execute(task);
    }

    @Override
    public void onMessageResponse(Message msg) {

    }
}
