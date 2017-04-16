package com.jmdroid.manageattendance.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static TimeUtil ourInstance = new TimeUtil();

    public static TimeUtil getInstance() {
        return ourInstance;
    }

    private TimeUtil() {
    }

    private SimpleDateFormat simpleDateFormat;

    // 현재 연월일
    public String nowDate;
    // 현재 시
    public String nowHour;
    // 현재 분
    public String nowMinute;

    public void setTime() {
        // 현재시간을 msec으로 구함
        long time = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장
        Date date = new Date(time);

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        TimeUtil.getInstance().nowDate = (simpleDateFormat.format(date)).substring(2, 8);
        simpleDateFormat = new SimpleDateFormat("HH");
        TimeUtil.getInstance().nowHour = simpleDateFormat.format(date);
        simpleDateFormat = new SimpleDateFormat("mm");
        TimeUtil.getInstance().nowMinute = simpleDateFormat.format(date);
    }
}

















