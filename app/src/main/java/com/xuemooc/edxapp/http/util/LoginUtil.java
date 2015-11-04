package com.xuemooc.edxapp.http.util;

import android.content.Context;
import android.os.Message;

import com.xuemooc.edxapp.http.Api;
import com.xuemooc.edxapp.utils.interfaces.WebCommunication;
import com.xuemooc.edxapp.utils.thread.LoginTask;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Util that helps deal with login event
 * Created by chaossss on 2015/9/21.
 */
public class LoginUtil implements WebCommunication {
    private WebCommunication webCommunication;
    private final Executor threadPool;
    private volatile static LoginUtil loginUtil;

    public static final int SEND_INFO = 0;
    public static final int LOGIN_RESET = 1;
    public static final int LOGIN_SUCCESS = 2;
    public static final int LOGIN_SUCCESS_SHOW = 3;
    public static final int LOGIN_ERROR_SHOW = 4;

    private static final int THREAD_POOL_SIZE = 2;

    private LoginUtil(WebCommunication webCommunication) {
        this.webCommunication = webCommunication;
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static LoginUtil getLoginUtil(WebCommunication webCommunication){
        if(loginUtil == null){
            synchronized (LoginUtil.class){
                if(loginUtil == null){
                    loginUtil = new LoginUtil(webCommunication);
                }
            }
        }

        return loginUtil;
    }

    @Override
    public void sendRequest(Message msg) {
        LoginTask task = new LoginTask(webCommunication);
        task.sendMsgToServer(msg);
        threadPool.execute(task);
    }

    @Override
    public void onMessageResponse(Message msg) {

    }
}
