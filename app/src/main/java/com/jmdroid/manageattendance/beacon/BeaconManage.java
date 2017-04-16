package com.jmdroid.manageattendance.beacon;

import com.jmdroid.manageattendance.dto.beaconListDTO;

import java.util.ArrayList;

public class BeaconManage {
    private static BeaconManage ourInstance = new BeaconManage();

    public static BeaconManage getInstance() {
        return ourInstance;
    }

    private BeaconManage() {
    }

    ArrayList<beaconListDTO> beaconList = new ArrayList<beaconListDTO>();
}

















