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
import android.app.AlarmManager;
import android.app.PendingIntent;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
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

    private TextView titTTS;
    private TextInputEditText inptSpeach;
    private TextView addTTS;
    private String ttsfala = "";
    

    private Switch switchStatus;
    private FloatingActionButton btnSave;
    private Button btnStop;
    private Runnable runnable;
    private Handler handler = new Handler();
    private Intent intent;


    Calendar nowdate;

    private int AlertTimei = 0;
    private int AlertTimef;

    private int Intervalo;

    private int segi,minutosi,horai,diai,mesi,anoi;
    private int segf,minutosf,horaf,diaf,mesf,anof;

    private Boolean ioSave = false;

    private AlarmManager alarmmanager;

    private String state = "true";
    private String tag = "tag1";


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

        titTTS = findViewById(R.id.titTTS);
        inptSpeach = findViewById(R.id.inptSpeach);
        addTTS = findViewById(R.id.addTTS);


        switchStatus = findViewById(R.id.switchStatus);

        txtTimeRT = findViewById(R.id.txtTimeRT);
        btnSave = findViewById(R.id.btnSave);
        btnStop = findViewById(R.id.btnStop);

        nowdate = Calendar.getInstance();

        alarmmanager = (AlarmManager) getSystemService(ALARM_SERVICE);

        AtualizarHora();

        /*============================================================*/
        //AJUSTA E ARDENDONDA(5min) HORARIO PERTO DE ratio = 30min DEPOIS
        ajusteDT();
        /*============================================================*/

        ajusteFormato();


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

                int vlDtH = Integer.parseInt(Objects.requireNonNull(inptDtH.getText()).toString());
                int vlDtM = Integer.parseInt(Objects.requireNonNull(inptDtM.getText()).toString());

                int vlIvH = Integer.parseInt(Objects.requireNonNull(inptIvH.getText()).toString());
                int vlIvM = Integer.parseInt(Objects.requireNonNull(inptIvM.getText()).toString());
                int vlIvS = Integer.parseInt(Objects.requireNonNull(inptIvS.getText()).toString());

                Calendar.getInstance();

                nowdate.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                nowdate.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                nowdate.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                nowdate.set(Calendar.HOUR_OF_DAY, vlDtH);
                nowdate.set(Calendar.MINUTE, vlDtM);
                nowdate.set(Calendar.SECOND, 0);

                Log.d("TAGNAME", "nowdate "+nowdate.getTime());

                int interval = (vlIvH*60*60*1000)+(vlIvM*60*1000)+(vlIvS*1000);
                int diffA = (int) (long) (nowdate.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());

                if(diffA > 0){
                    if(interval < diffA){
                        //------------------------------------------------------------------------------------------------
                        Intervalo = (vlIvH*60*60*1000)+(vlIvM*60*1000)+(vlIvS);
                        // android:text="11:20|First Step|18:30|Second Step"
                        //String ttsfala = Objects.requireNonNull(inptSpeach.getText()).toString();
                        //------------------------------------------------------------------------------------------------
                        //PARA TESTES
                        //Intervalo = 10;
                        //diffA = 60000; // em Segundos
                        //AlertTimei = 5000; // em ms tempo de espera para começar
                        //------------------------------------------------------------------------------------------------
                        //AlertTimef = 60000; // em ms temo que demora o trabalho = death timer

                        Log.d("TAGNAME", "AlertTimei = 0 "+String.valueOf(AlertTimei = 0 / 1000));
                        Log.d("TAGNAME", "AlertTimef "+String.valueOf(AlertTimef / 1000));

                        //salvarAlarme("tag1", AlertTimei = 0, "titititi", "fala teste", (AlertTimef-AlertTimei = 0), Intervalo);
                        salvarAlarme(tag, AlertTimei = 0, ttsfala, diffA/1000, Intervalo);
                        //------------------------------------------------------------------------------------------------
                    }else{
//                        int diffTime = diffMax-30000;
                        inptIvH.setText("00");
                        inptIvM.setText("00");
                        inptIvS.setText("30");
                        //ajusteDT();
                        Toast.makeText(EditForm.this, "Intervalo > (Death Timer + Time Atual)", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    ajusteDT();
                    Toast.makeText(EditForm.this, "Death Timer < Time Atual", Toast.LENGTH_SHORT).show();
                }
            }
        });

        titTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(EditForm.this, "okokokok", Toast.LENGTH_SHORT).show();
                String txtVld = Objects.requireNonNull(inptSpeach.getText()).toString();
                if(!txtVld.equals("")){
                    String txtaddTTS = vldTTS(txtVld);
                    if(!txtaddTTS.equals("ERROR")){
                        String listaddTTS = Objects.requireNonNull(addTTS.getText()).toString();
                        String[] arrTTS = listaddTTS.split("\n");
                        //Log.d("AVA", "arrTTS.length "+arrTTS.length);

                        //PRIMEIRO VALOR
                        if(arrTTS.length <= 1){
                            addTTS.setText(txtaddTTS);
                            addTTS.setVisibility(View.VISIBLE);
                            inptSpeach.setText("");
                            hideKeyboard(EditForm.this);
                        }

                        //SIGUIENTES VALORES
                        else{

                            //VALIDA VALOR REPETIDO
                            if( !useList(arrTTS, txtaddTTS) ){

                                String newtxtTTS = listaddTTS+"\n"+txtaddTTS;
                                addTTS.setText(newtxtTTS);
                                inptSpeach.setText("");
                                hideKeyboard(EditForm.this);
                            }else{
                                Toast.makeText(EditForm.this, "O valor tem que ser diferente!", Toast.LENGTH_SHORT).show();
                            }
                            //Log.d("AVA", "arrTTS "+arrTTS.toString());
                        }
                    }else{
                        Log.e("AVA", "ERROR no Valor ");
                    }
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBC = new Intent(getApplicationContext(), RecBroadcastReceiver.class);
                intentBC.putExtra("tag", tag);
                intentBC.putExtra("state", "false");
                sendBroadcast(intentBC);
            }
        });

        boxGeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(EditForm.this);
            }
        });


    }//FINAL DO ONCREATE

    private String vldTTS(String txtVld) {
        String[] arrtxtVld = txtVld.split(",");
        if( (arrtxtVld.length == 3) 
            && (arrtxtVld[0].matches("-?\\d+(\\.\\d+)?")) && (Integer.parseInt(arrtxtVld[0]) <= 23)
            && (arrtxtVld[1].matches("-?\\d+(\\.\\d+)?")) && (Integer.parseInt(arrtxtVld[1]) <= 59) ){

                return arrtxtVld[0];

        }else{
            return "ERROR";
        }
    }


    public static boolean useList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }


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

    private void salvarAlarme(String tag, int alertTime, String txt, int diffA, int intrv) {

        /*============================================================*/
        Intent intentBC = new Intent(this, RecBroadcastReceiver.class);
        intentBC.putExtra("alertTime", alertTime);
        intentBC.putExtra("tag", tag);
        intentBC.putExtra("txt", txt);
        intentBC.putExtra("diffA", diffA);
        intentBC.putExtra("intrv", intrv);
        intentBC.putExtra("state", state);

        sendBroadcast(intentBC);
        /*============================================================*/
        UnMuteAudio();
        Toast.makeText(EditForm.this, "Alarma Guardada", Toast.LENGTH_SHORT).show();
        //-------------------------------------------------------------
        // intent = new Intent(getApplicationContext(), FalaTTS.class);
        // intent.putExtra("FALATXT", "");
        // getApplicationContext().startService(intent);
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

    private void ajusteFormato() {
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
                //Log.d("TAGNAME", "nowdate "+s.length());
                //Log.d("TAGNAME", "nowdate "+inptDtH.getText().toString().length());
            }
        });
        inptDtH.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) if(inptDtH.getText().toString().length() == 1) inptDtH.setText("0"+inptDtH.getText().toString());
            }
        });


        inptDtM.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(2)});
        inptDtM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>1) inptIvH.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(Integer.parseInt(s.toString()) >= 60) inptDtM.setText("00");
            }
        });
        inptDtM.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) if(inptDtM.getText().toString().length() == 1) inptDtM.setText("0"+inptDtM.getText().toString());
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
        inptIvH.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) if(inptIvH.getText().toString().length() == 1) inptIvH.setText("0"+inptIvH.getText().toString());
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
        inptIvM.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) if(inptIvM.getText().toString().length() == 1) inptIvM.setText("0"+inptIvM.getText().toString());
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
        inptIvS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) if(inptIvS.getText().toString().length() == 1) inptIvS.setText("0"+inptIvS.getText().toString());
            }
        });
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
