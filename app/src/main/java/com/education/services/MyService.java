package com.education.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService  extends Service {

    private static final String TAG = MyService.class.getSimpleName();
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service created");
        mediaPlayer = MediaPlayer.create(this, R.raw.ringtone); // Place your MP3 file in the res/raw folder
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");
        mediaPlayer.start(); // Start playing the song
        return START_STICKY; // Service will be restarted if it gets killed
    }

    @Override
    public IBinder onBind(Intent intent) {
        // This is a started service, so we don't need to implement onBind
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic(); // Stop playing the song
        Log.d(TAG, "Service destroyed");
    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}