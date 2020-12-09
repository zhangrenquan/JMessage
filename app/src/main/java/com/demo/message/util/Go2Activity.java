package com.demo.message.util;

import android.content.Context;
import android.content.Intent;

public class Go2Activity {

    public static void go2Activity(Context context,Class<?> cls){
        context.startActivity(new Intent(context,cls));
    }
}
