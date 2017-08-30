package com.lmiot.BraceletDemo.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.lmiot.BraceletDemo.Bean.SleepDataInfo;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.BlueToothUtils;
import com.lmiot.BraceletDemo.Util.SPUtil;
import com.lmiot.BraceletDemo.Util.TimeUtils;
import com.lmiot.BraceletDemo.View.BlueStateView;
import com.lmiot.BraceletDemo.View.RoundProgressBar;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.Calendar;
import java.util.List;


/**
 */
public class Moonactivity extends Activity  implements View.OnClickListener{





    private Intent mIntent;
    private BraceletDBManager manager;
    private int mPercentNow;
    private BlueReceiver04 mBlueReceiver04;
    private SleepDataInfo mSleepDataInfo;
    private int mPercent;
    private ImageView mIdBack;
    private TextView mIdTitle;
    private BlueStateView mIdBlueView;
    private ImageView mIdIvMoonatSun;
    private ImageView mIdTvMoonatMoon;
    private ImageView mIdIvMoonatTrend;
    private ImageView mIdIvMoonatDevice;
    private ImageView mIdIvMoonatClock;
    private TextView mIdTvMoonatPoewrPercent;
    private ImageView mIdIvMoonatPowrShow;
    private RoundProgressBar mIdRpMoonatCircle;
    private TextView mIdTvMoonatFinishPercent;
    private TextView mIdTvMoonatPaceCounts;
    private PercentLinearLayout mIdTvMoonatPaceCountsLi;
    private PercentRelativeLayout mIdMoonlayoutMain;
    private PercentLinearLayout mIdTvMoonatHeartRateImg;
    private PercentLinearLayout mIdTvMoonatTestTiredImg;
    private PercentRelativeLayout mIdMoonmainLayout;
    private TextView mIdTvMoonatCenterShortLine;
    private PercentLinearLayout mIdIvMoonatTurndownArrow;
    private TextView mIdTvMoonatSleeptime;
    private TextView mIdTvMoonatDeepsleeptime;
    private TextView mIdTvMoonatWaketime;
    private TextView mIdTvMoonatLighttime;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moonactivity);
        initView();
        intiDB();
        RegistBroadcast();
    }

    private void intiDB() {
        manager = new BraceletDBManager(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ShowTarget();
    }

    /**
     * 显示目标
     */
    private void ShowTarget() {
        mIdTvMoonatCenterShortLine.setText(TimeUtils.getCurrentDate());/* 设置当前日期*/
        SleepDataInfo sleepDataInfo_now = manager.findSleepDataInfo(SPUtil.getUserName(getApplication()), TimeUtils.getCurrentDate());
        RefreshView(sleepDataInfo_now); /*选择日期后刷新ui*/
    }

    /**
     * 设置页头
     */
    private void initView() {



        mIdBack = findViewById(R.id.id_back);
        mIdTitle = findViewById(R.id.id_title);
        mIdBlueView = findViewById(R.id.id_blue_view);
        mIdIvMoonatSun = findViewById(R.id.id_iv_moonat_sun);
        mIdTvMoonatMoon = findViewById(R.id.id_tv_moonat_moon);
        mIdIvMoonatTrend = findViewById(R.id.id_iv_moonat_trend);
        mIdIvMoonatDevice = findViewById(R.id.id_iv_moonat_device);
        mIdIvMoonatClock = findViewById(R.id.id_iv_moonat_clock);
        mIdTvMoonatPoewrPercent = findViewById(R.id.id_tv_moonat_poewr_percent);
        mIdIvMoonatPowrShow = findViewById(R.id.id_iv_moonat_powr_show);
        mIdRpMoonatCircle = findViewById(R.id.id_rp_moonat_circle);
        mIdTvMoonatFinishPercent = findViewById(R.id.id_tv_moonat_finish_percent);
        mIdTvMoonatPaceCounts = findViewById(R.id.id_tv_moonat_pace_counts);
        mIdTvMoonatPaceCountsLi = findViewById(R.id.id_tv_moonat_pace_counts_li);
        mIdMoonlayoutMain = findViewById(R.id.id_moonlayout_main);
        mIdTvMoonatHeartRateImg = findViewById(R.id.id_tv_moonat_heart_rate_img);
        mIdTvMoonatTestTiredImg = findViewById(R.id.id_tv_moonat_test_tired_img);
        mIdMoonmainLayout = findViewById(R.id.id_moonmain_layout);
        mIdTvMoonatCenterShortLine = findViewById(R.id.id_tv_moonat_center_short_line);
        mIdIvMoonatTurndownArrow = findViewById(R.id.id_iv_moonat_turndown_arrow);
        mIdTvMoonatSleeptime = findViewById(R.id.id_tv_moonat_sleeptime);
        mIdTvMoonatDeepsleeptime = findViewById(R.id.id_tv_moonat_deepsleeptime);
        mIdTvMoonatWaketime = findViewById(R.id.id_tv_moonat_waketime);
        mIdTvMoonatLighttime = findViewById(R.id.id_tv_moonat_lighttime);

        mIdTitle.setText("智能手环");


        mIdBack.setOnClickListener(this);
        mIdTvMoonatHeartRateImg.setOnClickListener(this);
        mIdTvMoonatTestTiredImg.setOnClickListener(this);
        mIdIvMoonatSun.setOnClickListener(this);
        mIdIvMoonatTrend.setOnClickListener(this);
        mIdRpMoonatCircle.setOnClickListener(this);
        mIdIvMoonatTurndownArrow.setOnClickListener(this);


        String power = getIntent().getStringExtra("power");
        if (power != null) {
            mIdTvMoonatPoewrPercent.setText(power);
        }


        if (BlueToothUtils.GetBluetooGatt() == null) {
            mIdBlueView.setBackgroundResource(R.drawable.blue_disconnect);
        } else {
            mIdBlueView.setBackgroundResource(R.drawable.blue_connected);
        }


    }



    /**
     * 获取历史记录
     */
    private void GetHistoryData() {

        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(Moonactivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // TODO Auto-generated method stub
                int mMonth = month + 1;
                String bydate = year + "-" + mMonth + "-" + day;
                mIdTvMoonatCenterShortLine.setText(bydate);

                List<SleepDataInfo> all = manager.findAllSleep();
                SleepDataInfo sleepDataInfo_total = manager.findSleepDataInfo(SPUtil.getUserName(getApplication()), bydate);
                RefreshView(sleepDataInfo_total); /*选择日期后刷新ui*/
            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    /**
     * 刷新ui
     */
    private void RefreshView(SleepDataInfo sleepDataInfo) {

        int target_time = sleepDataInfo.getTarget();
        int sumTime = sleepDataInfo.getSleep_time();


        if (target_time != 0) {
            mPercentNow = (int) (sumTime * 100 / 60 / target_time);
        } else {
            mPercentNow = 0;
        }


        mIdRpMoonatCircle.setProgress(mPercentNow);

        String sleep_time = TimeUtils.convertMinuteTime(sumTime);
        String depth_time = TimeUtils.convertMinuteTime(sleepDataInfo.getDepth_time());
        String wake_time = TimeUtils.convertMinuteTime(sleepDataInfo.getWake_time());
        String light_time = TimeUtils.convertMinuteTime(sleepDataInfo.getLight_time());

        mIdTvMoonatPaceCounts.setText(target_time + "");
        mIdTvMoonatSleeptime.setText(sleep_time);
        mIdTvMoonatDeepsleeptime.setText(depth_time);
        mIdTvMoonatWaketime.setText(wake_time);
        mIdTvMoonatLighttime.setText(light_time);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 4:
                String str = data.getStringExtra("sleep_level");
                mIdTvMoonatPaceCounts.setText(str);
                break;
            default:
                break;
        }
    }

    /**
     * 动态注册广播接收者
     */
    private void RegistBroadcast() {
        mBlueReceiver04 = new BlueReceiver04();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.BraceletDemo.bracelet.success");
        registerReceiver(mBlueReceiver04, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBlueReceiver04 != null) {
            unregisterReceiver(mBlueReceiver04);
        }

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.id_back) {
            finish();

        } else if (i == R.id.id_tv_moonat_heart_rate_img) {
            mIntent = new Intent(Moonactivity.this, HeartrRateActivity.class);
            startActivity(mIntent);

        } else if (i == R.id.id_tv_moonat_test_tired_img) {
            mIntent = new Intent(Moonactivity.this, TiredTestactivity.class);
            startActivity(mIntent);

        } else if (i == R.id.id_iv_moonat_sun) {
            mIntent = new Intent(Moonactivity.this, SunActivity.class);
            startActivity(mIntent);

        } else if (i == R.id.id_iv_moonat_trend) {
            mIntent = new Intent(Moonactivity.this, Trendactivity.class);
            startActivity(mIntent);

        } else if (i == R.id.id_rp_moonat_circle) {
            mIntent = new Intent(Moonactivity.this, GoalSettinctivity.class);
            mIntent.putExtra("showView", "setSleepTime");
            mIntent.putExtra("target_sleep", mIdTvMoonatPaceCounts.getText().toString());
            mIntent.putExtra("sleep_now", mIdTvMoonatSleeptime.getText().toString());
            startActivityForResult(mIntent, 1);

        } else if (i == R.id.id_iv_moonat_turndown_arrow) {
            GetHistoryData();

        }
    }

    private class BlueReceiver04 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String SleepData = intent.getStringExtra("SleepData");
            if (SleepData != null) {
                ResolveSleep(SleepData, SleepData.length());
                ResponA3(SleepData.substring(8, 30));

            }
        }
    }


    /*处理接收到的睡眠信息*/
    private void ResolveSleep(String sa3, int NumA3) {
        int wake_time = 0;
        int light_time = 0;
        int depth_time = 0;
        String sleeptext = sa3.substring(30, NumA3 - 4);
        for (int i = 0; i < sleeptext.length(); i = i + 2) {

            String substring = sleeptext.substring(i, i + 2);
            if (substring.equals("00")) {
                wake_time++;
            } else if (substring.equals("01")) {
                light_time++;
            } else if (substring.equals("02")) {
                depth_time++;
            } else {
            }
        }

        ShowSleepData(wake_time, light_time, depth_time);

    }

    /**
     * 显示睡眠数据
     */
    private void ShowSleepData(int wake_time_get, int light_time_get, int depth_time_get) {
        if (mIdTvMoonatCenterShortLine.getText().toString().equals(TimeUtils.getCurrentDate())) {


            int sum_time = wake_time_get + light_time_get + depth_time_get;
            String sleep_target = mIdTvMoonatPaceCounts.getText().toString();
            int target = Integer.parseInt(sleep_target);

            mSleepDataInfo = new SleepDataInfo();
            mSleepDataInfo.setSessionID(SPUtil.getUserName(getApplicationContext()));
            mSleepDataInfo.setDate(TimeUtils.getCurrentDate());
            mSleepDataInfo.setTarget(target);
                        /*若该日期下，数据为空，则保存；若已存在，则相加后再更新*/
            SleepDataInfo sleepDataInfo_old = manager.findSleepDataInfo(SPUtil.getUserName(getApplicationContext()), TimeUtils.getCurrentDate());
            if (sleepDataInfo_old.getDate().equals("0")) {

                mSleepDataInfo.setPercent(1);
                mSleepDataInfo.setSleep_time(sum_time);
                mSleepDataInfo.setDepth_time(depth_time_get);
                mSleepDataInfo.setLight_time(light_time_get);
                mSleepDataInfo.setWake_time(wake_time_get);
                manager.saveSleepDataInfo(mSleepDataInfo);
                Log.e("睡眠统计输出02", "数据保存成功:" + sum_time + ":" + target);
            } else {


                sum_time = sum_time + sleepDataInfo_old.getSleep_time();


                if (target != 0) {
                    mPercent = (int) (sum_time / 60 / target);
                } else {
                    mPercent = 0;
                }

                depth_time_get = depth_time_get + sleepDataInfo_old.getDepth_time();
                light_time_get = light_time_get + sleepDataInfo_old.getLight_time();
                wake_time_get = wake_time_get + sleepDataInfo_old.getWake_time();

                mSleepDataInfo.setPercent(mPercent);
                mSleepDataInfo.setSleep_time(sum_time);
                mSleepDataInfo.setDepth_time(depth_time_get);
                mSleepDataInfo.setLight_time(light_time_get);
                mSleepDataInfo.setWake_time(wake_time_get);
                manager.updateSleepDataInfo(mSleepDataInfo, SPUtil.getUserName(getApplicationContext()), TimeUtils.getCurrentDate());
                Log.e("睡眠统计输出02", "数据更新成功:" + sum_time + ":" + target);
            }

            RefreshView(manager.findSleepDataInfo(SPUtil.getUserName(getApplicationContext()), TimeUtils.getCurrentDate()));


        }


    }


    //发送指令应答a2
    public void ResponA3(String s) {
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
        int sum = Integer.parseInt("68", 16) + Integer.parseInt("23", 16) + Integer.parseInt("0b", 16) + Integer.parseInt("00", 16) + i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8 + i9 + i10 + i11;
        int sk = sum % 256;

        byte[] b = {(byte) 0x68, (byte) 0x23, (byte) 0x0b, (byte) 0x00, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) i5, (byte) i6, (byte) i7, (byte) i8, (byte) i9, (byte) i10, (byte) i11, (byte) sk, (byte) 0x16};
        BlueToothUtils.ControlBracelet(b);


    }


}
