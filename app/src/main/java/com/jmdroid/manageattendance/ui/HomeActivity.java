package com.jmdroid.manageattendance.ui;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jmdroid.manageattendance.R;
import com.jmdroid.manageattendance.accout.AccountMange;
import com.jmdroid.manageattendance.network.reqmodel.ReqHeader;
import com.jmdroid.manageattendance.network.reqmodel.ReqLectureList;
import com.jmdroid.manageattendance.network.resmodel.ResLectureList;
import com.jmdroid.manageattendance.retrofit.RetrofitGenterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    // 스크롤뷰
    NestedScrollView nsv_main;
    // 리싸이클러뷰
    RecyclerView rv_lecture;

    // 리싸이클러뷰 아이템 리스트
    ResLectureList resLectureList;

    /*
     * 어댑터 및 매니저
     */
    // 리싸이클럽 어댑터
    LectureAdapter lectureAdapter;
    // 리싸이클러뷰 레아이웃 매니저
    LinearLayoutManager lectureLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nsv_main = (NestedScrollView) findViewById(R.id.nsv_main);
        rv_lecture = (RecyclerView) findViewById(R.id.rv_lecture);
        lectureAdapter = new LectureAdapter();
        // 리니어레이아웃 매니저
        lectureLinearLayoutManager = new LinearLayoutManager(this);
        // 버티컬 정렬 설정
        lectureLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rv_lecture.setLayoutManager(lectureLinearLayoutManager);
        // 스크롤뷰 부드럽게
        rv_lecture.setNestedScrollingEnabled(false);

        // 데이터 통신
        getLectureList();

    }

    // 리싸이클러뷰 어댑터
    class LectureAdapter extends RecyclerView.Adapter<PostHolder> {

        @Override
        public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_lecture, parent, false);
            return new PostHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PostHolder holder, int position) {
            holder.bindOnPost(
                    position,
                    resLectureList.getBody().get(position).getLecture_name(),
                    resLectureList.getBody().get(position).getTeacher_name(),
                    resLectureList.getBody().get(position).getLecture_time()
            );
        }

        @Override
        public int getItemCount() {
            return resLectureList.getBody().size();
        }
    }

    // 홀더
    public class PostHolder extends RecyclerView.ViewHolder {

        TextView tv_lecture_name;
        TextView tv_teacher_name;
        TextView tv_lecture_time;
        ImageButton btn_lecture_info;

        // 뷰로부터 컴포넌트를 획득
        public PostHolder(View itemView) {
            super(itemView);
            tv_lecture_name = (TextView) itemView.findViewById(R.id.tv_lecture_name);
            tv_teacher_name = (TextView) itemView.findViewById(R.id.tv_teacher_name);
            tv_lecture_time = (TextView) itemView.findViewById(R.id.tv_lecture_time);
            btn_lecture_info = (ImageButton) itemView.findViewById(R.id.btn_lecture_info);
        }

        public void bindOnPost(final int position, String lecture_name, String teacher_name, String lecture_time) {
            tv_lecture_name.setText(lecture_name);
            tv_teacher_name.setText(teacher_name);
            tv_lecture_time.setText(lecture_time);


            // 버튼은 클릭이벤트를 같이
            btn_lecture_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeActivity.this, resLectureList.getBody().get(position).getBeacon_id(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void getLectureList() {
        ReqHeader reqHeader = new ReqHeader(
                "lecturelist"
        );
        ReqLectureList reqLectureList = new ReqLectureList(reqHeader, AccountMange.getInstance().student_id);

        callNetLectureList(reqLectureList);
    }

    public void callNetLectureList(ReqLectureList reqLectureList) {
        Call<ResLectureList> NetLectureList = RetrofitGenterator.getInstance().getRetrofitImpFactory().NetLectureList(reqLectureList);
        NetLectureList.enqueue(new Callback<ResLectureList>() {
            @Override
            public void onResponse(Call<ResLectureList> call, Response<ResLectureList> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getBody() != null) {
                        // 통신 성공
                        Log.i("RES SUC", response.body().getBody().toString());
                        resLectureList = response.body();

                        // 리싸이클러뷰 setAdapter
                        rv_lecture.setAdapter(lectureAdapter);
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
            public void onFailure(Call<ResLectureList> call, Throwable t) {
                // 통신 실패
                Log.i("RES FAIL", t.getMessage().toString());
            }
        });
    }
}
