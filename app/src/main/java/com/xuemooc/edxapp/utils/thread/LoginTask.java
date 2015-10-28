package com.xuemooc.edxapp.utils.thread;

import android.os.Bundle;
import android.os.Message;

import com.xuemooc.edxapp.utils.handler.WebHandler;
import com.xuemooc.edxapp.utils.interfaces.WebCommunication;
import com.xuemooc.edxapp.http.util.LoginUtil;
import com.xuemooc.edxapp.view.consts.PBConst;

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

                //start
                if(uid.equals("yhc") && psd.equals("666")){
                    temp.what = LoginUtil.LOGIN_SUCCESS_SHOW;
                    state = PBConst.PB_LOGIN_SUCCESS;
                }else{
                    temp.what = LoginUtil.LOGIN_ERROR_SHOW;

                    if(!uid.equals("yhc")){
                        state = PBConst.PB_INVALID_USER;
                    }else if(psd.equals("123")){
                        state = PBConst.PB_BAD_WEB;
                    }else if(psd.equals("111")){
                        state = PBConst.PB_WRONG_PSD;
                    }
                }

                temp.obj = state;
                webHandler.sendMessageDelayed(temp, 3000);

                //end

                //start->end 代码段应该添加发送UID+PSD到网络的逻辑，获得网络响应后通过接口回调的方式向Handler发送服务器的响应结果，更新UI
                break;

            case LoginUtil.LOGIN_SUCCESS:
                temp.what = msg.what;
                temp.obj = PBConst.PB_LOGIN_SUCCESS;

                webHandler.sendMessageDelayed(temp, 4000);
                break;

            case LoginUtil.LOGIN_RESET:
                temp.what = msg.what;
                temp.obj = PBConst.PB_INIT;

                webHandler.sendMessageDelayed(temp, 4000);
                break;
        }
    }

    public void sendMsgToServer(Message msg){
        this.msg = msg;
    }
}
