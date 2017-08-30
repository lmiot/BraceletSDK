package com.lmiot.BraceletDemo.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ming on 2016/3/19.
 */
public class BraceletDBHelper extends SQLiteOpenHelper {
    private static final String MYDB_NAME = "sport.db";
    private static final int MyVersion=1;
    //创建sportData表
    private static final String CREATE_TABLE_SPORTSDATA="CREATE TABLE IF NOT EXISTS sportData " +
            "( sessionID VARCHAR(45)," +
            "date VARCHAR(45)," +
            "day_time VARCHAR(45)," +
            "day_step VARCHAR(45)," +
            "day_heat VARCHAR(45)," +
            "day_distanc VARCHAR(45)," +
            "percent VARCHAR(45)," +
            "target VARCHAR(45))";

    //创建sleePData表
    private static final String CREATE_TABLE_SLEEPDATA="create table if not exists sleepData " +
            "(sessionID VARCHAR(45)," +
            "date VARCHAR(45)," +
            "sleep_time INTEGER," +
            "depth_time INTEGER," +
            "light_time INTEGER," +
            "wake_time INTEGER," +
            "percent INTEGER," +
            "target INTEGER)";
    //创建rateData表
    private static final String CREATE_TABLE_RATEDATA="create table if not exists rateData" +
            "(sessionID VARCHAR(45)," +
            "date VARCHAR(45)," +
            "sport_type VARCHAR(45)," +
            "start_time VARCHAR(45)," +
            "end_time VARCHAR(45)," +
            "sport_force VARCHAR(45)," +
            "sport_time VARCHAR(45)," +
            "heat_num VARCHAR(45)," +
            "step_num VARCHAR(45)," +
            "average_rate VARCHAR(45)," +
            "average_speed VARCHAR(45))";
    //创建tiredData表
    private static final String CREATE_TABLE_TIREDDATA="create table if not exists tiredData" +
            "(sessionID VARCHAR(45)," +
            "date VARCHAR(45)," +
            "sdnn VARCHAR(45))";

    public BraceletDBHelper(Context context) {
        super(context, MYDB_NAME, null, MyVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SPORTSDATA);
        db.execSQL(CREATE_TABLE_SLEEPDATA);
        db.execSQL(CREATE_TABLE_RATEDATA);
        db.execSQL(CREATE_TABLE_TIREDDATA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sportData");
        db.execSQL("DROP TABLE IF EXISTS sleepData");
        db.execSQL("DROP TABLE IF EXISTS rateData");
        db.execSQL("DROP TABLE IF EXISTS tiredData");
        onCreate(db);


    }
}
