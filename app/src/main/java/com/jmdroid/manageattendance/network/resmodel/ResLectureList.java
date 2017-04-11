package com.jmdroid.manageattendance.network.resmodel;

import com.jmdroid.manageattendance.dto.LectureListDTO;

import java.util.ArrayList;

/**
 * Created by jimin on 2017. 4. 11..
 */

public class ResLectureList {
    ResHeader header;
    ArrayList<LectureListDTO> body;

    public ResLectureList() {
    }

    public ResLectureList(ResHeader header, ArrayList<LectureListDTO> body) {

        this.header = header;
        this.body = body;
    }

    public ResHeader getHeader() {

        return header;
    }

    public void setHeader(ResHeader header) {
        this.header = header;
    }

    public ArrayList<LectureListDTO> getBody() {
        return body;
    }

    public void setBody(ArrayList<LectureListDTO> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ResLectureList{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
