package com.jmdroid.manageattendance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jmdroid.manageattendance.R;
import com.jmdroid.manageattendance.accout.AccountManage;
import com.jmdroid.manageattendance.dto.LoginDTO;
import com.jmdroid.manageattendance.network.reqmodel.ReqHeader;
import com.jmdroid.manageattendance.network.reqmodel.ReqLogin;
import com.jmdroid.manageattendance.network.reqmodel.ReqMyInfo;
import com.jmdroid.manageattendance.network.resmodel.ResBasic;
import com.jmdroid.manageattendance.network.resmodel.ResMyInfo;
import com.jmdroid.manageattendance.retrofit.RetrofitGenterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText et_login_id;
    EditText et_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_login_id = (EditText) findViewById(R.id.et_login_id);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
    }

    public void onLogin(View view) {
        ReqHeader reqHeader = new ReqHeader(
                "Login"
        );
        LoginDTO loginDTO = new LoginDTO(
                et_login_id.getText().toString(),
                et_login_password.getText().toString()
        );
        ReqLogin reqLogin = new ReqLogin(reqHeader, loginDTO);

        callNetLogin(reqLogin);
    }

    public void onClickGoSignup(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    public void callNetLogin(ReqLogin reqLogin) {
        Call<ResBasic> NetLogin = RetrofitGenterator.getInstance().getRetrofitImpFactory().NetLogin(reqLogin);
        NetLogin.enqueue(new Callback<ResBasic>() {
            @Override
            public void onResponse(Call<ResBasic> call, Response<ResBasic> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getMsg() != null) {
                        // 통신 성공
                        Log.i("RES SUC", response.body().getMsg());
                        if (response.body().getMsg().contains("성공")) {
                            AccountManage.getInstance().student_id = et_login_id.getText().toString();

                            // 내 정보 조회
                            callNetMyInfo();
                        }
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

    public void callNetMyInfo() {

        ReqHeader reqHeader = new ReqHeader(
                "MyInfo"
        );
        ReqMyInfo reqMyInfo = new ReqMyInfo(reqHeader, AccountManage.getInstance().student_id);

        Call<ResMyInfo> NetMyInfo = RetrofitGenterator.getInstance().getRetrofitImpFactory().NetMyInfo(reqMyInfo);
        NetMyInfo.enqueue(new Callback<ResMyInfo>() {
            @Override
            public void onResponse(Call<ResMyInfo> call, Response<ResMyInfo> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getBody() != null) {
                        // 통신 성공
                        Log.i("RES SUC", response.body().getBody().toString());
                        AccountManage.getInstance().student_name = response.body().getBody().get(0).getStudent_name().toString();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
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
            public void onFailure(Call<ResMyInfo> call, Throwable t) {
                // 통신 실패
                Log.i("RES FAIL", t.getMessage().toString());
            }
        });
    }
}
