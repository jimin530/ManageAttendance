package com.jmdroid.manageattendance.ui;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
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
import com.jmdroid.manageattendance.RecoActivity;
import com.jmdroid.manageattendance.Util.TimeUtil;
import com.jmdroid.manageattendance.accout.AccountManage;
import com.jmdroid.manageattendance.beacon.BeaconManage;
import com.jmdroid.manageattendance.dto.ReqChangeStateDTO;
import com.jmdroid.manageattendance.dto.ReqLectureListDTO;
import com.jmdroid.manageattendance.network.reqmodel.ReqChangeState;
import com.jmdroid.manageattendance.network.reqmodel.ReqHeader;
import com.jmdroid.manageattendance.network.reqmodel.ReqLectureList;
import com.jmdroid.manageattendance.network.resmodel.ResBasic;
import com.jmdroid.manageattendance.network.resmodel.ResLectureList;
import com.jmdroid.manageattendance.retrofit.RetrofitGenterator;
import com.perples.recosdk.RECOBeacon;
import com.perples.recosdk.RECOBeaconRegion;
import com.perples.recosdk.RECOErrorCode;
import com.perples.recosdk.RECORangingListener;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends RecoActivity implements RECORangingListener {
    ArrayList<RECOBeacon> mRangedBeacons;
    //This is a default proximity uuid of the RECO
    public static final String RECO_UUID = "24DDF411-8CF1-440C-87CD-E368DAF9C93E";
    public static final boolean SCAN_RECO_ONLY = true;
    public static final boolean ENABLE_BACKGROUND_RANGING_TIMEOUT = true;
    public static final boolean DISCONTINUOUS_SCAN = false;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_LOCATION = 10;

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;

    private View mLayout;

    /*
     * UI
     */
    // 스크롤뷰
    NestedScrollView nsv_main;
    // 리싸이클러뷰
    RecyclerView rv_lecture;
    RecyclerView rv_beacon;
    // 내 이름
    TextView tv_student_name;

    /*
     * 어댑터 및 매니저
     */
    // 리싸이클럽 어댑터
    LectureAdapter lectureAdapter;
    BeaconAdapter beaconAdapter;
    // 리싸이클러뷰 레아이웃 매니저
    LinearLayoutManager lectureLinearLayoutManager;
    LinearLayoutManager lectureLinearLayoutManager2;

    /*
     * 기타
     */
    // 리싸이클러뷰 아이템 리스트
    ResLectureList resLectureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //If a user device turns off bluetooth, request to turn it on.
        //사용자가 블루투스를 켜도록 요청합니다.
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is not granted.");
                this.requestLocationPermission();
            } else {
                Log.i("MainActivity", "The location permission (ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION) is already granted.");
            }
        }

        mRecoManager.setRangingListener(this);
        mRecoManager.bind(this);

        nsv_main = (NestedScrollView) findViewById(R.id.nsv_main);
        rv_lecture = (RecyclerView) findViewById(R.id.rv_lecture);
        rv_beacon = (RecyclerView) findViewById(R.id.rv_beacon);
        tv_student_name = (TextView) findViewById(R.id.tv_student_name);
        // 로그인 사용자 이름 표시
        tv_student_name.setText(AccountManage.getInstance().student_name + " 님");

        lectureAdapter = new LectureAdapter();
        beaconAdapter = new BeaconAdapter();
        // 리니어레이아웃 매니저
        lectureLinearLayoutManager = new LinearLayoutManager(this);
        // 버티컬 정렬 설정
        lectureLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rv_lecture.setLayoutManager(lectureLinearLayoutManager);
        // 스크롤뷰 부드럽게
        rv_lecture.setNestedScrollingEnabled(false);

        // 리니어레이아웃 매니저
        lectureLinearLayoutManager2 = new LinearLayoutManager(this);
        // 버티컬 정렬 설정
        lectureLinearLayoutManager2.setOrientation(OrientationHelper.VERTICAL);
        rv_beacon.setLayoutManager(lectureLinearLayoutManager2);
        // 스크롤뷰 부드럽게
        rv_beacon.setNestedScrollingEnabled(false);

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
                    resLectureList.getBody().get(position).getAtt_state(),
                    resLectureList.getBody().get(position).getLecture_code()
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

        public void bindOnPost(final int position, final String lecture_name, String teacher_name, final String lecture_time, String att_state, final String lecture_code) {
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
                    // 제일 먼저 비콘이 있는지 검사
                    if (BeaconManage.getInstance().isNear(mRangedBeacons, lecture_code)) {
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
                    } else {
                        Toast.makeText(HomeActivity.this, "강의실에서 진행해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    ////
    // 리싸이클러뷰 어댑터
    class BeaconAdapter extends RecyclerView.Adapter<BPostHolder> {

        @Override
        public BPostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_beacon, parent, false);
            return new BPostHolder(itemView);
        }

        @Override
        public void onBindViewHolder(BPostHolder holder, int position) {
            holder.bindOnPost(
                    position,
                    mRangedBeacons.get(position).getMajor() + "/"
                            + mRangedBeacons.get(position).getMinor()
            );
        }

        @Override
        public int getItemCount() {
            return mRangedBeacons.size();
        }
    }

    // 홀더
    public class BPostHolder extends RecyclerView.ViewHolder {

        TextView tv_beacon_id;

        // 뷰로부터 컴포넌트를 획득
        public BPostHolder(View itemView) {
            super(itemView);
            tv_beacon_id = (TextView) itemView.findViewById(R.id.tv_beacon_id);
        }

        public void bindOnPost(final int position, final String id) {
            if (id.equals("501/24853")) {
                tv_beacon_id.setText("국어");
            } else if (id.equals("501/24854")) {
                tv_beacon_id.setText("수학");
            } else if (id.equals("501/24861")) {
                tv_beacon_id.setText("영어");
            }
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

    @Override
    public void onServiceConnect() {
        Log.i("RECORangingActivity", "onServiceConnect()");
        mRecoManager.setDiscontinuousScan(HomeActivity.DISCONTINUOUS_SCAN);
        this.start(mRegions);
        //Write the code when RECOBeaconManager is bound to RECOBeaconService
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<RECOBeacon> recoBeacons, RECOBeaconRegion recoRegion) {
        //Log.i("RECORangingActivity", "didRangeBeaconsInRegion() region: " + recoRegion.getUniqueIdentifier() + ", number of beacons ranged: " + recoBeacons.size());
//        mRangingListAdapter.updateAllBeacons(recoBeacons);
//        mRangingListAdapter.notifyDataSetChanged();
        //Write the code when the beacons in the region is received
        synchronized (recoBeacons) {
            mRangedBeacons = new ArrayList<RECOBeacon>(recoBeacons);
        }

        //Log.i("확인", mRangedBeacons.toString());
        //Toast.makeText(this, mRangedBeacons.size() + "", Toast.LENGTH_SHORT).show();
//        for (int i = 0; i < mRangedBeacons.size(); i++) {
//            //Toast.makeText(this, mRangedBeacons.get(i).getMinor() + "", Toast.LENGTH_SHORT).show();
//            //Log.i("확인", mRangedBeacons.get(i).getMinor()+"");
//        }
        rv_beacon.setAdapter(beaconAdapter);
    }

    @Override
    protected void start(ArrayList<RECOBeaconRegion> regions) {

        /**
         * There is a known android bug that some android devices scan BLE devices only once. (link: http://code.google.com/p/android/issues/detail?id=65863)
         * To resolve the bug in our SDK, you can use setDiscontinuousScan() method of the RECOBeaconManager.
         * This method is to set whether the device scans BLE devices continuously or discontinuously.
         * The default is set as FALSE. Please set TRUE only for specific devices.
         *
         * mRecoManager.setDiscontinuousScan(true);
         */

        for (RECOBeaconRegion region : regions) {
            try {
                mRecoManager.startRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void stop(ArrayList<RECOBeaconRegion> regions) {
        for (RECOBeaconRegion region : regions) {
            try {
                mRecoManager.stopRangingBeaconsInRegion(region);
            } catch (RemoteException e) {
                Log.i("RECORangingActivity", "Remote Exception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.i("RECORangingActivity", "Null Pointer Exception");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceFail(RECOErrorCode errorCode) {
        //Write the code when the RECOBeaconService is failed.
        //See the RECOErrorCode in the documents.
        return;
    }

    @Override
    public void rangingBeaconsDidFailForRegion(RECOBeaconRegion region, RECOErrorCode errorCode) {
        Log.i("RECORangingActivity", "error code = " + errorCode);
        //Write the code when the RECOBeaconService is failed to range beacons in the region.
        //See the RECOErrorCode in the documents.
        return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.stop(mRegions);
        this.unbind();
    }

    private void unbind() {
        try {
            mRecoManager.unbind();
        } catch (RemoteException e) {
            Log.i("RECORangingActivity", "Remote Exception");
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            //If the request to turn on bluetooth is denied, the app will be finished.
            //사용자가 블루투스 요청을 허용하지 않았을 경우, 어플리케이션은 종료됩니다.
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Snackbar.make(mLayout, "Location permission has been granted. RECO SDK can now work properly.", Snackbar.LENGTH_LONG).show();
                } else {
                    //Snackbar.make(mLayout, "Location permission for this application is denied. RECO SDK may not work properly.", Snackbar.LENGTH_LONG).show();
                }
            }
            default:
                break;
        }
    }

    private void requestLocationPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
            return;
        }
    }
}
