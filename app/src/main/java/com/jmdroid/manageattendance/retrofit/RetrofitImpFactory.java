package com.jmdroid.manageattendance.retrofit;


import com.jmdroid.manageattendance.network.reqmodel.ReqLectureList;
import com.jmdroid.manageattendance.network.reqmodel.ReqLogin;
import com.jmdroid.manageattendance.network.reqmodel.ReqMyInfo;
import com.jmdroid.manageattendance.network.reqmodel.ReqSignup;
import com.jmdroid.manageattendance.network.resmodel.ResBasic;
import com.jmdroid.manageattendance.network.resmodel.ResLectureList;
import com.jmdroid.manageattendance.network.resmodel.ResMyInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitImpFactory {

    // 회원가입
    @POST("/ma_signup")
    Call<ResBasic> NetSignup(@Body ReqSignup reqSignup);

    // 로그인
    @POST("/ma_login")
    Call<ResBasic> NetLogin(@Body ReqLogin reqLogin);

    // 강의 리스트 가져오기
    @POST("/ma_lecturelist")
    Call<ResLectureList> NetLectureList(@Body ReqLectureList reqLectureList);

    // 내 정보 가져오기
    @POST("/ma_myinfo")
    Call<ResMyInfo> NetMyInfo(@Body ReqMyInfo reqMyInfo);

}
