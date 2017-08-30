package com.lmiot.BraceletDemo.Bean;

import java.io.Serializable;

/**
 * Created by ming on 2016/3/19.
 */
public class SportDataInfo implements Serializable{


    /**
     * sessionID : 13912345678
     * date : 2016-3-18
     * day_time : 2小时5分
     * day_step : 100
     * day_heat : 125
     * day_distanc : 100
     * percent : 50%
     * target : 8000
     */

    private String sessionID;
    private String date;
    private String day_time;
    private String day_step;
    private String day_heat;
    private String day_distanc;
    private String percent;
    private String target;

    public SportDataInfo(String sessionID, String date, String day_time, String day_step, String day_distanc, String target, String percent, String day_heat) {
        this.sessionID = sessionID;
        this.date = date;
        this.day_time = day_time;
        this.day_step = day_step;
        this.day_distanc = day_distanc;
        this.target = target;
        this.percent = percent;
        this.day_heat = day_heat;
    }

    public SportDataInfo() {
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

    public String getDay_time() {
        return day_time;
    }

    public void setDay_time(String day_time) {
        this.day_time = day_time;
    }

    public String getDay_step() {
        return day_step;
    }

    public void setDay_step(String day_step) {
        this.day_step = day_step;
    }

    public String getDay_heat() {
        return day_heat;
    }

    public void setDay_heat(String day_heat) {
        this.day_heat = day_heat;
    }

    public String getDay_distanc() {
        return day_distanc;
    }

    public void setDay_distanc(String day_distanc) {
        this.day_distanc = day_distanc;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "SportDataInfo{" +
                "sessionID='" + sessionID + '\'' +
                ", date='" + date + '\'' +
                ", day_time='" + day_time + '\'' +
                ", day_step='" + day_step + '\'' +
                ", day_heat='" + day_heat + '\'' +
                ", day_distanc='" + day_distanc + '\'' +
                ", percent='" + percent + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
