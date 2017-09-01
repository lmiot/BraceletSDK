package com.lmiot.BraceletDemo.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.lmiot.BraceletDemo.Bean.HeartRateData;
import com.lmiot.BraceletDemo.Bean.RateDataInfo;
import com.lmiot.BraceletDemo.Bean.SleepDataInfo;
import com.lmiot.BraceletDemo.Bean.SportDataInfo;
import com.lmiot.BraceletDemo.Bean.TiredDataInfo;
import com.lmiot.BraceletDemo.Util.LogUtils;
import com.lmiot.BraceletDemo.Util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lmiot.BraceletDemo.R.color.a;

/**
 * Created by ming on 2016/3/19.
 */
public class BraceletDBManager {
    private BraceletDBHelper braceletDBHelper;
    private SQLiteDatabase db;

    public BraceletDBManager(Context context) {
        braceletDBHelper=new BraceletDBHelper(context);
        db=braceletDBHelper.getWritableDatabase();
    }
    //heartRateData表的增加功能
    public void saveHeartRateDataInfo(HeartRateData heartRateData){
        Log.d("BraceletDBManager", "保存心率："+new Gson().toJson(heartRateData));
        db.beginTransaction();
        try {


            db.execSQL("INSERT INTO heartRateData(sessionID, date, day_time,heart_rate) VALUES(?,?,?,?)",new Object[]{
                    heartRateData.getSessionID(), heartRateData.getDate(),
                    heartRateData.getTime(), heartRateData.getHeartRate(),
                    });
            db.setTransactionSuccessful();
            LogUtils.d("sqlsucceed", "heartRateData增加数据成功");
        }catch (SQLException e) {
            LogUtils.d("myerror" + "heartRateData增加数据不成功"+e.getMessage());
        }
            db.endTransaction();

    }
    //heartRateData表的更新
    public void updateHeartRateDataInfo(HeartRateData heartRateData){
        Log.d("BraceletDBManager", "更新心率："+new Gson().toJson(heartRateData));
        db.beginTransaction();
        try {
            db.execSQL("update heartRateData set sessionID=?, date=?, day_time=?,heart_rate=? where day_time=?"
                    ,new Object[]{
                            heartRateData.getSessionID(), heartRateData.getDate(),
                            heartRateData.getTime(), heartRateData.getHeartRate(),
                            heartRateData.getTime()});
            db.setTransactionSuccessful();
            LogUtils.d("sqlsucceed", "HeartRate修改数据成功");
        } catch (SQLException e) {
            LogUtils.d("myerror" + "HeartRate修改数据不成功");
        }
        db.endTransaction();

    }




    //根据时间来查找心率数据
    public HeartRateData findHeartRateDataByTimer(String date,String time ){
        HeartRateData heartRateData = new HeartRateData();
        try {
            Cursor cursor = db.rawQuery("select * from heartRateData where date=? and day_time = ?", new String[]{date,time});
            while (cursor.moveToNext()) {
                heartRateData.setSessionID(cursor.getString(0));
                heartRateData.setDate(cursor.getString(1));
                heartRateData.setTime(cursor.getString(2));
                heartRateData.setHeartRate(cursor.getInt(3));

            }
            LogUtils.d("sqlsucceed", "HeartRate查询数据成功");
            cursor.close();
            return heartRateData;
        }catch (SQLException e){
            LogUtils.d("myerror" + "HeartRate查询数据不成功"+e.getMessage());
            return null;
        }


    }
    //根据日期来查找心率数据（每天）
    public List<HeartRateData> findHeartRateDataByDate(String date ){
        Log.d("BraceletDBManager", "查找数据："+date);
        List<HeartRateData> a = new ArrayList<>();
        try {
         //   Cursor cursor = db.rawQuery("select * from heartRateData where date =?", new String[]{date});
            Cursor cursor = db.rawQuery("select * from heartRateData where date=?",new String[]{date});
            while (cursor.moveToNext()) {
                HeartRateData heartRateData=new HeartRateData();
                heartRateData.setSessionID(cursor.getString(0));
                heartRateData.setDate(cursor.getString(1));
                heartRateData.setTime(cursor.getString(2));
                heartRateData.setHeartRate(cursor.getInt(3));
                a.add(heartRateData);
            }
            LogUtils.d("sqlsucceed", "HeartRate查询数据成功");
            cursor.close();
            return a;
        }catch (SQLException e){
            LogUtils.d("myerror" + "HeartRate查询数据不成功"+e.getMessage());
            return null;
        }


    }




    //sportData表的增加功能
    public void saveSportDataInfo(SportDataInfo sportDataInfo){
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO sportData(sessionID, date, day_time,day_step, day_heat,day_distanc, percent, target) VALUES(?,?,?,?,?,?,?,?)",new Object[]{
                    sportDataInfo.getSessionID(), sportDataInfo.getDate(),
                    sportDataInfo.getDay_time(), sportDataInfo.getDay_step(),
                    sportDataInfo.getDay_heat(), sportDataInfo.getDay_distanc(),
                    sportDataInfo.getPercent(), sportDataInfo.getTarget()});
            db.setTransactionSuccessful();
            LogUtils.d("sqlsucceed", "sportData增加数据成功");
        }catch (SQLException e) {
            LogUtils.d("myerror" + "sportData增加数据不成功");
        }
            db.endTransaction();

    }
    //sportData表的修改功能
    public void updateSportDataInfo(SportDataInfo sportDataInfo,String a,String b){
        db.beginTransaction();
        try {
            db.execSQL("update sportData set sessionID=?, date=?, day_time=?,day_step=?, day_heat=?,day_distanc=?, percent=?, target=? where SessionID=? and date=?"
                    ,new Object[]{
                            sportDataInfo.getSessionID(), sportDataInfo.getDate(),
                            sportDataInfo.getDay_time(), sportDataInfo.getDay_step(),
                            sportDataInfo.getDay_heat(), sportDataInfo.getDay_distanc(),
                            sportDataInfo.getPercent(), sportDataInfo.getTarget(),a,b});
            db.setTransactionSuccessful();
            LogUtils.d("sqlsucceed", "sportData修改数据成功");
        } catch (SQLException e) {
            LogUtils.d("myerror" + "sportData修改数据不成功");
        }
        db.endTransaction();


    }
    //sportData表的查找功能
    public SportDataInfo findSportDataInfo(String sessionid,String dateid) {
        try {
            Cursor cursor = db.rawQuery("select * from sportData where sessionID=? and date=?", new String[]{sessionid, dateid});
            if (cursor.moveToNext()) {
                SportDataInfo sportDataInfo = new SportDataInfo();
                sportDataInfo.setSessionID((cursor.getString(0)));
                sportDataInfo.setDate(cursor.getString(1));
                sportDataInfo.setDay_time(cursor.getString(2));
                sportDataInfo.setDay_step(cursor.getString(3));
                sportDataInfo.setDay_heat(cursor.getString(4));
                sportDataInfo.setDay_distanc(cursor.getString(5));
                sportDataInfo.setPercent(cursor.getString(6));
                sportDataInfo.setTarget(cursor.getString(7));
                cursor.close();
                return sportDataInfo;
            }
            LogUtils.d("sqlsucceed", "sportData查询数据成功");

        }
        catch (SQLException e) {
            LogUtils.d("myerror" + "sportData查询数据不成功");
        }
        return new SportDataInfo("0","0","0","0","0","2000","0","0");

    }
    //列出表sportData的所有数据
    public List<SportDataInfo> findAll(){
        List<SportDataInfo>  a=new ArrayList<SportDataInfo>();
        Cursor cursor=db.rawQuery("select * from sportData",null);
        while(cursor.moveToNext()){
            SportDataInfo sportDataInfo = new SportDataInfo();
            sportDataInfo.setSessionID((cursor.getString(0)));
            sportDataInfo.setDate(cursor.getString(1));
            sportDataInfo.setDay_time(cursor.getString(2));
            sportDataInfo.setDay_step(cursor.getString(3));
            sportDataInfo.setDay_heat(cursor.getString(4));
            sportDataInfo.setDay_distanc(cursor.getString(5));
            sportDataInfo.setPercent(cursor.getString(6));
            sportDataInfo.setTarget(cursor.getString(7));
            a.add(sportDataInfo);
        }
        cursor.close();
        return a;
    }
    //根据月来查询数据sport表
    public List<SportDataInfo> findsportInmonth(int xi){
        List<SportDataInfo> a = new ArrayList<SportDataInfo>();
        try {
            Cursor cursor = db.rawQuery("select * from sportData where date like ?", new String[]{TimeUtils.getAmonth_otherFormat(xi)+"%"});
            while (cursor.moveToNext()) {
                SportDataInfo sportDataInfo = new SportDataInfo();
                sportDataInfo.setSessionID((cursor.getString(0)));
                sportDataInfo.setDate(cursor.getString(1));
                sportDataInfo.setDay_time(cursor.getString(2));
                sportDataInfo.setDay_step(cursor.getString(3));
                sportDataInfo.setDay_heat(cursor.getString(4));
                sportDataInfo.setDay_distanc(cursor.getString(5));
                sportDataInfo.setPercent(cursor.getString(6));
                sportDataInfo.setTarget(cursor.getString(7));
                a.add(sportDataInfo);
            }
            LogUtils.d("sqlsucceed", "sportData查询这一周数据成功");
            cursor.close();
            return a;
        }catch (SQLException e){
            LogUtils.d("myerror" + "sportData查询这一周的数据不成功"+e.getMessage());
        }
        return null;

    }
    //根据月来查询数据sleep表
    public List<SleepDataInfo> findsleeptInmonth(int xi){
        List<SleepDataInfo> a = new ArrayList<SleepDataInfo>();
        try {
            Cursor cursor = db.rawQuery("select * from sleepData where date like ?", new String[]{TimeUtils.getAmonth_otherFormat(xi)+"%"});
            while (cursor.moveToNext()) {
                SleepDataInfo si = new SleepDataInfo();
                si.setSessionID((cursor.getString(0)));
                si.setDate(cursor.getString(1));
                si.setSleep_time(cursor.getInt(2));
                si.setDepth_time(cursor.getInt(3));
                si.setLight_time(cursor.getInt(4));
                si.setWake_time(cursor.getInt(5));
                si.setPercent(cursor.getInt(6));
                si.setTarget(cursor.getInt(7));
                a.add(si);
            }
            LogUtils.d("sqlsucceed", "sleepData查询这一周数据成功");
            cursor.close();
            return a;
        }catch (SQLException e){
            LogUtils.d("myerror" + "sleepData查询这一周的数据不成功"+e.getMessage());
        }
        return null;

    }

    //根据周来查询数据sport表
    public List<SportDataInfo> findsportInweek(int ff){
        List<SportDataInfo> a = new ArrayList<SportDataInfo>();
        try {
            Cursor cursor = db.rawQuery("select * from sportData where date=? or date=? or date=? or date=? or date=? or date=? or date=?", new String[]{TimeUtils.getMondayof(ff).get(0),TimeUtils.getMondayof(ff).get(1),
                    TimeUtils.getMondayof(ff).get(2), TimeUtils.getMondayof(ff).get(3), TimeUtils.getMondayof(ff).get(4), TimeUtils.getMondayof(ff).get(5), TimeUtils.getMondayof(ff).get(6)});

            while (cursor.moveToNext()) {
                SportDataInfo sportDataInfo = new SportDataInfo();
                sportDataInfo.setSessionID((cursor.getString(0)));
                sportDataInfo.setDate(cursor.getString(1));
                sportDataInfo.setDay_time(cursor.getString(2));
                sportDataInfo.setDay_step(cursor.getString(3));
                sportDataInfo.setDay_heat(cursor.getString(4));
                sportDataInfo.setDay_distanc(cursor.getString(5));
                sportDataInfo.setPercent(cursor.getString(6));
                sportDataInfo.setTarget(cursor.getString(7));
                a.add(sportDataInfo);
            }
            LogUtils.d("sqlsucceed", "sportData查询这一周数据成功");
            cursor.close();
            return a;
        }catch (SQLException e){
            LogUtils.d("myerror" + "sportData查询这一周的数据不成功"+e.getMessage());
        }
        return null;

    }
    //根据周来查询数据sleep表
    public List<SleepDataInfo> findsleepInweek(int xi){
        List<SleepDataInfo> a = new ArrayList<SleepDataInfo>();
        try {
            Cursor cursor = db.rawQuery("select * from sleepData where date=? or date=? or date=? or date=? or date=? or date=? or date=?", new String[]{TimeUtils.getMondayof(xi).get(0),TimeUtils.getMondayof(xi).get(1), TimeUtils.getMondayof(xi).get(2), TimeUtils.getMondayof(xi).get(3), TimeUtils.getMondayof(xi).get(4), TimeUtils.getMondayof(xi).get(5),
                    TimeUtils.getMondayof(xi).get(6)});
            while (cursor.moveToNext()) {
                SleepDataInfo si = new SleepDataInfo();
                si.setSessionID((cursor.getString(0)));
                si.setDate(cursor.getString(1));
                si.setSleep_time(cursor.getInt(2));
                si.setDepth_time(cursor.getInt(3));
                si.setLight_time(cursor.getInt(4));
                si.setWake_time(cursor.getInt(5));
                si.setPercent(cursor.getInt(6));
                si.setTarget(cursor.getInt(7));
                a.add(si);
            }
            LogUtils.d("sqlsucceed", "sleepData查询这一周数据成功");
            cursor.close();
            return a;


        }catch (SQLException e){
            LogUtils.d("myerror" + "sleepData查询这一周的数据不成功"+e.getMessage());
        }
        return null;

    }
    //列出当天的所有心率数据
    public List<RateDataInfo> findAllRate(String date){
        List<RateDataInfo> a = new ArrayList<RateDataInfo>();

            Cursor cursor = db.rawQuery("select * from rateData where date=?", new String[]{date});
        while (cursor.moveToNext()) {
                RateDataInfo rateDataInfo = new RateDataInfo();
                rateDataInfo.setSessionID((cursor.getString(cursor.getColumnIndex("sessionID"))));
                rateDataInfo.setDate(cursor.getString(1));
                rateDataInfo.setSport_type(cursor.getString(2));
                rateDataInfo.setStart_time(cursor.getString(3));
                rateDataInfo.setEnd_time(cursor.getString(4));
                rateDataInfo.setSport_force(cursor.getString(5));
                rateDataInfo.setSport_time(cursor.getString(6));
                rateDataInfo.setStep_num(cursor.getString(7));
                rateDataInfo.setHeat_num(cursor.getString(8));
                rateDataInfo.setAverage_rate(cursor.getString(9));
                rateDataInfo.setAverage_speed(cursor.getString(10));
                a.add(rateDataInfo);
                LogUtils.d("sqlsucceed", "rateData查询日期数据成功");
            }
            return a;


    }

    //列出表sportData的所有数据
    public List<SleepDataInfo> findAllSleep(){
        List<SleepDataInfo>  a=new ArrayList<SleepDataInfo>();
        Cursor cursor=db.rawQuery("select * from sleepData",null);
        if (cursor.moveToNext()){

            SleepDataInfo sleepDataInfo = new SleepDataInfo();
            sleepDataInfo.setSessionID(cursor.getString(0));
            sleepDataInfo.setDate(cursor.getString(1));
            sleepDataInfo.setSleep_time(cursor.getInt(2));
            sleepDataInfo.setDepth_time(cursor.getInt(3));
            sleepDataInfo.setLight_time(cursor.getInt(4));
            sleepDataInfo.setWake_time(cursor.getInt(5));
            sleepDataInfo.setPercent(cursor.getInt(6));
            sleepDataInfo.setTarget(cursor.getInt(7));
            a.add(sleepDataInfo);
        }
        return a;
    }
/*    public List<T> listALL(String table){
        List<T> a=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from " + table,null);

    return a;

    }*/
    public enum Tablename {
        sportData, sleepData, rateData, tiredData
    }
    //删掉指定表的所有数据
    public void deleteall(Tablename i){
        db.delete(String.valueOf(i),null,null);
    }

    //sleepData表的存储功能
    public void saveSleepDataInfo(SleepDataInfo sdi){
        db.beginTransaction();
        LogUtils.d("sqlsucceedDDD", sdi.toString());

        try {
            db.execSQL("INSERT INTO sleepData(sessionID, date, sleep_time,depth_time, light_time,wake_time, percent, target) VALUES(?,?,?,?,?,?,?,?)",new Object[]{
                    sdi.getSessionID(), sdi.getDate(),
                    sdi.getSleep_time(), sdi.getDepth_time(),
                    sdi.getLight_time(), sdi.getWake_time(),
                    sdi.getPercent(), sdi.getTarget()});
            db.setTransactionSuccessful();
            LogUtils.d("sqlsucceed", "sleepData增加数据成功");
        }catch (Exception e) {
            LogUtils.d("myerror" + "sleepData增加数据不成功");
        }
            db.endTransaction();


    }
    //sleepData表的修改功能 OK
    public void updateSleepDataInfo(SleepDataInfo sdi,String a,String b){
        db.beginTransaction();
        try {
            db.execSQL("update sleepData set sessionID=?, date=?, sleep_time=?,depth_time=?, light_time=?,wake_time=?, percent=?, target=? where SessionID=? and date=?"
                    ,new Object[]{
                            sdi.getSessionID(), sdi.getDate(),
                            sdi.getSleep_time(), sdi.getDepth_time(),
                            sdi.getLight_time(), sdi.getWake_time(),
                            sdi.getPercent(), sdi.getTarget(),a,b});
            db.setTransactionSuccessful();
            LogUtils.d("sqlsucceed", "sleepData修改数据成功");

        }catch (SQLException e) {
            LogUtils.d("myerror" + "sleepData修改数据不成功");
        }
            db.endTransaction();



    }

    //sleepData表的查找功能 OK
    public SleepDataInfo findSleepDataInfo(String sessionid,String dateid) {
        try {
            Cursor cursor = db.rawQuery(
                    "select * from sleepData where sessionID=? and date=?", new String[]{sessionid, dateid});
            if (cursor.moveToNext()) {
                SleepDataInfo si = new SleepDataInfo();
                si.setSessionID((cursor.getString(0)));
                si.setDate(cursor.getString(1));
                si.setSleep_time(cursor.getInt(2));
                si.setDepth_time(cursor.getInt(3));
                si.setLight_time(cursor.getInt(4));
                si.setWake_time(cursor.getInt(5));
                si.setPercent(cursor.getInt(6));
                si.setTarget(cursor.getInt(7));
                cursor.close();
                return si;
            }
            LogUtils.d("sqlsucceed", "sleepData查询数据成功");
        }catch (SQLException E)
        {
            LogUtils.d("myerror" + "sleepData查询数据不成功");
        }
        return new SleepDataInfo("0","0",0,0,0,0,0,4);
    }
    //sleepData表的查找综合
    public SleepDataInfo findSleepDataInfo_total(String sessionid,String dateid) {

        SleepDataInfo si = new SleepDataInfo();

        try {
            Cursor cursor01 = db.rawQuery(
                    "select sum(sleep_time) from sleepData where sessionID=? and date=?", new String[]{sessionid, dateid});
           cursor01.moveToFirst();
            Log.e("daytime总和",cursor01.getInt(0)+":"+cursor01.getColumnCount());
                si.setSleep_time(cursor01.getInt(0));
            cursor01.close();

            /*Cursor cursor02 = db.rawQuery(
                    "select sum(depth_time) from sleepData where sessionID=? and date=?", new String[]{sessionid, dateid});
            if (cursor02.moveToNext()) {   si.setDepth_time(cursor02.getInt(0));}
            cursor02.close();

            Cursor cursor03 = db.rawQuery(
                    "select sum(light_time) from sleepData where sessionID=? and date=?", new String[]{sessionid, dateid});
            if (cursor03.moveToNext()) {   si.setLight_time(cursor03.getInt(0));}
            cursor03.close();

            Cursor cursor04 = db.rawQuery(
                    "select sum(wake_time) from sleepData where sessionID=? and date=?", new String[]{sessionid, dateid});
            if (cursor04.moveToNext()) {    si.setWake_time(cursor04.getInt(0));}
             cursor04.close();

            Cursor cursor05 = db.rawQuery(
                    "select target(target) from sleepData where sessionID=? and date=?", new String[]{sessionid, dateid});
            if (cursor05.moveToNext()) {    si.setTarget(cursor05.getInt(0));}
            cursor05.close();

            Cursor cursor06 = db.rawQuery(
                    "select target(target) from sleepData where sessionID=? and date=?", new String[]{sessionid, dateid});
            if (cursor06.moveToNext()) {    si.setPercent(cursor06.getInt(0));}
            cursor06.close();
*/
            return si;
            }

    catch (SQLException E)
        {
            LogUtils.d("myerror" + "sleepData查询数据不成功");
        }
        return new SleepDataInfo("0","0",0,0,0,0,0,0);
    }


    //rateData表的增加功能 OK
    public void saveRateDataInfo(RateDataInfo rdi){
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO rateData(sessionID,date, sport_type, start_time,end_time, sport_force,sport_time, heat_num, step_num, average_rate, average_speed) VALUES(?,?,?,?,?,?,?,?,?,?,?)",new Object[]{
                    rdi.getSessionID(), rdi.getDate(),
                    rdi.getSport_type(), rdi.getStart_time(),
                    rdi.getEnd_time(), rdi.getSport_force(),
                    rdi.getSport_time(), rdi.getHeat_num(),
                    rdi.getStep_num(),rdi.getAverage_rate(),
                    rdi.getAverage_speed()});
            db.setTransactionSuccessful();
            LogUtils.d("sqlsucceed", "rateData增加数据成功");
        }catch(SQLException e)
        {
            LogUtils.d("myerror" + "rateData增加数据不成功");
        }
            db.endTransaction();



    }
    //rateData表的修改功能 OK
    public void updateRateDataInfo(RateDataInfo ri,String a,String b){
        db.beginTransaction();
        try {
            db.execSQL("update rateData set sessionID=?,date=?, sport_type=?, start_time=?,end_time=?, sport_force=?,sport_time=?, heat_num=?, step_num=?, average_rate=?, average_speed=? where SessionID=? and date=?"
                    ,new Object[]{
                            ri.getSessionID(), ri.getDate(),
                            ri.getSport_type(), ri.getStart_time(),
                            ri.getEnd_time(), ri.getSport_force(),
                            ri.getSport_time(), ri.getHeat_num(),
                            ri.getStep_num(),ri.getAverage_rate(),
                            ri.getAverage_speed(),a,b});
            db.setTransactionSuccessful();
            LogUtils.d("sqlsucceed", "rateData修改数据成功");

        }catch (SQLException e) {
            LogUtils.d("myerror" + "rateData修改数据不成功");
        }
            db.endTransaction();


    }
    //rateData表的查找功能 OK
    public RateDataInfo findRateDataInfo(String sessionid,String dateid) {
        try {
            Cursor cursor = db.rawQuery(
                    "select * from rateData where sessionID=? and date=?", new String[]{sessionid, dateid});
            if (cursor.moveToNext()) {
                RateDataInfo ri = new RateDataInfo();
                ri.setSessionID((cursor.getString(0)));
                ri.setDate(cursor.getString(1));
                ri.setSport_type(cursor.getString(2));
                ri.setStart_time(cursor.getString(3));
                ri.setEnd_time(cursor.getString(4));
                ri.setSport_force(cursor.getString(5));
                ri.setSport_time(cursor.getString(6));
                ri.setHeat_num(cursor.getString(7));
                ri.setStep_num(cursor.getString(8));
                ri.setAverage_rate(cursor.getString(9));
                ri.setAverage_speed(cursor.getString(10));
                cursor.close();
                return ri;
            }
            LogUtils.d("sqlsucceed", "rateData查询数据成功");
        }catch (SQLException e){
            LogUtils.d("myerror" + "rateData查诡数据数据不成功");
        }
        return new RateDataInfo("0","0","0","0","0","0","0","0","0","0","0");
    }
    //tiredData表的增加功能 OK
    public void saveTiredDataInfo(TiredDataInfo tdi){
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO tiredData(sessionID, date,sdnn) VALUES(?,?,?)",new Object[]{
                    tdi.getSessionID(), tdi.getDate(),
                    tdi.getSdnn()});
            db.setTransactionSuccessful();
            LogUtils.d("sqlsucceed", "tiredData增加数据成功");

        }catch(SQLException e)
        {
            LogUtils.d("myerror" + "tiredData增加数据不成功");
        }
            db.endTransaction();



    }
    //tiredData表的修改功能 OK
    public void updateTiredDataInfo(TiredDataInfo ri,String a,String b){
        db.beginTransaction();
        try {
            db.execSQL("update tiredData set sessionID=?,date=? ,sdnn=? where sessionID=? and date=?"
                    ,new Object[]{
                            ri.getSessionID(), ri.getDate(),
                            ri.getSdnn(),a,b});
            db.setTransactionSuccessful();
            LogUtils.d("sqlsucceed", "tiredData修改数据成功");
        }catch(SQLException e)
        {
            LogUtils.d("myerror" + "tiredData修改数据不成功");
        }
            db.endTransaction();

    }
    //tiredData表的查找功能 OK
    public TiredDataInfo findTiredDataInfo(String sessionid,String dateid) {
        try {
            Cursor cursor = db.rawQuery(
                    "select * from tiredData where sessionID=? and date=?", new String[]{sessionid, dateid});
            if (cursor.moveToNext()) {
                TiredDataInfo ti = new TiredDataInfo();
                ti.setSessionID((cursor.getString(0)));
                ti.setDate(cursor.getString(1));
                ti.setSdnn(cursor.getString(2));
                cursor.close();
                return ti;
            }
            LogUtils.d("sqlsucceed", "tiredData查询数据成功");
        }catch (SQLException e){
            LogUtils.d("myerror" + "tiredData查找数据不成功");
        }
        return new TiredDataInfo("0","0","0");
    }


}
