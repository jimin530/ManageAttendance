package com.jmdroid.manageattendance.network.resmodel;


import com.jmdroid.manageattendance.dto.MyInfoDTO;

import java.util.ArrayList;

public class ResMyInfo {

    ResHeader header;
    ArrayList<MyInfoDTO> body;

    public ResMyInfo() {
    }

    public ResMyInfo(ResHeader header, ArrayList<MyInfoDTO> body) {

        this.header = header;
        this.body = body;
    }

    public ResHeader getHeader() {

        return header;
    }

    public void setHeader(ResHeader header) {
        this.header = header;
    }

    public ArrayList<MyInfoDTO> getBody() {
        return body;
    }

    public void setBody(ArrayList<MyInfoDTO> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ResMyInfo{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
