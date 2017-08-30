package com.lmiot.BraceletDemo.Util;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.lmiot.BraceletDemo.Util.LogUtils;

/**
 * Created by hanson on 2016/4/12.
 */
public class GetPeopleUtils {
    public static String getContactNameByPhoneNumber(Context context, String address) {
                 String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER };

                 // 将自己添加到 msPeers 中
                 Cursor cursor = context.getContentResolver().query(
                                 ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                 projection, // Which columns to return.
                                 ContactsContract.CommonDataKinds.Phone.NUMBER + " = '"
                                         + address + "'", // WHERE clause.
                                 null, // WHERE clause value substitution
                                 null); // Sort order.

                 if (cursor == null) {
                         LogUtils.e( "getPeople null");
                         return null;
                     }
                 for (int i = 0; i < cursor.getCount(); i++) {
                         cursor.moveToPosition(i);

                        // 取得联系人名字
                         int nameFieldColumnIndex = cursor
                                 .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                         String name = cursor.getString(nameFieldColumnIndex);
                     LogUtils.e("联系人是："+name);
                         return name;
                     }
                 return null;
             }
}
