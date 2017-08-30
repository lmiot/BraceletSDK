package com.lmiot.BraceletDemo.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lmiot.BraceletDemo.Adapter.FragmentViewPagerAdapter;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.fragments.ConsumptionDayFragment;
import com.lmiot.BraceletDemo.fragments.ConsumptionMonthFragment;
import com.lmiot.BraceletDemo.fragments.ConsumptionWeekFragment;
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
    private ImageView mIdTrendatYearLeftArrow;
    private TextView mIdTvTrendatYear;
    private ImageView mIdTrendatYearRightArrow;
    private CheckBox mIdCbTrendatDay;
    private CheckBox mIdCbTrendatWeek;
    private CheckBox mIdCbTrendatMonth;
    private ViewPager mIdVpTrendat;
    private LinearLayout mIdTrendLayout;
    private CheckBox mIdIvTrendatWeek;
    private CheckBox mIdIvTrendatDay;
    private CheckBox mIdIvTrendatMooth;


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
        mIdTrendatYearLeftArrow = (ImageView) findViewById(R.id.id_trendat_year_left_arrow);
        mIdTvTrendatYear = (TextView) findViewById(R.id.id_tv_trendat_year);
        mIdTrendatYearRightArrow = (ImageView) findViewById(R.id.id_trendat_year_right_arrow);
        mIdCbTrendatDay = (CheckBox) findViewById(R.id.id_cb_trendat_day);
        mIdCbTrendatWeek = (CheckBox) findViewById(R.id.id_cb_trendat_week);
        mIdCbTrendatMonth = (CheckBox) findViewById(R.id.id_cb_trendat_month);
        mIdVpTrendat = (ViewPager) findViewById(R.id.id_vp_trendat);
        mIdTrendLayout = (LinearLayout) findViewById(R.id.id_trend_layout);
        mIdIvTrendatWeek = (CheckBox) findViewById(R.id.id_iv_trendat_week);
        mIdIvTrendatDay = (CheckBox) findViewById(R.id.id_iv_trendat_day);
        mIdIvTrendatMooth = (CheckBox) findViewById(R.id.id_iv_trendat_mooth);

        mIdTitle.setText("趋势");

        mIdBack.setOnClickListener(this);
        mIdTrendatYearLeftArrow.setOnClickListener(this);
        mIdTrendatYearRightArrow.setOnClickListener(this);
        mIdCbTrendatDay.setOnClickListener(this);
        mIdCbTrendatWeek.setOnClickListener(this);
        mIdCbTrendatMonth.setOnClickListener(this);
        mIdIvTrendatDay.setOnClickListener(this);
        mIdIvTrendatWeek.setOnClickListener(this);
        mIdIvTrendatMooth.setOnClickListener(this);


    }


    private void initFragments() {
        fragments = new ArrayList<Fragment>();
        PaceDayFragment paceDayFragment = new PaceDayFragment();
        PaceWeekFragment paceWeekFragment = new PaceWeekFragment();
        PaceMonthFragment paceMonthFragment = new PaceMonthFragment();
        ConsumptionDayFragment consumptionDayFragment = new ConsumptionDayFragment();
        ConsumptionWeekFragment consumptionWeekFragment = new ConsumptionWeekFragment();
        ConsumptionMonthFragment consumptionMonthFragment = new ConsumptionMonthFragment();
        SleepDayFragment sleepDayFragment = new SleepDayFragment();
        SleepWeekFragment sleepWeekFragment = new SleepWeekFragment();
        SleepMonthFragment sleepMonthFragment = new SleepMonthFragment();
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
        mIdCbTrendatDay.setChecked(true);
        mIdIvTrendatDay.setChecked(true);
        FVPadter.setOnExtraPageChangeListener((new FragmentViewPagerAdapter.OnExtraPageChangeListener() {
                    @Override
                    public void onExtraPageSelected(int i) {
                        switch (i) {
                            case 0:
                                mIdVpTrendat.setCurrentItem(0);
                                ChangeCheckBox();
                                ChangeBottomIcon();
                                mIdIvTrendatDay.setBackgroundResource(R.drawable.pace_small_green);
                                mIdIvTrendatDay.setChecked(true);
                                mIdCbTrendatDay.setChecked(true);
                                break;
                            case 1:
                                mIdVpTrendat.setCurrentItem(1);
                                ChangeCheckBox();
                                ChangeBottomIcon();
                                mIdIvTrendatDay.setBackgroundResource(R.drawable.pace_small_green);
                                mIdIvTrendatDay.setChecked(true);
                                mIdCbTrendatWeek.setChecked(true);
                                break;
                            case 2:
                                mIdVpTrendat.setCurrentItem(2);
                                ChangeCheckBox();
                                ChangeBottomIcon();
                                mIdIvTrendatDay.setBackgroundResource(R.drawable.pace_small_green);
                                mIdIvTrendatDay.setChecked(true);
                                mIdCbTrendatMonth.setChecked(true);
                                break;
                            case 3:
                                mIdVpTrendat.setCurrentItem(3);
                                ChangeCheckBox();
                                ChangeBottomIcon();
                                mIdIvTrendatWeek.setBackgroundResource(R.drawable.consumption_small_green);
                                mIdIvTrendatWeek.setChecked(true);
                                mIdCbTrendatDay.setChecked(true);
                                break;
                            case 4:
                                mIdVpTrendat.setCurrentItem(4);
                                ChangeCheckBox();
                                ChangeBottomIcon();
                                mIdIvTrendatWeek.setBackgroundResource(R.drawable.consumption_small_green);
                                mIdIvTrendatWeek.setChecked(true);
                                mIdCbTrendatWeek.setChecked(true);
                                break;
                            case 5:
                                mIdVpTrendat.setCurrentItem(5);
                                ChangeCheckBox();
                                ChangeBottomIcon();
                                mIdIvTrendatWeek.setBackgroundResource(R.drawable.consumption_small_green);
                                mIdIvTrendatWeek.setChecked(true);
                                mIdCbTrendatMonth.setChecked(true);
                                break;
                            case 6:
                                mIdVpTrendat.setCurrentItem(6);
                                ChangeCheckBox();
                                ChangeBottomIcon();
                                mIdIvTrendatMooth.setBackgroundResource(R.drawable.sleep_small_green);
                                mIdIvTrendatMooth.setChecked(true);
                                mIdCbTrendatDay.setChecked(true);
                                break;
                            case 7:
                                mIdVpTrendat.setCurrentItem(7);
                                ChangeCheckBox();
                                ChangeBottomIcon();
                                mIdIvTrendatMooth.setBackgroundResource(R.drawable.sleep_small_green);
                                mIdIvTrendatMooth.setChecked(true);
                                mIdCbTrendatWeek.setChecked(true);
                                break;
                            case 8:
                                mIdVpTrendat.setCurrentItem(8);
                                ChangeCheckBox();
                                ChangeBottomIcon();
                                mIdIvTrendatMooth.setBackgroundResource(R.drawable.sleep_small_green);
                                mIdIvTrendatMooth.setChecked(true);
                                mIdCbTrendatMonth.setChecked(true);
                                break;
                        }
                    }
                })
        );

    }

    private void ChangeBottomIcon() {
        mIdIvTrendatDay.setBackgroundResource(R.drawable.pace_small_gray);
        mIdIvTrendatDay.setChecked(false);
        mIdIvTrendatMooth.setBackgroundResource(R.drawable.sleep_small_gray);
        mIdIvTrendatMooth.setChecked(false);
        mIdIvTrendatWeek.setBackgroundResource(R.drawable.consumption_small_gray);
        mIdIvTrendatWeek.setChecked(false);
    }

    private void ChangeCheckBox() {
        mIdCbTrendatDay.setChecked(false);
        mIdCbTrendatMonth.setChecked(false);
        mIdCbTrendatWeek.setChecked(false);

    }


    private void getSystemYear() {
        systemYear = Calendar.getInstance().get(Calendar.YEAR);
        mIdTvTrendatYear.setText(Integer.toString(systemYear)+"");
    }


    private void MonthCheck() {
        if (mIdIvTrendatDay.isChecked()) {
            mIdVpTrendat.setCurrentItem(2);
            ChangeCheckBox();
            mIdCbTrendatMonth.setChecked(true);

        }
        if (mIdIvTrendatWeek.isChecked()) {
            mIdVpTrendat.setCurrentItem(5);
            ChangeCheckBox();
            mIdCbTrendatMonth.setChecked(true);


        }
        if (mIdIvTrendatMooth.isChecked()) {
            mIdVpTrendat.setCurrentItem(8);
            ChangeCheckBox();
            mIdCbTrendatMonth.setChecked(true);
        }

    }

    private void WeekCheck() {
        if (mIdIvTrendatDay.isChecked()) {
            mIdVpTrendat.setCurrentItem(1);
            ChangeCheckBox();
            mIdCbTrendatWeek.setChecked(true);
        }
        if (mIdIvTrendatWeek.isChecked()) {
            mIdVpTrendat.setCurrentItem(4);
            ChangeCheckBox();
            mIdCbTrendatWeek.setChecked(true);


        }
        if (mIdIvTrendatMooth.isChecked()) {
            mIdVpTrendat.setCurrentItem(7);
            ChangeCheckBox();
            mIdCbTrendatWeek.setChecked(true);
        }

    }

    private void DayCheck() {
        if (mIdIvTrendatDay.isChecked()) {
            mIdVpTrendat.setCurrentItem(0);
            ChangeCheckBox();
            mIdCbTrendatDay.setChecked(true);
        }
        if (mIdIvTrendatWeek.isChecked()) {
            mIdVpTrendat.setCurrentItem(3);
            ChangeCheckBox();
            mIdCbTrendatDay.setChecked(true);


        }
        if (mIdIvTrendatMooth.isChecked()) {
            mIdVpTrendat.setCurrentItem(6);
            ChangeCheckBox();
            mIdCbTrendatDay.setChecked(true);
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

        } else if (i == R.id.id_iv_trendat_day) {
            mIdVpTrendat.setCurrentItem(0);
            ChangeCheckBox();
            ChangeBottomIcon();
            mIdIvTrendatDay.setBackgroundResource(R.drawable.pace_small_green);
            mIdIvTrendatDay.setChecked(true);
            mIdCbTrendatDay.setChecked(true);


        } else if (i == R.id.id_iv_trendat_week) {
            mIdVpTrendat.setCurrentItem(3);
            ChangeCheckBox();
            ChangeBottomIcon();
            mIdIvTrendatWeek.setBackgroundResource(R.drawable.consumption_small_green);
            mIdIvTrendatWeek.setChecked(true);
            mIdCbTrendatDay.setChecked(true);


        } else if (i == R.id.id_iv_trendat_mooth) {
            mIdVpTrendat.setCurrentItem(6);
            ChangeCheckBox();
            ChangeBottomIcon();
            mIdIvTrendatMooth.setBackgroundResource(R.drawable.sleep_small_green);
            mIdIvTrendatMooth.setChecked(true);
            mIdCbTrendatDay.setChecked(true);

        }
    }
}
