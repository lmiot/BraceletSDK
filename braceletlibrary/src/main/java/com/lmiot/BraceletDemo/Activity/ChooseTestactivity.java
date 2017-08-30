package com.lmiot.BraceletDemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.lmiot.BraceletDemo.R;

public class ChooseTestactivity extends Activity implements View.OnClickListener {

    public String pass_exercise_tpye;
    private ImageView mIdBack;
    private TextView mIdTitle;
    private CheckBox mIdCbChooseTPace;
    private CheckBox mIdCbChooseTRun;
    private CheckBox mIdCbChooseTClimb;
    private CheckBox mIdCbChooseTBallType;
    private CheckBox mIdCbChooseTPower;
    private CheckBox mIdCbChooseTOxygen;
    private Button mIdBtChooseTatStartTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_testactivity);
        initView();

    }


    /**
     * 设置页头
     */
    private void initView() {
        mIdBack = findViewById(R.id.id_back);
        mIdTitle = findViewById(R.id.id_title);
        mIdCbChooseTPace = findViewById(R.id.id_cb_chooseT_pace);
        mIdCbChooseTRun = findViewById(R.id.id_cb_chooseT_run);
        mIdCbChooseTClimb = findViewById(R.id.id_cb_chooseT_climb);
        mIdCbChooseTBallType = findViewById(R.id.id_cb_chooseT_ballType);
        mIdCbChooseTPower = findViewById(R.id.id_cb_chooseT_power);
        mIdCbChooseTOxygen = findViewById(R.id.id_cb_chooseT_oxygen);
        mIdBtChooseTatStartTest = findViewById(R.id.id_bt_chooseTat_start_test);

        mIdTitle.setText("选择测试模式");
        mIdBack.setOnClickListener(this);
        mIdCbChooseTPace.setOnClickListener(this);
        mIdCbChooseTRun.setOnClickListener(this);
        mIdCbChooseTClimb.setOnClickListener(this);
        mIdCbChooseTBallType.setOnClickListener(this);
        mIdCbChooseTPower.setOnClickListener(this);
        mIdCbChooseTOxygen.setOnClickListener(this);
        mIdBtChooseTatStartTest.setOnClickListener(this);
    }




    private void StartTest() {

        if (mIdCbChooseTPace.isChecked()) {
            pass_exercise_tpye = mIdCbChooseTPace.getText().toString();
        }
        if (mIdCbChooseTRun.isChecked()) {
            pass_exercise_tpye = mIdCbChooseTRun.getText().toString();
        }
        if (mIdCbChooseTClimb.isChecked()) {
            pass_exercise_tpye = mIdCbChooseTClimb.getText().toString();
        }
        if (mIdCbChooseTBallType.isChecked()) {
            pass_exercise_tpye = mIdCbChooseTBallType.getText().toString();
        }
        if (mIdCbChooseTOxygen.isChecked()) {
            pass_exercise_tpye = mIdCbChooseTOxygen.getText().toString();
        }
        if (mIdCbChooseTPower.isChecked()) {
            pass_exercise_tpye = mIdCbChooseTPower.getText().toString();
        }
        if (pass_exercise_tpye == null || (pass_exercise_tpye.equals(""))) {
            Toast.makeText(ChooseTestactivity.this, "您还没选择", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(ChooseTestactivity.this, HeartrRateActivity.class);
            String DeviceAdress02 = getIntent().getStringExtra("devID");
            intent.putExtra("devID", DeviceAdress02);
            intent.putExtra("type", pass_exercise_tpye);
            mIdBtChooseTatStartTest.setText("选择测试");
            pass_exercise_tpye = null;

            AllturnOriginal();
            startActivity(intent);
            finish();
        }
    }

    private void AllturnOriginal() {
        mIdCbChooseTPace.setChecked(false);
        mIdCbChooseTRun.setChecked(false);
        mIdCbChooseTClimb.setChecked(false);
        mIdCbChooseTBallType.setChecked(false);
        mIdCbChooseTOxygen.setChecked(false);
        mIdCbChooseTPower.setChecked(false);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.id_back) {
            finish();

        } else if (i == R.id.id_cb_chooseT_pace) {
            mIdBtChooseTatStartTest.setText("确定测试");
            AllturnOriginal();
            mIdCbChooseTPace.setChecked(true);


        } else if (i == R.id.id_cb_chooseT_run) {
            mIdBtChooseTatStartTest.setText("确定测试");
            AllturnOriginal();
            mIdCbChooseTRun.setChecked(true);

        } else if (i == R.id.id_cb_chooseT_climb) {
            mIdBtChooseTatStartTest.setText("确定测试");
            AllturnOriginal();
            mIdCbChooseTClimb.setChecked(true);

        } else if (i == R.id.id_cb_chooseT_ballType) {
            mIdBtChooseTatStartTest.setText("确定测试");
            AllturnOriginal();
            mIdCbChooseTBallType.setChecked(true);

        } else if (i == R.id.id_cb_chooseT_power) {
            mIdBtChooseTatStartTest.setText("确定测试");
            AllturnOriginal();
            mIdCbChooseTPower.setChecked(true);

        } else if (i == R.id.id_cb_chooseT_oxygen) {
            mIdBtChooseTatStartTest.setText("确定测试");
            AllturnOriginal();
            mIdCbChooseTOxygen.setChecked(true);

        } else if (i == R.id.id_bt_chooseTat_start_test) {
            StartTest();

        }
    }
}
