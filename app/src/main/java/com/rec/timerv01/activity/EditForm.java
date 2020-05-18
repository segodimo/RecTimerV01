package com.rec.timerv01.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.OneTimeWorkRequest;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rec.timerv01.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class EditForm extends AppCompatActivity {

    private TextView txtTimeRT;
    private TextInputEditText inptDtH;
    private TextInputEditText inptDtM;
    private TextInputEditText inptIvH;
    private TextInputEditText inptIvM;
    private TextInputEditText inptIvS;
    private TextInputEditText inptSpeach;
    private Switch switchStatus;
    private FloatingActionButton btnSave;
    private Button btnStop;
    private Runnable runnable;
    private Handler handler = new Handler();
    private Intent intent;


    Calendar calendar;

    private int AlertTimei;
    private int AlertTimef;

    private int Intervalo;

    private int segi,minutosi,horai,diai,mesi,anoi;
    private int segf,minutosf,horaf,diaf,mesf,anof;

    private Boolean valida = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);

        inptDtH = findViewById(R.id.inptDtH);
        inptDtM = findViewById(R.id.inptDtM);
        inptIvH = findViewById(R.id.inptIvH);
        inptIvM = findViewById(R.id.inptIvM);
        inptIvS = findViewById(R.id.inptIvS);


        txtTimeRT = findViewById(R.id.txtTimeRT);
        inptSpeach = findViewById(R.id.inptSpeach);
        switchStatus = findViewById(R.id.switchStatus);
        btnSave = findViewById(R.id.btnSave);
        btnStop = findViewById(R.id.btnStop);

        AtualizarHora();

        /*============================================================*/
        inptDtH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h = inptDtH.getText().toString();
                Toast.makeText(EditForm.this, "h "+h, Toast.LENGTH_SHORT).show();
                //calendar.set(calendar.HOUR_OF_DAY,h);
            }
        });

        /*============================================================*/



        switchStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(!valida){
                    //Toast.makeText(EditForm.this, "no valido", Toast.LENGTH_SHORT).show();
                    //btnSave.setBackgroundColor(Color.parseColor("#ff0000"));
                    //btnSave.getBackground().mutate().setTint(getApplicationContext().getColor(this, R.color.anyColor));
                    //btnSave.setBackgroundTintList(getResources().getColorStateList(Color.parseColor("#ff0000"));
//                    btnSave.setBackgroundTintList(btnSave.valueOf(ContextCompat.getColor(mContext,R.color.mColor)));
//                    btnSave.setBackgroundTintList(btnSave, ColorStateList.valueOf());
//                    btnSave.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.favourite_selected));
                    //btnSave.setBackgroundTintList(btnSave, ColorStateList.valueOf(100, 80, 80, ));
                    btnSave.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0000")));



                }
                else{
                    //------------------------------------------------------------------------------------------------
//                if(calendari != null){
//                    Log.d("TAGNAME", String.valueOf(Calendar.getInstance().getTime())+" (ddai) "+Calendar.getInstance().getTimeInMillis());
//                    Log.d("TAGNAME", String.valueOf(calendari.getTime())+" (ddsf) "+calendari.getTimeInMillis());
//                    AlertTimei = (int) (long) (calendari.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
//                    Log.d("TAGNAME", "AlertTimei "+String.valueOf(AlertTimei / 1000));
//                    //salvarAlarme("tag1", AlertTimei, "titititi", "Detalhe funcionando", 1);
//                    //salvarAlarme("tag1", 8000, "titititi", "T.X.T. Fala funcionando", 1);
//                }
                    //------------------------------------------------------------------------------------------------
//                if(calendarf != null){
//                    Log.d("TAGNAME", String.valueOf(Calendar.getInstance().getTime())+" (ddai) "+Calendar.getInstance().getTimeInMillis());
//                    Log.d("TAGNAME", String.valueOf(calendarf.getTime())+" (dddf) "+calendarf.getTimeInMillis());
//                    AlertTimef = (int) (long)(calendarf.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
//                    Log.d("TAGNAME", "AlertTimef "+String.valueOf(AlertTimef / 1000));
//                    //salvarAlarme("tag2", AlertTimef, "titititi", "Alarme Final", 1);
//                    //salvarAlarme("tag2", 15000, "titititi", "Alarme Final", 1);
//                }
                    //------------------------------------------------------------------------------------------------


                    Intervalo = 5; // em Segundos
                    AlertTimei = 3000; // em ms
                    AlertTimef = 60000; // em ms

                    Log.d("TAGNAME", "AlertTimei "+String.valueOf(AlertTimei / 1000));
                    Log.d("TAGNAME", "AlertTimef "+String.valueOf(AlertTimef / 1000));

                    salvarAlarme("tag1", AlertTimei, "titititi", "fala teste", (AlertTimef-AlertTimei), Intervalo);

//                if(calendarf != null && calendari != null && AlertTimef > AlertTimei){
//                    //salvarAlarme("tag1", AlertTimei, "titititi", String.valueOf(hh+" horas"+mm+" minutos "+ss+" segundos"), 1);
//                    //salvarAlarme("tag2", AlertTimef, "titititi", "Alarme Final", 1);
//                }else{
//                    Toast.makeText(EditForm.this, "Death Timer < Start Timer", Toast.LENGTH_SHORT).show();
//                }
                    //------------------------------------------------------------------------------------------------
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("tag1");
                Toast.makeText(EditForm.this, "Alarma Eliminada", Toast.LENGTH_SHORT).show();
            }
        });


    }//FINAL DO ONCREATE

    private void salvarAlarme(String tag, int alertTime, String tit, String txt, int diffA, int intrv) {
        /*============================================================*/
        // SEND DADOS PRO WORKMANAGER
        Data data = new Data.Builder()
                .putString("titulo",tit)
                .putString("detalle",txt)
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
        WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
        /*============================================================*/
        UnMuteAudio();
        Toast.makeText(EditForm.this, "Alarma Guardada", Toast.LENGTH_SHORT).show();
        //-------------------------------------------------------------
        intent = new Intent(getApplicationContext(), FalaTTS.class);
        intent.putExtra("FALATXT", "");
        getApplicationContext().startService(intent);
        /*=============================================================*/
        // RECEIVE DADOS DO WORKMANAGER
        WorkManager.getInstance(EditForm.this).getWorkInfoByIdLiveData(workRequest.getId()).observe(EditForm.this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if(workInfo  != null ){
                    if(workInfo.getState().isFinished()){
                        Data data = workInfo.getOutputData();
                        String outputdata = data.getString(WorkManagerRecTimer.RECEIVE_DADO);
                        Log.d("TAGNAME", "FINALISOU DOWORK");
                        //Toast.makeText(EditForm.this, "Chegou!! "+outputdata, Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
        /*=============================================================*/
    }














    public void UnMuteAudio(){
        //AudioManager mAlramMAnager = (AudioManager) EditForm.getSystemService(getApplicationContext().AUDIO_SERVICE);
        AudioManager mAlramMAnager=(AudioManager)getSystemService(getApplicationContext().AUDIO_SERVICE);

        //mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        //mAlramMAnager.setStreamVolume(AudioManager.STREAM_MUSIC, mAlramMAnager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        //mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        mAlramMAnager.setStreamVolume(AudioManager.STREAM_MUSIC,2 , AudioManager.FLAG_SHOW_UI);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE,0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
        }
    }

    private void AtualizarHora() {
        final Calendar calendarRt = Calendar.getInstance();

        runnable = new Runnable() {
            @Override
            public void run() {

                calendarRt.setTimeInMillis(System.currentTimeMillis());

                String tiempo = String.format("%02d:%02d:%02d",
                        calendarRt.get(Calendar.HOUR_OF_DAY),
                        calendarRt.get(Calendar.MINUTE),
                        calendarRt.get(Calendar.SECOND));
                txtTimeRT.setText(tiempo);

                long agora = SystemClock.uptimeMillis();
                long proximo = agora + (1000 - (agora % 1000));
                handler.postAtTime(runnable, proximo);

            }
        };
        runnable.run();
    }


}
