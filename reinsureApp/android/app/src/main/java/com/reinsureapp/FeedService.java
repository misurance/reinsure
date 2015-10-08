package com.reinsureapp;

import android.content.Context;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONObject;

import java.util.HashMap;

import rx.functions.Action0;
import rx.subjects.PublishSubject;

/**
 * Created by talarari on 10/8/15.
 */
public class FeedService {
    private PublishSubject<String> eventsSubject = PublishSubject.create();
    Firebase ref;
    ChildEventListener eventListener;
    public FeedService(Context context, String userId){
        Firebase.setAndroidContext(context);
        ref = new Firebase("https://intense-inferno-8553.firebaseio.com").child(userId).child("feed");
        eventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String,String> value = (HashMap<String,String>)dataSnapshot.getValue();
                String key = dataSnapshot.getKey();
                value.put("key", key);
                eventsSubject.onNext(new JSONObject(value).toString());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        };
    }

    public PublishSubject<String> stream(){
        return eventsSubject;
    }

    public void start(){
        ref.addChildEventListener(eventListener);

    }

    public void stop(){
        ref.addChildEventListener(eventListener);

    }





}
