package com.lmiot.BraceletDemo.Bean;

/**
 * Created by ming on 2016/3/15.
 */
public class UserSportData {
    String sessionID;
    String username;
    String date;
    String sport_type;
    String start_time;
    String end_time;
    int sport_time;
    int heart_num;
    int step_num;
    int average_speed;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getSport_time() {
        return sport_time;
    }

    public void setSport_time(int sport_time) {
        this.sport_time = sport_time;
    }

    public int getHeart_num() {
        return heart_num;
    }

    public void setHeart_num(int heart_num) {
        this.heart_num = heart_num;
    }

    public int getStep_num() {
        return step_num;
    }

    public void setStep_num(int step_num) {
        this.step_num = step_num;
    }

    public int getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(int average_speed) {
        this.average_speed = average_speed;
    }
}
