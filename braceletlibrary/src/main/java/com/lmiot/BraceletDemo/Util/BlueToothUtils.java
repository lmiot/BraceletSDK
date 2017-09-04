package com.lmiot.BraceletDemo.Util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lmiot.BraceletDemo.Util.ToastUtil;

import java.util.UUID;

/**
 * Created by ming on 2016/7/15.
 */
public class BlueToothUtils {

    public static BluetoothGatt gatt;
    public static void SetBluetooGagg(BluetoothGatt gatt01){
        gatt=gatt01;
    }
    public static BluetoothGatt GetBluetooGatt(){
        return gatt;
    }





    private static BluetoothAdapter mBluetoothAdapter;
    private static BluetoothAdapter.LeScanCallback mLeScanCallback;


    /**
     * 蓝牙搜索
     * @param context
     * @param mHandler
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void scanDevice(final Context context, final Handler mHandler){

        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtil.ToastLongMessage(context,"不支持蓝牙4.0！");
            try {
                Activity activity= (Activity) context;
                activity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            ToastUtil.ToastLongMessage(context,"不支持蓝牙！");
            try {
                Activity activity= (Activity) context;
                activity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        else {
            if(mBluetoothAdapter.isEnabled()){
                mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
                    @Override
                    public void onLeScan(BluetoothDevice bluetoothDevice, int mI, byte[] mBytes) {

                        Message msg = new Message();
                        msg.obj=bluetoothDevice;
                        msg.what=0x200;
                        mHandler.sendMessage(msg);
                        
                    }
                };
                mBluetoothAdapter.startLeScan(mLeScanCallback);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                        Message msg = new Message();
                        msg.what=0x201;
                        mHandler.sendMessage(msg);

                    }
                },5000);

            }
            else {
                //开启蓝牙
                mBluetoothAdapter.enable();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scanDevice(context,mHandler);
                    }
                },5000);
            }

        }

    }










    /**
     * 控制手环的方法
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void ControlBracelet(byte[] value){
        Log.d("BlueToothUtils", "发送数据:" + value.length);

        BluetoothGatt bluetoothGatt = GetBluetooGatt();
        if(bluetoothGatt!=null){
            BluetoothGattService service = bluetoothGatt.getService(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"));
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb"));
            characteristic.setValue(value);
            gatt.writeCharacteristic(characteristic);
        }

    }



}
