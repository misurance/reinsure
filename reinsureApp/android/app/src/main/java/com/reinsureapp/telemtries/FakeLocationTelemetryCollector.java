package com.reinsureapp.telemtries;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.reinsureapp.domain.GpsLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class FakeLocationTelemetryCollector {

    private AtomicInteger mCurrentSpeed = new AtomicInteger(0);

    public FakeLocationTelemetryCollector() {

    }
    
    public Double[][] getFakeData()
    {
        return new Double[][]{
                        new Double[]{32.06564,34.77591},
                new Double[]{32.06612,34.77639},
                new Double[]{32.0662,34.77645},new Double[]{32.06687,34.77677},new Double[]{32.06804,34.77714},new Double[]{32.06983,34.77769},new Double[]{32.07161,34.77822},new Double[]{32.07227,34.77844},new Double[]{32.07242,34.77856},new Double[]{32.07321,34.77711},new Double[]{32.07328,34.77698},new Double[]{32.07358,34.77643},new Double[]{32.07402,34.77549},new Double[]{32.07414,34.77519},new Double[]{32.07464,34.77422},new Double[]{32.0752,34.77305},new Double[]{32.07589,34.77174},new Double[]{32.07617,34.77122},new Double[]{32.07645,34.77069},new Double[]{32.0769,34.76988},new Double[]{32.07763,34.76843},new Double[]{32.07807,34.76755},new Double[]{32.07817,34.76759},new Double[]{32.07849,34.76769},new Double[]{32.07918,34.76791},new Double[]{32.07995,34.76825},new Double[]{32.0806,34.76846},new Double[]{32.08135,34.76841},new Double[]{32.08174,34.76843},new Double[]{32.08218,34.76852},new Double[]{32.08228,34.76856},new Double[]{32.08274,34.76884},new Double[]{32.08295,34.76899},new Double[]{32.08313,34.76908},new Double[]{32.0839,34.76936},new Double[]{32.08477,34.76967},new Double[]{32.08541,34.76976},new Double[]{32.08559,34.76977},new Double[]{32.08572,34.76977},new Double[]{32.08592,34.76982}
                };

    }

    public Observable<GpsLocation> start() {
        return Observable.from(getFakeData()).concatMap(new Func1<Double[], Observable<? extends GpsLocation>>() {
            @Override
            public Observable<? extends GpsLocation> call(final Double[] doubles) {
                return Observable.timer((300 - 100)/2, TimeUnit.MILLISECONDS)
                        .map(new Func1<Long, GpsLocation>() {
                            @Override
                            public GpsLocation call(Long aLong) {
                                return new GpsLocation(doubles[1], doubles[0]);
                            }
                        });
            }
        }).concatWith(Observable.defer(new Func0<Observable<GpsLocation>>() {
            @Override
            public Observable<GpsLocation> call() {
                return start();
            }
        }));
    }
}
