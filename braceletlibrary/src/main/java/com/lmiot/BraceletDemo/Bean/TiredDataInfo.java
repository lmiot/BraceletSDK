package com.lmiot.BraceletDemo.Bean;

/**
 * Created by ming on 2016/3/19.
 */
public class TiredDataInfo {

    /**
     * sessionID : 13912345678
     * date : 2016-3-18
     * sdnn : 25
     */

    private String sessionID;
    private String date;
    private String sdnn;

    public TiredDataInfo() {
    }

    @Override
    public String toString() {
        return "TiredDataInfo{" +
                "sessionID='" + sessionID + '\'' +
                ", date='" + date + '\'' +
                ", sdnn='" + sdnn + '\'' +
                '}';
    }

    public TiredDataInfo(String sessionID, String date, String sdnn) {
        this.sessionID = sessionID;
        this.date = date;
        this.sdnn = sdnn;
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

    public String getSdnn() {
        return sdnn;
    }

    public void setSdnn(String sdnn) {
        this.sdnn = sdnn;
    }
}
