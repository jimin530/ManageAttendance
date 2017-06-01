package com.jmdroid.manageattendance.accout;

public class AccountManage {
    private static AccountManage ourInstance = new AccountManage();

    public static AccountManage getInstance() {
        return ourInstance;
    }

    private AccountManage() {
    }

    public String student_id;
    public String student_name;
}

















