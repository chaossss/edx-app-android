package com.xuemooc.edxapp.http.util;

import android.os.Message;

import com.xuemooc.edxapp.http.interfaces.ILogin;
import com.xuemooc.edxapp.http.thread.LoginThread;

/**
 * Created by chaossss on 2015/9/21.
 */
public class LoginUtil{
    private ILogin iLogin;
    private volatile static LoginUtil loginUtil;

    public static final int SEND_INFO = 0;
    public static final int LOGIN_RESET = 1;
    public static final int LOGIN_SUCCESS = 2;
    public static final int LOGIN_SUCCESS_SHOW = 3;
    public static final int LOGIN_ERROR_SHOW = 4;

    private LoginUtil(ILogin iLogin) {
        this.iLogin = iLogin;
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
        new LoginThread(iLogin).sendMsgToServer(msg);
    }
}
