package com.jmdroid.manageattendance.network.reqmodel;

import com.jmdroid.manageattendance.dto.SignupDTO;

public class ReqSignup {
    ReqHeader header;
    SignupDTO body;

    public ReqSignup() {
    }

    public ReqSignup(ReqHeader header, SignupDTO body) {
        this.header = header;
        this.body = body;
    }

    public ReqHeader getHeader() {
        return header;
    }

    public void setHeader(ReqHeader header) {
        this.header = header;
    }

    public SignupDTO getBody() {
        return body;
    }

    public void setBody(SignupDTO body) {
        this.body = body;
    }
}
