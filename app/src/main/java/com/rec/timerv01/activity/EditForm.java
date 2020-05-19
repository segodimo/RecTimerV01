package com.rec.timerv01.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.OneTimeWorkRequest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rec.timerv01.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.round;

public class EditForm extends AppCompatActivity {

    private ConstraintLayout boxGeral;

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


    Calendar nowdate;

    private int AlertTimei;
    private int AlertTimef;

    private int Intervalo;

    private int segi,minutosi,horai,diai,mesi,anoi;
    private int segf,minutosf,horaf,diaf,mesf,anof;

    private Boolean ioSave = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);

        boxGeral = findViewById(R.id.boxGeral);

        inptDtH = findViewById(R.id.inptDtH);
        inptDtM = findViewById(R.id.inptDtM);
        inptIvH = findViewById(R.id.inptIvH);
        inptIvM = findViewById(R.id.inptIvM);
        inptIvS = findViewById(R.id.inptIvS);

        inptSpeach = findViewById(R.id.inptSpeach);
        switchStatus = findViewById(R.id.switchStatus);

        txtTimeRT = findViewById(R.id.txtTimeRT);
        btnSave = findViewById(R.id.btnSave);
        btnStop = findViewById(R.id.btnStop);

        nowdate = Calendar.getInstance();

        AtualizarHora();

        /*============================================================*/
        //AJUSTA E ARDENDONDA(5min) HORARIO PERTO DE ratio = 30min DEPOIS
        ajusteDT();
        /*============================================================*/

        inptDtH.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(2)});
        inptDtH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>1) inptDtM.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inptDtM.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(2)});
        inptDtM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>1) inptIvH.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(Integer.parseInt(s.toString()) >= 60) inptDtM.setText("00");
            }
        });

        inptIvH.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(2)});
        inptIvH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>1) inptIvM.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        inptIvM.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(2)});
        inptIvM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>1) inptIvS.requestFocus();

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(Integer.parseInt(s.toString()) >= 60) inptIvM.setText("00");
            }
        });

        inptIvS.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(2)});
        inptIvS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>1){
                    //hideKeyboard(EditForm.this);
                    inptSpeach.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(Integer.parseInt(s.toString()) >= 60) inptIvS.setText("00");
            }
        });


        /*============================================================*/
        //inptDtH.setText(Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
        //inptDtM.setText(Integer.toString(Calendar.getInstance().get(Calendar.MINUTE)));
        //inptDtM.setText(Integer.toString(conta*7));


        /*============================================================*/



        switchStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vlDtH = Integer.parseInt(inptDtH.getText().toString());
                int vlDtM = Integer.parseInt(inptDtM.getText().toString());

                int vlIvH = Integer.parseInt(inptIvH.getText().toString());
                int vlIvM = Integer.parseInt(inptIvM.getText().toString());
                int vlIvS = Integer.parseInt(inptIvS.getText().toString());

                int ano = Calendar.getInstance().get(Calendar.getInstance().YEAR);
                int mes = Calendar.getInstance().get(Calendar.getInstance().MONTH);
                int dia = Calendar.getInstance().get(Calendar.getInstance().DAY_OF_MONTH);

                nowdate.set(Calendar.getInstance().YEAR,ano);
                nowdate.set(Calendar.getInstance().MONTH,mes);
                nowdate.set(Calendar.getInstance().DAY_OF_MONTH,dia);
                nowdate.set(Calendar.getInstance().HOUR_OF_DAY,vlDtH);
                nowdate.set(Calendar.getInstance().MINUTE,vlDtM);
                nowdate.set(Calendar.getInstance().SECOND,0);

                Log.d("TAGNAME", "nowdate "+nowdate.getTime());

                if(!ioSave){
                    /*--------------------------------------------------------------------------------------------------------------*/

                    int interval = (vlIvH*60*60*1000)+(vlIvM*60*1000)+(vlIvS*1000);

                    int diffMax = (int) (long) (nowdate.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
                    if(diffMax <= 0){
                        ajusteDT();
                        Toast.makeText(EditForm.this, "Death Timer < Time Atual", Toast.LENGTH_SHORT).show();
                    }else{
                        if(interval < diffMax){
                            btnSave.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                            ioSave = true;
                        }else{
                            int diffTime = diffMax-30000;
                            /*Log.d("TAGNAME", "diffTime "+diffTime);*/
//                        inptIvH.setText(timNum(Integer.toString(((diffTime/1000)/60)/60)));
//                        inptIvM.setText(timNum(Integer.toString((diffTime/1000)/60)));
//                        inptIvS.setText(timNum(Integer.toString((diffTime/1000)%60)));

                            inptIvH.setText("00");
                            inptIvM.setText("01");
                            inptIvS.setText("00");

                            Toast.makeText(EditForm.this, "Intervalo > (Death Timer + Time Atual)", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    //------------------------------------------------------------------------------------------------
                    Intervalo = (vlIvH*60*60*1000)+(vlIvM*60*1000)+(vlIvS);
                    int diffA = (int) (long) (nowdate.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
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


                    //Intervalo = 5; // em Segundos
                    //AlertTimei = 1000; // em ms tempo de espera para começar
                    //AlertTimef = 60000; // em ms temo que demora o trabalho = death timer

                    Log.d("TAGNAME", "AlertTimei "+String.valueOf(AlertTimei / 1000));
                    Log.d("TAGNAME", "AlertTimef "+String.valueOf(AlertTimef / 1000));

                    //salvarAlarme("tag1", AlertTimei, "titititi", "fala teste", (AlertTimef-AlertTimei), Intervalo);
                    salvarAlarme("tag1", AlertTimei, "titititi", "fala teste", diffA, Intervalo);

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



        boxGeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(EditForm.this);
            }
        });


    }//FINAL DO ONCREATE

    private void ajusteDT() {
        int ratio = 30;
        int nvHora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int conta = (((Integer.parseInt(Integer.toString(Calendar.getInstance().get(Calendar.MINUTE))))+ratio)/5)*5;
        if(conta >= 60){
            inptDtH.setText(timNum(Integer.toString(nvHora + 1)));
            inptDtM.setText(timNum(Integer.toString(conta - 60)));
        }else{
            inptDtH.setText(timNum(Integer.toString(nvHora)));
            inptDtM.setText(timNum(Integer.toString(conta)));
        }
    }

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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public String timNum(String txtnum){
        if(txtnum.length() == 1 ) return "0"+txtnum;
        else return txtnum;
    }




}
