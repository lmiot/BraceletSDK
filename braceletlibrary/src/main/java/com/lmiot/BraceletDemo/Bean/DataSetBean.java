package com.lmiot.BraceletDemo.Bean;

import java.util.List;

/**
 * Created by ming on 2016/3/21.
 */
public class DataSetBean {

    List<SportDataBean> sportData;
    List<SleepDataBean> sleepData;
    List<RateDataBean> rateData;
    List<TiredDataBean> tiredData;

    public List<SportDataBean> getSprotData() {
        return sportData;
    }

    public void setSprotData(List<SportDataBean> sprotData) {
        this.sportData = sprotData;
    }

    public List<SleepDataBean> getSleepData() {
        return sleepData;
    }

    public void setSleepData(List<SleepDataBean> sleepData) {
        this.sleepData = sleepData;
    }

    public List<RateDataBean> getRateData() {
        return rateData;
    }

    public void setRateData(List<RateDataBean> rateData) {
        this.rateData = rateData;
    }

    public List<TiredDataBean> getTiredData() {
        return tiredData;
    }

    public void setTiredData(List<TiredDataBean> tiredData) {
        this.tiredData = tiredData;
    }
}
