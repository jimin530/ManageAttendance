package com.jmdroid.manageattendance.dto;

/**
 * Created by jimin on 2017. 4. 11..
 */

public class LectureListDTO {
    String lecture_code;
    String lecture_name;
    String lecture_time;
    String teacher_name;
    String beacon_id;
    String att_state;

    public LectureListDTO() {
    }

    public LectureListDTO(String lecture_code, String lecture_name, String lecture_time, String teacher_name, String beacon_id, String att_state) {

        this.lecture_code = lecture_code;
        this.lecture_name = lecture_name;
        this.lecture_time = lecture_time;
        this.teacher_name = teacher_name;
        this.beacon_id = beacon_id;
        this.att_state = att_state;
    }

    public String getLecture_code() {

        return lecture_code;
    }

    public void setLecture_code(String lecture_code) {
        this.lecture_code = lecture_code;
    }

    public String getLecture_name() {
        return lecture_name;
    }

    public void setLecture_name(String lecture_name) {
        this.lecture_name = lecture_name;
    }

    public String getLecture_time() {
        return lecture_time;
    }

    public void setLecture_time(String lecture_time) {
        this.lecture_time = lecture_time;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getBeacon_id() {
        return beacon_id;
    }

    public void setBeacon_id(String beacon_id) {
        this.beacon_id = beacon_id;
    }

    public String getAtt_state() {
        return att_state;
    }

    public void setAtt_state(String att_state) {
        this.att_state = att_state;
    }

    @Override
    public String toString() {
        return "LectureListDTO{" +
                "lecture_code='" + lecture_code + '\'' +
                ", lecture_name='" + lecture_name + '\'' +
                ", lecture_time='" + lecture_time + '\'' +
                ", teacher_name='" + teacher_name + '\'' +
                ", beacon_id='" + beacon_id + '\'' +
                ", att_state='" + att_state + '\'' +
                '}';
    }
}
