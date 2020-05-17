package com.rec.timerv01.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
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

import java.util.Calendar;
import java.util.Locale;

public class WorkManagerRecTimer extends Worker {

    private TextToSpeech txtfala;
    private Intent intent;
    private Boolean trabalha = true;
    private Integer cnt = 0;

    public static final String RECEIVE_DADO = "receive_dado"; //SEND PARA DEVOLVER

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
        Log.d("TAGNAME", "INICIANDO DOWORK");
        // ------------------------------------------------------------------------------------------
        // RECEIVE DADOS DO EDITFORM
        String titulo = getInputData().getString("titulo");
        String detalhe = getInputData().getString("detalle");
        int diffA = getInputData().getInt("diffA", 0);
        int intrv = getInputData().getInt("intrv", 0);
        Log.d("TAGNAME", "diffA " + diffA);
        // ------------------------------------------------------------------------------------------
        while ( cnt<=diffA && trabalha){
            Log.d("TAGNAME", "doWork: was " + cnt);
            if (Calendar.getInstance().get(Calendar.SECOND) % intrv == 0) {
                falaAlarme(" "+ Calendar.getInstance().get(Calendar.MINUTE) +" minutos e "+ Calendar.getInstance().get(Calendar.SECOND)+" segundos");
            }
            try{ Thread.sleep(1000); }
            catch (InterruptedException e){
                e.printStackTrace();
                return  Result.failure();
            }
            cnt++;
        }
        // ------------------------------------------------------------------------------------------
        // SEND DADOS PRO EDITFORM
        Data data1 = new Data.Builder()
                .putString(RECEIVE_DADO, "Recevendo Dado Teste")
                .build();
        // ------------------------------------------------------------------------------------------
        //displayNotification("TITULO", "txtxtxtxtxxtxtxtxt txtxtxtxtxxtxtxtxt txtxtxtxtxxtxtxtxt", detalhe);
        falaAlarme("Taimer Finalizado");
        return Result.success(data1);
        // ------------------------------------------------------------------------------------------
    }


    private  void falaAlarme(String falatxt){
//        String toSpeak = "Testando fala, 1, 2, 3,"+detalhe;
//        txtfala.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//        Log.d("TAGNAME", "Pasou por aquI!!");

//        intent = new Intent(getApplicationContext(), FalaTTS.class);
//        intent.putExtra("titulo", "TXTXTXTXTXTX");
//        getApplicationContext().startService(intent);

        intent = new Intent(getApplicationContext(), FalaTTS.class);
        intent.putExtra("FALATXT", falatxt);
        getApplicationContext().startService(intent);
    }

    private void displayNotification(String task, String desc, String detalhe) {
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
        //falaAlarme(detalhe);
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.d("TAGNAME", "onStopped ");
        trabalha = false;
    }



}
