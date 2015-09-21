package com.xuemooc.edxapp.http.util;

import android.os.Message;

import com.xuemooc.edxapp.http.interfaces.ILogin;
import com.xuemooc.edxapp.http.thread.LoginThread;

/**
 * Created by chaossss on 2015/9/21.
 */
public class LoginUtil{
    private LoginThread loginThread;
    private volatile static LoginUtil loginUtil;

    public static final int SEND_LOGIN_INFO = 0;

    private LoginUtil(ILogin iLogin) {
        loginThread = new LoginThread(iLogin);
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

    public void sendRequest(Message msg){
        loginThread.sendMsgToServer(msg);
    }
}
