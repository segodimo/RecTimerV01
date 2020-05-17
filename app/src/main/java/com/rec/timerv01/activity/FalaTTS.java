package com.rec.timerv01.activity;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.SynthesisCallback;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Locale;

public class FalaTTS extends Service {

    private TextToSpeech txtfala;


    @Override
    public void onCreate() {
        super.onCreate();

        txtfala = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) txtfala.setLanguage(Locale.getDefault());
            }
        });

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String falatxt = intent.getStringExtra("FALATXT");
        //String toSpeak = "Testando fala, 1, 2, 3, 4";
        txtfala.speak(falatxt, TextToSpeech.QUEUE_FLUSH, null);
        return START_STICKY;
        // return START_NOT_STICKY;
        //return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




}