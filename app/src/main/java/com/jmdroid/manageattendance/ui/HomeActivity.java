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
import com.jmdroid.manageattendance.Util.TimeUtil;
import com.jmdroid.manageattendance.accout.AccountManage;
import com.jmdroid.manageattendance.dto.ReqChangeStateDTO;
import com.jmdroid.manageattendance.dto.ReqLectureListDTO;
import com.jmdroid.manageattendance.network.reqmodel.ReqChangeState;
import com.jmdroid.manageattendance.network.reqmodel.ReqHeader;
import com.jmdroid.manageattendance.network.reqmodel.ReqLectureList;
import com.jmdroid.manageattendance.network.resmodel.ResBasic;
import com.jmdroid.manageattendance.network.resmodel.ResLectureList;
import com.jmdroid.manageattendance.retrofit.RetrofitGenterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    /*
     * UI
     */
    // 스크롤뷰
    NestedScrollView nsv_main;
    // 리싸이클러뷰
    RecyclerView rv_lecture;
    // 내 이름
    TextView tv_student_name;

    /*
     * 어댑터 및 매니저
     */
    // 리싸이클럽 어댑터
    LectureAdapter lectureAdapter;
    // 리싸이클러뷰 레아이웃 매니저
    LinearLayoutManager lectureLinearLayoutManager;

    /*
     * 기타
     */
    // 리싸이클러뷰 아이템 리스트
    ResLectureList resLectureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nsv_main = (NestedScrollView) findViewById(R.id.nsv_main);
        rv_lecture = (RecyclerView) findViewById(R.id.rv_lecture);
        tv_student_name = (TextView) findViewById(R.id.tv_student_name);
        // 로그인 사용자 이름 표시
        tv_student_name.setText(AccountManage.getInstance().student_name + " 님");

        lectureAdapter = new LectureAdapter();
        // 리니어레이아웃 매니저
        lectureLinearLayoutManager = new LinearLayoutManager(this);
        // 버티컬 정렬 설정
        lectureLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rv_lecture.setLayoutManager(lectureLinearLayoutManager);
        // 스크롤뷰 부드럽게
        rv_lecture.setNestedScrollingEnabled(false);

        // 데이터 통신(강의 정보 받아오기)
        callNetLectureList();
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

            String lecture_time = resLectureList.getBody().get(position).getLecture_time();
            lecture_time = lecture_time.split("/")[0].substring(0, 2) + "시 "
                    + lecture_time.split("/")[0].substring(2, 4) + "분"
                    + " ~ "
                    + lecture_time.split("/")[1].substring(0, 2) + "시 "
                    + lecture_time.split("/")[1].substring(2, 4) + "분";

            holder.bindOnPost(
                    position,
                    resLectureList.getBody().get(position).getLecture_name(),
                    resLectureList.getBody().get(position).getTeacher_name(),
                    lecture_time,
                    resLectureList.getBody().get(position).getAtt_state()
            );
        }

        @Override
        public int getItemCount() {
            return resLectureList.getBody().size();
        }
    }

    public static ImageButton btn_lecture_info;
    // 홀더
    public class PostHolder extends RecyclerView.ViewHolder {

        TextView tv_lecture_name;
        TextView tv_teacher_name;
        TextView tv_lecture_time;
        TextView tv_att_state;
        //ImageButton btn_lecture_info;

        // 뷰로부터 컴포넌트를 획득
        public PostHolder(View itemView) {
            super(itemView);
            tv_lecture_name = (TextView) itemView.findViewById(R.id.tv_lecture_name);
            tv_teacher_name = (TextView) itemView.findViewById(R.id.tv_teacher_name);
            tv_lecture_time = (TextView) itemView.findViewById(R.id.tv_lecture_time);
            tv_att_state = (TextView) itemView.findViewById(R.id.tv_att_state);
            btn_lecture_info = (ImageButton) itemView.findViewById(R.id.btn_lecture_info);
        }

        public void bindOnPost(final int position, final String lecture_name, String teacher_name, final String lecture_time, String att_state) {
            // 현재 시간 가져오기
            TimeUtil.getInstance().setTime();

            tv_lecture_name.setText(lecture_name);
            tv_teacher_name.setText(teacher_name);
            tv_lecture_time.setText(lecture_time);
            tv_att_state.setText(att_state);

            if (!att_state.equals("미출석")) {
                btn_lecture_info.setEnabled(false);
            }

            // 버튼은 클릭이벤트를 같이
            btn_lecture_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(HomeActivity.this, resLectureList.getBody().get(position).getBeacon_id(), Toast.LENGTH_SHORT).show();
                    // 현재 시간 가져오기
                    TimeUtil.getInstance().setTime();
                    // 출석 시간보다 이른 시간인 경우
                    int possibleTime = TimeUtil.getInstance().possibleAtt(lecture_time, TimeUtil.getInstance().nowHour, TimeUtil.getInstance().nowMinute);
                    if (possibleTime == 1) {
                        Log.i("확인", "else if 2");
                        Toast.makeText(HomeActivity.this, "출석 가능 시간이 아닙니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        btn_lecture_info.setEnabled(false);
                        callNetChangeState(TimeUtil.getInstance().nowDate, AccountManage.getInstance().student_id, resLectureList.getBody().get(position).getLecture_code(), lecture_time);
                    }
                }
            });
        }

    }

    public void callNetLectureList() {
        TimeUtil.getInstance().setTime();
        ReqHeader reqHeader = new ReqHeader(
                "lecturelist"
        );
        ReqLectureListDTO reqLectureListDTO = new ReqLectureListDTO(
                TimeUtil.getInstance().nowDate,
                AccountManage.getInstance().student_id
        );
        ReqLectureList reqLectureList = new ReqLectureList(reqHeader, reqLectureListDTO);

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

    public void callNetChangeState(String date, String student_id, String lecture_code, String lecture_time) {
        String state = null;
        int possibleTime = TimeUtil.getInstance().possibleAtt(lecture_time, TimeUtil.getInstance().nowHour, TimeUtil.getInstance().nowMinute);
        if (possibleTime == 1) {
        }
        // 시작 시간(시)과 현재 시간(시)이 같고, 시작 시간(분) + 10 => 현재 시간(분)일 때 출석
        else if (possibleTime == 2) {
            state = "출석";
        }
        // 10초과 15분 이하로 출석했을 때 지각
        else if (possibleTime == 3) {
            state = "지각";
        }
        // 나머지 결석
        else if (possibleTime == 4) {
            state = "결석";
        }

        ReqHeader reqHeader = new ReqHeader(
                "changestate"
        );
        ReqChangeStateDTO reqChangeStateDTO = new ReqChangeStateDTO(
                date,
                student_id,
                lecture_code,
                state
        );
        ReqChangeState reqChangeState = new ReqChangeState(reqHeader, reqChangeStateDTO);

        Call<ResBasic> NetChangeState = RetrofitGenterator.getInstance().getRetrofitImpFactory().NetChangeState(reqChangeState);
        NetChangeState.enqueue(new Callback<ResBasic>() {
            @Override
            public void onResponse(Call<ResBasic> call, Response<ResBasic> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getMsg() != null) {
                        // 통신 성공
                        Log.i("RES SUC", response.body().getMsg());
                        if (response.body().getMsg().contains("성공")) {
                            // 다시 리스트 초기화
                            callNetLectureList();
                        }
                        btn_lecture_info.setEnabled(true);
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
