package com.xuemooc.edxapp.utils.handler;

import android.os.Handler;
import android.os.Message;

import com.xuemooc.edxapp.utils.interfaces.WebCommunication;

/**
 * Handler used to update Login page by calling WebCommunication interface
 * Created by chaossss on 2015/9/21.
 */
public class WebHandler extends Handler {
    private WebCommunication webCommunication;

    public WebHandler(WebCommunication webCommunication) {
        this.webCommunication = webCommunication;
    }

    @Override
    public void handleMessage(Message msg) {
        webCommunication.onMessageResponse(msg);
    }
}
