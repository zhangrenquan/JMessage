package com.demo.message.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class KeyBroadUtil {
    /**
     * 隐藏软键盘
     * @param context
     */
    public static void hide(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
