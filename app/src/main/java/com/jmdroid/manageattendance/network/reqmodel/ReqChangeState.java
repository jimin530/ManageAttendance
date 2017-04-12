package com.jmdroid.manageattendance.network.reqmodel;

import com.jmdroid.manageattendance.dto.ReqChangeStateDTO;

/**
 * Created by jimin on 2017. 4. 12..
 */

public class ReqChangeState {
    ReqHeader header;
    ReqChangeStateDTO body;

    public ReqChangeState() {
    }

    public ReqChangeState(ReqHeader header, ReqChangeStateDTO body) {

        this.header = header;
        this.body = body;
    }

    public ReqHeader getHeader() {

        return header;
    }

    public void setHeader(ReqHeader header) {
        this.header = header;
    }

    public ReqChangeStateDTO getBody() {
        return body;
    }

    public void setBody(ReqChangeStateDTO body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ReqChangeState{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
