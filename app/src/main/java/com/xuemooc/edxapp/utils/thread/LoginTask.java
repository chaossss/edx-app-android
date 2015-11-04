package com.xuemooc.edxapp.utils.thread;

import android.os.Bundle;
import android.os.Message;

import com.xuemooc.edxapp.http.IApi;
import com.xuemooc.edxapp.model.api.AuthResponse;
import com.xuemooc.edxapp.utils.handler.WebHandler;
import com.xuemooc.edxapp.utils.interfaces.WebCommunication;
import com.xuemooc.edxapp.http.util.LoginUtil;
import com.xuemooc.edxapp.view.CustomApplication;
import com.xuemooc.edxapp.view.consts.PBConst;

import io.vov.vitamio.utils.Log;

/**
 * while executing login operation, LoginTask is a async task which process
 * network communication
 * Created by chaossss on 2015/9/21.
 */
public class LoginTask implements Runnable {
    private Message msg;
    private WebHandler webHandler;
    private PBConst state;

    public LoginTask(WebCommunication webCommunication) {
        webHandler = new WebHandler(webCommunication);
    }

    @Override
    public void run() {
        Message temp = webHandler.obtainMessage();
        switch(msg.what){
            case LoginUtil.SEND_INFO:
                Bundle b = msg.getData();
                String uid = b.getString("uid");
                String psd = b.getString("psd");

                try{
                    CustomApplication.api.auth(uid, psd);
                    state = PBConst.PB_LOGIN_SUCCESS;
                    temp.what = LoginUtil.LOGIN_SUCCESS_SHOW;
                } catch (Exception e){
                    e.printStackTrace();
                    state = PBConst.PB_WRONG_PSD;
                    temp.what = LoginUtil.LOGIN_ERROR_SHOW;
                }

                temp.obj = state;
                webHandler.sendMessage(temp);

                break;

            case LoginUtil.LOGIN_SUCCESS:
                temp.what = msg.what;
                temp.obj = PBConst.PB_LOGIN_SUCCESS;
                webHandler.sendMessage(temp);
                break;

            case LoginUtil.LOGIN_RESET:
                temp.what = msg.what;
                temp.obj = PBConst.PB_INIT;
                webHandler.sendMessageDelayed(temp, 2000);
                break;
        }
    }

    public void sendMsgToServer(Message msg){
        this.msg = msg;
    }
}
