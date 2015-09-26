package com.xuemooc.edxapp.utils.handler;

import android.os.Handler;
import android.os.Message;

import com.xuemooc.edxapp.http.interfaces.IWebMessage;

/**
 * Handler used to update Login page by calling IWebMessage interface
 * Created by chaossss on 2015/9/21.
 */
public class WebHandler extends Handler {
    private IWebMessage iWebMessage;

    public WebHandler(IWebMessage iWebMessage) {
        this.iWebMessage = iWebMessage;
    }

    @Override
    public void handleMessage(Message msg) {
        iWebMessage.onMessageResponse(msg);
    }
}
