package com.jmdroid.manageattendance.network.reqmodel;

import com.jmdroid.manageattendance.dto.LoginDTO;

public class ReqLogin {
    ReqHeader header;
    LoginDTO body;

    public ReqHeader getHeader() {
        return header;
    }

    public void setHeader(ReqHeader header) {
        this.header = header;
    }

    public LoginDTO getBody() {
        return body;
    }

    public void setBody(LoginDTO body) {
        this.body = body;
    }

    public ReqLogin(ReqHeader header, LoginDTO body) {

        this.header = header;
        this.body = body;
    }

    public ReqLogin() {

    }
}
