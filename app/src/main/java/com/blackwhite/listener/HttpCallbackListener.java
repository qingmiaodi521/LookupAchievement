package com.blackwhite.listener;

/**
 * Created by BlackWhite on 15/9/9.
 */
public interface HttpCallbackListener {
    void onFinish(String response,String response1);
    void onError(int type);
}
