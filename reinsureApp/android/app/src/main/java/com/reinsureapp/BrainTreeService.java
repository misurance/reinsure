package com.reinsureapp;

import android.content.Intent;

import com.braintreepayments.api.dropin.BraintreePaymentActivity;

import rx.subjects.PublishSubject;

public class BrainTreeService {

    private MainActivity mMainActivity;
    private boolean mWasCalled = false;

    public PublishSubject<String> paymentRegistrationSubject;
    public BrainTreeService(MainActivity mainActivity) {
        mMainActivity = mainActivity;
        paymentRegistrationSubject = PublishSubject.create();
    }

    public void startBraintreeDropIn() {
        if (mWasCalled) return;
        paymentRegistrationSubject.onNext("start");
        Intent intent = new Intent(mMainActivity, BraintreePaymentActivity.class);
        intent.putExtra(BraintreePaymentActivity.EXTRA_CLIENT_TOKEN, mMainActivity.clientToken);
        mMainActivity.startActivityForResult(intent, 12345);
        mWasCalled = true;
    }

    public void paymentRegistrationCompleted(){
        paymentRegistrationSubject.onNext("completed");
    }

    public PublishSubject<String> paymentRegistration(){
        return paymentRegistrationSubject;
    }
}
