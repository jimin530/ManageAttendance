package com.jmdroid.manageattendance.Util;

import java.text.DateFormat;
import java.text.ParseException;
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

    public boolean canAtt(String lecture_time, String nowHour, String nowMinute) {
        int startHour = Integer.parseInt(lecture_time.substring(0, 2));
        int startMinute = Integer.parseInt(lecture_time.substring(4, 6));

        // 출석 체크 가능한 시간
        if (Integer.parseInt(nowHour) <= startHour
                && Integer.parseInt(nowMinute) <= startMinute + 15) {
            return true;
        }
        return false;
    }

    public int possibleAtt(String lecture_time, String nowHour, String nowMinute) {
        int startHour = Integer.parseInt(lecture_time.substring(0, 2));
        int startMinute = Integer.parseInt(lecture_time.substring(4, 6));

        Date f_lecture_time = null;
        Date f_now_time = null;
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        try {
            f_lecture_time = dateFormat.parse(startHour + ":" + startMinute);
            f_now_time = dateFormat.parse(nowHour + ":" + nowMinute);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long compareTime = f_lecture_time.getTime() - f_now_time.getTime();
        if (compareTime > 0.0) {
            // 출석 가능 전
            return 1;
        } else if (compareTime <= 0.0 && compareTime >= -600000.0) {
            // 출석 가능
            return 2;
        } else if (compareTime < -600000.0 && compareTime >= -900000.0) {
            // 지각
            return 3;
        } else {
            // 결석
            return 4;
        }
    }
}

















