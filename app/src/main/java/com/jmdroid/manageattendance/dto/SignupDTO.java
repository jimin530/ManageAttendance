package com.jmdroid.manageattendance.dto;


public class SignupDTO {
    String student_id;
    String student_name;
    String password;

    public SignupDTO() {
    }

    public SignupDTO(String student_id, String student_name, String password) {

        this.student_id = student_id;
        this.student_name = student_name;
        this.password = password;
    }

    public String getStudent_id() {

        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupDTO{" +
                "student_id='" + student_id + '\'' +
                ", student_name='" + student_name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
