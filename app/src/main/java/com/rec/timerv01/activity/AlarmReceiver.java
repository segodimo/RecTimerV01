package com.rec.timerv01.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAGNAME", "CHEGANDO AVAVAVAVAVAVAVAVAVAVA !!! ");

        Intent intentAS = new Intent(context, AlarmService.class);
        context.startService(intentAS);
    }
}
