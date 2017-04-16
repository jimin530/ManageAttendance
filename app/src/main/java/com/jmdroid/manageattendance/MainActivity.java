package com.jmdroid.manageattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jmdroid.manageattendance.accout.AccountManage;
import com.jmdroid.manageattendance.retrofit.RetrofitGenterator;
import com.jmdroid.manageattendance.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 레트로핏 초기 설정
        RetrofitGenterator.getInstance().launch_retrofit(getApplicationContext());

        // 로그인 아이디 초기 null
        AccountManage.getInstance().student_id = "";

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
