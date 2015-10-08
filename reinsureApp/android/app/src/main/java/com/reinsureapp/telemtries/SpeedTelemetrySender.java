package com.reinsureapp.telemtries;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

public class SpeedTelemetrySender {

    public Observable<Integer> start() {
        return Observable.interval(5, TimeUnit.SECONDS).map(new Func1<Long, Integer>() {
            @Override
            public Integer call(Long aLong) {
                return 50;
            }
        });
    }
}
