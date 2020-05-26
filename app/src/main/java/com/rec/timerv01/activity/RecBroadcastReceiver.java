package com.rec.timerv01.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.UUID;

public class RecBroadcastReceiver extends BroadcastReceiver {

    private WorkManager workManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getExtras().getString("extra");
        Log.e("AVA", ">>> Funcionando BroadcastReceiver!!! "+ state);


//        if(state.equals("ASASAS")){
//            //workManager.getInstance(this, cancelAllWorkByTag("tag1"));
//
//            WorkRequest request = new OneTimeWorkRequest.Builder(WorkManagerRecTimer.class).build();
//            workManager.enqueue(request);
//            //workManager.cancelWorkById(request.getId());
//            //workManager.cancelAllWorkByTag("tag1");
//            workManager.cancelAllWork();
//
//        }
    }
}
