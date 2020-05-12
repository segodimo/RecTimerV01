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
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;
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
    private TextToSpeech txtfala;
    private Intent intent;

    Calendar actuali;
    Calendar calendari;

    Calendar actualf;
    Calendar calendarf;


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

        // TTS
        txtfala = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    txtfala.setLanguage(Locale.getDefault());
                }
            }
        });



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

                TimePickerDialog timepickerdialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int h, int m) {
                        calendarf.set(Calendar.HOUR_OF_DAY,h);
                        calendarf.set(Calendar.MINUTE,m);

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


                String tag = "tag1";
                actuali = Calendar.getInstance();
                Long ddi = calendari.getTimeInMillis();
                Long dda = actuali.getTimeInMillis();
                Long AlertTimei = (calendari.getTimeInMillis() - actuali.getTimeInMillis());

                Log.d("TAGNAME", "ddf "+String.valueOf(calendari.getTime())+" ddi "+ddi);
                Log.d("TAGNAME", "dda "+String.valueOf(actuali.getTime())+" dda "+dda);
                Log.d("TAGNAME", "AlertTimei "+String.valueOf(AlertTimei / 1000));

                //int random = (int)(Math.random() * 50 * 1); // *** Otro Tag

                Data data = new Data.Builder()
                        .putString("titulo","txt tit")
                        .putString("detalle","txt detalhe")
                        .putInt("id_noti", 1)
                        .build();
                /*============================================================*/
//                OneTimeWorkRequest noti = new OneTimeWorkRequest.Builder(WorkManagerRecTimer.class)
//                        .setInitialDelay(AlertTimei, TimeUnit.MILLISECONDS).addTag(tag)
//                        //.setInputData(data)
//                        .build();

//                WorkManager instance = WorkManager.getInstance(getApplicationContext());
//                instance.enqueue(workRequest);

                final OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(WorkManagerRecTimer.class)
                        //.setInputData(data)
                        .setInitialDelay(AlertTimei, TimeUnit.MILLISECONDS)
                        .addTag(tag)
                        .build();

                WorkManager.getInstance(getApplicationContext()).enqueue(workRequest);
                /*============================================================*/

                Toast.makeText(EditForm.this, "Alarma Guardada", Toast.LENGTH_SHORT).show();


                WorkManager.getInstance(EditForm.this).getWorkInfoByIdLiveData(workRequest.getId()).observe(EditForm.this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if(workInfo  != null ){
                            if(workInfo.getState().isFinished()){
                                Data data = workInfo.getOutputData();
                                String outputdata = data.getString(WorkManagerRecTimer.RECEIVE_DADO);
                                Log.d("TAGNAME", "FINALISOU doWork");
                                Toast.makeText(EditForm.this, "Chegou!! "+outputdata, Toast.LENGTH_LONG).show();

                                //String toSpeak = "testando fala ok";
                                //txtfala.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                
                            }
                        }
                    }
                });

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("tag1");
                //Toast.makeText(EditForm.this, "Alarma Eliminada", Toast.LENGTH_SHORT).show();
                /*============================================================*/

                intent = new Intent(getApplicationContext(), FalaTextToSpeechService.class);
                //intent.putString(INTENT_TXT, value);
                startService(intent);

                //context.startService(new Intent(context, TTS.class));
                /*============================================================*/

            }
        });

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
