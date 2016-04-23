package com.example.ankursingh.shaeredelementdemo.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by Ankur Singh on 23/04/16.
 */
public class BaseHandler  extends Handler{

    private WeakReference<HandlerCallbacks> mHandlerCallbacks;
    public BaseHandler(HandlerCallbacks pHandlerCallbacks){
        mHandlerCallbacks = new WeakReference<>(pHandlerCallbacks);
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if(mHandlerCallbacks.get()!=null){
            mHandlerCallbacks.get().handleMessage(msg);
        }
    }

    public interface HandlerCallbacks{
        void handleMessage(Message pMessage);
    }
}
