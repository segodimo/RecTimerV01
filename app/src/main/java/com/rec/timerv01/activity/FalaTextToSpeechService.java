package com.rec.timerv01.activity;


import android.speech.tts.SynthesisCallback;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeechService;
import android.util.Log;

public class FalaTextToSpeechService extends TextToSpeechService {

    @Override
    protected int onIsLanguageAvailable(String lang, String country, String variant) {
        Log.d("TTSVER", "onIsLanguageAvailable");
        return 0;
    }

    @Override
    protected String[] onGetLanguage() {
        Log.d("TTSVER", "onGetLanguage");
        return new String[0];
    }

    @Override
    protected int onLoadLanguage(String lang, String country, String variant) {
        Log.d("TTSVER", "onLoadLanguage");
        return 0;
    }

    @Override
    protected void onStop() {
        Log.d("TTSVER", "onStop");
    }

    @Override
    protected void onSynthesizeText(SynthesisRequest request, SynthesisCallback callback) {
        Log.d("TTSVER", "onSynthesizeText");
    }
}