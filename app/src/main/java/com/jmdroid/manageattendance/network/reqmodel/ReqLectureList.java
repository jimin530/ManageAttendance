package com.jmdroid.manageattendance.network.reqmodel;

public class ReqLectureList {
    ReqHeader header;
    String student_id;

    public ReqLectureList() {
    }

    public ReqLectureList(ReqHeader header, String student_id) {

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
        return "ReqLectureList{" +
                "header=" + header +
                ", student_id='" + student_id + '\'' +
                '}';
    }
}
