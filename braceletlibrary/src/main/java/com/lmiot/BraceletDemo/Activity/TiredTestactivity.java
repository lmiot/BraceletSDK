package com.lmiot.BraceletDemo.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lmiot.BraceletDemo.Adapter.FragmentViewPagerAdapter;
import com.lmiot.BraceletDemo.Bean.TiredData;
import com.lmiot.BraceletDemo.Bean.TiredDataInfo;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.BlueToothUtils;
import com.lmiot.BraceletDemo.Util.SPUtil;
import com.lmiot.BraceletDemo.Util.StrUtils;
import com.lmiot.BraceletDemo.Util.TimeUtils;
import com.lmiot.BraceletDemo.View.BlueStateView;
import com.lmiot.BraceletDemo.View.RoundProgressBar;
import com.lmiot.BraceletDemo.fragments.FiftheenDayfragment;
import com.lmiot.BraceletDemo.fragments.SevenDayfragment;
import com.lmiot.BraceletDemo.fragments.ThirtyDayfragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TiredTestactivity extends AppCompatActivity implements View.OnClickListener {



    private List<Fragment> test_fragments;
    int average_rate = 0;
    private int average_rate01;
    private int sdnn;
    int count = 0;
    Timer mTimer;
    TimerTask mTimerTask;


    TimerTask timerTask_start;
    int proBarNum = 0;
    private TiredData tiredData;
    String url_tiredData = "http://192.168.1.209/bluemediaTest/BraceletDemo/bracelet_saveTired";
    private Intent intent_jump;
    private BraceletDBManager manager;
    private boolean timerFlag = false;
    boolean connected = false;
    int connecttime = 0;
    private Handler mHandler_connect;
    private Runnable mRunnable_connect;
    boolean bluePermission;
    private RateReceiver02 mRateReceiver02;
    private int mSDNN;
    private boolean mStartFlag = false;
    private int mAllRata = 0;
    private ImageView mIdBack;
    private TextView mIdTitle;
    private BlueStateView mIdBlueView;
    private TextView mIdTvTiredTatStart;
    private LinearLayout mIdTiredTatLinerTest;
    private TextView mIdTvTiredTatBPMNumber;
    private TextView mIdTvTiredTatTipStart;
    private RoundProgressBar mIdTiredTatRoundProgressBar;
    private CheckBox mIdCbTiredTatSevenDay;
    private CheckBox mIdCbTiredTatFifthteenDay;
    private CheckBox mIdCbTiredTatThirtyDay;
    private ViewPager mIdVpTiredTat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.tired_testactivity);
        initView();
        initFragments();
        intiDB();
        BlueState();


    }

    /**
     * 设置页头
     */
    private void initView() {

        mIdBack = (ImageView) findViewById(R.id.id_back);
        mIdTitle = (TextView) findViewById(R.id.id_title);
        mIdBlueView = (BlueStateView) findViewById(R.id.id_blue_view);
        mIdTvTiredTatStart = (TextView) findViewById(R.id.id_tv_TiredTat_start);
        mIdTiredTatLinerTest = (LinearLayout) findViewById(R.id.id_tiredTat_liner_test);
        mIdTvTiredTatBPMNumber = (TextView) findViewById(R.id.id_tv_tiredTat_BPM_number);
        mIdTvTiredTatTipStart = (TextView) findViewById(R.id.id_tv_tiredTat_tip_start);
        mIdTiredTatRoundProgressBar = (RoundProgressBar) findViewById(R.id.id_tiredTat_roundProgressBar);
        mIdCbTiredTatSevenDay = (CheckBox) findViewById(R.id.id_cb_tiredTat_sevenDay);
        mIdCbTiredTatFifthteenDay = (CheckBox) findViewById(R.id.id_cb_tiredTat_fifthteenDay);
        mIdCbTiredTatThirtyDay = (CheckBox) findViewById(R.id.id_cb_tiredTat_thirtyDay);
        mIdVpTiredTat = (ViewPager) findViewById(R.id.id_vp_tiredTat);

        mIdTitle.setText("疲劳测试");
        mIdBack.setOnClickListener(this);
        mIdTiredTatLinerTest.setOnClickListener(this);
        mIdTvTiredTatBPMNumber.setOnClickListener(this);
        mIdTiredTatRoundProgressBar.setOnClickListener(this);
        mIdCbTiredTatSevenDay.setOnClickListener(this);
        mIdCbTiredTatFifthteenDay.setOnClickListener(this);
        mIdCbTiredTatThirtyDay.setOnClickListener(this);
        mIdVpTiredTat.setOnClickListener(this);

    }

    /**
     * 显示蓝牙的连接状态
     */
    private void BlueState() {
        if (BlueToothUtils.GetBluetooGatt() == null) {
            mIdBlueView.setBackgroundResource(R.drawable.blue_disconnect);
        } else {
            mIdBlueView.setBackgroundResource(R.drawable.blue_connected);

        }
    }


    /**
     * 定时获取实时心率，先打开疲劳测试，再打开心率测试
     */
    private void GetRate() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                proBarNum++;

                Log.d("TiredTestactivity", "proBarNum:" + proBarNum);
                mIdTiredTatRoundProgressBar.setProgress(proBarNum);

                if (proBarNum == 3) {
                    byte[] b = {(byte) 0x68, (byte) 0x0a, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x74, (byte) 0x16};
                    BlueToothUtils.ControlBracelet(b);
                } else if (proBarNum > 4) {
                    byte[] b = {(byte) 0x68, (byte) 0x06, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x6f, (byte) 0x16};
                    BlueToothUtils.ControlBracelet(b);
                }
            }
        };
        mTimer.schedule(mTimerTask, 1000, 1000);


    }

    /**
     * 动态注册广播接收者
     */
    private void RegistBroadcast01() {
        mRateReceiver02 = new RateReceiver02();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.BraceletDemo.bracelet.success");
        registerReceiver(mRateReceiver02, filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRateReceiver02 != null) {
            unregisterReceiver(mRateReceiver02);
        }

    }

    private void initFragments() {
        test_fragments = new ArrayList<Fragment>();
        SevenDayfragment sevenfragment = new SevenDayfragment();
        FiftheenDayfragment fiftheenfragment = new FiftheenDayfragment();
        ThirtyDayfragment thirtyfragment = new ThirtyDayfragment();
        test_fragments.add(sevenfragment);
        test_fragments.add(fiftheenfragment);
        test_fragments.add(thirtyfragment);

        FragmentViewPagerAdapter testAdapter = new FragmentViewPagerAdapter(this.getSupportFragmentManager(), mIdVpTiredTat, test_fragments);
        mIdVpTiredTat.setAdapter(testAdapter);
        mIdVpTiredTat.setCurrentItem(0);
        testAdapter.setOnExtraPageChangeListener(new FragmentViewPagerAdapter.OnExtraPageChangeListener() {
            @Override
            public void onExtraPageSelected(int i) {
                switch (i) {
                    case 0:
                        mIdVpTiredTat.setCurrentItem(0);
                        changeCheckB();
                        mIdCbTiredTatSevenDay.setChecked(true);

                        break;
                    case 1:
                        mIdVpTiredTat.setCurrentItem(1);
                        changeCheckB();
                        mIdCbTiredTatFifthteenDay.setChecked(true);

                        break;
                    case 2:
                        mIdVpTiredTat.setCurrentItem(2);
                        changeCheckB();
                        mIdCbTiredTatThirtyDay.setChecked(true);

                        break;
                }
            }
        });


    }

    private void changeCheckB() {
        mIdCbTiredTatSevenDay.setChecked(false);
        mIdCbTiredTatFifthteenDay.setChecked(false);
        mIdCbTiredTatThirtyDay.setChecked(false);

    }

    private void showAndHide() {
        mIdTvTiredTatStart.setVisibility(View.INVISIBLE);
        mIdTvTiredTatTipStart.setVisibility(View.INVISIBLE);
        mIdTiredTatLinerTest.setVisibility(View.VISIBLE);
        mIdTvTiredTatBPMNumber.setVisibility(View.VISIBLE);
    }

    private void resetHide() {
        proBarNum = 0;
        mIdTiredTatRoundProgressBar.setProgress(0);
        mIdTvTiredTatStart.setVisibility(View.VISIBLE);
        mIdTvTiredTatTipStart.setVisibility(View.VISIBLE);
        mIdTiredTatLinerTest.setVisibility(View.INVISIBLE);
        mIdTvTiredTatBPMNumber.setVisibility(View.INVISIBLE);
    }

    private void StartTest() {
        mIdTiredTatRoundProgressBar.setMax(120);

        //打开心率测试
        byte[] open = {(byte) 0x68, (byte) 0x06, (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x70, (byte) 0x16};
        BlueToothUtils.ControlBracelet(open);
        RegistBroadcast01();

        //打开疲劳测试和获取实时心率
        GetRate();


    }

    private void intiDB() {
        manager = new BraceletDBManager(this);
    }


    public void jumpDetail() { /*测试完毕，跳转到详情页*/

        intent_jump = new Intent(TiredTestactivity.this, TiredTestDetailActivity.class);
        String get_average = (int) (mAllRata / 115) + "";
        String get_sdnn = sdnn + "";
        if (get_average.equals("") || get_sdnn.equals("")) {
            intent_jump.putExtra("result", "empty");

        } else {
            TiredDataInfo tiredDataInfo = manager.findTiredDataInfo(SPUtil.getUserName(getApplicationContext()), TimeUtils.getCurrentDate());
            if (tiredDataInfo.getDate().equals("0")) {
                manager.saveTiredDataInfo(new TiredDataInfo(SPUtil.getUserName(getApplicationContext()), TimeUtils.getCurrentDate(), get_sdnn));
            } else {
                manager.updateTiredDataInfo(new TiredDataInfo(SPUtil.getUserName(getApplicationContext()), TimeUtils.getCurrentDate(), get_sdnn), SPUtil.getUserName(getApplicationContext()), TimeUtils.getCurrentDate());

            }

            intent_jump.putExtra("average_rate01", get_average);
            intent_jump.putExtra("sdnn", get_sdnn);
            resetHide();
            startActivity(intent_jump);
        }

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.id_back) {
            finish();

        } else if (i == R.id.id_tiredTat_liner_test) {
        } else if (i == R.id.id_tv_tiredTat_BPM_number) {
        } else if (i == R.id.id_tiredTat_roundProgressBar) {
            if (!mStartFlag) {
                mStartFlag = true;
                StartTest();
                showAndHide();
            }

        } else if (i == R.id.id_cb_tiredTat_sevenDay) {
            mIdVpTiredTat.setCurrentItem(0);
            changeCheckB();
            mIdCbTiredTatSevenDay.setChecked(true);

        } else if (i == R.id.id_cb_tiredTat_fifthteenDay) {
            mIdVpTiredTat.setCurrentItem(1);
            changeCheckB();
            mIdCbTiredTatFifthteenDay.setChecked(true);

        } else if (i == R.id.id_cb_tiredTat_thirtyDay) {
            mIdVpTiredTat.setCurrentItem(2);
            changeCheckB();
            mIdCbTiredTatThirtyDay.setChecked(true);

        }
    }


    private class RateReceiver02 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //收到实时心率数据
            String TiredData = intent.getExtras().getString("TiredData");
            if (TiredData != null) {
                ResolveTired(TiredData.substring(8, 16), TiredData.substring(16, 18));
            }

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
        mIdTvTiredTatBPMNumber.setText(Irate + "");

        mAllRata = mAllRata + Irate;

        if (proBarNum == 120) {
            CloseRate();

        }

    }


    /**
     * 关闭心率测试
     */
    private void CloseRate() {
        mStartFlag = false;
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

    @Override
    protected void onStop() {
        super.onStop();
        CloseRate();

    }

    //这是处理疲劳的信息
    private void ResolveTired(String substring, String substring1) {
        String time = StrUtils.strConvert(substring);
        int timestamp = StrUtils.str16to10int(time) * 1000;
        SimpleDateFormat ff = new SimpleDateFormat("yy-MM-dd");
        String formatdate = ff.format(timestamp);
        String date = TimeUtils.getCurrentDate();
        sdnn = StrUtils.str16to10int(substring1);
        jumpDetail();


    }


}