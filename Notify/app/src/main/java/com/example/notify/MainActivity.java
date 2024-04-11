package com.example.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateAirMode();
            }
        };
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_CHANGED);
        registerReceiver(broadcastReceiver,intentFilter);

    }

    private void updateAirMode() {
        boolean isAirModeOn = isAirModeOn();
        NotificationChannel channel = null;
        NotificationManager notificationManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        channel = new NotificationChannel(
                "Test_Channel",
                "test",
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

        Notification notification = new NotificationCompat.Builder(this,"Test_Channel")
                .setContentText("Timezonechange")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        /*
        if(isAirModeOn) {
            notification = new NotificationCompat.Builder(this,"Test_Channel")
                    .setContentText("Air mode On")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
        }
        else {
            notification = new NotificationCompat.Builder(this,"Test_Channel")
                    .setContentText("Air mode OFF")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
        }

         */
        notificationManager.notify(1,notification);
    }

    private boolean isAirModeOn(){

        return Settings.Global.getInt(getContentResolver(),Settings.Global.AIRPLANE_MODE_ON,1) != 0;
    }
}