package com.jmdroid.manageattendance.network.reqmodel;

import com.jmdroid.manageattendance.dto.ReqLectureListDTO;

public class ReqLectureList {
    ReqHeader header;
    ReqLectureListDTO body;

    public ReqLectureList() {
    }

    public ReqLectureList(ReqHeader header, ReqLectureListDTO body) {

        this.header = header;
        this.body = body;
    }

    public ReqHeader getHeader() {

        return header;
    }

    public void setHeader(ReqHeader header) {
        this.header = header;
    }

    public ReqLectureListDTO getBody() {
        return body;
    }

    public void setBody(ReqLectureListDTO body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ReqLectureList{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
