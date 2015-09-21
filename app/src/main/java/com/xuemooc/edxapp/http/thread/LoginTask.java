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
public class LoginTask implements Runnable {
    private Message msg;
    private LoginHandler loginHandler;
    private ProgressButtonUtil.PBConst state;

    public LoginTask(ILogin iLogin) {
        loginHandler = new LoginHandler(iLogin);
    }

    @Override
    public void run() {
        Message temp = loginHandler.obtainMessage();
        switch(msg.what){
            case LoginUtil.SEND_INFO:
                Bundle b = msg.getData();
                String uid = b.getString("uid");
                String psd = b.getString("psd");

                //start
                if(uid.equals("yhc") && psd.equals("666")){
                    temp.what = LoginUtil.LOGIN_SUCCESS_SHOW;
                    state = ProgressButtonUtil.PBConst.PB_LOGIN_SUCCESS;
                }else{
                    temp.what = LoginUtil.LOGIN_ERROR_SHOW;

                    if(!uid.equals("yhc")){
                        state = ProgressButtonUtil.PBConst.PB_INVALID_USER;
                    }else if(psd.equals("123")){
                        state = ProgressButtonUtil.PBConst.PB_BAD_WEB;
                    }else if(psd.equals("111")){
                        state = ProgressButtonUtil.PBConst.PB_WRONG_PSD;
                    }
                }

                temp.obj = state;
                loginHandler.sendMessageDelayed(temp, 3000);

                //end

                //start->end 代码段应该添加发送UID+PSD到网络的逻辑，获得网络响应后通过接口回调的方式向Handler发送服务器的响应结果，更新UI
                break;

            case LoginUtil.LOGIN_SUCCESS:
                temp.what = msg.what;
                temp.obj = ProgressButtonUtil.PBConst.PB_LOGIN_SUCCESS;

                loginHandler.sendMessageDelayed(temp, 4000);
                break;

            case LoginUtil.LOGIN_RESET:
                temp.what = msg.what;
                temp.obj = ProgressButtonUtil.PBConst.PB_INIT;

                loginHandler.sendMessageDelayed(temp, 4000);
                break;
        }
    }

    public void sendMsgToServer(Message msg){
        this.msg = msg;
    }
}
