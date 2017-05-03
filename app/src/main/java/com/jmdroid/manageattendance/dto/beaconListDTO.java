package com.jmdroid.manageattendance.dto;

/**
 * Created by jimin on 2017. 4. 16..
 */

public class beaconListDTO {
    double distance;
    String id;

    public beaconListDTO() {
    }

    public beaconListDTO(double distance, String id) {

        this.distance = distance;
        this.id = id;
    }

    public double getDistance() {

        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "beaconListDTO{" +
                "distance=" + distance +
                ", id='" + id + '\'' +
                '}';
    }
}
