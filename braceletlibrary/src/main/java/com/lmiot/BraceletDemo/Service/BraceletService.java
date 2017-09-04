package com.lmiot.BraceletDemo.Service;

import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lmiot.BraceletDemo.Activity.HeartrRateActivity;
import com.lmiot.BraceletDemo.Bean.HeartRateData;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.Util.BlueToothUtils;
import com.lmiot.BraceletDemo.Util.StrUtils;
import com.lmiot.BraceletDemo.Util.TimeUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by ming on 2016/7/14.
 */
public class BraceletService extends Service {
    private String mBlueAddress;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mRemoteDevice;
    private BluetoothGattCallback mBluetoothGattCallback;
    private BluetoothGatt mBluetoothGatt;
    private Timer mTimer;
    private TimerTask mTask;
    private int mNum=0;
    private String mDataValue;
    private String mMotionData;
    private Intent mIntent;
    private String mA2Data01;
    private String mRateData;
    private String mSleepData;
    private int mDataLength;
    private BraceletDBManager mBraceletDBManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BraceletService", "服务被开启");

        mBlueAddress = intent.getStringExtra("blueAddress");
        mBraceletDBManager = new BraceletDBManager(this);

        JudgeBlue();

        return super.onStartCommand(intent, flags, startId);

    }

    /**
     * 判断蓝牙是否开启
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void JudgeBlue() {
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();

            SetStateListener();


        } else {
            ConnectDevice();
        }
    }

    /**
     * 是否打开蓝牙监听
     */
    private void SetStateListener() {
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mNum++;
                Log.d("BraceletService", "蓝牙开启mNum:" + mNum);
                if(mBluetoothAdapter.getState()==12){
                    mTask.cancel();
                    ConnectDevice();
                };

            }
        };
        mTimer.schedule(mTask,1000,1000);


    }


    /**
     * 连接蓝牙设备
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void ConnectDevice() {

        mRemoteDevice = mBluetoothAdapter.getRemoteDevice(mBlueAddress);
        mBluetoothGattCallback = new BluetoothGattCallback() {

            //连接成功
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
                if(newState==2){

                    mIntent = new Intent();
                    mIntent.setAction("com.BraceletDemo.bracelet.success");
                    mIntent.putExtra("connect","success");
                    sendBroadcast(mIntent);

                    Log.d("BraceletService", "蓝牙连接成功");
                    gatt.discoverServices();



                }
                else{
                    Log.d("BraceletService", "蓝牙连接失败");
                }
            }

            //发现服务
            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                Log.d("BraceletService", "gattstatus:" + status);
                if(status==0){
                    Log.d("BraceletService", "gatt.getServices().size():" + gatt.getServices().size());

                    BlueToothUtils.SetBluetooGagg(gatt);//保存gatt，以便activity操作


                    Log.d("BraceletService", "mBluetoothGatt.getServices().size():" + mBluetoothGatt.getServices().size());


                    //必须使能才能读到返回的特征值
                    BluetoothGattService service = gatt.getService(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"));
                    BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb"));
                    gatt.setCharacteristicNotification(characteristic,true);
                    service.getCharacteristics();

                    }


            }




            //收到数据
            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicWrite(gatt, characteristic, status);

                String value01 = StrUtils.bytesToHexString(characteristic.getValue());
                Log.d("BraceletService", "数据结果001:" + value01);


            }


            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicRead(gatt, characteristic, status);
                String value01 = StrUtils.bytesToHexString(characteristic.getValue());
                Log.d("BraceletService", "数据结果002:" + value01);
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                super.onCharacteristicChanged(gatt, characteristic);


                mDataValue = StrUtils.bytesToHexString(characteristic.getValue());
                Log.d("BraceletService", "数据结果003:" + mDataValue);

                /******************接收监测数据*********************/
                if(mDataValue.substring(0,2).equals("68")){
                    if(mDataValue.substring(2,4).equals("a1")){
                        mMotionData = mDataValue;
                    }


                    /******************接收离线数据*********************/
                   else if(mDataValue.substring(2,4).equals("a2")){
                        if(mDataValue.substring(24, 26).equals("01")){
                            mA2Data01 = mDataValue;
                        }
                        else if(mDataValue.substring(24, 26).equals("02")){
                            Respona2_02(mDataValue.substring(8,30));

                        }
                        else if(mDataValue.substring(24, 26).equals("03")){
                            Respona2_03(mDataValue.substring(8,30));

                        }

                    }


                    /******************接收实时心率数据*********************/
                    else if(mDataValue.substring(2,4).equals("86")&mDataValue.length()==40){
                        mRateData = mDataValue;
                        Log.d("BraceletService", "收到实时心率数据86:"+mRateData);
                        mIntent = new Intent();
                        mIntent.setAction("com.BraceletDemo.bracelet.success");
                        mIntent.putExtra("RateData",mRateData);
                        saverateDate(mRateData);
                        sendBroadcast(mIntent);
                    }


                    /******************接收手环电量信息*********************/
                    else if(mDataValue.substring(2,4).equals("83")){
                        String poweData = mDataValue;
                        Log.d("BraceletService", "接收手环电量信息83:"+poweData);
                        mIntent = new Intent();
                        mIntent.setAction("com.BraceletDemo.bracelet.success");
                        mIntent.putExtra("poweData",poweData);
                        sendBroadcast(mIntent);
                    }


                    /******************接收疲劳信息*********************/
                    else if(mDataValue.substring(2,4).equals("a5")){
                        String TiredData = mDataValue;
                        Log.d("BraceletService", "接收疲劳信息a5:"+TiredData);
                        mIntent = new Intent();
                        mIntent.setAction("com.BraceletDemo.bracelet.success");
                        mIntent.putExtra("TiredData",TiredData);
                        sendBroadcast(mIntent);
                    }


                    /******************接收睡眠数据********************/
                    else if(mDataValue.substring(2,4).equals("a3")){

                        mSleepData = mDataValue;
                        String num01 = mSleepData.substring(4, 8);
                        String num = StrUtils.strConvert4(num01);
                        mDataLength = (Integer.parseInt(num, 16) + 6) * 2;
                        if (mDataLength <= 40) {
                            mIntent = new Intent();
                            mIntent.setAction("com.BraceletDemo.bracelet.success");
                            mIntent.putExtra("SleepData", mSleepData);
                            sendBroadcast(mIntent);
                        }

                    }


                }
                else{
                    mMotionData=mMotionData+mDataValue;
                    mA2Data01=mA2Data01+mDataValue;
                    mSleepData=mSleepData+mDataValue;

                    if(mMotionData.length()==50){
                        Log.d("BraceletService", "收到监测数据A1:"+mMotionData);
                        mIntent = new Intent();
                        mIntent.setAction("com.BraceletDemo.bracelet.success");
                        mIntent.putExtra("monitor",mMotionData);
                        sendBroadcast(mIntent);
                    }


                    if(mA2Data01.length()==46){
                        Log.d("BraceletService", "收到监测数据A2:"+mA2Data01);
                        mIntent = new Intent();
                        mIntent.setAction("com.BraceletDemo.bracelet.success");
                        mIntent.putExtra("a2data",mA2Data01);
                        sendBroadcast(mIntent);
                    }


                    if(mSleepData.length()==mDataLength){
                        mIntent = new Intent();
                        mIntent.setAction("com.BraceletDemo.bracelet.success");
                        mIntent.putExtra("SleepData", mSleepData);
                        sendBroadcast(mIntent);
                    }


                }

            }
        };




        mBluetoothGatt = mRemoteDevice.connectGatt(this, false, mBluetoothGattCallback);
        mBluetoothGatt.connect();

    }


    /**
     * 保存心率数据到数据库:以S为单位保存，具体精度可以自己根据实际修改
     * @param rateData
     */
    private void saverateDate(String rateData) {

        try {
            String substring = rateData.substring(8, 10);
            int nowRate = StrUtils.str16to10int(substring);

            String heartRateCurrentTime = TimeUtils.getHeartRateCurrentTime();
            String currentDate = TimeUtils.getCurrentDate();

            //先查找当前时间（精确到秒）

            if(nowRate!=0){ //心率为0不保存
                HeartRateData heartRateData = new HeartRateData();
                heartRateData.setSessionID("");
                heartRateData.setTitle(HeartrRateActivity.mSaveTitle);
                heartRateData.setDate(currentDate);
                heartRateData.setTime(heartRateCurrentTime);
                heartRateData.setHeartRate(nowRate);
                mBraceletDBManager.saveHeartRateDataInfo(heartRateData);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //不处理，应答a2：02
    private void Respona2_02(String s) {
        String s1 = s.substring(0, 2);
        String s2 = s.substring(2, 4);
        String s3 = s.substring(4, 6);
        String s4 = s.substring(6, 8);
        String s5 = s.substring(8, 10);
        String s6 = s.substring(10, 12);
        String s7 = s.substring(12, 14);
        String s8 = s.substring(14, 16);
        String s9 = s.substring(16, 18);
        String s10 = s.substring(18, 20);
        String s11 = s.substring(20, 22);
        int i1 = Integer.parseInt(s1, 16);
        int i2 = Integer.parseInt(s2, 16);
        int i3 = Integer.parseInt(s3, 16);
        int i4 = Integer.parseInt(s4, 16);
        int i5 = Integer.parseInt(s5, 16);
        int i6 = Integer.parseInt(s6, 16);
        int i7 = Integer.parseInt(s7, 16);
        int i8 = Integer.parseInt(s8, 16);
        int i9 = Integer.parseInt(s9, 16);
        int i10 = Integer.parseInt(s10, 16);
        int i11 = Integer.parseInt(s11, 16);
        int sum = Integer.parseInt("68", 16) + Integer.parseInt("22", 16) + Integer.parseInt("0b", 16) + Integer.parseInt("00", 16) + i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11;
        int sk = sum % 256;
        byte[] b = {(byte) 0x68, (byte) 0x22, (byte) 0x0b, (byte) 0x00, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) i5, (byte) i6, (byte) i7, (byte) i8, (byte) i9, (byte) i10, (byte) i11, (byte) sk, (byte) 0x16};
        BlueToothUtils.ControlBracelet(b);



    }

    //不处理，直接应答a2：03
    private void Respona2_03(String s) {
        String s1 = s.substring(0, 2);
        String s2 = s.substring(2, 4);
        String s3 = s.substring(4, 6);
        String s4 = s.substring(6, 8);
        String s5 = s.substring(8, 10);
        String s6 = s.substring(10, 12);
        String s7 = s.substring(12, 14);
        String s8 = s.substring(14, 16);
        String s9 = s.substring(16, 18);
        String s10 = s.substring(18, 20);
        String s11 = s.substring(20, 22);
        int i1 = Integer.parseInt(s1, 16);
        int i2 = Integer.parseInt(s2, 16);
        int i3 = Integer.parseInt(s3, 16);
        int i4 = Integer.parseInt(s4, 16);
        int i5 = Integer.parseInt(s5, 16);
        int i6 = Integer.parseInt(s6, 16);
        int i7 = Integer.parseInt(s7, 16);
        int i8 = Integer.parseInt(s8, 16);
        int i9 = Integer.parseInt(s9, 16);
        int i10 = Integer.parseInt(s10, 16);
        int i11 = Integer.parseInt(s11, 16);
        int sum = Integer.parseInt("68", 16) + Integer.parseInt("22", 16) + Integer.parseInt("0b", 16) + Integer.parseInt("00", 16) + i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11;
        int sk = sum % 256;

        byte[] b = {(byte) 0x68, (byte) 0x22, (byte) 0x0b, (byte) 0x00, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) i5, (byte) i6, (byte) i7, (byte) i8, (byte) i9, (byte) i10, (byte) i11, (byte) sk, (byte) 0x16};
        BlueToothUtils.ControlBracelet(b);


    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("BraceletService", "服务被销毁");

    }
}
