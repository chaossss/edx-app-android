package com.xuemooc.edxapp.utils.thread;

import android.os.Message;

import com.xuemooc.edxapp.utils.handler.WebHandler;
import com.xuemooc.edxapp.utils.interfaces.WebCommunication;

/**
 * Created by chaossss on 2015/9/23.
 */
public class FeedBackTask implements Runnable {
    private Message msg;
    private WebHandler handler;

    public FeedBackTask(WebCommunication webCommunication) {
        handler = new WebHandler(webCommunication);
    }

    @Override
    public void run() {
        Message msg = new Message();
        msg.what = 666;
        this.msg.obj = msg.obj;
        handler.sendMessageDelayed(msg, 1000);
    }

    public void sendMsgToServer(Message msg){
        this.msg = msg;
    }
}
