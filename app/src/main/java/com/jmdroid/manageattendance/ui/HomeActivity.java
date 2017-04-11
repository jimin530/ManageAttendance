package com.jmdroid.manageattendance.ui;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jmdroid.manageattendance.R;
import com.jmdroid.manageattendance.network.reqmodel.ReqHeader;
import com.jmdroid.manageattendance.network.reqmodel.ReqLectureList;
import com.jmdroid.manageattendance.network.resmodel.ResBasic;
import com.jmdroid.manageattendance.retrofit.RetrofitGenterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    // 스크롤뷰
    NestedScrollView nsv_main;
    // 리싸이클러뷰
    RecyclerView rv_lecture;
    //
    TextView tv_lecture_name;
    TextView tv_teacher_name;
    TextView tv_lecture_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void getLectureList(View view) {
        ReqHeader reqHeader = new ReqHeader(
                "lecturelist"
        );
        ReqLectureList reqLectureList = new ReqLectureList(reqHeader, "101884");

        callNetLectureList(reqLectureList);
    }

    public void callNetLectureList(ReqLectureList reqLectureList) {
        Call<ResBasic> NetLectureList = RetrofitGenterator.getInstance().getRetrofitImpFactory().NetLectureList(reqLectureList);
        NetLectureList.enqueue(new Callback<ResBasic>() {
            @Override
            public void onResponse(Call<ResBasic> call, Response<ResBasic> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getMsg() != null) {
                        // 통신 성공
                        Log.i("RES SUC", response.body().getMsg());
                    } else {
                        // 결과값 없음
                        Log.i("RES NULL", response.message().toString());
                    }
                } else {
                    // 결과값 실패
                    Log.i("RES ERR", response.message().toString());
                }
            }

            @Override
            public void onFailure(Call<ResBasic> call, Throwable t) {
                // 통신 실패
                Log.i("RES FAIL", t.getMessage().toString());
            }
        });
    }
}
