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

public class FalaTTS extends Service implements TextToSpeech.OnInitListener {
   public FalaTTS() {
   }

   public static TextToSpeech mTts;

   @Override
   public IBinder onBind(Intent intent) {
       return null;
   }

   public void onStart(Intent intent, int startId) {
       // TODO Auto-generated method stub
       //mPreferences = getSharedPreferences(Mysettings.PREF_NAME, Service.MODE_PRIVATE);

       //pit = Float.parseFloat(mPreferences.getString("pit","0.8"));
       //rate = Float.parseFloat(mPreferences.getString("rate","1.1"));
       mTts = new TextToSpeech(this, this);
       super.onStart(intent, startId);
   }

public void onInit(int status) {
    // TODO Auto-generated method stub
    if (status == TextToSpeech.SUCCESS) {
        if (mTts.isLanguageAvailable(Locale.getDefault()) >= 0)

        //Toast.makeText( FalaTTS.this, "Sucessfull intialization of Text-To-Speech engine FalaTTS ", Toast.LENGTH_LONG).show();
        mTts.setLanguage(Locale.getDefault());

        //mTts.setPitch(pit);
        //mTts.setSpeechRate(rate);

    } else if (status == TextToSpeech.ERROR) {
//        Toast.makeText(FalaTTS.this, "Unable to initialize Text-To-Speech engine", Toast.LENGTH_LONG).show();
    }
}}