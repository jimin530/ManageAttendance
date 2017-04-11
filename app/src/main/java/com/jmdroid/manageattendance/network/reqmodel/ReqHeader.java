package com.jmdroid.manageattendance.network.reqmodel;


public class ReqHeader
{
    String code;

    public ReqHeader(){}

    public ReqHeader(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
