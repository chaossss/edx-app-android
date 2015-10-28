package com.xuemooc.edxapp.utils.interfaces;

import android.os.Message;

/**
 * Interface used to handle web request and get web's response
 * Created by chaossss on 2015/9/21.
 */
public interface WebCommunication {
    void sendRequest(Message msg);
    void onMessageResponse(Message msg);
}
