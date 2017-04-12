package com.jmdroid.manageattendance.dto;

/**
 * Created by jimin on 2017. 4. 12..
 */

public class ReqChangeStateDTO {
    String date;
    String student_id;
    String lecture_code;
    String state;

    public ReqChangeStateDTO() {
    }

    public ReqChangeStateDTO(String date, String student_id, String lecture_code, String state) {

        this.date = date;
        this.student_id = student_id;
        this.lecture_code = lecture_code;
        this.state = state;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getLecture_code() {
        return lecture_code;
    }

    public void setLecture_code(String lecture_code) {
        this.lecture_code = lecture_code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ReqChangeStateDTO{" +
                "date='" + date + '\'' +
                ", student_id='" + student_id + '\'' +
                ", lecture_code='" + lecture_code + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
