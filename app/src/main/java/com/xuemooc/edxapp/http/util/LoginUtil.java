package com.xuemooc.edxapp.http.util;

import android.os.Message;

import com.xuemooc.edxapp.http.interfaces.ILogin;
import com.xuemooc.edxapp.http.interfaces.WebUtil;
import com.xuemooc.edxapp.http.thread.LoginTask;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Util that helps deal with login event
 * Created by chaossss on 2015/9/21.
 */
public class LoginUtil implements WebUtil{
    private ILogin iLogin;
    private final Executor threadPool;
    private volatile static LoginUtil loginUtil;

    public static final int SEND_INFO = 0;
    public static final int LOGIN_RESET = 1;
    public static final int LOGIN_SUCCESS = 2;
    public static final int LOGIN_SUCCESS_SHOW = 3;
    public static final int LOGIN_ERROR_SHOW = 4;

    private static final int THREAD_POOL_SIZE = 2;

    private LoginUtil(ILogin iLogin) {
        this.iLogin = iLogin;
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public static LoginUtil getLoginUtil(ILogin iLogin){
        if(loginUtil == null){
            synchronized (LoginUtil.class){
                if(loginUtil == null){
                    loginUtil = new LoginUtil(iLogin);
                }
            }
        }

        return loginUtil;
    }

    @Override
    public void sendRequest(Message msg) {
        LoginTask task = new LoginTask(iLogin);
        task.sendMsgToServer(msg);
        threadPool.execute(task);
    }
}
