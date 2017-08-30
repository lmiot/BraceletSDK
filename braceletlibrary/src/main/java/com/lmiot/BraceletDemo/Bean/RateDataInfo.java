package com.lmiot.BraceletDemo.Bean;

/**
 * Created by ming on 2016/3/19.
 */
public class RateDataInfo {

    /**
     * sessionID : 13912345678
     * date : 2016-3-18
     * sport_type : 徒步
     * start_time : 13:25
     * end_time : 14:25
     * sport_force : 非常低
     * sport_time : 1小时
     * heat_num : 259
     * step_num : 1000
     * average_rate : 72
     * average_speed : 2
     */

    private String sessionID;
    private String date;
    private String sport_type;
    private String start_time;
    private String end_time;
    private String sport_force;
    private String sport_time;
    private String heat_num;
    private String step_num;
    private String average_rate;
    private String average_speed;
    public RateDataInfo() {
    }

    @Override
    public String toString() {
        return "RateDataInfo{" +
                "sessionID='" + sessionID + '\'' +
                ", date='" + date + '\'' +
                ", sport_type='" + sport_type + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", sport_force='" + sport_force + '\'' +
                ", sport_time='" + sport_time + '\'' +
                ", heat_num='" + heat_num + '\'' +
                ", step_num='" + step_num + '\'' +
                ", average_rate='" + average_rate + '\'' +
                ", average_speed='" + average_speed + '\'' +
                '}';
    }

    public RateDataInfo(String sessionID, String date, String sport_type, String start_time, String end_time, String sport_force, String sport_time, String step_num, String heat_num, String average_rate, String average_speed) {
        this.sessionID = sessionID;
        this.date = date;
        this.sport_type = sport_type;
        this.start_time = start_time;
        this.end_time = end_time;
        this.sport_force = sport_force;
        this.sport_time = sport_time;
        this.step_num = step_num;
        this.heat_num = heat_num;
        this.average_rate = average_rate;
        this.average_speed = average_speed;
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

    public String getSport_type() {
        return sport_type;
    }

    public void setSport_type(String sport_type) {
        this.sport_type = sport_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSport_force() {
        return sport_force;
    }

    public void setSport_force(String sport_force) {
        this.sport_force = sport_force;
    }

    public String getSport_time() {
        return sport_time;
    }

    public void setSport_time(String sport_time) {
        this.sport_time = sport_time;
    }

    public String getHeat_num() {
        return heat_num;
    }

    public void setHeat_num(String heat_num) {
        this.heat_num = heat_num;
    }

    public String getStep_num() {
        return step_num;
    }

    public void setStep_num(String step_num) {
        this.step_num = step_num;
    }

    public String getAverage_rate() {
        return average_rate;
    }

    public void setAverage_rate(String average_rate) {
        this.average_rate = average_rate;
    }

    public String getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(String average_speed) {
        this.average_speed = average_speed;
    }
}
