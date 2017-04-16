package com.jmdroid.manageattendance.dto;

/**
 * Created by jimin on 2017. 4. 16..
 */

public class beaconListDTO {
    double distance;
    String uuid;
    String major;
    String minor;

    public beaconListDTO() {
    }

    public beaconListDTO(double distance, String uuid, String major, String minor) {

        this.distance = distance;
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
    }

    public double getDistance() {

        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    @Override
    public String toString() {
        return "beaconListDTO{" +
                "distance=" + distance +
                ", uuid='" + uuid + '\'' +
                ", major='" + major + '\'' +
                ", minor='" + minor + '\'' +
                '}';
    }
}
