package com.lmiot.BraceletDemo.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ming on 2016/3/15.
 */
public class TimeUtils {

    public static String getCurrentDate(){  /*获取当前日期*/

        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
        Date date=new Date(System.currentTimeMillis());
        return format.format(date);
    }

    public static String getPreDate(){  /*获取当前日期*/

        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
        Date date=new Date(System.currentTimeMillis()-24*3600*1000);
        return format.format(date);
    }


    public static String convertSecondTime(int seconds) {  /*把秒数转为xx小时xx分xx秒*/

        int hours=(int)(seconds/3600);
        int minute=(int)((seconds%3600)/60);
        int second=seconds-hours*3600-minute*60;

        if(hours==0){
            return  minute+"分"+second+"秒";
        }
        else{
            return  hours+"小时"+ minute+"分"+second+"秒";
        }
    }
    public static String convertMinuteTime(int minutes) {  /*把分钟转为xx小时xx分*/

        int hours=(int)(minutes/60);
        int minute=minutes-hours*60;

            return  hours+"小时"+ minute+"分";
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        return time;
    }
    public static String getCurrentAgeDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        return date;
    }
    public static String getCurrentAgeDate_otherformat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        return date;
    }


    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdf.format(new java.util.Date());
        return StrUtils.convertDateMonth(date);
    }


    public static String getCurrentWeek() {
        Calendar c = Calendar.getInstance();
        int i = c.get(Calendar.WEEK_OF_YEAR);
        return String.valueOf(i);
    }

    public static String getAdayDate(int i) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, i);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = formatter.format(date);
        return StrUtils.convertDate(dateString);
    }
    //手环用到的，方便到数据查询数据用到的格式
    public static String getdayDate_otherFormat(int i) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, i);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getWeekByDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cl = Calendar.getInstance();
        try {
            cl.setTime(sdf.parse(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int week = cl.get(Calendar.WEEK_OF_YEAR);
        return String.valueOf(week);
    }

    public static String getAweek(int i) {
        return String.valueOf(Integer.valueOf(getCurrentWeek()) + i);
    }

    public static String getAmonth(int i) {
        String[] date=getCurrentMonth().split("/");
        String year=date[0];
        String month=date[1];
        String s="";
        if (Integer.valueOf(month)+i<=0){
            month= String.valueOf(12+Integer.valueOf(month)+i);
            year= String.valueOf(Integer.valueOf(year)-1);
            s=year+"/"+month;
        }else {
            month= String.valueOf(Integer.valueOf(month)+i);
            s=year+"/"+month;
        }
        return s;
    }
    public static String getAmonth_otherFormat(int i) {
        String[] date=getCurrentAgeDate_otherformat().split("-");
        String year=date[0];
        String month=date[1];
        String s="";
        if (Integer.valueOf(month)+i<=0){
            month= String.valueOf(12+Integer.valueOf(month)+i);
            year= String.valueOf(Integer.valueOf(year)-1);
            s=year+"-"+month;
        }else {
            month= String.valueOf(Integer.valueOf(month)+i);
            s=year+"-"+month;
        }
        return s;
    }

    //得到这一周的7天日期
    public static List<String> getMondayof(int  mi ) {
        SimpleDateFormat b = new SimpleDateFormat("yyyy-M-dd");
        Calendar cc = Calendar.getInstance();
        int abc = cc.get(Calendar.WEEK_OF_YEAR);
        cc.set(Calendar.WEEK_OF_YEAR, abc+mi);
        LogUtils.d(b.format(cc.getTime()));
       /* cc.add(Calendar.WEEK_OF_YEAR, mi);
        LogUtils.d(b.format(cc.getTime()));*/
        List<String> a=new ArrayList<>();
        cc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        int ab=1;
        while(cc.get(Calendar.DAY_OF_WEEK)<=Calendar.SATURDAY)
        {
            String d = b.format(cc.getTime());
            a.add(d);
            cc.add(Calendar.DAY_OF_WEEK,1);
            ab++;


            if(ab==8){
                break;

            }

        }
      /*  LogUtils.d("BBBBB",a.get(0)+a.get(6));*/
        return a;
    }

}
