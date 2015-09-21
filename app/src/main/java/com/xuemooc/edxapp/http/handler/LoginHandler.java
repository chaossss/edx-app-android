package com.xuemooc.edxapp.http.handler;

import android.os.Handler;
import android.os.Message;

import com.xuemooc.edxapp.http.interfaces.ILogin;
import com.xuemooc.edxapp.view.utils.ProgressButtonUtil;

/**
 * Created by chaossss on 2015/9/21.
 */
public class LoginHandler extends Handler {
    private ILogin iLogin;

    public LoginHandler(ILogin iLogin) {
        this.iLogin = iLogin;
    }

    @Override
    public void handleMessage(Message msg) {
        iLogin.updateUI((ProgressButtonUtil.PBConst)msg.obj);
    }
}
