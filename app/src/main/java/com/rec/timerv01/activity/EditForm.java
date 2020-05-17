package com.rec.timerv01.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.OneTimeWorkRequest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rec.timerv01.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class EditForm extends AppCompatActivity {

    private TextView txtTimeRT;
    private TextView btnDatai;
    private TextView btnTimei;
    private TextView btnDataf;
    private TextView btnTimef;
    private TextInputEditText inptIntervalo;
    private TextInputEditText inptSpeach;
    private Switch switchStatus;
    private FloatingActionButton btnSave;
    private Button btnStop;
    private Runnable runnable;
    private Handler handler = new Handler();
    private Intent intent;


    Calendar actuali;
    Calendar calendari;

    Calendar actualf;
    Calendar calendarf;

    private int AlertTimei;
    private int AlertTimef;

    private int Intervalo;

    private int segi,minutosi,horai,diai,mesi,anoi;
    private int segf,minutosf,horaf,diaf,mesf,anof;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);

        txtTimeRT = findViewById(R.id.txtTimeRT);
        btnDatai = findViewById(R.id.btnDatai);
        btnTimei = findViewById(R.id.btnTimei);
        btnDataf = findViewById(R.id.btnDataf);
        btnTimef = findViewById(R.id.btnTimef);
        inptIntervalo = findViewById(R.id.inptIntervalo);
        inptSpeach = findViewById(R.id.inptSpeach);
        switchStatus = findViewById(R.id.switchStatus);
        btnSave = findViewById(R.id.btnSave);
        btnStop = findViewById(R.id.btnStop);

        AtualizarHora();

        /*============================================================*/
        btnDatai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actuali = Calendar.getInstance();
                calendari = Calendar.getInstance();

                anoi = actuali.get(Calendar.YEAR);
                mesi = actuali.get(Calendar.MONTH);
                diai = actuali.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datepickerdialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        calendari.set(Calendar.DAY_OF_MONTH,d);
                        calendari.set(Calendar.MONTH,m);
                        calendari.set(Calendar.YEAR,y);

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = format.format(calendari.getTime());
                        btnDatai.setText(strDate);

                    }
                },anoi,mesi,diai);
                datepickerdialog.show();
            }
        });

        btnTimei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actuali = Calendar.getInstance();
                calendari = Calendar.getInstance();

                horai = actuali.get(Calendar.HOUR_OF_DAY);
                minutosi = actuali.get(Calendar.MINUTE);
                minutosi = minutosi+1; //AUMENTA UM MINUTO NA ALARME
                //segi = actuali.get(Calendar.SECOND);

                TimePickerDialog timepickerdialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int h, int m) {
                        calendari.set(Calendar.HOUR_OF_DAY,h);
                        calendari.set(Calendar.MINUTE,m);
                        calendari.set(Calendar.SECOND,0);

                        btnTimei.setText(String.format("%02d:%02d", h, m));

                    }
                }, horai,minutosi,true);
                timepickerdialog.show();
            }
        });
        /*============================================================*/






        /*============================================================*/
        btnDataf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualf = Calendar.getInstance();
                calendarf = Calendar.getInstance();

                anof = actualf.get(Calendar.YEAR);
                mesf = actualf.get(Calendar.MONTH);
                diaf = actualf.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datepickerdialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        calendarf.set(Calendar.DAY_OF_MONTH,d);
                        calendarf.set(Calendar.MONTH,m);
                        calendarf.set(Calendar.YEAR,y);

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = format.format(calendarf.getTime());
                        btnDataf.setText(strDate);

                    }
                },anof,mesf,diaf);
                datepickerdialog.show();
            }
        });

        btnTimef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualf = Calendar.getInstance();
                calendarf = Calendar.getInstance();

                horaf = actualf.get(Calendar.HOUR_OF_DAY);
                minutosf = actualf.get(Calendar.MINUTE);
                minutosf= minutosf+1; //AUMENTA UM MINUTO NA ALARME

                TimePickerDialog timepickerdialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int h, int m) {
                        calendarf.set(Calendar.HOUR_OF_DAY,h);
                        calendarf.set(Calendar.MINUTE,m);
                        calendarf.set(Calendar.SECOND,0);

                        btnTimef.setText(String.format("%02d:%02d", h, m));

                    }
                }, horaf,minutosf,true);
                timepickerdialog.show();
            }
        });
        /*============================================================*/


        inptIntervalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        inptSpeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        switchStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //------------------------------------------------------------------------------------------------
                if(calendari != null){
                    Log.d("TAGNAME", String.valueOf(Calendar.getInstance().getTime())+" (ddai) "+Calendar.getInstance().getTimeInMillis());
                    Log.d("TAGNAME", String.valueOf(calendari.getTime())+" (ddsf) "+calendari.getTimeInMillis());
                    AlertTimei = (int) (long) (calendari.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
                    Log.d("TAGNAME", "AlertTimei "+String.valueOf(AlertTimei / 1000));
                    //salvarAlarme("tag1", AlertTimei, "titititi", "Detalhe funcionando", 1);
                    //salvarAlarme("tag1", 8000, "titititi", "T.X.T. Fala funcionando", 1);
                }
                //------------------------------------------------------------------------------------------------
                if(calendarf != null){
                    Log.d("TAGNAME", String.valueOf(Calendar.getInstance().getTime())+" (ddai) "+Calendar.getInstance().getTimeInMillis());
                    Log.d("TAGNAME", String.valueOf(calendarf.getTime())+" (dddf) "+calendarf.getTimeInMillis());
                    AlertTimef = (int) (long)(calendarf.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
                    Log.d("TAGNAME", "AlertTimef "+String.valueOf(AlertTimef / 1000));
                    //salvarAlarme("tag2", AlertTimef, "titititi", "Alarme Final", 1);
                    //salvarAlarme("tag2", 15000, "titititi", "Alarme Final", 1);
                }
                //------------------------------------------------------------------------------------------------


                Intervalo = 5; // em Segundos
                AlertTimei = 3000; // em ms
                AlertTimef = 60000; // em ms

                Log.d("TAGNAME", "AlertTimei "+String.valueOf(AlertTimei / 1000));
                Log.d("TAGNAME", "AlertTimef "+String.valueOf(AlertTimef / 1000));

//                for (int i = AlertTimei+Intervalo; i < AlertTimef; i+= Intervalo){
//
//                    int hh = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//                    int mm = Calendar.getInstance().get(Calendar.MINUTE);
//                    int ss = Calendar.getInstance().get(Calendar.SECOND);
//
//                    Log.d("TAGNAME", "i "+i);
//                    salvarAlarme("tag1", i, "titititi", String.valueOf(hh+" horas"+mm+" minutos "+ss+" segundos"), 1);
//                }

                salvarAlarme("tag1", AlertTimei, "titititi", "fala teste", (AlertTimef-AlertTimei), Intervalo);

//                if(calendarf != null && calendari != null && AlertTimef > AlertTimei){
//                    //salvarAlarme("tag1", AlertTimei, "titititi", String.valueOf(hh+" horas"+mm+" minutos "+ss+" segundos"), 1);
//                    //salvarAlarme("tag2", AlertTimef, "titititi", "Alarme Final", 1);
//                }else{
//                    Toast.makeText(EditForm.this, "Death Timer < Start Timer", Toast.LENGTH_SHORT).show();
//                }
                //------------------------------------------------------------------------------------------------


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
