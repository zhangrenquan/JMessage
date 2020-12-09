package com.demo.message.JMessage;

import android.content.Context;
import android.util.Log;

import com.demo.message.JPush.JPCallBack;
import com.demo.message.base.BaseApp;
import com.demo.message.base.BaseContract;
import com.demo.message.util.LogUtil;
import com.demo.message.util.ToastUtils;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;

public class JMessageUtil {
    private static JMessageUtil jMessageUtil;
    private Conversation mConversation;

    public static JMessageUtil getInstance() {
        if (null == jMessageUtil) {
            synchronized (JMessageUtil.class) {
                if (null == jMessageUtil) {
                    jMessageUtil = new JMessageUtil();
                }
            }
        }
        return jMessageUtil;
    }

    /**
     * 注册
     * 参数说明
     * String username 用户名
     * String password 用户密码
     * RegisterOptionalUserInfo optionalUserInfo 注册时的用户其他信息
     * BasicCallback callback 结果回调
     */
    public void register(final Context context, String username, String password, final JPCallBack.RegisterCallBack registerCallBack) {
        JMessageClient.register(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("TAG", "register：code：" + i + "  msg：" + s);
                if (i == 0) {
                    registerCallBack.onResult(i, s);
                } else {
                    checkCode(context, i);
                }
            }
        });
    }

    /**
     * 登录
     * 参数说明
     * String username 用户名
     * String password 用户密码
     * BasicCallback callback 结果回调
     */
    public void login(final Context context, String username, String password, final JPCallBack.RegisterCallBack registerCallBack) {
        JMessageClient.login(username, password, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("TAG", "register：code：" + i + "  msg：" + s);
                if (i == 0) {
                    registerCallBack.onResult(i, s);
                } else {
                    checkCode(context, i);
                }
            }
        });
    }

    /**
     * 创建单聊会话
     * 参数说明
     * String username 会话对象的username.
     * String appkey 用户所属应用的appkey,如果填空则默认为本应用的appkey
     */
    public void createConversation(String userName) {
        Conversation singleConversation = Conversation.createSingleConversation(userName, BaseContract.APP_KEY);
        mConversation = singleConversation;
    }

    /**
     * 发送消息     使用默认的配置参数发送
     * 参数说明
     * Message message 消息对象
     */
    public void sendMessage(Context context, String userName, String text, JPCallBack.SendCallBack sendCallBack) {
        Message message = JMessageClient.createSingleTextMessage(userName, BaseContract.APP_KEY, text);
        JMessageClient.sendMessage(message);
        checkSendResult(context, message, sendCallBack);
    }

    /**
     * 发送消息    附带控制参数的消息发送
     * 参数说明
     * Message message 消息对象
     */
    public void sendMessage(String text, MessageSendingOptions options) {

    }

    /**
     * 校验消息发送结果监听
     *
     * @param message
     * @param sendCallBack
     */
    private void checkSendResult(final Context context, Message message, final JPCallBack.SendCallBack sendCallBack) {
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.i("TAG", "register：code：" + i + "  msg：" + s);
                if (i == 0) {
                    sendCallBack.onResult(i, s);
                } else {
                    checkCode(context, i);
                }
            }
        });
    }

    /**
     * 校验JMessage返回值
     *
     * @param context
     * @param i
     */
    private void checkCode(Context context, int i) {
        switch (i) {
            case 871102:
                ToastUtils.showToast(context, "请求失败，请检查网络");
                break;
            case 871103:
            case 871104:
                ToastUtils.showToast(context, "服务器内部错误");
                break;
            case 871105:
                ToastUtils.showToast(context, "请求的用户信息不存在");
                break;
            case 871201:
                ToastUtils.showToast(context, "响应超时");
                break;
            case 871303:
                ToastUtils.showToast(context, "用户名不合法");
                break;
            case 871304:
                ToastUtils.showToast(context, "密码不合法");
                break;
            case 871305:
                ToastUtils.showToast(context, "名称不合法");
                break;
            case 871308:
                ToastUtils.showToast(context, "SDK尚未初始化");
                break;
            case 871310:
                ToastUtils.showToast(context, "网络连接已断开，请检查网络");
                break;
            case 898001:
                ToastUtils.showToast(context, "用户已存在");
                break;
            case 801003:
                ToastUtils.showToast(context, "登录的用户名未注册，登录失败");
                break;
            case 801004:
                ToastUtils.showToast(context, "登录的用户密码错误，登录失败");
                break;
            case 801005:
                ToastUtils.showToast(context, "登录的用户设备有误，登录失败");
                break;
            case 801006:
                ToastUtils.showToast(context, "登录的用户被禁用，登录失败");
                break;
            case 872100:
                ToastUtils.showToast(context, "音视频引擎初始化失败，appkey为空");
                break;
            case 872101:
                ToastUtils.showToast(context, "音视频引擎由于一些问题初始化失败，详情请看日志");
                break;
            case 872102:
                ToastUtils.showToast(context, "音视频引擎初始化失败，由于网络异常造成");
                break;
            case 872103:
                ToastUtils.showToast(context, "音视频引擎初始化失败，由于服务器端返回内容错误造成");
                break;
            case 872104:
                ToastUtils.showToast(context, "音视频引擎初始化失败，由于服务器端内部错误造成");
                break;
            case 872105:
                ToastUtils.showToast(context, "音视频引擎初始化失败，由于需要的权限没有获取成功造成");
                break;
            case 872106:
                ToastUtils.showToast(context, "音视频引擎还未初始化");
                break;
        }
    }
}
