package com.demo.message.JPush;

public class JPCallBack {
    //注册回调
    public interface RegisterCallBack{
        void onResult(int code,String s);
    }

    //发送消息回调
    public interface SendCallBack{
        void onResult(int code,String s);
    }

}
