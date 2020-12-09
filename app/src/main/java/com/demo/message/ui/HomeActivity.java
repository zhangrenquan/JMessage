package com.demo.message.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.message.JMessage.JMessageUtil;
import com.demo.message.JPush.JPCallBack;
import com.demo.message.R;
import com.demo.message.adapter.HomeRecyAdapter;
import com.demo.message.base.BaseContract;
import com.demo.message.enity.MessageBean;
import com.demo.message.util.Go2Activity;
import com.demo.message.util.KeyBroadUtil;
import com.demo.message.util.LogUtil;
import com.demo.message.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cn.jpush.android.helper.Logger;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadAvatarCallback;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mMessageEt;
    private Button mSendBt;
    private Context context;
    private RecyclerView mRecyHome;
    private ArrayList<MessageBean> datas;
    private HomeRecyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(context);//事件接收类的解绑
    }

    private void init() {
        datas = new ArrayList<>();

        initView();
        initConversation();

        JMessageClient.registerEventReceiver(context);//事件接收类的注册
    }

    private void initConversation() {
        JMessageUtil.getInstance().createConversation(BaseContract.USER_NAME);
    }

    private void initView() {
        mMessageEt = (EditText) findViewById(R.id.et_message);
        mSendBt = (Button) findViewById(R.id.bt_send);
        mSendBt.setOnClickListener(this);
        mRecyHome = (RecyclerView) findViewById(R.id.home_recy);

        mRecyHome.setLayoutManager(new LinearLayoutManager(context));
        adapter = new HomeRecyAdapter(datas);
        mRecyHome.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send:
                // TODO 20/12/09
                final String message = mMessageEt.getText().toString();
                if (message.isEmpty()) {
                    ToastUtils.showToast(context, "消息不可为空");
                    return;
                }
                JMessageUtil.getInstance().sendMessage(context, BaseContract.USER_NAME, message, new JPCallBack.SendCallBack() {
                    @Override
                    public void onResult(int code, String s) {
                        datas.add(new MessageBean(MessageBean.RIGHT, message));
                        adapter.setNewData(datas);
                        adapter.reflush();
                    }
                });
                mMessageEt.setText("");
                KeyBroadUtil.hide(context);
                break;
            default:
                break;
        }
    }


    //用户在线期间收到的消息都会以MessageEvent的方式上抛
    public void onEvent(MessageEvent event) {
        Message msg = event.getMessage();

        checkMessage(msg);
    }

    //用户离线期间收到的消息会以OfflineMessageEvent的方式上抛，处理方式类似上面的
    //MessageEvent
    public void onEvent(OfflineMessageEvent event) {
        List<Message> msgs = event.getOfflineMessageList();
        for (Message msg : msgs) {
            checkMessage(msg);
        }
    }

    private void checkMessage(Message msg) {
        switch (msg.getContentType()) {
            case text:
                //处理文字消息
                TextContent textContent = (TextContent) msg.getContent();
                final String message = textContent.getText();
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        datas.add(new MessageBean(MessageBean.LEFT, message));
                        adapter.setNewData(datas);
                        adapter.reflush();
                    }
                });

//                adapter.notifyDataSetChanged();
                break;
            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();//图片本地地址
                imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                voiceContent.getLocalPath();//语音文件本地地址
                voiceContent.getDuration();//语音文件时长
                break;
            case custom:
                //处理自定义消息
                CustomContent customContent = (CustomContent) msg.getContent();
                customContent.getNumberValue("custom_num"); //获取自定义的值
                customContent.getBooleanValue("custom_boolean");
                customContent.getStringValue("custom_string");
                break;
            case eventNotification:
                //处理事件提醒消息
                EventNotificationContent eventNotificationContent = (EventNotificationContent) msg.getContent();
                switch (eventNotificationContent.getEventNotificationType()) {
                    case group_member_added:
                        //群成员加群事件
                        break;
                    case group_member_removed:
                        //群成员被踢事件
                        break;
                    case group_member_exit:
                        //群成员退群事件
                        break;
                    case group_info_updated://since 2.2.1
                        //群信息变更事件
                        break;
                }
                break;
            case unknown:
                // 处理未知消息，未知消息的Content为PromptContent 默认提示文本为“当前版本不支持此类型消息，请更新sdk版本”，上层可选择不处理
                PromptContent promptContent = (PromptContent) msg.getContent();
                promptContent.getPromptType();//未知消息的type是unknown_msg_type
                promptContent.getPromptText();//提示文本，“当前版本不支持此类型消息，请更新sdk版本”
                break;
        }
    }

}