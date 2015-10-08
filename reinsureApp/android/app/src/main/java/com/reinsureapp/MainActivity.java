package com.reinsureapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.braintreepayments.api.Braintree;
import com.braintreepayments.api.dropin.BraintreePaymentActivity;
import com.braintreepayments.api.dropin.view.PaymentButton;
import com.braintreepayments.api.models.PaymentMethod;
import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;

import java.util.Collections;

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler, Braintree.PaymentMethodCreatedListener, Braintree.BraintreeSetupFinishedListener {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        mReactRootView.startReactApplication(mReactInstanceManager, "reinsureApp", null);

        setContentView(mReactRootView);

        //Braintree.setup(this, getIntent().getStringExtra(BraintreePaymentActivity.EXTRA_CLIENT_TOKEN), this);

//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get("https://your-server/client_token", new TextHttpResponseHandler() {
//            @Override
//            public void onSuccess(String clientToken) {
//                this.clientToken = clientToken;
//            }
//        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
      super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onResume(this);
        }
    }

    @Override
    public void onBraintreeSetupFinished(boolean setupSuccessful, Braintree braintree, String errorMessage, Exception exception) {
       String t = "";
    }




    @Override
    public void onPaymentMethodCreated(PaymentMethod paymentMethod) {

    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {

    }
}
