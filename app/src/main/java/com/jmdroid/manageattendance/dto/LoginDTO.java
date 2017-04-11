package com.jmdroid.manageattendance.dto;


public class LoginDTO {
    String student_id;
    String password;

    public LoginDTO() {
    }

    public LoginDTO(String student_id, String password) {

        this.student_id = student_id;
        this.password = password;
    }

    public String getStudent_id() {

        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "student_id='" + student_id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
