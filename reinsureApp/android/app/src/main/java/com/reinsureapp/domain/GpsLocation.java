package com.reinsureapp.domain;


public class GpsLocation {
    public boolean isEmpty = false;
    public Double longitude;
    public Double latitude;

    public GpsLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
