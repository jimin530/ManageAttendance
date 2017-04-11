package com.jmdroid.manageattendance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jmdroid.manageattendance.R;
import com.jmdroid.manageattendance.dto.SignupDTO;
import com.jmdroid.manageattendance.network.reqmodel.ReqHeader;
import com.jmdroid.manageattendance.network.reqmodel.ReqSignup;
import com.jmdroid.manageattendance.network.resmodel.ResBasic;
import com.jmdroid.manageattendance.retrofit.RetrofitGenterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText et_signup_id;
    EditText et_signup_name;
    EditText et_signup_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_signup_id = (EditText) findViewById(R.id.et_signup_id);
        et_signup_name = (EditText) findViewById(R.id.et_signup_name);
        et_signup_password = (EditText) findViewById(R.id.et_signup_password);
    }

    public void onClickSignup(View view) {
        ReqHeader reqHeader = new ReqHeader(
                "Signup"
        );
        SignupDTO signupDTO = new SignupDTO(
                et_signup_id.getText().toString(),
                et_signup_name.getText().toString(),
                et_signup_password.getText().toString()
        );
        ReqSignup reqSignup = new ReqSignup(reqHeader, signupDTO);

        callNetSignup(reqSignup);
    }

    public void callNetSignup(ReqSignup reqSignup) {
        Call<ResBasic> NetSignup = RetrofitGenterator.getInstance().getRetrofitImpFactory().NetSignup(reqSignup);
        NetSignup.enqueue(new Callback<ResBasic>() {
            @Override
            public void onResponse(Call<ResBasic> call, Response<ResBasic> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getMsg() != null) {
                        // 통신 성공
                        Log.i("RES SUC", response.body().getMsg());
                        if (response.body().getMsg().contains("성공")) {
                            Toast.makeText(SignupActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
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
}

