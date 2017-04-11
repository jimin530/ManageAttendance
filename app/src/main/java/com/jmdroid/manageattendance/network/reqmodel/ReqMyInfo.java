package com.jmdroid.manageattendance.network.reqmodel;

public class ReqMyInfo {
    ReqHeader header;
    String student_id;

    public ReqMyInfo() {
    }

    public ReqMyInfo(ReqHeader header, String student_id) {

        this.header = header;
        this.student_id = student_id;
    }

    public ReqHeader getHeader() {

        return header;
    }

    public void setHeader(ReqHeader header) {
        this.header = header;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "ReqMyInfo{" +
                "header=" + header +
                ", student_id='" + student_id + '\'' +
                '}';
    }
}
