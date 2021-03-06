package com.rec.timerv01.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
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

    private Ringtone ringtone;

    public static final String RECEIVE_DADO = "receive_dado"; //SEND PARA DEVOLVER

    public WorkManagerRecTimer(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("TAGNAME", ">>> INICIANDO DOWORK");
        // ------------------------------------------------------------------------------------------
        // RECEIVE DADOS DO EDITFORM
        String txt = getInputData().getString("txt");
        /*+++++++++++++++++++++++++++++++++++++++++++++++*/
        //Validar txt length
        String[] txtlin = txt.split(";");
        Log.e("TAGNAME", ">>> txtlin :  " + txtlin[0]);
        //Log.e("TAGNAME", ">>> txtlin :  " + txtlin[1]);
        //Log.e("TAGNAME", ">>> txtlin :  " + txtlin[2]);
        /*+++++++++++++++++++++++++++++++++++++++++++++++*/
        int diffA = getInputData().getInt("diffA", 0);
        int intrv = getInputData().getInt("intrv", 0);
        Log.d("TAGNAME", ">>> diffA " + diffA);
        // ------------------------------------------------------------------------------------------
        Log.e("TAGNAME", ">>> intrv :  " + intrv);
        // ------------------------------------------------------------------------------------------
        setRecAlamServ("taimer rodando", "Taimer Rodando");
        while ( cnt < diffA && trabalha){
            Log.d("TAGNAME", ">>> doWork trabalhando :  " + cnt +" de "+ diffA);

            //if (Calendar.getInstance().get(Calendar.SECOND) % 3 == 0) {
            if (Calendar.getInstance().get(Calendar.SECOND) % intrv == 0) {
                String falTTS = (txtlin[0]+" "+ Calendar.getInstance().get(Calendar.MINUTE) +" e "+ Calendar.getInstance().get(Calendar.SECOND));
                setRecAlamServ(falTTS, (txtlin[0]+" "+falTTS));
            }
            if (Calendar.getInstance().get(Calendar.SECOND) % (intrv+5) == 0) {
                stopRecAlamServ();
            }

            try{ Thread.sleep(1000); }
            catch (InterruptedException e){
                e.printStackTrace();
                return  Result.failure();
            }
            cnt++;
        }
        // ------------------------------------------------------------------------------------------
        //falaAlarme("Taimer Finalizado");
        // ------------------------------------------------------------------------------------------
        setRecAlamServ("Taimer Finalizado", "Taimer Finalizado");
        try{ Thread.sleep(3000); }
        catch (InterruptedException e){
            e.printStackTrace();
            return  Result.failure();
        }
        onStopped();
        // SEND DADOS PRO EDITFORM
        Data data1 = new Data.Builder()
                .putString(RECEIVE_DADO, "Recevendo Dado Teste")
                .build();
        return Result.success(data1);
        // ------------------------------------------------------------------------------------------
    }

    private void setRecAlamServ(String falTTS, String txt) {
        //Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        Intent intentService = new Intent(getApplicationContext(), FalaTTS.class);
        intentService.putExtra("falTTS", falTTS);
        intentService.putExtra("txt", txt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(getApplicationContext(), intentService);
        } else {
            getApplicationContext().startService(intentService);
        }
    }

    private void stopRecAlamServ(){
        //Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        Intent intentService = new Intent(getApplicationContext(), FalaTTS.class);
        getApplicationContext().stopService(intentService);
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.d("TAGNAME", ">>> WorkManagerRecTimer onStopped ");
        trabalha = false;

        //Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        Intent intentService = new Intent(getApplicationContext(), FalaTTS.class);
        getApplicationContext().stopService(intentService);

    }
}
