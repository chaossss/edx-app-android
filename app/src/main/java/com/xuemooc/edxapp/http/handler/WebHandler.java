package com.xuemooc.edxapp.http.handler;

import android.os.Handler;
import android.os.Message;

import com.xuemooc.edxapp.http.interfaces.ILogin;

/**
 * Handler used to update Login page by calling ILogin interface
 * Created by chaossss on 2015/9/21.
 */
public class WebHandler extends Handler {
    private ILogin iLogin;

    public WebHandler(ILogin iLogin) {
        this.iLogin = iLogin;
    }

    @Override
    public void handleMessage(Message msg) {
        iLogin.updateUI(msg);
    }
}
