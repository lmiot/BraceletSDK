package com.lmiot.BraceletDemo.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lmiot.BraceletDemo.Adapter.FragmentViewPagerAdapter;
import com.lmiot.BraceletDemo.Bean.SportDataInfo;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.SPUtil;
import com.lmiot.BraceletDemo.Util.TimeUtils;
import com.lmiot.BraceletDemo.fragments.ConsumptionDayFragment;
import com.lmiot.BraceletDemo.fragments.ConsumptionMonthFragment;
import com.lmiot.BraceletDemo.fragments.ConsumptionWeekFragment;
import com.lmiot.BraceletDemo.fragments.HeartRateFargment;
import com.lmiot.BraceletDemo.fragments.PaceDayFragment;
import com.lmiot.BraceletDemo.fragments.PaceMonthFragment;
import com.lmiot.BraceletDemo.fragments.PaceWeekFragment;
import com.lmiot.BraceletDemo.fragments.SleepDayFragment;
import com.lmiot.BraceletDemo.fragments.SleepMonthFragment;
import com.lmiot.BraceletDemo.fragments.SleepWeekFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Trendactivity extends AppCompatActivity implements View.OnClickListener {

    private int systemYear;
    private List<Fragment> fragments;
    private FragmentViewPagerAdapter FVPadter;
    private int currentId;
    private ImageView mIdBack;
    private TextView mIdTitle;
    private TextView mIdNowDay;
    private ImageView mIdTrendatYearLeftArrow;
    private TextView mIdTvTrendatYear;
    private ImageView mIdTrendatYearRightArrow;
    private RadioButton mIdHeartRate;
    private RadioButton mIdCbTrendatDay;
    private RadioButton mIdCbTrendatWeek;
    private RadioButton mIdCbTrendatMonth;
    private ViewPager mIdVpTrendat;
    private LinearLayout mIdTrendLayout;
    private RadioButton mIdIvTrendatWeek;
    private RadioButton mIdIvTrendatDay;
    private RadioButton mIdIvTrendatMooth;
    private LinearLayout mMainLayout;
    private LinearLayout mHeartRateLayout;
    private HeartRateFargment mHeartRateFargment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.trendactivity);
        initView();
        getSystemYear();
        initFragments();
    }


    /**
     * 设置页头
     */
    private void initView() {
        mIdBack = (ImageView) findViewById(R.id.id_back);
        mIdTitle = (TextView) findViewById(R.id.id_title);
        mIdNowDay = (TextView) findViewById(R.id.id_now_day);
        mIdTrendatYearLeftArrow = (ImageView) findViewById(R.id.id_trendat_year_left_arrow);
        mIdTvTrendatYear = (TextView) findViewById(R.id.id_tv_trendat_year);
        mIdTrendatYearRightArrow = (ImageView) findViewById(R.id.id_trendat_year_right_arrow);
        mMainLayout = (LinearLayout) findViewById(R.id.id_main_title_layout);
        mHeartRateLayout = (LinearLayout) findViewById(R.id.id_heart_rate_layout);


        mIdHeartRate = (RadioButton) findViewById(R.id.id_iv_heart_rate);
        mIdCbTrendatDay = (RadioButton) findViewById(R.id.id_cb_trendat_day);
        mIdCbTrendatWeek = (RadioButton) findViewById(R.id.id_cb_trendat_week);
        mIdCbTrendatMonth = (RadioButton) findViewById(R.id.id_cb_trendat_month);
        mIdVpTrendat = (ViewPager) findViewById(R.id.id_vp_trendat);
        mIdTrendLayout = (LinearLayout) findViewById(R.id.id_trend_layout);
        mIdIvTrendatWeek = (RadioButton) findViewById(R.id.id_iv_trendat_week);
        mIdIvTrendatDay = (RadioButton) findViewById(R.id.id_iv_trendat_day);
        mIdIvTrendatMooth = (RadioButton) findViewById(R.id.id_iv_trendat_mooth);

        mIdTitle.setText("趋势");
        mIdNowDay.setText(TimeUtils.getCurrentDate());

        mIdNowDay.setOnClickListener(this);

        mIdBack.setOnClickListener(this);
        mIdTrendatYearLeftArrow.setOnClickListener(this);
        mIdTrendatYearRightArrow.setOnClickListener(this);
        mIdCbTrendatDay.setOnClickListener(this);
        mIdCbTrendatWeek.setOnClickListener(this);
        mIdCbTrendatMonth.setOnClickListener(this);
        mIdIvTrendatDay.setOnClickListener(this);
        mIdHeartRate.setOnClickListener(this);
        mIdIvTrendatWeek.setOnClickListener(this);
        mIdIvTrendatMooth.setOnClickListener(this);



    }


    private void initFragments() {
        fragments = new ArrayList<Fragment>();
        mHeartRateFargment = new HeartRateFargment();
        PaceDayFragment paceDayFragment = new PaceDayFragment();
        PaceWeekFragment paceWeekFragment = new PaceWeekFragment();
        PaceMonthFragment paceMonthFragment = new PaceMonthFragment();
        ConsumptionDayFragment consumptionDayFragment = new ConsumptionDayFragment();
        ConsumptionWeekFragment consumptionWeekFragment = new ConsumptionWeekFragment();
        ConsumptionMonthFragment consumptionMonthFragment = new ConsumptionMonthFragment();
        SleepDayFragment sleepDayFragment = new SleepDayFragment();
        SleepWeekFragment sleepWeekFragment = new SleepWeekFragment();
        SleepMonthFragment sleepMonthFragment = new SleepMonthFragment();
        fragments.add(mHeartRateFargment);
        fragments.add(paceDayFragment);
        fragments.add(paceWeekFragment);
        fragments.add(paceMonthFragment);
        fragments.add(consumptionDayFragment);
        fragments.add(consumptionWeekFragment);
        fragments.add(consumptionMonthFragment);
        fragments.add(sleepDayFragment);
        fragments.add(sleepWeekFragment);
        fragments.add(sleepMonthFragment);

        FVPadter = new FragmentViewPagerAdapter(this.getSupportFragmentManager(), mIdVpTrendat, fragments);
        mIdVpTrendat.setAdapter(FVPadter);
        mIdVpTrendat.setCurrentItem(0);
        mIdHeartRate.setChecked(true);
        mMainLayout.setVisibility(View.GONE);
        mHeartRateLayout.setVisibility(View.VISIBLE);

        FVPadter.setOnExtraPageChangeListener((new FragmentViewPagerAdapter.OnExtraPageChangeListener() {
                    @Override
                    public void onExtraPageSelected(int i) {
                        switch (i) {
                            case 0:
                                mMainLayout.setVisibility(View.GONE);
                                mHeartRateLayout.setVisibility(View.VISIBLE);
                                mIdVpTrendat.setCurrentItem(0);
                                ChangeBottomIcon();
                                mIdHeartRate.setChecked(true);
                                break;
                            case 1:
                                mMainLayout.setVisibility(View.VISIBLE);
                                mHeartRateLayout.setVisibility(View.GONE);
                                mIdVpTrendat.setCurrentItem(1);
                                ChangeRadioButton();
                                ChangeBottomIcon();
                                mIdIvTrendatDay.setChecked(true);
                                mIdCbTrendatDay.setChecked(true);
                                break;
                            case 2:
                                mMainLayout.setVisibility(View.VISIBLE);
                                mHeartRateLayout.setVisibility(View.GONE);
                                mIdVpTrendat.setCurrentItem(2);
                                ChangeRadioButton();
                                ChangeBottomIcon();
                                mIdIvTrendatDay.setChecked(true);
                                mIdCbTrendatWeek.setChecked(true);
                                break;
                            case 3:
                                mMainLayout.setVisibility(View.VISIBLE);
                                mHeartRateLayout.setVisibility(View.GONE);
                                mIdVpTrendat.setCurrentItem(3);
                                ChangeRadioButton();
                                ChangeBottomIcon();
                                mIdIvTrendatDay.setChecked(true);
                                mIdCbTrendatMonth.setChecked(true);
                                break;
                            case 4:
                                mMainLayout.setVisibility(View.VISIBLE);
                                mHeartRateLayout.setVisibility(View.GONE);
                                mIdVpTrendat.setCurrentItem(4);
                                ChangeRadioButton();
                                ChangeBottomIcon();
                                mIdIvTrendatWeek.setChecked(true);
                                mIdCbTrendatDay.setChecked(true);
                                break;
                            case 5:
                                mMainLayout.setVisibility(View.VISIBLE);
                                mHeartRateLayout.setVisibility(View.GONE);
                                mIdVpTrendat.setCurrentItem(5);
                                ChangeRadioButton();
                                ChangeBottomIcon();
                                mIdIvTrendatWeek.setChecked(true);
                                mIdCbTrendatWeek.setChecked(true);
                                break;
                            case 6:
                                mMainLayout.setVisibility(View.VISIBLE);
                                mHeartRateLayout.setVisibility(View.GONE);
                                mIdVpTrendat.setCurrentItem(6);
                                ChangeRadioButton();
                                ChangeBottomIcon();
                                mIdIvTrendatWeek.setChecked(true);
                                mIdCbTrendatMonth.setChecked(true);
                                break;
                            case 7:
                                mMainLayout.setVisibility(View.VISIBLE);
                                mHeartRateLayout.setVisibility(View.GONE);
                                mIdVpTrendat.setCurrentItem(7);
                                ChangeRadioButton();
                                ChangeBottomIcon();
                                mIdIvTrendatMooth.setChecked(true);
                                mIdCbTrendatDay.setChecked(true);
                                break;
                            case 8:
                                mMainLayout.setVisibility(View.VISIBLE);
                                mHeartRateLayout.setVisibility(View.GONE);
                                mIdVpTrendat.setCurrentItem(8);
                                ChangeRadioButton();
                                ChangeBottomIcon();
                                mIdIvTrendatMooth.setChecked(true);
                                mIdCbTrendatWeek.setChecked(true);
                                break;
                            case 9:
                                mMainLayout.setVisibility(View.VISIBLE);
                                mHeartRateLayout.setVisibility(View.GONE);
                                mIdVpTrendat.setCurrentItem(9);
                                ChangeRadioButton();
                                ChangeBottomIcon();
                                mIdIvTrendatMooth.setChecked(true);
                                mIdCbTrendatMonth.setChecked(true);
                                break;
                        }
                    }
                })
        );

    }

    private void ChangeBottomIcon() {
        mIdHeartRate.setChecked(false);
        mIdIvTrendatDay.setChecked(false);
        mIdIvTrendatMooth.setChecked(false);
        mIdIvTrendatWeek.setChecked(false);
    }

    private void ChangeRadioButton() {
        mIdCbTrendatDay.setChecked(false);
        mIdCbTrendatMonth.setChecked(false);
        mIdCbTrendatWeek.setChecked(false);

    }


    private void getSystemYear() {
        systemYear = Calendar.getInstance().get(Calendar.YEAR);
        mIdTvTrendatYear.setText(Integer.toString(systemYear) + "");
    }


    private void MonthCheck() {
        mIdCbTrendatMonth.setChecked(false);
        mIdCbTrendatWeek.setChecked(false);
        if (mIdIvTrendatDay.isChecked()) {
            mIdVpTrendat.setCurrentItem(3);
        }
        if (mIdIvTrendatWeek.isChecked()) {
            mIdVpTrendat.setCurrentItem(6);

        }
        if (mIdIvTrendatMooth.isChecked()) {
            mIdVpTrendat.setCurrentItem(6);
        }

    }

    private void WeekCheck() {
        mIdCbTrendatDay.setChecked(false);
        mIdCbTrendatMonth.setChecked(false);

        if (mIdIvTrendatDay.isChecked()) {
            mIdVpTrendat.setCurrentItem(2);
        }
        if (mIdIvTrendatWeek.isChecked()) {
            mIdVpTrendat.setCurrentItem(5);

        }
        if (mIdIvTrendatMooth.isChecked()) {
            mIdVpTrendat.setCurrentItem(8);
        }

    }

    private void DayCheck() {
        mIdCbTrendatMonth.setChecked(false);
        mIdCbTrendatWeek.setChecked(false);

        if (mIdIvTrendatDay.isChecked()) {
            mIdVpTrendat.setCurrentItem(1);

        }
        if (mIdIvTrendatWeek.isChecked()) {
            mIdVpTrendat.setCurrentItem(4);

        }
        if (mIdIvTrendatMooth.isChecked()) {
            mIdVpTrendat.setCurrentItem(7);
        }


    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.id_back) {
            finish();

        } else if (i == R.id.id_trendat_year_left_arrow) {
            systemYear = systemYear - 1;
            mIdTvTrendatYear.setText(Integer.toString(systemYear));

        } else if (i == R.id.id_trendat_year_right_arrow) {
            systemYear = systemYear + 1;
            mIdTvTrendatYear.setText(Integer.toString(systemYear));

        } else if (i == R.id.id_cb_trendat_day) {
            DayCheck();

        } else if (i == R.id.id_cb_trendat_week) {
            WeekCheck();

        } else if (i == R.id.id_cb_trendat_month) {
            MonthCheck();

        } else if (i == R.id.id_iv_heart_rate) {  //心率
            mIdVpTrendat.setCurrentItem(0);


        } else if (i == R.id.id_iv_trendat_day) { //步数
            mIdVpTrendat.setCurrentItem(1);


        } else if (i == R.id.id_iv_trendat_week) { //热量
            mIdVpTrendat.setCurrentItem(4);


        }

        else if (i == R.id.id_iv_trendat_mooth) {//睡眠
            mIdVpTrendat.setCurrentItem(7);

        }
        else if (i == R.id.id_now_day) {//睡眠
            showHistory();

        }
    }

    /**
     * 获取历史数据
     */
    private void showHistory() {

        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(Trendactivity.this, new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // TODO Auto-generated method stub
                int mMonth = month + 1;
                int mDay = day;
                final String bydate = year + "-" + mMonth + "-" + mDay;
                mIdNowDay.setText(bydate);
                mHeartRateFargment.setCurrentDate(bydate);
                mHeartRateFargment.initView(mHeartRateFargment.getViewSave());

            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
