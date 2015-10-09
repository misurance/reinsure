package com.reinsureapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.braintreepayments.api.dropin.BraintreePaymentActivity;
import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.reinsureapp.domain.GpsLocation;
import com.reinsureapp.telemtries.FakeLocationTelemetryCollector;
import com.reinsureapp.telemtries.PositionUpdateTelemetryCollector;
import com.reinsureapp.telemtries.SpeedTelemetryCollector;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import remote.android.react.ReactAndroidBridgePackage;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;
    private Socket mSocket;

    public String clientToken;
    public String userId = "Asaf";

    public BrainTreeService brainTreeService = new BrainTreeService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView = new ReactRootView(this);

        Map<String, Object> services = new HashMap<>();
        services.put("braintree", brainTreeService);
        services.put("feed", new FeedService(this, userId));

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new ReactAndroidBridgePackage(services))
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        mReactRootView.startReactApplication(mReactInstanceManager, "reinsureApp", null);

        setContentView(mReactRootView);

        //get token for braintree
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://misurance.herokuapp.com/api/client_token", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                String t = "";
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MainActivity.this.clientToken = responseString;
                Log.i("MainActivity", "Got token - " + responseString);
            }
        });

        //final Observable<GpsLocation> locations = new PositionUpdateTelemetryCollector(MainActivity.this).start();
        final Observable<Integer> speeds = new SpeedTelemetryCollector().start();
        final Observable<GpsLocation> locations = new FakeLocationTelemetryCollector().start();


        //connect to server socket.io
        try {
            Log.i("MainActivity", "connecting to socket.io");
            mSocket = IO.socket("http://misurance.herokuapp.com");
            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        final boolean[] firstTime = {true};
                        Log.i("MainActivity", "connected to socket.io!");
                        Observable.combineLatest(speeds, locations.startWith(new GpsLocation()), new Func2<Integer, GpsLocation, Object>() {
                            @Override
                            public Object call(Integer speed, GpsLocation location) {
                                if (firstTime[0]) {
                                    mSocket.emit("start driving", userId, UUID.randomUUID().toString());
                                    firstTime[0] = false;
                                }
                                Log.i("MainActivity", "position update: " + speed + ", " + location.latitude + ", " + location.longitude);
                                if (location.isEmpty)
                                    return null;

                                mSocket.emit("position update", new Date(), speed, new Gson().toJson(location));
                                return null;
                            }
                        }).subscribeOn(Schedulers.io()).subscribe();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12345) {
            switch (resultCode) {
                case BraintreePaymentActivity.RESULT_OK:
                    String paymentMethodNonce = data
                            .getStringExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);
                    postNonceToServer(paymentMethodNonce);
                    break;
                case BraintreePaymentActivity.BRAINTREE_RESULT_DEVELOPER_ERROR:
                case BraintreePaymentActivity.BRAINTREE_RESULT_SERVER_ERROR:
                case BraintreePaymentActivity.BRAINTREE_RESULT_SERVER_UNAVAILABLE:
                    // handle errors here, a throwable may be available in
                    // data.getSerializableExtra(BraintreePaymentActivity.EXTRA_ERROR_MESSAGE)
                    break;
                default:
                    break;
            }
        }
    }

    void postNonceToServer(String nonce) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("nonce", nonce);
        client.post("https://misurance.herokuapp.com/api/create_customer", params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                    // Your implementation here
                }
        );
        brainTreeService.paymentRegistrationCompleted();
    }
}
