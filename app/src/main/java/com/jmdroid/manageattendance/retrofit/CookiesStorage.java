package com.jmdroid.manageattendance.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class CookiesStorage {
    // 저장소 메인키

    private static CookiesStorage ourInstance = new CookiesStorage();

    public static CookiesStorage getInstance() {
        return ourInstance;
    }

    private CookiesStorage() {
    }

    public static final String STORAGE_KEY = "pref";
    public static final String SESSION_KEY = "cookies";
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    // 세션 유지를 위해 쿠키 집합을 보관 =============================================================
    public void setSet(String key, Set<String> value) {
        // 저장소 획득
        SharedPreferences.Editor editor = context.getSharedPreferences(STORAGE_KEY, 0).edit();
        // 저장
        Log.i("RF", "저장쿠키[" + value.toString() + "]");
        editor.putStringSet(key, value);
        // 커밋
        editor.commit();
    }

    public Set<String> getSet(String key) {
        Set<String> s = context.getSharedPreferences(STORAGE_KEY, 0).getStringSet(key, new HashSet<String>());
        Log.i("RF", "획득쿠키[" + s.toString() + "]");
        return s;
    }
    // ============================================================================================
}













