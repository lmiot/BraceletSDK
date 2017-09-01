package com.lmiot.BraceletDemo.Bean;

/**
 * 创建日期：2017-08-31 16:57
 * 作者:Mr Li
 * 描述:
 */
public class HeartRateData {

    private String sessionID;
    private    String date;
    private String time;
    private  int heartRate;


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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
}
