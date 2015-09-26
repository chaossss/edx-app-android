package com.xuemooc.edxapp.utils.thread;

import android.os.Message;

import com.xuemooc.edxapp.utils.handler.WebHandler;
import com.xuemooc.edxapp.http.interfaces.IWebMessage;

/**
 * Created by chaossss on 2015/9/23.
 */
public class FeedBackTask implements Runnable {
    private Message msg;
    private WebHandler handler;

    public FeedBackTask(IWebMessage iWebMessage) {
        handler = new WebHandler(iWebMessage);
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
