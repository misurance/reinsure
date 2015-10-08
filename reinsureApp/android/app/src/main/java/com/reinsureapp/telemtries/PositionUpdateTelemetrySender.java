package com.reinsureapp.telemtries;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.reinsureapp.domain.GpsLocation;

import rx.Observable;
import rx.subjects.PublishSubject;

public class PositionUpdateTelemetrySender {

    private Context mContext;
    private LocationListener mLocationListener;
    private PublishSubject<GpsLocation> mPositions = PublishSubject.create();

    public PositionUpdateTelemetrySender(Context context) {
        mContext = context;
    }

    public Observable<GpsLocation> start() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        mLocationListener  = new LocationListener() {
            public void onLocationChanged(Location location) {
                mPositions.onNext(new GpsLocation(location.getLongitude(), location.getLatitude()));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        return mPositions;
    }

    public void stop() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(mLocationListener);
    }
}
