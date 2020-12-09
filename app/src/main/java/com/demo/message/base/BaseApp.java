package com.demo.message.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.demo.message.ui.LoginActivity;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;


public class BaseApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        init();
    }

    private void init() {
        initJPush();//初始化极光
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);//推送

        JMessageClient.init(context,true);//即时通讯

    }


}
