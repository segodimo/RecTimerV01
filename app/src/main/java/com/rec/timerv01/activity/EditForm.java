package com.rec.timerv01.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.WorkManager;
import androidx.work.OneTimeWorkRequest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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

    Calendar actual = Calendar.getInstance();
    Calendar calendarf = Calendar.getInstance();
    Calendar calendari = Calendar.getInstance();

    private int minutos,hora,dia,mes,ano;

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


        btnDataf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ano = actual.get(calendarf.YEAR);
                mes = actual.get(calendarf.MONTH);
                dia = actual.get(calendarf.DAY_OF_MONTH);


                DatePickerDialog datepickerdialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        calendarf.set(calendarf.DAY_OF_MONTH,d);
                        calendarf.set(calendarf.MONTH,m);
                        calendarf.set(calendarf.YEAR,y);

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = format.format(calendarf.getTime());
                        btnDataf.setText(strDate);

                    }
                },ano,mes,dia);
                datepickerdialog.show();
            }
        });

        btnTimef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hora = actual.get(calendarf.HOUR_OF_DAY);
                minutos = actual.get(calendarf.MINUTE);

                TimePickerDialog timepickerdialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int h, int m) {
                        calendarf.set(calendarf.HOUR_OF_DAY,h);
                        calendarf.set(calendarf.MINUTE,m);

                        btnTimef.setText(String.format("%02d:%02d", h, m));

                    }
                }, hora,minutos,true);
                timepickerdialog.show();
            }
        });


        btnDatai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ano = actual.get(calendari.YEAR);
                mes = actual.get(calendari.MONTH);
                dia = actual.get(calendari.DAY_OF_MONTH);


                DatePickerDialog datepickerdialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        calendari.set(calendari.DAY_OF_MONTH,d);
                        calendari.set(calendari.MONTH,m);
                        calendari.set(calendari.YEAR,y);

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = format.format(calendari.getTime());
                        btnDatai.setText(strDate);

                    }
                },ano,mes,dia);
                datepickerdialog.show();
            }
        });

        btnTimei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hora = actual.get(calendari.HOUR_OF_DAY);
                minutos = actual.get(calendari.MINUTE);

                TimePickerDialog timepickerdialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int h, int m) {
                        calendari.set(calendari.HOUR_OF_DAY,h);
                        calendari.set(calendari.MINUTE,m);

                        btnTimei.setText(String.format("%02d:%02d", h, m));

                    }
                }, hora,minutos,true);
                timepickerdialog.show();
            }
        });

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
                Long AlertTime = calendari.getTimeInMillis() - System.currentTimeMillis();
                //int random = (int)(Math.random() * 50 * 1); // *** Otro Tag

                Data data = new Data.Builder()
                        .putString("titulo","txt tit")
                        .putString("detalle","txt detalhe")
                        .putInt("id_noti", 1)
                        .build();
                /*============================================================*/
                OneTimeWorkRequest noti = new OneTimeWorkRequest.Builder(WorkManagerRecTimer.class)
                        .setInitialDelay(AlertTime, TimeUnit.MILLISECONDS).addTag(tag)
                        //.setInputData(data)
                        .build();

                WorkManager instance = WorkManager.getInstance(getApplicationContext());
                instance.enqueue(noti);
                /*============================================================*/

                Toast.makeText(EditForm.this, "Alarma Guardada", Toast.LENGTH_SHORT).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("tag1");
                Toast.makeText(EditForm.this, "Alarma Eliminada", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void AtualizarHora() {
        final Calendar calendarrt = Calendar.getInstance();

        runnable = new Runnable() {
            @Override
            public void run() {

                calendarrt.setTimeInMillis(System.currentTimeMillis());

                String tiempo = String.format("%02d:%02d:%02d",
                        calendarrt.get(Calendar.HOUR_OF_DAY),
                        calendarrt.get(Calendar.MINUTE),
                        calendarrt.get(Calendar.SECOND));
                txtTimeRT.setText(tiempo);

                long agora = SystemClock.uptimeMillis();
                long proximo = agora + (1000 - (agora % 1000));
                handler.postAtTime(runnable, proximo);

            }
        };
        runnable.run();
    }

}
