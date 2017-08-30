package com.lmiot.BraceletDemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lmiot.BraceletDemo.Bean.SleepDataInfo;
import com.lmiot.BraceletDemo.Bean.SportDataInfo;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.SPUtil;
import com.lmiot.BraceletDemo.Util.TimeUtils;
import com.lmiot.BraceletDemo.View.BoundProgressView;
import com.lmiot.BraceletDemo.View.BoundProgressViewTwo;
import com.zhy.android.percent.support.PercentLinearLayout;


public class GoalSettinctivity extends Activity implements View.OnTouchListener ,View.OnClickListener{




    private int step;
    private float DownX, NowX;
    private float NowX2;
    private BraceletDBManager manager;
    private SleepDataInfo sleepDataInfo_set;
    private SportDataInfo mSportDataInfo_set;
    private ImageView mIdBack;
    private TextView mIdTitle;
    private TextView mStepNow;
    private View mIdGoalSatPaceLine;
    private ImageView mIdIvGoalSatRedFlag;
    private TextView mIdTvGoalSatPaceCounts;
    private BoundProgressView mIdGoalSatExerciseBpv;
    private PercentLinearLayout mIdGoalSatLinearExercise;
    private TextView mSleepNow;
    private View mIdGoalSatOxygenLine;
    private ImageView mIdIvGoalSatOxygenRedFlag;
    private TextView mIdTvGoalSatOxygenCounts;
    private BoundProgressViewTwo mBottomBpvTime;
    private PercentLinearLayout mIdGoalSatLinearSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_settinctivity);
        initView();
        show_whichView();
        setListener();
        intiDB();


    }

    /**
     * 设置页头
     */
    private void initView() {

        mIdBack = findViewById(R.id.id_back);
        mIdTitle = findViewById(R.id.id_title);
        mStepNow = findViewById(R.id.step_now);
        mIdGoalSatPaceLine = findViewById(R.id.id_goalSat_pace_line);
        mIdIvGoalSatRedFlag = findViewById(R.id.id_iv_goalSat_redFlag);
        mIdTvGoalSatPaceCounts = findViewById(R.id.id_tv_goalSat_pace_counts);
        mIdGoalSatExerciseBpv = findViewById(R.id.id_goalSat_exercise_bpv);
        mIdGoalSatLinearExercise = findViewById(R.id.id_goalSat_linear_exercise);
        mSleepNow = findViewById(R.id.sleep_now);
        mIdGoalSatOxygenLine = findViewById(R.id.id_goalSat_oxygen_line);
        mIdIvGoalSatOxygenRedFlag = findViewById(R.id.id_iv_goalSat_oxygen_redFlag);
        mIdTvGoalSatOxygenCounts = findViewById(R.id.id_tv_goalSat_oxygen_counts);
        mBottomBpvTime = findViewById(R.id.bottom_bpv_time);
        mIdGoalSatLinearSleep = findViewById(R.id.id_goalSat_linear_sleep);

        mIdTitle.setText("目标设置");
        mIdBack.setOnClickListener(this);
        mIdGoalSatPaceLine.setOnClickListener(this);
        mIdIvGoalSatRedFlag.setOnClickListener(this);
        mIdTvGoalSatPaceCounts.setOnClickListener(this);
        mIdGoalSatOxygenLine.setOnClickListener(this);
        mIdIvGoalSatOxygenRedFlag.setOnClickListener(this);
        mIdTvGoalSatOxygenCounts.setOnClickListener(this);
        mBottomBpvTime.setOnClickListener(this);


    }

    private void setListener() {
        mIdGoalSatExerciseBpv.setOnTouchListener(this);
        mBottomBpvTime.setOnTouchListener(this);
    }

    private void intiDB() {
        manager = new BraceletDBManager(this);
    }

    private void show_whichView() {
        Intent intent_show = getIntent();
        String show = intent_show.getStringExtra("showView");
        if (show.equals("showPaceCounts")) {
            mIdGoalSatLinearSleep.setVisibility(View.INVISIBLE);
            getUserTarget_step();
        } else {
            mIdGoalSatLinearExercise.setVisibility(View.INVISIBLE);
            getUserTarget_sleep();
        }
    }




    private void setnumber() {
        String userName_now = SPUtil.getUserName(getApplicationContext());
        String currentDate_now = TimeUtils.getCurrentDate();
        if (mIdGoalSatLinearExercise.getVisibility() == View.VISIBLE) {
            Intent intent_backSun = new Intent();
            intent_backSun.putExtra("pace_level", mIdTvGoalSatPaceCounts.getText());
            setResult(3, intent_backSun);
            /*保存数据到服务器*/
            String target_step = mIdTvGoalSatPaceCounts.getText().toString();


            mSportDataInfo_set = new SportDataInfo();
            mSportDataInfo_set.setSessionID(userName_now);
            mSportDataInfo_set.setDate(currentDate_now);
            mSportDataInfo_set.setTarget(target_step);

            SportDataInfo sportDataInfo_old = manager.findSportDataInfo(userName_now, currentDate_now);
            if (sportDataInfo_old.getDate().equals("0")) {
                mSportDataInfo_set.setDay_time("0");
                mSportDataInfo_set.setDay_step("0");
                mSportDataInfo_set.setDay_heat("0");
                mSportDataInfo_set.setDay_distanc("0");
                mSportDataInfo_set.setPercent("0");
                manager.saveSportDataInfo(mSportDataInfo_set);
            } else {
                mSportDataInfo_set.setDay_time(sportDataInfo_old.getDay_time());
                mSportDataInfo_set.setDay_step(sportDataInfo_old.getDay_step());
                mSportDataInfo_set.setDay_heat(sportDataInfo_old.getDay_heat());
                mSportDataInfo_set.setDay_distanc(sportDataInfo_old.getDay_distanc());
                mSportDataInfo_set.setPercent(sportDataInfo_old.getPercent());
                manager.updateSportDataInfo(mSportDataInfo_set, userName_now, currentDate_now);
            }


            finish();
        } else {

             /*保存数据到服务器*/
            String target_set = mIdTvGoalSatOxygenCounts.getText().toString();

            sleepDataInfo_set = new SleepDataInfo();
            sleepDataInfo_set.setSessionID(userName_now);
            sleepDataInfo_set.setDate(currentDate_now);

           /*若该日期下，数据为空，则保存；若已存在，则更新target*/
            SleepDataInfo sleepDataInfo_old = manager.findSleepDataInfo(userName_now, currentDate_now);
            if (sleepDataInfo_old.getDate().equals("0")) {

                sleepDataInfo_set.setPercent(0);
                sleepDataInfo_set.setSleep_time(0);
                sleepDataInfo_set.setDepth_time(0);
                sleepDataInfo_set.setLight_time(0);
                sleepDataInfo_set.setWake_time(0);
                sleepDataInfo_set.setTarget(Integer.parseInt(target_set));
                manager.saveSleepDataInfo(sleepDataInfo_set);
                Log.e("设置睡眠目标", "目标保存成功");
            } else {
                sleepDataInfo_set.setPercent(sleepDataInfo_old.getPercent());
                sleepDataInfo_set.setSleep_time(sleepDataInfo_old.getSleep_time());
                sleepDataInfo_set.setDepth_time(sleepDataInfo_old.getDepth_time());
                sleepDataInfo_set.setLight_time(sleepDataInfo_old.getLight_time());
                sleepDataInfo_set.setWake_time(sleepDataInfo_old.getWake_time());
                sleepDataInfo_set.setTarget(Integer.parseInt(target_set));
                manager.updateSleepDataInfo(sleepDataInfo_set, userName_now, currentDate_now);
                Log.e("设置睡眠目标", "目标更新成功");
            }


            Intent intent_backMoon = new Intent();
            intent_backMoon.putExtra("sleep_level", mIdTvGoalSatOxygenCounts.getText());
            setResult(4, intent_backMoon);

            finish();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.id_goalSat_exercise_bpv) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    NowX = event.getX();
                    float width = mIdGoalSatExerciseBpv.getWidth();
                    float circle_width = mIdGoalSatExerciseBpv.getCircleWidth();
                    float piece = (width - 3 * circle_width) / 12f;
                    if ((NowX > (circle_width * 2)) && (NowX < ((circle_width * 2) + (piece / 2)))) {
                        mIdGoalSatExerciseBpv.setStep(0);
                    } else if ((NowX > ((circle_width * 2) + (piece))) && (NowX < ((circle_width * 4) + (piece + piece)))) {
                        mIdGoalSatExerciseBpv.setStep(1);
                    } else if ((NowX > ((circle_width * 4) + (piece * 2))) && (NowX < ((circle_width * 6) + (piece * 3)))) {
                        mIdGoalSatExerciseBpv.setStep(2);
                    } else if ((NowX > ((circle_width * 6) + (piece * 3))) && (NowX < ((circle_width * 8) + (piece * 4)))) {
                        mIdGoalSatExerciseBpv.setStep(3);
                    } else if ((NowX > ((circle_width * 8) + (piece * 4))) && (NowX < ((circle_width * 10) + (piece * 5)))) {
                        mIdGoalSatExerciseBpv.setStep(4);
                    } else if ((NowX > ((circle_width * 10) + (piece * 5))) && (NowX < ((circle_width * 12) + (piece * 6)))) {
                        mIdGoalSatExerciseBpv.setStep(5);
                    } else if ((NowX > ((circle_width * 12) + (piece * 6))) && (NowX < ((circle_width * 14) + (piece * 7)))) {
                        mIdGoalSatExerciseBpv.setStep(6);
                    }
                    mIdGoalSatExerciseBpv.invalidate();
                    break;

                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_UP:
                    step = mIdGoalSatExerciseBpv.getStep();
                    if (step == 0) {
                        mIdTvGoalSatOxygenCounts.setText("2000");
                    }
                    if (step == 1) {
                        mIdTvGoalSatPaceCounts.setText("5000");
                    }
                    if (step == 2) {
                        mIdTvGoalSatPaceCounts.setText("8000");
                    }
                    if (step == 3) {
                        mIdTvGoalSatPaceCounts.setText("11000");
                    }
                    if (step == 4) {
                        mIdTvGoalSatPaceCounts.setText("14000");
                    }
                    if (step == 5) {
                        mIdTvGoalSatPaceCounts.setText("17000");
                    }
                    if (step == 6) {
                        mIdTvGoalSatPaceCounts.setText("20000");
                    }
                    break;
                default:
            }

        }
        if (v.getId() == R.id.bottom_bpv_time) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    NowX2 = event.getX();
                    float width = mBottomBpvTime.getWidth();
                    float circle_width = mBottomBpvTime.getCircleWidth();
                    float piece = (width - 3 * circle_width) / 18f;
                    if ((NowX2 > (circle_width * 2)) && (NowX2 < ((circle_width * 2) + (piece / 2)))) {
                        mBottomBpvTime.setStep(0);
                    } else if ((NowX2 > ((circle_width * 2) + (piece))) && (NowX2 < ((circle_width * 4) + (piece + piece)))) {
                        mBottomBpvTime.setStep(1);
                    } else if ((NowX2 > ((circle_width * 4) + (piece * 2))) && (NowX2 < ((circle_width * 6) + (piece * 3)))) {
                        mBottomBpvTime.setStep(2);
                    } else if ((NowX2 > ((circle_width * 6) + (piece * 3))) && (NowX2 < ((circle_width * 8) + (piece * 4)))) {
                        mBottomBpvTime.setStep(3);
                    } else if ((NowX2 > ((circle_width * 8) + (piece * 4))) && (NowX2 < ((circle_width * 10) + (piece * 5)))) {
                        mBottomBpvTime.setStep(4);
                    } else if ((NowX2 > ((circle_width * 10) + (piece * 5))) && (NowX2 < ((circle_width * 12) + (piece * 6)))) {
                        mBottomBpvTime.setStep(5);
                    } else if ((NowX2 > ((circle_width * 12) + (piece * 6))) && (NowX2 < ((circle_width * 14) + (piece * 7)))) {
                        mBottomBpvTime.setStep(6);
                    } else if ((NowX2 > ((circle_width * 14) + (piece * 7))) && (NowX2 < ((circle_width * 16) + (piece * 8)))) {
                        mBottomBpvTime.setStep(7);
                    } else if ((NowX2 > ((circle_width * 16) + (piece * 8))) && (NowX2 < ((circle_width * 18) + (piece * 9)))) {
                        mBottomBpvTime.setStep(8);
                    }
                    mBottomBpvTime.invalidate();
                    break;

                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_UP:
                    step = mBottomBpvTime.getStep();
                    if (step == 0) {
                        mIdTvGoalSatOxygenCounts.setText("4");
                    }
                    if (step == 1) {
                        mIdTvGoalSatOxygenCounts.setText("5");
                    }
                    if (step == 2) {
                        mIdTvGoalSatOxygenCounts.setText("6");
                    }
                    if (step == 3) {
                        mIdTvGoalSatOxygenCounts.setText("7");
                    }
                    if (step == 4) {
                        mIdTvGoalSatOxygenCounts.setText("8");
                    }
                    if (step == 5) {
                        mIdTvGoalSatOxygenCounts.setText("9");
                    }
                    if (step == 6) {
                        mIdTvGoalSatOxygenCounts.setText("10");
                    }
                    if (step == 7) {
                        mIdTvGoalSatOxygenCounts.setText("11");
                    }
                    if (step == 8) {
                        mIdTvGoalSatOxygenCounts.setText("12");
                    }
                    break;
                default:
            }
        }
        return true;
    }


    public void getUserTarget_step() { /*显示用户设定的运动目标*/
        Intent intent_step = getIntent();
        String user_target_step = intent_step.getStringExtra("target_step");
        String step_now = intent_step.getStringExtra("step_now");
        mStepNow.setText("目前已完成：" + step_now);
        mIdTvGoalSatOxygenCounts.setText(user_target_step);

        switch (user_target_step) {
            case "2000":
                mIdGoalSatExerciseBpv.setStep(0);
                break;
            case "5000":
                mIdGoalSatExerciseBpv.setStep(1);
                break;
            case "8000":
                mIdGoalSatExerciseBpv.setStep(2);
                break;
            case "11000":
                mIdGoalSatExerciseBpv.setStep(3);
                break;
            case "14000":
                mIdGoalSatExerciseBpv.setStep(4);
                break;
            case "17000":
                mIdGoalSatExerciseBpv.setStep(5);
                break;
            case "20000":
                mIdGoalSatExerciseBpv.setStep(6);
                break;
        }

    }

    public void getUserTarget_sleep() { /*显示用户设定的睡眠目标*/
        Intent intent_sleep = getIntent();
        String user_target_sleep = intent_sleep.getStringExtra("target_sleep");
        String sleep_now = intent_sleep.getStringExtra("sleep_now");
        mSleepNow.setText("目前已完成：" + sleep_now);
        mIdTvGoalSatOxygenCounts.setText(user_target_sleep);
        switch (user_target_sleep) {
            case "4":
                mBottomBpvTime.setStep(0);
                break;
            case "5":
                mBottomBpvTime.setStep(1);
                break;
            case "6":
                mBottomBpvTime.setStep(2);
                break;
            case "7":
                mBottomBpvTime.setStep(3);
                break;
            case "8":
                mBottomBpvTime.setStep(4);
                break;
            case "9":
                mBottomBpvTime.setStep(5);
                break;
            case "10":
                mBottomBpvTime.setStep(6);
                break;
            case "11":
                mBottomBpvTime.setStep(7);
                break;
            case "12":
                mBottomBpvTime.setStep(8);
                break;

        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.id_back) {
            setnumber();

        } else if (i == R.id.id_goalSat_pace_line) {
        } else if (i == R.id.id_iv_goalSat_redFlag) {
        } else if (i == R.id.id_tv_goalSat_pace_counts) {
        } else if (i == R.id.id_goalSat_oxygen_line) {
        } else if (i == R.id.id_iv_goalSat_oxygen_redFlag) {
        } else if (i == R.id.id_tv_goalSat_oxygen_counts) {
        } else if (i == R.id.bottom_bpv_time) {
        }
    }
}
