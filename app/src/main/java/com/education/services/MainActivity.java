package com.education.services;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String TAG=MainActivity.class.getSimpleName();
    private MyBoundService myBoundService;
    private boolean isBound = false;
    Timer t = new Timer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, MyBoundService.class);
        startService(intent);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyBoundService.MyBinder binder = (MyBoundService.MyBinder) iBinder;
            myBoundService = binder.getService();
            isBound = true;
            performServiceOperation();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    // ...

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyBoundService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    // Use myBoundService to call service methods
    private void performServiceOperation() {
        if (isBound) {
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int result = myBoundService.getRandomNumber();
                    // Do something with the result
                    Log.d(TAG, "Random Number: "+result);
                }
            },200,200);
//            t.scheduleAtFixedRate(
//                    new TimerTask()
//                    {
//                        public void run()
//                        {
//                            int result = myBoundService.getRandomNumber();
//                            // Do something with the result
//                            Log.d(TAG, "performServiceOperation: "+result);
//                        }
//                    },
//                    0,      // run first occurrence immediatetly
//                    2000)); // run every two seconds


        }
    }
}