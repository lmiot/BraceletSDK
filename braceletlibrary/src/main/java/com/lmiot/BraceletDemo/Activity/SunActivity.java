package com.lmiot.BraceletDemo.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lmiot.BraceletDemo.Bean.HeartRateData;
import com.lmiot.BraceletDemo.Bean.SportDataInfo;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Service.BraceletService;
import com.lmiot.BraceletDemo.Util.BlueToothUtils;
import com.lmiot.BraceletDemo.Util.SPUtil;
import com.lmiot.BraceletDemo.Util.StrUtils;
import com.lmiot.BraceletDemo.Util.TimeUtils;
import com.lmiot.BraceletDemo.View.BlueStateView;
import com.lmiot.BraceletDemo.View.RoundProgressBar;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SunActivity extends Activity implements View.OnClickListener {





    private String mAddress;
    private Intent mIntent;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private BlueReceiver mBlueReceiver;
    private boolean mConnectFlag = false;
    private Intent mIntent1;
    private BraceletDBManager manager;
    private int mPercent;
    private SportDataInfo mSportDataInfo;
    private String mSport_time;
    private int mA1Flag = 0;
    private ImageView mIdBack;
    private TextView mIdTitle;
    private BlueStateView mIdBlueView;
    private ImageView mIdIvSunatSun;
    private ImageView mIdIvSunatMoon;
    private ImageView mIdIvSunatTrend;
    private ImageView mIdIvSunatDevice;
    private ImageView mIdIvSunatClock;
    private ImageView mIdIvHelp;
    private TextView mIdTvSunatPoewrPercent;
    private ImageView mIdIvSunatPowrShow;
    private RoundProgressBar mIdRpSunatCircle;
    private TextView mIdTvSunatFinishPercent;
    private TextView mIdTvSunTarget;
    private TextView mIdTvSunatPaceCounts;
    private PercentLinearLayout mIdTvSunatPaceCountsLi;
    private View mPaceCountsLine;
    private PercentLinearLayout mIdTvSunatHeartRateImg;
    private PercentLinearLayout mIdIvSunatSports;
    private PercentLinearLayout mIdTvSunatTestTiredImg;
    private PercentRelativeLayout mIdBlueMain;
    private TextView mIdTvSunatCenterDate;
    private PercentLinearLayout mIdIvSunatTurndownArrow;
    private TextView mIdTvSunatPacetime;
    private TextView mIdTvSunatRuncouts;
    private TextView mIdTvSunatHeatconsumption;
    private TextView mIdTvSunatTotalcounts;
    private PercentLinearLayout mIdSunRateDetail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sunactivity);
        initView();
        intiDB();

        StartConnectService(getIntent().getStringExtra("devID")); //开启蓝牙连接服务
        mIdBlueView.startSearcheBlue();
        RegistBroadcast();


    }

    private void intiDB() {
        manager = new BraceletDBManager(this);
    }



    @Override
    protected void onResume() {

        super.onResume();
        Log.d("SunActivity", "恢复被调用");
        GetMonitorData();
        ShowTarget();


     /*   final String title = TimeUtils.getCurrentTime();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int random=50+(int)(Math.random()*160); //产生随机数
                SAveRateDate(title,random,TimeUtils.getHeartRateCurrentTime());
            }
        },1000,1000);*/


    }


    /**
     * 虚拟保存心率数据到数据库:以小时为单位保存，具体精度可以自己根据实际修改
     */
    private void SAveRateDate(String title,int rate, String time) {

        try {

            String currentDate = TimeUtils.getCurrentDate();

            HeartRateData heartRateData = new HeartRateData();
            heartRateData.setSessionID("");
            heartRateData.setTitle(title);
            heartRateData.setDate(currentDate);
            heartRateData.setTime(time);
            heartRateData.setHeartRate(rate);
            manager.saveHeartRateDataInfo(heartRateData);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 显示目标
     */
    private void ShowTarget() {
        mIdTvSunatCenterDate.setText(TimeUtils.getCurrentDate());
        SportDataInfo sportDataInfo_now = manager.findSportDataInfo(SPUtil.getUserName(getApplicationContext()), TimeUtils.getCurrentDate());
        RefreshView(sportDataInfo_now);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StopGetMonitorData();


    }

    /**
     * 停止刷新线程
     */
    private void StopGetMonitorData() {

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 校准时间和电量
     */
    private void CorrectTimerAndPower() {
        int millis = (int) (System.currentTimeMillis() / 1000);
        millis = millis + 8 * 3600;
        String s = Integer.toHexString(millis);  /*把时间转为16进制*/
        String t1 = s.substring(6, 8);
        String t2 = s.substring(4, 6);
        String t3 = s.substring(2, 4);
        String t4 = s.substring(0, 2);

        int i =
                Integer.parseInt("68", 16) +
                        Integer.parseInt("20", 16) +
                        Integer.parseInt("04", 16) +
                        Integer.parseInt("00", 16) +
                        Integer.parseInt(t1, 16) +
                        Integer.parseInt(t2, 16) +
                        Integer.parseInt(t3, 16) +
                        Integer.parseInt(t4, 16);


        int i1 = Integer.parseInt(t1, 16);
        int i2 = Integer.parseInt(t2, 16);
        int i3 = Integer.parseInt(t3, 16);
        int i4 = Integer.parseInt(t4, 16);
        int code = i % 256;
        byte[] realterTimer = {(byte) 0x68, (byte) 0x20, (byte) 0x04, (byte) 0x00, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) code, (byte) 0x16};
        BlueToothUtils.ControlBracelet(realterTimer);
        try {
            Thread.sleep(300);  //发送指令查看电量
            byte[] power = {(byte) 0x68, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x6B, (byte) 0x16};

            Log.d("SunActivity", "电量发送成功");
            BlueToothUtils.ControlBracelet(power);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 定时获取设备的监控数据:3秒刷新一次数据
     */
    private void GetMonitorData() {

        if (mTimerTask == null) {
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Calendar c = Calendar.getInstance();
                    int myear = c.get(Calendar.YEAR) - 2000;
                    int mmonth = c.get(Calendar.MONTH) + 1;
                    int mday = c.get(Calendar.DAY_OF_MONTH);
                    int check = (myear + mmonth + mday) / 256;
                    Log.e("获取日期", myear + ":" + mmonth + ":" + mday);

                    int sum = Integer.parseInt("68", 16) + Integer.parseInt("21", 16) + Integer.parseInt("03", 16) + Integer.parseInt("00", 16) + myear + mmonth + mday;
                    String s = Integer.toHexString(sum % 256);
                    int code = Integer.parseInt(s, 16);
                    Log.e("获取日期的校验码", code + "");
                    byte[] getMotionData = {(byte) 0x68, (byte) 0x21, (byte) 0x03, (byte) 0x00, (byte) mday, (byte) mmonth, (byte) myear, (byte) code, (byte) 0x16};
                    BlueToothUtils.ControlBracelet(getMotionData);

                }
            };

            mTimer.schedule(mTimerTask, 1000, 1000);
        }

    }


    /**
     * 开启蓝牙连接服务
     */
    private void StartConnectService(String DevAddress) {
        mIntent = new Intent(SunActivity.this, BraceletService.class);
        mIntent.putExtra("blueAddress", DevAddress);
        startService(mIntent);


    }

    /**
     * 动态注册广播接收者
     */
    private void RegistBroadcast() {
        mBlueReceiver = new BlueReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.BraceletDemo.bracelet.success");
        registerReceiver(mBlueReceiver, filter);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.id_back) {
            finish();

        } else if (i == R.id.id_iv_sunat_sun) {
        } else if (i == R.id.id_iv_sunat_moon) {
            mIntent1 = new Intent(SunActivity.this, Moonactivity.class);
            mIntent1.putExtra("power", mIdTvSunatPoewrPercent.getText().toString());
            startActivity(mIntent1);


        } else if (i == R.id.id_iv_sunat_trend) {
            mIntent1 = new Intent(SunActivity.this, Trendactivity.class);
            startActivity(mIntent1);

        } else if (i == R.id.id_iv_sunat_device) {
        } else if (i == R.id.id_iv_sunat_clock) {
            mIntent1 = new Intent(SunActivity.this, Clockactivity.class);
            startActivity(mIntent1);

        } else if (i == R.id.id_iv_help) {
        } else if (i == R.id.id_tv_sunat_poewr_percent) {
        } else if (i == R.id.id_iv_sunat_powr_show) {
        } else if (i == R.id.id_tv_sunat_heart_rate_img) {
            mIntent1 = new Intent(SunActivity.this, HeartrRateActivity.class);
            startActivity(mIntent1);

        } else if (i == R.id.id_iv_sunat_sports) {
            mIntent1 = new Intent(SunActivity.this, ChooseTestactivity.class);
            startActivity(mIntent1);

        } else if (i == R.id.id_tv_sunat_test_tired_img) {
            mIntent1 = new Intent(SunActivity.this, TiredTestactivity.class);
            startActivity(mIntent1);

        } else if (i == R.id.id_iv_sunat_turndown_arrow) {
            GetHistoryData();

        } else if (i == R.id.id_rp_sunat_circle) {
            Intent intent_paceCounts = new Intent(SunActivity.this, GoalSettinctivity.class);
            intent_paceCounts.putExtra("showView", "showPaceCounts");
            intent_paceCounts.putExtra("target_step", mIdTvSunatPaceCounts.getText().toString());
            intent_paceCounts.putExtra("step_now", mIdTvSunatRuncouts.getText().toString());
            startActivityForResult(intent_paceCounts, 0);

        } else if (i == R.id.id_sun_rate_detail) {
            Intent intent = new Intent(SunActivity.this, Detialactivity.class);
            intent.putExtra("bydate", mIdTvSunatCenterDate.getText().toString());
            startActivity(intent);

        }
    }


    private class BlueReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getExtras().getString("connect");
            Log.i("Recevier1", "接收到:" + result);

            if (result != null) {
                if (result.equals("success")) {
                    mIdBlueView.stopSearcheBlue();
                    mIdBlueView.setBackgroundResource(R.drawable.blue_connected);
                    mConnectFlag = true;
                    Toast.makeText(context, "蓝牙连接成功", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CorrectTimerAndPower();//3秒后校时
                        }
                    },3000);



                }
            }

            //收到a1监测数据
            String monitorData = intent.getExtras().getString("monitor");
            if (monitorData != null) {
                mA1Flag++;

                ResolveRealTimeTesting_one(monitorData.substring(8, 14), monitorData.substring(14, 22), monitorData.substring(22, 30), monitorData.substring(30, 38), monitorData.substring(38, 46));
                if (mA1Flag < 3) {
                    ResponA1(monitorData.substring(8, 14));
                }
            }

            String A2Data01 = intent.getStringExtra("a2data");
            if (A2Data01 != null) {
                ResolveA201(A2Data01);
                ResponeA201(A2Data01.substring(8, 26));
            }

            String poweData = intent.getStringExtra("poweData");
            if (poweData != null) {
                ResolvePower(poweData.substring(8, 10));
            }


        }

    }

    /**
     * 显示电量信息
     */

    private void ResolvePower(String power) {
        int mpower = StrUtils.str16to10int(power);
        if (mpower <= 10) {
            mIdIvSunatPowrShow.setBackgroundResource(R.drawable.power1);
        } else if (mpower > 10 & mpower <= 25) {
            mIdIvSunatPowrShow.setBackgroundResource(R.drawable.power2);
        } else if (mpower > 25 & mpower <= 50) {
            mIdIvSunatPowrShow.setBackgroundResource(R.drawable.power3);
        } else if (mpower > 50 & mpower <= 75) {
            mIdIvSunatPowrShow.setBackgroundResource(R.drawable.power4);
        } else {
            mIdIvSunatPowrShow.setBackgroundResource(R.drawable.power5);
        }

        mIdTvSunatPoewrPercent.setText(String.valueOf(mpower) + "%");


    }

    //应答a2:01
    private void ResponeA201(String s) {
        String s1 = s.substring(0, 2);
        String s2 = s.substring(2, 4);
        String s3 = s.substring(4, 6);
        String s4 = s.substring(6, 8);
        String s5 = s.substring(8, 10);
        String s6 = s.substring(10, 12);
        String s7 = s.substring(12, 14);
        String s8 = s.substring(14, 16);
        String s9 = s.substring(16, 18);
        int i1 = Integer.parseInt(s1, 16);
        int i2 = Integer.parseInt(s2, 16);
        int i3 = Integer.parseInt(s3, 16);
        int i4 = Integer.parseInt(s4, 16);
        int i5 = Integer.parseInt(s5, 16);
        int i6 = Integer.parseInt(s6, 16);
        int i7 = Integer.parseInt(s7, 16);
        int i8 = Integer.parseInt(s8, 16);
        int i9 = Integer.parseInt(s9, 16);
        int sum = Integer.parseInt("68", 16) + Integer.parseInt("22", 16) + Integer.parseInt("09", 16) + Integer.parseInt("00", 16) + i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9;
        int sk = sum % 256;
        byte[] b = {(byte) 0x68, (byte) 0x22, (byte) 0x09, (byte) 0x00, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) i5, (byte) i6, (byte) i7, (byte) i8, (byte) i9, (byte) sk, (byte) 0x16};
        BlueToothUtils.ControlBracelet(b);

    }

    //处理a2数据01
    private void ResolveA201(String a2Data) {
    }

    //应答A1
    private void ResponA1(String substring) {
        String s1 = substring.substring(0, 2);
        String s2 = substring.substring(2, 4);
        String s3 = substring.substring(4, 6);
        int i1 = Integer.parseInt(s1, 16);
        int i2 = Integer.parseInt(s2, 16);
        int i3 = Integer.parseInt(s3, 16);
        int sum = Integer.parseInt("68", 16) + Integer.parseInt("21", 16) + Integer.parseInt("03", 16) + Integer.parseInt("00", 16) + i1 + i2 + i3;
        int sk = sum % 256;
        byte[] b = {(byte) 0x68, (byte) 0x21, (byte) 0x03, (byte) 0x00, (byte) i1, (byte) i2, (byte) i3, (byte) sk, (byte) 0x16};
        BlueToothUtils.ControlBracelet(b);
    }


    //处理监测的数据
    private void ResolveRealTimeTesting_one(String dates, String pacecouts, String milecouts, String heatcouts, String movetime) {

        String date = "20" + Integer.parseInt(dates.substring(4, 6), 16) + "-" + Integer.parseInt(dates.substring(2, 4), 16) + "-" + Integer.parseInt(dates.substring(0, 2), 16);
        int day_step = Integer.parseInt(StrUtils.strConvert(pacecouts), 16); //步数
        int day_distance = Integer.parseInt(StrUtils.strConvert(milecouts), 16);//
        int day_heat = Integer.parseInt(StrUtils.strConvert(heatcouts), 16);//
        int day_time = Integer.parseInt(StrUtils.strConvert(movetime), 16);//


        if (mIdTvSunatCenterDate.getText().toString().equals(TimeUtils.getCurrentDate())) {  //为当前日期，则刷新
            DecimalFormat df1 = new DecimalFormat("0.00"); //保留两位小数
            mSport_time = df1.format((double) day_time / 3600);
            mIdTvSunatPacetime.setText(mSport_time + "小时");
            mIdTvSunatRuncouts.setText(day_step + "步");
            mIdTvSunatHeatconsumption.setText(day_heat + "千卡");
            mIdTvSunatTotalcounts.setText(day_distance + "米");

            String user_target = mIdTvSunatPaceCounts.getText().toString();
            int user_targetnum = Integer.parseInt(user_target);
            if (user_targetnum != 0) {
                mPercent = (int) (day_step * 100 / user_targetnum);
            }

            if (mPercent == 0) {
                mIdRpSunatCircle.setProgress(0);
            } else {
                mIdRpSunatCircle.setProgress(mPercent);
            }


        }


        mSportDataInfo = new SportDataInfo();
        mSportDataInfo.setSessionID(SPUtil.getUserName(getApplicationContext()));
        mSportDataInfo.setDate(date);

        mSportDataInfo.setPercent(mPercent + "");
        mSportDataInfo.setTarget(mIdTvSunatPaceCounts.getText().toString());
        mSportDataInfo.setDay_time(mSport_time);
        mSportDataInfo.setDay_step(day_step + "");
        mSportDataInfo.setDay_heat(day_heat + "");
        mSportDataInfo.setDay_distanc(day_distance + "");

                   /* 拿到数据，先保存在本地，若为空，则保存，若不为空，则覆盖*/
        SportDataInfo sportDataInfo_search = manager.findSportDataInfo(SPUtil.getUserName(getApplicationContext()), date);
        if (sportDataInfo_search.getDate().equals("0")) {
            manager.saveSportDataInfo(mSportDataInfo);
            Log.e("数据库操作:新数据", mSportDataInfo.getDate());
        } else {
            manager.updateSportDataInfo(mSportDataInfo, SPUtil.getUserName(getApplicationContext()), date);
            Log.e("数据库操作：", "更新数据");
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(mIntent);
        unregisterReceiver(mBlueReceiver);
        CloseRate();
        Log.d("SunActivity", "activity被销毁");

    }

    /**
     *
     */
    private void CloseRate() {
        byte[] close = {(byte) 0x68, (byte) 0x06, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x71, (byte) 0x16};
        BlueToothUtils.ControlBracelet(close);

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 设置页头
     */
    private void initView() {


        mIdBack = findViewById(R.id.id_back);
        mIdTitle = findViewById(R.id.id_title);
        mIdBlueView = findViewById(R.id.id_blue_view);
        mIdIvSunatSun = findViewById(R.id.id_iv_sunat_sun);
        mIdIvSunatMoon = findViewById(R.id.id_iv_sunat_moon);
        mIdIvSunatTrend = findViewById(R.id.id_iv_sunat_trend);
        mIdIvSunatDevice = findViewById(R.id.id_iv_sunat_device);
        mIdIvSunatClock = findViewById(R.id.id_iv_sunat_clock);
        mIdIvHelp = findViewById(R.id.id_iv_help);
        mIdTvSunatPoewrPercent = findViewById(R.id.id_tv_sunat_poewr_percent);
        mIdIvSunatPowrShow = findViewById(R.id.id_iv_sunat_powr_show);
        mIdRpSunatCircle = findViewById(R.id.id_rp_sunat_circle);
        mIdTvSunatFinishPercent = findViewById(R.id.id_tv_sunat_finish_percent);
        mIdTvSunTarget = findViewById(R.id.id_tv_sun_target);
        mIdTvSunatPaceCounts = findViewById(R.id.id_tv_sunat_pace_counts);
        mIdTvSunatPaceCountsLi = findViewById(R.id.id_tv_sunat_pace_counts_li);
        mPaceCountsLine = findViewById(R.id.paceCounts_line);
        mIdTvSunatHeartRateImg = findViewById(R.id.id_tv_sunat_heart_rate_img);
        mIdIvSunatSports = findViewById(R.id.id_iv_sunat_sports);
        mIdTvSunatTestTiredImg = findViewById(R.id.id_tv_sunat_test_tired_img);
        mIdBlueMain = findViewById(R.id.id_blue_main);
        mIdTvSunatCenterDate = findViewById(R.id.id_tv_sunat_center_date);
        mIdIvSunatTurndownArrow = findViewById(R.id.id_iv_sunat_turndown_arrow);
        mIdTvSunatPacetime = findViewById(R.id.id_tv_sunat_pacetime);
        mIdTvSunatRuncouts = findViewById(R.id.id_tv_sunat_runcouts);
        mIdTvSunatHeatconsumption = findViewById(R.id.id_tv_sunat_heatconsumption);
        mIdTvSunatTotalcounts = findViewById(R.id.id_tv_sunat_totalcounts);
        mIdSunRateDetail = findViewById(R.id.id_sun_rate_detail);

        mIdTitle.setText("智能手环");


        mIdBack.setOnClickListener(this);
        mIdIvSunatSun.setOnClickListener(this);
        mIdIvSunatMoon.setOnClickListener(this);
        mIdIvSunatTrend.setOnClickListener(this);
        mIdIvSunatDevice.setOnClickListener(this);
        mIdIvSunatClock.setOnClickListener(this);
        mIdIvHelp.setOnClickListener(this);
        mIdTvSunatPoewrPercent.setOnClickListener(this);
        mIdIvSunatPowrShow.setOnClickListener(this);
        mIdTvSunatHeartRateImg.setOnClickListener(this);
        mIdIvSunatSports.setOnClickListener(this);
        mIdTvSunatTestTiredImg.setOnClickListener(this);
        mIdIvSunatTurndownArrow.setOnClickListener(this);
        mIdRpSunatCircle.setOnClickListener(this);
        mIdSunRateDetail.setOnClickListener(this);




    }




    /**
     * 显示设置的目标
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("SunActivity", "返回数据");
        switch (resultCode) {
            case 3:
                String str = data.getStringExtra("pace_level");
                mIdTvSunatPaceCounts.setText(str);
                break;
            default:
                break;
        }
    }


    /**
     * 获取历史数据
     */
    private void GetHistoryData() {

        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(SunActivity.this, new DatePickerDialog.OnDateSetListener() {

            private SportDataInfo sdi;

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // TODO Auto-generated method stub
                int mMonth = month + 1;
                int mDay = day;
                final String bydate = year + "-" + mMonth + "-" + mDay;
                mIdTvSunatCenterDate.setText(bydate);
                sdi = manager.findSportDataInfo(SPUtil.getUserName(getApplicationContext()), bydate);

                RefreshView(sdi);


            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 刷新数据
     */
    private void RefreshView(SportDataInfo sdi) {
        int percent_now = Integer.parseInt(sdi.getPercent());
        Log.d("SunActivity", "目标：" + sdi.getTarget());
        Log.d("SunActivity", "目标百分比：" + sdi.getPercent());
        mIdRpSunatCircle.setProgress(percent_now);
        mIdTvSunatPaceCounts.setText(sdi.getTarget());
        mIdTvSunatPacetime.setText(sdi.getDay_time() + "小时");
        mIdTvSunatRuncouts.setText(sdi.getDay_step() + "步");
        mIdTvSunatHeatconsumption.setText(sdi.getDay_heat() + "千卡");
        mIdTvSunatTotalcounts.setText(sdi.getDay_distanc() + "米");
    }


}

