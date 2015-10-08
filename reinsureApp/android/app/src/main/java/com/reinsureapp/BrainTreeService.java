package com.reinsureapp;

import android.content.Intent;

import com.braintreepayments.api.dropin.BraintreePaymentActivity;

public class BrainTreeService {

    private MainActivity mMainActivity;
    private boolean mWasCalled = false;

    public BrainTreeService(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    public void startBraintreeDropIn() {
        if (mWasCalled) return;
        Intent intent = new Intent(mMainActivity, BraintreePaymentActivity.class);
        intent.putExtra(BraintreePaymentActivity.EXTRA_CLIENT_TOKEN, mMainActivity.clientToken);
        mMainActivity.startActivityForResult(intent, 12345);
        mWasCalled = true;
    }
}
