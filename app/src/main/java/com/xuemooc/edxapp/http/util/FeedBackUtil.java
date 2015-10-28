package com.xuemooc.edxapp.http.util;

import android.os.Message;

import com.xuemooc.edxapp.utils.interfaces.WebCommunication;
import com.xuemooc.edxapp.utils.thread.FeedBackTask;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by chaossss on 2015/9/22.
 */
public class FeedBackUtil implements WebCommunication {
    private WebCommunication webCommunication;
    private final Executor threadPool;

    private volatile static FeedBackUtil feedBackUtil;

    private static final int THREAD_POOL_SIZE = 2;

    private FeedBackUtil(WebCommunication webCommunication) {
        this.webCommunication = webCommunication;
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static FeedBackUtil getFeedBackUtil(WebCommunication webCommunication){
        if(feedBackUtil == null){
            synchronized (FeedBackUtil.class){
                if(feedBackUtil == null){
                    feedBackUtil = new FeedBackUtil(webCommunication);
                }
            }
        }

        return feedBackUtil;
    }

    @Override
    public void sendRequest(Message msg) {
        FeedBackTask task = new FeedBackTask(webCommunication);
        task.sendMsgToServer(msg);
        threadPool.execute(task);
    }

    @Override
    public void onMessageResponse(Message msg) {

    }
}
