package com.jmdroid.manageattendance.accout;

public class AccountMange {
    private static AccountMange ourInstance = new AccountMange();
    public static AccountMange getInstance() {
        return ourInstance;
    }
    private AccountMange() {
    }

    public String student_id;
    public String student_name;
}

















