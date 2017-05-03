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

    public boolean isNear(ArrayList<RECOBeacon> mRangedBeacons, String lecture_name) {
        String lecture = "";
        if (lecture_name.equals("국어")) {
            lecture = "501/24853";
        } else if (lecture_name.equals("수학")) {
            lecture = "501/24854";
        } else if (lecture_name.equals("영어")) {
            lecture = "501/24861";
        }

        for (int i = 0; i < mRangedBeacons.size(); i++) {
            if ((mRangedBeacons.get(i).getMajor() + "/" + mRangedBeacons.get(i).getMinor()).equals(lecture)) {
                return true;
            }
        }

        return false;
    }
}

















