package com.xuemooc.edxapp.http.interfaces;

import android.os.Message;

/**
 * Interface used to handle web request and update UI
 * Created by chaossss on 2015/9/21.
 */
public interface IWebMessage {
    void sendRequest(Message msg);
    void onMessageResponse(Message msg);
}
