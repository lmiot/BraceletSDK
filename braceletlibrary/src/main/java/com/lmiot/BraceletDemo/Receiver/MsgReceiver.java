package com.lmiot.BraceletDemo.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.lmiot.BraceletDemo.Util.BlueToothUtils;
import com.lmiot.BraceletDemo.Util.LogUtils;
import com.lmiot.BraceletDemo.Util.StrUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hanson on 2016/4/12.
 */
public class MsgReceiver extends BroadcastReceiver {
    private Context mContext;

    byte[] a;
    byte[] b;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());//时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                System.out.println("number:" + msg.getOriginatingAddress()
                        + "   body:" + msg.getDisplayMessageBody() + "  time:"
                        + msg.getTimestampMillis());


                //在这里写自己的逻辑
                if (msg.getOriginatingAddress().equals("10086")) {

                }



            }
        }








    }

    /**
     * 来电提醒测试
     */
    private void MsgRemind(String num, String msg) {

        StringBuilder ss = new StringBuilder();
        ss.append(num);
        if (num.length() < 11) {
            for (int i = 0; i < 11 - num.length(); i++) {
                ss.append("0");
            }
        }
        LogUtils.e("转换后的号码:" + ss);
        String s1 = ss.substring(0, 1);
        String s2 = ss.substring(1, 2);
        String s3 = ss.substring(2, 3);
        String s4 = ss.substring(3, 4);
        String s5 = ss.substring(4, 5);
        String s6 = ss.substring(5, 6);
        String s7 = ss.substring(6, 7);
        String s8 = ss.substring(7, 8);
        String s9 = ss.substring(8, 9);
        String s10 = ss.substring(9, 10);
        String s11 = ss.substring(10, 11);
        int i1 = Integer.parseInt(s1) + 48;
        int i2 = Integer.parseInt(s2) + 48;
        int i3 = Integer.parseInt(s3) + 48;
        int i4 = Integer.parseInt(s4) + 48;
        int i5 = Integer.parseInt(s5) + 48;
        int i6 = Integer.parseInt(s6) + 48;
        int i7 = Integer.parseInt(s7) + 48;
        int i8 = Integer.parseInt(s8) + 48;
        int i9 = Integer.parseInt(s9) + 48;
        int i10 = Integer.parseInt(s10) + 48;
        int i11 = Integer.parseInt(s11) + 48;
        if (msg.equals("")) {
            int sum = Integer.parseInt("68", 16) + 16 + Integer.parseInt("0B", 16) + Integer.parseInt("00", 16) + i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11;
            final int sk = sum % 256;
            a = new byte[]{(byte) sk, (byte) 0x16};
            b = new byte[]{(byte) 0x68, (byte) 0x0B, (byte) 16, (byte) 0x00, (byte) 0x00, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) i5, (byte) i6, (byte) i7, (byte) i8, (byte) i9, (byte) i10, (byte) i11, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
            LogUtils.e("byte[]a(1):" + StrUtils.bytesToHexString(a));
            LogUtils.e("byte[]b(1):" + StrUtils.bytesToHexString(b));

        } else {
            List<Integer> namelist = new ArrayList<>();
            for (int i = 2; i <= msg.length(); i += 2) {
                namelist.add(Integer.parseInt(msg.substring(i - 2, i), 16));
            }
            LogUtils.e("namelist:" + namelist);
            int namesum = 0;
            for (int a : namelist) {
                namesum += a;
            }
            LogUtils.e("namesum" + namesum);
            int sum = Integer.parseInt("68", 16) + 16 + namelist.size() + Integer.parseInt("01", 16) + +i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11 + namesum;
            final int sk = sum % 256;
            a = new byte[namelist.size() + 2];
            for (int i = 0; i < namelist.size(); i++) {
                a[i] = (byte) (int) namelist.get(i);
            }
            a[namelist.size()] = (byte) sk;
            a[namelist.size() + 1] = (byte) 0x16;
            b = new byte[]{(byte) 0x68, (byte) 0x0B, (byte) (16 + namelist.size()), (byte) 0x00, (byte) 0x00, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) i5, (byte) i6, (byte) i7, (byte) i8, (byte) i9, (byte) i10, (byte) i11, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
            LogUtils.e("byte[]a(2):" + StrUtils.bytesToHexString(a));
            LogUtils.e("byte[]b(2):" + StrUtils.bytesToHexString(b));

        }


        BlueToothUtils.ControlBracelet(b);
        try {
            Thread.sleep(200);
            BlueToothUtils.ControlBracelet(a);
        } catch (InterruptedException e) {
        }


    }


    /**
     * 打开短信提醒
     */
    private void OpenMsgRemind() {
        int sum = Integer.parseInt("68", 16) + Integer.parseInt("05", 16) + Integer.parseInt("03", 16) + Integer.parseInt("00", 16) + 2 +1;
        int sk = sum % 256;
        byte[] b = {(byte) 0x68, (byte) 0x05, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x01, (byte) sk, (byte) 0x16};
        BlueToothUtils.ControlBracelet(b);
    }

    /**
     * 关闭短信提醒
     */
    private void CloseMsgRemind() {
        int sum = Integer.parseInt("68", 16) + Integer.parseInt("05", 16) + Integer.parseInt("03", 16) + Integer.parseInt("00", 16)  + 2;
        int sk = sum % 256;
        byte[] b = {(byte) 0x68, (byte) 0x05, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) sk, (byte) 0x16};
        BlueToothUtils.ControlBracelet(b);
    }


}
