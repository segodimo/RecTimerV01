package com.rec.timerv01.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.rec.timerv01.R;

import java.util.Locale;
import java.util.Timer;

public class FalaTTS extends Service {

    private Integer alarmHour;
    private Integer alarmMinute;
    private Ringtone ringtone;
    private Timer t = new Timer();
    private TextToSpeech mTts;

    private int result;

    private String falTTS = "Testando T.T.S. 1, 2, 3";
    private String tit = "RecTimer";
    private String txt = "Testando Txt";

    private static final String CHANNEL_ID = "MyNotificationChannelID";

    @Override
    public void onCreate() {
        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = mTts.setLanguage(Locale.getDefault());
                    if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                        mTts.speak(falTTS, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        falTTS = intent.getExtras().getString("falTTS");
        txt = intent.getExtras().getString("txt");

        //alarmHour = intent.getIntExtra("alarmHour", 0);
        //alarmMinute = intent.getIntExtra("alarmMinute", 0);

        //ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));

        try {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);


            Intent intent_rbr = new Intent(this, RecBroadcastReceiver.class);
            //Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class);
            //intent_rbr.setAction(ACTION_SNOOZE);
            //intent_rbr.putExtra(EXTRA_NOTIFICATION_ID, 0);
            intent_rbr.putExtra("state", "false");
            intent_rbr.putExtra("tag", "tag1");
            PendingIntent intent_rbrPendingIntent = PendingIntent.getBroadcast(this, 0, intent_rbr, PendingIntent.FLAG_UPDATE_CURRENT);


            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID )
                    .setContentTitle(tit)
                    //.setContentText("Alarm time - " + alarmHour.toString() + " : " + alarmMinute.toString())
                    .setContentText(txt)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .setSound(null)
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_launcher_foreground, "Parar", intent_rbrPendingIntent)
                    .build();

            startForeground(1, notification);

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "My Alarm clock Service", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            //ringtone.play();

            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                //mTts.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null);
                mTts.speak(falTTS, TextToSpeech.QUEUE_FLUSH, null);
            }

            //=================================================================================
            // Intent intentService = new Intent(getApplicationContext(), FalaTTSstop.class);
            // //getApplicationContext().startService(intentService);
            // // Valida a versão do Android. A partir do 8, usar startForegroundService.
            // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // ContextCompat.startForegroundService(getApplicationContext(), intentService);
            // } else {
                // getApplicationContext().startService(intentService);
            // }
            //==================================================================================

            //==================================================================================



        }
        catch (Exception e){
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //ringtone.stop();
        //t.cancel();

        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }

        super.onDestroy();
    }


    // private MediaPlayer player;
    // @Override
    // public int onStartCommand(Intent intent, int flags, int startId) {
        // player = MediaPlayer.create( this, Settings.System.DEFAULT_ALARM_ALERT_URI);
        // player.setLooping(true);
        // player.start();
        // return START_STICKY;
        // //return START_NOT_STICKY;
        // //return START_REDELIVER_INTENT;
    // }
    // @Override
    // public void onDestroy() {
        // super.onDestroy();
        // player.stop();
    // }

}
