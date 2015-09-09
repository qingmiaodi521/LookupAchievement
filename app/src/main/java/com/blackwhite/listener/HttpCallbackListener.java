package com.blackwhite.listener;

/**
 * Created by BlackWhite on 15/9/9.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(int type);
}
