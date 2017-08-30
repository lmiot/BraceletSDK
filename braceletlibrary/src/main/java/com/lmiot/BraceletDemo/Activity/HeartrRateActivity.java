package com.lmiot.BraceletDemo.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lmiot.BraceletDemo.Bean.RateDataInfo;
import com.lmiot.BraceletDemo.Bean.UserSportData;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.BlueToothUtils;
import com.lmiot.BraceletDemo.Util.SPUtil;
import com.lmiot.BraceletDemo.Util.StrUtils;
import com.lmiot.BraceletDemo.Util.TimeUtils;
import com.lmiot.BraceletDemo.View.BlueStateView;
import com.lmiot.BraceletDemo.View.RippleBackground;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.Timer;
import java.util.TimerTask;


public class HeartrRateActivity extends Activity  implements View.OnClickListener{


   
   
    private Timer mTimer;
    private TimerTask mTimerTask;
    private int mNum = 0;
    private int mTimerNum = 0;
    private boolean mStopFlag = false;
    private int mAllRata = 0;
    private int mIstep01;
    private int mIdistance01;
    private int mIkcal01;
    private RateReceiver mBlueReceiver01;
    private UserSportData mUserSportData;
    private String mStart_time;
    private String mStop_time;
    private int mIkcal;
    private int mIstep;
    private int mIspeed;
    private int mIdistance;
    private BraceletDBManager manager;
    private String sport_type;
    private ImageView mIdBack;
    private TextView mTvTitle;
    private BlueStateView mIdBlueView;
    private RippleBackground mIdHeartRContent;
    private ImageView mCenterImage;
    private TextView mIdTvHeartRatAverageRate;
    private TextView mIdTvRateNumber;
    private ImageView mIdIvHeartRatHeartWhile;
    private TextView mIdTvHeartRatHeartNumber;
    private CheckBox mIdCbHeartREnd;
    private CheckBox mIdCbHearRStop;
    private ImageView mRateClockImg;
    private TextView mIdTvHeartRatPacetime;
    private TextView mIdTvHeartRatHeartcomsuption;
    private TextView mIdTvHeartRatTatalcounts;
    private TextView mIdTvHeartRatAveragePace;
    private PercentLinearLayout mIdAllDataId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_ratingactivity);
        initView();
        SetCheckListener();
        BlueState();
        intiDB();


    }

    /**
     * 显示蓝牙的连接状态
     */
    private void BlueState() {
        if (BlueToothUtils.GetBluetooGatt() == null) {
            mIdBlueView.setBackgroundResource(R.drawable.blue_disconnect);
        } else {
            mIdBlueView.setBackgroundResource(R.drawable.blue_connected);
            Toast.makeText(this, "正在自动打开心率测试……", Toast.LENGTH_SHORT).show();
            mIdCbHeartREnd.setChecked(true);

        }
    }

    private void intiDB() {
        manager = new BraceletDBManager(this);
    }

    /**
     * 设置checkbox监听
     */
    private void SetCheckListener() {
        mIdCbHeartREnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    mIdCbHeartREnd.setText("结束");
                    OpenRate();
                    mIdCbHearRStop.setEnabled(true);
                    mStart_time = TimeUtils.getCurrentTime();


                } else {
                    mIdCbHeartREnd.setText("打开");
                    CloseRate();
                    mIdCbHearRStop.setEnabled(false);
                    mIdCbHearRStop.setChecked(false);

                    mStop_time = TimeUtils.getCurrentTime();
                    SaveData();
                }
            }
        });


        mIdCbHearRStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIdCbHearRStop.setText("继续");
                    mStopFlag = true;
                } else {
                    mIdCbHearRStop.setText("暂停");
                    mStopFlag = false;
                }
            }
        });


    }

    /**
     * 保存数据到本地并跳转到详情页
     */
    private void SaveData() {


        mUserSportData = new UserSportData();
        mUserSportData.setSessionID(SPUtil.getSessionID(getApplicationContext()));
        mUserSportData.setUsername(SPUtil.getUserName(getApplication()));
        mUserSportData.setDate(TimeUtils.getCurrentDate());

        mUserSportData.setSport_type(sport_type);

        mUserSportData.setStart_time(mStart_time);
        mUserSportData.setEnd_time(mStop_time);
        mUserSportData.setSport_time(mTimerNum);
        mUserSportData.setHeart_num(mIkcal);
        mUserSportData.setStep_num(mIstep);
        mUserSportData.setAverage_speed(mIspeed);


                      /*保存运动数据到本地数据库*/
        manager.saveRateDataInfo(new RateDataInfo(SPUtil.getUserName(HeartrRateActivity.this), TimeUtils.getCurrentDate(),
                sport_type,
                mStart_time,
                mStop_time,
                "低强度",
                mIdTvHeartRatPacetime.getText().toString(),
                mIdTvHeartRatTatalcounts.getText().toString(),
                mIdTvHeartRatHeartcomsuption.getText().toString(),
                mIdTvRateNumber.getText().toString(),
                mIdTvHeartRatAveragePace.getText().toString()
        ));


        Intent intent_heart = new Intent(HeartrRateActivity.this, HeartRateDatailActivity.class);
        intent_heart.putExtra("sport_type", sport_type);//运动类型
        intent_heart.putExtra("start_time", mStart_time);//测试的开始时间
        intent_heart.putExtra("end_time", mStop_time);//测试的结束时间
        intent_heart.putExtra("sport_time", mIdTvHeartRatPacetime.getText().toString());//运动时间
        intent_heart.putExtra("heart_num", mIdTvHeartRatHeartcomsuption.getText().toString());//消耗热量
        intent_heart.putExtra("step_num", mIdTvHeartRatTatalcounts.getText().toString());//步数
        intent_heart.putExtra("average_rate", mIdTvRateNumber.getText().toString());//平均心率
        intent_heart.putExtra("average_speed", mIdTvHeartRatAveragePace.getText().toString());//平均速度

        startActivity(intent_heart);
        finish();


    }

    /**
     * 设置页头
     */
    private void initView() {
        mIdBack = findViewById(R.id.id_back);
        mTvTitle = findViewById(R.id.id_title);
        mIdBlueView = findViewById(R.id.id_blue_view);
        mIdHeartRContent = findViewById(R.id.id_heartR_content);
        mCenterImage = findViewById(R.id.centerImage);
        mIdTvHeartRatAverageRate = findViewById(R.id.id_tv_heartRat_averageRate);
        mIdTvRateNumber = findViewById(R.id.id_tv_rate_number);
        mIdIvHeartRatHeartWhile = findViewById(R.id.id_iv_heartRat_heart_while);
        mIdTvHeartRatHeartNumber = findViewById(R.id.id_tv_heartRat_heart_number);
        mIdCbHeartREnd = findViewById(R.id.id_cb_heartR_end);
        mIdCbHearRStop = findViewById(R.id.id_cb_hearR_stop);
        mRateClockImg = findViewById(R.id.rate_clock_img);
        mIdTvHeartRatPacetime = findViewById(R.id.id_tv_heartRat_pacetime);
        mIdTvHeartRatHeartcomsuption = findViewById(R.id.id_tv_heartRat_heartcomsuption);
        mIdTvHeartRatTatalcounts = findViewById(R.id.id_tv_heartRat_tatalcounts);
        mIdTvHeartRatAveragePace = findViewById(R.id.id_tv_heartRat_averagePace);
        mIdAllDataId = findViewById(R.id.id_all_data_id);

        mIdBack.setOnClickListener(this);

        setTitle();


    }

    private void setTitle() {
        String type = getIntent().getStringExtra("type");
        if (type == null) {
            sport_type = "未知运动";
            mRateClockImg.setImageResource(R.drawable.lock_green);
            mTvTitle.setText("实时心率");
        } else {
            sport_type = type;

            if (sport_type.equals("徒步")) {
                mRateClockImg.setBackgroundResource(R.drawable.rate_step);
                mTvTitle.setText("徒步");
            } else if (sport_type.equals("跑步")) {
                mRateClockImg.setBackgroundResource(R.drawable.rate_run);
                mTvTitle.setText("跑步");
            } else if (sport_type.equals("爬山")) {
                mRateClockImg.setBackgroundResource(R.drawable.rate_climb);
                mTvTitle.setText("爬山");
            } else if (sport_type.equals("球类运动")) {
                mRateClockImg.setBackgroundResource(R.drawable.rate_ball);
                mTvTitle.setText("球类运动");
            } else if (sport_type.equals("力量训练")) {
                mRateClockImg.setBackgroundResource(R.drawable.rate_power);
                mTvTitle.setText("力量训练");
            } else if (sport_type.equals("有氧运动")) {
                mRateClockImg.setBackgroundResource(R.drawable.rate_oxygen);
                mTvTitle.setText("有氧运动");
            }


        }
    }


    /**
     * 打开心率测试
     */
    private void OpenRate() {
        byte[] open = {(byte) 0x68, (byte) 0x06, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x70, (byte) 0x16};
        BlueToothUtils.ControlBracelet(open);
        GetRate();
        RegistBroadcast01();

    }

    /**
     * 动态注册广播接收者
     */
    private void RegistBroadcast01() {
        mBlueReceiver01 = new RateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.BraceletDemo.bracelet.success");
        registerReceiver(mBlueReceiver01, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBlueReceiver01 != null) {
            unregisterReceiver(mBlueReceiver01);
        }
    }

    /**
     * 定时获取实时心率
     */
    private void GetRate() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (!mStopFlag) {
                    mTimerNum++;
                    byte[] b = {(byte) 0x68, (byte) 0x06, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x6f, (byte) 0x16};
                    BlueToothUtils.ControlBracelet(b);
                }
            }
        };
        mTimer.schedule(mTimerTask, 1000, 1000);


    }

    /**
     * 关闭心率测试
     */
    private void CloseRate() {
        byte[] close = {(byte) 0x68, (byte) 0x06, (byte) 0x01, (byte) 0x00, (byte) 0x02, (byte) 0x71, (byte) 0x16};
        BlueToothUtils.ControlBracelet(close);
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.id_back) {
            CloseRate();
            finish();

        }
    }


    private class RateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //收到实时心率数据
            String RateData = intent.getExtras().getString("RateData");
            if (RateData != null) {
                ResolveRealTimeRate(RateData.substring(8, 10), RateData.substring(10, 18), RateData.substring(18, 26), RateData.substring(26, 34), RateData.substring(34, 36));
            }


        }

    }

    /**
     * 处理实时心率数据
     */
    private void ResolveRealTimeRate(String rate, String step, String distance, String kcal, String speed) {

        int Irate = StrUtils.str16to10int(rate);
        mNum++;

        if (mNum == 1) {
            mIstep01 = StrUtils.str16to10int(StrUtils.strConvert(step));
            mIdistance01 = StrUtils.str16to10int(StrUtils.strConvert(distance));
            mIkcal01 = StrUtils.str16to10int(StrUtils.strConvert(kcal));
            Log.d("HeartrRateActivity", "Istep01:" + mIstep01);

        } else if (mNum > 1) {

            mIspeed = StrUtils.str16to10int(speed);
            mIstep = StrUtils.str16to10int(StrUtils.strConvert(step));
            mIdistance = StrUtils.str16to10int(StrUtils.strConvert(distance));
            mIkcal = StrUtils.str16to10int(StrUtils.strConvert(kcal));

            Log.d("HeartrRateActivity", "Istep:" + mIstep);

            if (Irate < 220) {
                ShowTimer();
                mAllRata = mAllRata + Irate;
                mIdTvRateNumber.setText((int) (mAllRata / mNum) + "");
                mIdTvHeartRatHeartNumber.setText(Irate + "");
                mIdTvHeartRatHeartcomsuption.setText((mIkcal - mIkcal01) + "千卡");
                mIdTvHeartRatTatalcounts.setText((mIstep - mIstep01) + "步");
                mIdTvHeartRatAveragePace.setText(mIspeed + "步/秒");
            }
        }
    }

    /**
     * 显示运动时间
     */
    private void ShowTimer() {
        if (mTimerNum < 3600) {
            int minute = (int) (mTimerNum / 60);
            int second = mTimerNum - (minute * 60);
            mIdTvHeartRatPacetime.setText(minute + "分" + second + "秒");
        } else {
            int hour = (int) (mTimerNum / 3600);
            int minute = (int) ((mTimerNum - (hour * 3600)) / 60);
            int second = mTimerNum - (hour * 3600) - (minute * 60);
            mIdTvHeartRatPacetime.setText(hour + "小时" + minute + "分" + second + "秒");
        }

    }

}

