package com.rec.timerv01.activity;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkManagerRecTimer extends Worker {

    public static final String RECEIVE_DADO = "receive_dado";

    public WorkManagerRecTimer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.d("TAGNAME", "doWork");

//        for (int i=3; i > 0; i-- ){
//            Log.d("TAGNAME", "i = " + i);
//            try{
//                Thread.sleep(1000);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//                return  Result.failure();
//            }
//        }

        Data data1 = new Data.Builder()
                .putString(RECEIVE_DADO, "TXTXTXTXTTX")
                .build();

        //return Result.success();
        return Result.success(data1);
    }
}
