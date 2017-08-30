package com.lmiot.BraceletDemo.Bean;

/**
 * Created by ming on 2016/3/19.
 */
public class SleepDataInfo {

    /**
     * sessionID : 13912345678
     * date : 2016-3-18
     * sleep_time : 2小时5分
     * depth_time : 2小时5分
     * light_time : 2小时5分
     * wake_time : 2小时5分
     * percent : 50%
     * target : 4
     */

    private String sessionID;
    private String date;
    private int sleep_time;
    private int depth_time;
    private int light_time;
    private int wake_time;
    private int percent;
    private int target;

    @Override
    public String toString() {
        return "SleepDataInfo{" +
                "sessionID='" + sessionID + '\'' +
                ", date='" + date + '\'' +
                ", sleep_time='" + sleep_time + '\'' +
                ", depth_time='" + depth_time + '\'' +
                ", light_time='" + light_time + '\'' +
                ", wake_time='" + wake_time + '\'' +
                ", percent='" + percent + '\'' +
                ", target='" + target + '\'' +
                '}';
    }

    public SleepDataInfo() {
    }

    public SleepDataInfo(String sessionID, String date, int sleep_time, int depth_time, int light_time, int wake_time, int percent, int target) {
        this.sessionID = sessionID;
        this.date = date;
        this.sleep_time = sleep_time;
        this.depth_time = depth_time;
        this.light_time = light_time;
        this.wake_time = wake_time;
        this.percent = percent;
        this.target = target;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSleep_time() {
        return sleep_time;
    }

    public void setSleep_time(int sleep_time) {
        this.sleep_time = sleep_time;
    }

    public int getDepth_time() {
        return depth_time;
    }

    public void setDepth_time(int depth_time) {
        this.depth_time = depth_time;
    }

    public int getLight_time() {
        return light_time;
    }

    public void setLight_time(int light_time) {
        this.light_time = light_time;
    }

    public int getWake_time() {
        return wake_time;
    }

    public void setWake_time(int wake_time) {
        this.wake_time = wake_time;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
