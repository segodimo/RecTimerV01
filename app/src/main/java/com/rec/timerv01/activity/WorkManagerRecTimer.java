package com.rec.timerv01.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.rec.timerv01.R;

import java.util.Locale;

public class WorkManagerRecTimer extends Worker {

    private TextToSpeech txtfala;

    public static final String RECEIVE_DADO = "receive_dado";

    public WorkManagerRecTimer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        txtfala = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) txtfala.setLanguage(Locale.getDefault());
            }
        });

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

        //displayNotification("TITULO", "txtxtxtxtxxtxtxtxt txtxtxtxtxxtxtxtxt txtxtxtxtxxtxtxtxt");

        falaAlarme();

        return Result.success(data1);
    }

    private void displayNotification(String task, String desc) {

        NotificationManager manager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1, builder.build());

        falaAlarme();

    }

    private  void falaAlarme(){
        String toSpeak = "Testando fala, 1, 2, 3,";
        txtfala.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        Log.d("TAGNAME", "Pasou por aquI!!");
    }
}
