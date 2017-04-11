package com.jmdroid.manageattendance.dto;

/**
 * Created by jimin on 2017. 4. 11..
 */

public class MyInfoDTO {
    String student_name;

    public MyInfoDTO() {
    }

    public MyInfoDTO(String student_name) {

        this.student_name = student_name;
    }

    public String getStudent_name() {

        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    @Override
    public String toString() {
        return "MyInfoDTO{" +
                "student_name='" + student_name + '\'' +
                '}';
    }
}
