package com.demo.message.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.message.JMessage.JMessageUtil;
import com.demo.message.JPush.JPCallBack;
import com.demo.message.R;
import com.demo.message.util.Go2Activity;
import com.demo.message.util.ToastUtils;

/**
 * 登录Activity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUsername;
    private EditText mPassword;
    private Button mLoginBt;
    private static Context context;
    private Button mRegistBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        context = this;
        initView();
    }

    private void initView() {
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mLoginBt = (Button) findViewById(R.id.bt_login);
        mLoginBt.setOnClickListener(this);
        mRegistBt = (Button) findViewById(R.id.bt_regist);
        mRegistBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                // TODO 20/12/08
                String userName = mUsername.getText().toString();
                String passWord = mPassword.getText().toString();
                if (null == userName && null == passWord || userName.isEmpty() || passWord.isEmpty()) {
                    Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                JMessageUtil.getInstance().login(this,userName, passWord, new JPCallBack.RegisterCallBack() {
                    @Override
                    public void onResult(int code, String s) {
                        Go2Activity.go2Activity(context,HomeActivity.class);
                    }
                });
                break;
            case R.id.bt_regist:// TODO 20/12/09
                String userName1 = mUsername.getText().toString();
                String passWord1 = mPassword.getText().toString();
                if (null == userName1 && null == passWord1 || userName1.isEmpty() || passWord1.isEmpty()) {
                    Toast.makeText(context, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                JMessageUtil.getInstance().register(this,userName1, passWord1, new JPCallBack.RegisterCallBack() {
                    @Override
                    public void onResult(int code, String s) {
                        ToastUtils.showToast(context,"注册成功");
                    }
                });

                break;
            default:
                break;
        }
    }
}