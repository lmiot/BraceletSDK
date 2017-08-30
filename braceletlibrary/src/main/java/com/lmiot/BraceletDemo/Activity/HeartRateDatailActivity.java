package com.lmiot.BraceletDemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lmiot.BraceletDemo.Bean.RateDataInfo;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.LogUtils;
import com.lmiot.BraceletDemo.Util.SPUtil;
import com.lmiot.BraceletDemo.Util.TimeUtils;
import com.zhy.android.percent.support.PercentRelativeLayout;

public class HeartRateDatailActivity extends Activity implements View.OnClickListener{




    private BraceletDBManager bmb;
    private String currentDate;
    private String sport_type;//运动类型
    private String start_time;//开始时间
    private String end_time;//开始时间
    private String sport_time;//运动时间
    private String heart_num;//心跳数
    private String step_num;//步数
    private String average_num;//平均心跳 数
    private String average_speed;//平均步数
    private ImageView mIdBack;
    private TextView mIdTitle;
    private ImageView mIdIvHeartRDatTrend;
    private TextView mIdTvHeartRDatAverage;
    private TextView mIdTvHeartRDatAverageNumber;
    private TextView mIdTvHeartRDatExercise;
    private TextView mIdTvHeartRDatExerciseLevel;
    private PercentRelativeLayout mRateResultId;
    private TextView mIdTvHearRDatPacetime;
    private TextView mIdTvHeartRDatHeartcomsuption;
    private TextView mIdTvHeartRDatTatalcounts;
    private TextView mIdTvHeartRDatAveragePace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_rate_datail);
        initView();
        intiaDBandTime();
        getData();

    }

    /**
     * 设置页头
     */
    private void initView() {


        mIdBack = findViewById(R.id.id_back);
        mIdTitle = findViewById(R.id.id_title);
        mIdIvHeartRDatTrend = findViewById(R.id.id_iv_heartRDat_trend);
        mIdTvHeartRDatAverage = findViewById(R.id.id_tv_heartRDat_average);
        mIdTvHeartRDatAverageNumber = findViewById(R.id.id_tv_heartRDat_average_number);
        mIdTvHeartRDatExercise = findViewById(R.id.id_tv_heartRDat_exercise);
        mIdTvHeartRDatExerciseLevel = findViewById(R.id.id_tv_heartRDat_exercise_level);
        mRateResultId = findViewById(R.id.rate_result_id);
        mIdTvHearRDatPacetime = findViewById(R.id.id_tv_hearRDat_pacetime);
        mIdTvHeartRDatHeartcomsuption = findViewById(R.id.id_tv_heartRDat_heartcomsuption);
        mIdTvHeartRDatTatalcounts = findViewById(R.id.id_tv_heartRDat_tatalcounts);
        mIdTvHeartRDatAveragePace = findViewById(R.id.id_tv_heartRDat_averagePace);

        mIdTitle.setText("心率详情");
        mIdBack.setOnClickListener(this);
        mIdIvHeartRDatTrend.setOnClickListener(this);
    }

    //保存心率的信息到数据库
    private void saveData() {
        bmb.saveRateDataInfo(new RateDataInfo(SPUtil.getUserName(this), currentDate,
                sport_type,
                start_time,
                end_time,
                mIdTvHeartRDatExerciseLevel.getText().toString(),
                sport_time,
                step_num,
                heart_num,
                average_num,
                average_speed
        ));

    }

    private void intiaDBandTime() {
        bmb = new BraceletDBManager(this);
        currentDate = TimeUtils.getCurrentDate();

    }

    public void getData() {    /* 根据测试的数据刷新ui*/

        Intent intent = getIntent();
        sport_type = intent.getStringExtra("sport_type");//运动类型
        start_time = intent.getStringExtra("start_time");//开始时间
        end_time = intent.getStringExtra("end_time");//结束时间
        if (start_time == null) {
            start_time = end_time;
        }
        sport_time = intent.getStringExtra("sport_time");
        heart_num = intent.getStringExtra("heart_num");
        step_num = intent.getStringExtra("step_num");
        average_num = intent.getStringExtra("average_rate");
        average_speed = intent.getStringExtra("average_speed");
        mIdTvHeartRDatAverageNumber.setText(average_num); //平均心率

        int average_rate = Integer.parseInt(average_num);
        if (average_rate <= 120) {
            mIdTvHeartRDatExerciseLevel.setText("低强度");
        } else if (average_rate > 120 & average_rate < 150) {
            mIdTvHeartRDatExerciseLevel.setText("中等强度");
        } else {
            mIdTvHeartRDatExerciseLevel.setText("高强度");
        }


        mIdTvHearRDatPacetime.setText(sport_time);
        mIdTvHeartRDatHeartcomsuption.setText(heart_num);
        mIdTvHeartRDatTatalcounts.setText(step_num);
        mIdTvHeartRDatAveragePace.setText(average_speed);
        LogUtils.d("abc1", sport_type + "-" +
                start_time + "-" +
                end_time + "-" +
                sport_time + "-" +
                heart_num + "-" +
                step_num + "-" +
                average_num + "-" +
                average_speed
        );


    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.id_back) {
            finish();

        } else if (i == R.id.id_iv_heartRDat_trend) {// Intent intent = new Intent(HeartRateDatailActivity.this, Detialactivity.class);
            //startActivity(intent);
            //finish();

        }
    }
}
