package com.xuemooc.edxapp.http.util;

import android.os.Message;

import com.xuemooc.edxapp.http.interfaces.ILogin;
import com.xuemooc.edxapp.http.interfaces.WebUtil;
import com.xuemooc.edxapp.http.thread.FeedBackTask;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by chaossss on 2015/9/22.
 */
public class FeedBackUtil implements WebUtil {
    private ILogin iLogin;
    private final Executor threadPool;

    private volatile static FeedBackUtil feedBackUtil;

    private static final int THREAD_POOL_SIZE = 2;

    private FeedBackUtil(ILogin iLogin) {
        this.iLogin = iLogin;
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static FeedBackUtil getFeedBackUtil(ILogin iLogin){
        if(feedBackUtil == null){
            synchronized (FeedBackUtil.class){
                if(feedBackUtil == null){
                    feedBackUtil = new FeedBackUtil(iLogin);
                }
            }
        }

        return feedBackUtil;
    }

    @Override
    public void sendRequest(Message msg) {
        FeedBackTask task = new FeedBackTask(iLogin);
        task.sendMsgToServer(msg);
        threadPool.execute(task);
    }
}
