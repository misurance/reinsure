package com.reinsureapp.telemtries;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.ObdResetCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.enums.ObdProtocols;
import com.reinsureapp.RetryWithDelay;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SpeedTelemetryCollector {

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public Observable<Integer> start() {
        return Observable.create(new Observable.OnSubscribe<BluetoothSocket>() {
            @Override
            public void call(Subscriber<? super BluetoothSocket> subscriber) {
                final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                final Object[] devices = btAdapter.getBondedDevices().toArray();
                BluetoothSocket socket;
                try {
                    socket = ((BluetoothDevice) devices[0]).createRfcommSocketToServiceRecord(MY_UUID);
                    socket.connect();
                    subscriber.onNext(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        })
        .flatMap(new Func1<BluetoothSocket, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(final BluetoothSocket bluetoothSocket) {
                return Observable.interval(1, TimeUnit.SECONDS).map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long aLong) {
                        try {
                            new ObdResetCommand().run(bluetoothSocket.getInputStream(), bluetoothSocket.getOutputStream());
                            new EchoOffCommand().run(bluetoothSocket.getInputStream(), bluetoothSocket.getOutputStream());
                            SpeedCommand speedCommand = new SpeedCommand();
                            speedCommand.run(bluetoothSocket.getInputStream(), bluetoothSocket.getOutputStream());
                            int speed = speedCommand.getMetricSpeed();
                            return speed;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
            }
        }).retryWhen(new RetryWithDelay(3, 2000));
    }
}