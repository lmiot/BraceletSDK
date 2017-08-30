package com.lmiot.BraceletDemo.Util;

import android.content.Context;

import com.example.yideng.loaddialoglibrary.LoadDialog;

/**
 * Created by Mr.Li
 * Date：2017年08月29日
 * Description: 对话工具类
 */

public class DialogUtil {

    private static LoadDialog mLoadDialog;

    public static void showDialog(Context context){
        try {
            if(mLoadDialog!=null){
                mLoadDialog=null;
            }
            mLoadDialog = new LoadDialog(context);
            mLoadDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void Hidden(){
        try {
            if(mLoadDialog!=null){
                mLoadDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
