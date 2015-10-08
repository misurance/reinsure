package com.reinsureapp.telemtries;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.reinsureapp.domain.GpsLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class PositionUpdateTelemetrySender {

    private Context mContext;
    private LocationListener mLocationListener;
    private PublishSubject<GpsLocation> mPositions = PublishSubject.create();

    public PositionUpdateTelemetrySender(Context context) {
        mContext = context;
    }

    public Observable<GpsLocation> start() {

        return Observable.interval(5, TimeUnit.SECONDS).map(new Func1<Long, GpsLocation>() {
            @Override
            public GpsLocation call(Long aLong) {
                return new GpsLocation(10, 10);
            }
        });
//        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
//
//        mLocationListener  = new LocationListener() {
//            public void onLocationChanged(Location location) {
//                mPositions.onNext(new GpsLocation(location.getLongitude(), location.getLatitude()));
//                Log.i("reinsure", "position:" + location.toString());
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//            public void onProviderEnabled(String provider) {}
//
//            public void onProviderDisabled(String provider) {}
//        };
//
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
//        return mPositions;
    }

    public void stop() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(mLocationListener);
    }
}
