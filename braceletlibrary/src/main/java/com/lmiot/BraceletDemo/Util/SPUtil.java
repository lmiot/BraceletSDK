package com.lmiot.BraceletDemo.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;


/**
 * Created by Administrator on 2015-12-13.
 */
public class SPUtil {
    public static final String SP_NAME = "share_config";


    public static String getUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getString("userName", "");
    }


    public static boolean setSessionID(Context context, String sessionId) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, 0);
        Editor editor = sp.edit();
        editor.putString("sessionId", sessionId);
        return editor.commit();
    }

    public static String getSessionID(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getString("sessionId", "");
    }


    /**
     * 手环设置来电提醒和短信提醒
     */

    public static boolean setPhoneRemind(Context context, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, 0);
        Editor editor = sp.edit();
        editor.putBoolean("phoneremind", value);
        return editor.commit();
    }

    public static boolean getPhoneRemind(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getBoolean("phoneremind", false);
    }

    public static boolean setMsgRemind(Context context, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, 0);
        Editor editor = sp.edit();
        editor.putBoolean("msgremind", value);
        return editor.commit();
    }

    public static boolean getMsgRemind(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getBoolean("msgremind", false);
    }




}
