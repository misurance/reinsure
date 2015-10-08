package com.reinsureapp.telemtries;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

public class SpeedTelemetryCollector {

    public Observable<Integer> start() {
        return Observable.interval(1, TimeUnit.SECONDS).map(new Func1<Long, Integer>() {
            @Override
            public Integer call(Long aLong) {
                return 50;
            }
        });
    }
}
