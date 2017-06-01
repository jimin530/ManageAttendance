package com.jmdroid.manageattendance.beacon;

import com.perples.recosdk.RECOBeacon;

import java.util.ArrayList;

public class BeaconManage {
    private static BeaconManage ourInstance = new BeaconManage();

    public static BeaconManage getInstance() {
        return ourInstance;
    }

    private BeaconManage() {
    }

    public boolean isNear(ArrayList<RECOBeacon> mRangedBeacons, String lecture_code) {

        try {
            for (int i = 0; i < mRangedBeacons.size(); i++) {
                if ((mRangedBeacons.get(i).getMajor() + "/" + mRangedBeacons.get(i).getMinor()).equals(lecture_code)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }
}

















