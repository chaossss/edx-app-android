package com.xuemooc.edxapp.http.thread;

import android.os.Bundle;
import android.os.Message;

import com.xuemooc.edxapp.http.handler.LoginHandler;
import com.xuemooc.edxapp.http.interfaces.ILogin;
import com.xuemooc.edxapp.http.util.LoginUtil;
import com.xuemooc.edxapp.view.utils.ProgressButtonUtil;

/**
 * Created by chaossss on 2015/9/21.
 */
public class LoginThread extends Thread{
    private Message msg;
    private LoginHandler loginHandler;
    private ProgressButtonUtil.PBConst state;

    public LoginThread(ILogin iLogin) {
        loginHandler = new LoginHandler(iLogin);
    }

    @Override
    public void run() {
        switch(msg.what){
            case LoginUtil.SEND_LOGIN_INFO:
                Bundle b = msg.getData();
                String uid = b.getString("uid");
                String psd = b.getString("psd");

                if(!uid.equals("yhc")){
                    state = ProgressButtonUtil.PBConst.PB_INVALID_USER;
                }else{
                    if(psd.equals("123")){
                        state = ProgressButtonUtil.PBConst.PB_BAD_WEB;
                    }else if(psd.equals("111")){
                        state = ProgressButtonUtil.PBConst.PB_WRONG_PSD;
                    }else{
                        state = ProgressButtonUtil.PBConst.PB_LOGIN_SUCCESS;
                    }
                }

                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                Message msg = loginHandler.obtainMessage();
                msg.obj = state;
                loginHandler.sendMessage(msg);

                break;
        }
    }

    public void sendMsgToServer(Message msg){
        this.msg = msg;
        this.start();
    }
}
