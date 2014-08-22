package com.home.cuckoo;
import java.io.IOException;


import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements OnLoadCompleteListener {

    final String LOG_TAG = "myLogs";
    final int MAX_STREAMS = 5;

    SoundPool sp;
    int soundCuckoo;


    int maxAmplitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);

        soundCuckoo = sp.load(this, R.raw.cuckoo, 1);
        Log.d(LOG_TAG, "soundCuckoo = " + soundCuckoo);




    }

    public void button1Click(View view) {
        sp.play(soundCuckoo, 1, 1, 0, 5, 1);
        sp.play(soundCuckoo, 1, 0.5f, 0, 6, 1);
        sp.play(soundCuckoo, 0.5f, 0.5f, 0, 4, 1);
     //  sp.play(soundCuckoo, 0.1f, 1, 0, 10, 2);
       // sp.play(soundIdExplosion, 1, 1, 0, 0, 1);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        Log.d(LOG_TAG, "onLoadComplete, sampleId = " + sampleId + ", status = " + status);
    }


    public void button2Click(View v) throws IOException, IllegalStateException

    {
        MediaRecorder recorder;
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile("/dev/null");
        try {
            recorder.prepare();
            Log.d(LOG_TAG, "RECORDER Ok ");
        } catch (IOException e) {
            Log.d(LOG_TAG, "Error WITH RECORDER " + e);
            e.printStackTrace();
        }
        recorder.start();
        Log.d(LOG_TAG, "Start ");
        for(int i=0; i<20; i++) {
           // System.err.println("current amplitude is:"+recorder.getMaxAmplitude());
            //System.err.println("current amplitude is:"+recorder.getMaxAmplitude());
           // textView1.setText("current MIC amplitude is:"+"_"+recorder.getMaxAmplitude());
            maxAmplitude = recorder.getMaxAmplitude();
            Log.d(LOG_TAG, "MaxAmplitude = " + maxAmplitude);
            if (maxAmplitude > 20000) {
                sp.play(soundCuckoo, 1, 1, 0, 1, 1);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    Log.d(LOG_TAG, "Error " + e);
                }

            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                Log.d(LOG_TAG, "Error " + e);
            }
        }
        try {
        recorder.stop();
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error " + e);
        }
        recorder.reset();
        recorder.release();


    }

    }

