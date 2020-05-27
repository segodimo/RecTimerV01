package com.rec.timerv01.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RecBroadcastReceiver extends BroadcastReceiver {

    private WorkManager workManager;
    @Override
    public void onReceive(Context context, Intent intent) {

        /*============================================================*/
        int alertTime = intent.getExtras().getInt("alertTime");
        String tag = intent.getExtras().getString("tag");
        String tit = intent.getExtras().getString("tit");
        String txt = intent.getExtras().getString("txt");
        int diffA = intent.getExtras().getInt("diffA");
        int intrv = intent.getExtras().getInt("intrv");
        String state = intent.getExtras().getString("state");
        Log.e("AVA", ">>> Funcionando BroadcastReceiver!!! "+ state + " tag "+ tag);
        /*============================================================*/
        // SEND DADOS PRO WORKMANAGER
        Data data = new Data.Builder()
                .putString("tit",tit)
                .putString("txt",txt)
                .putInt("diffA", diffA)
                .putInt("intrv", intrv)
                .build();
        /*============================================================*/
        final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WorkManagerRecTimer.class)
                .setInputData(data)
                .setInitialDelay(alertTime, TimeUnit.MILLISECONDS)
                //.setInitialDelay(5000, TimeUnit.MILLISECONDS)
                .addTag(tag)
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);
        /*=============================================================*/
        if(state.equals("false")){
            WorkManager.getInstance(context).cancelAllWorkByTag(tag);
            //Intent intentAlarm = new Intent(context, AlarmService.class);
            //stopService(intentAlarm);
        }
        //Toast.makeText(EditForm.this, "Alarma Eliminada", Toast.LENGTH_SHORT).show();
        //-----------------------------------------------------------------------------------

        /*=============================================================*/
        // RECEIVE DADOS DO WORKMANAGER
//        WorkManager.getInstance(context).getWorkInfoByIdLiveData(workRequest.getId()).observe(EditForm.this, new Observer<WorkInfo>() {
//            @Override
//            public void onChanged(@Nullable WorkInfo workInfo) {
//                if(workInfo  != null ){
//                    if(workInfo.getState().isFinished()){
//                        Data data = workInfo.getOutputData();
//                        String outputdata = data.getString(WorkManagerRecTimer.RECEIVE_DADO);
//                        Log.d("TAGNAME", "FINALISOU DOWORK");
//                        //Toast.makeText(EditForm.this, "Chegou!! "+outputdata, Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
        /*============================================================*/






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
