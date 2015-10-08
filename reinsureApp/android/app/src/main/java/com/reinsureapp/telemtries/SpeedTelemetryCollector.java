package com.reinsureapp.telemtries;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SpeedTelemetryCollector {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public Observable<Integer> start() {
        final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        final Object[] devices = btAdapter.getBondedDevices().toArray();

        BluetoothSocket socket = null;
        try {
            socket = ((BluetoothDevice)devices[0]).createRfcommSocketToServiceRecord(MY_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final BluetoothSocket finalSocket = socket;
        return Observable.interval(1, TimeUnit.SECONDS).map(new Func1<Long, Integer>() {
            @Override
            public Integer call(Long aLong) {
                try {
                    new ObdResetCommand().run(finalSocket.getInputStream(), finalSocket.getOutputStream());
                    new EchoOffCommand().run(finalSocket.getInputStream(), finalSocket.getOutputStream());

                    SpeedCommand speedCommand = new SpeedCommand();
                    speedCommand.run(finalSocket.getInputStream(), finalSocket.getOutputStream());
                    int speed = speedCommand.getMetricSpeed();
                    return speed;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).subscribeOn(Schedulers.io());
    }
}