package com.jmdroid.manageattendance.dto;

/**
 * Created by jimin on 2017. 4. 12..
 */

public class ReqLectureListDTO {
    String date;
    String student_id;

    public ReqLectureListDTO() {
    }

    public ReqLectureListDTO(String date, String student_id) {

        this.date = date;
        this.student_id = student_id;
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

    @Override
    public String toString() {
        return "ReqLectureListDTO{" +
                "date='" + date + '\'' +
                ", student_id='" + student_id + '\'' +
                '}';
    }
}
