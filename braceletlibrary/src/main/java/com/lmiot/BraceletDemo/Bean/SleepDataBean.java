package com.lmiot.BraceletDemo.Bean;

/**
 * Created by ming on 2016/3/19.
 */
public class SleepDataBean {

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

    private int sleep_time;
    private int depth_time;
    private int light_time;
    private int wake_time;
    private int percent;
    private int target;

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
