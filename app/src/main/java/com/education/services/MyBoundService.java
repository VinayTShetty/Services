package com.education.services;



import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class MyBoundService extends Service {
    private final IBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        MyBoundService getService() {
            return MyBoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }



    public int getRandomNumber() {
        // Generate a random number and print it
        int randomNumber = new Random().nextInt(100);
    //    Log.d(TAG, "getRandomNumber: " + randomNumber);
        return randomNumber;
    }
}
