package com.rec.timerv01.activity;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkManagerRecTimer extends Worker {
    public WorkManagerRecTimer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        for (int i=10; i > 0; i-- ){
            Log.d("TAGNAME", "doWork: was " + i);
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
                return  Result.failure();
            }
        }

        return Result.success();
    }
}
