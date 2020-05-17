package com.rec.timerv01.activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Locale;

public class FalaTTSstop extends Service implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {
    private TextToSpeech mTts;
    private String spokenText;

    @Override
    public void onCreate() {
        mTts = new TextToSpeech(this, this);
        // This is a good place to set spokenText
        Log.d("TAGNAME", "Pasou por aqui 1!!");
    }

    @Override
    public void onInit(int status) {
        Log.d("TAGNAME", "Pasou por aqui 2!!");
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.getDefault());
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                //mTts.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null);
                mTts.speak("Testando fala, 1, 2, 3", TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d("TAGNAME", "Pasou por aqui 3!!");
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TAGNAME", "Pasou por aqui 4!!");
        return null;
    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        Log.d("TAGNAME", "Pasou por aqui 5!!");
    }
}