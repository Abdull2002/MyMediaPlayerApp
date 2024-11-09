package com.example.mymediaplayerapp;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Button playButton, pauseButton, forwardButton, rewindButton;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        playButton = findViewById(R.id.button3);
        pauseButton = findViewById(R.id.button2);
        forwardButton = findViewById(R.id.button);
        rewindButton = findViewById(R.id.button4);
        seekBar = findViewById(R.id.seekBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.song);
        seekBar.setClickable(false);
        pauseButton.setEnabled(false);

        playButton.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Playing Sound", Toast.LENGTH_SHORT).show();
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration());
            pauseButton.setEnabled(true);
            playButton.setEnabled(false);
            handler.postDelayed(updateSeekBar, 100);
        });

        pauseButton.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Pausing Sound", Toast.LENGTH_SHORT).show();
            mediaPlayer.pause();
            pauseButton.setEnabled(false);
            playButton.setEnabled(true);
        });

        forwardButton.setOnClickListener(v -> {
            int currentPosition = mediaPlayer.getCurrentPosition();
            if ((currentPosition + forwardTime) <= mediaPlayer.getDuration()) {
                mediaPlayer.seekTo(currentPosition + forwardTime);
            } else {
                Toast.makeText(getApplicationContext(), "Cannot jump forward", Toast.LENGTH_SHORT).show();
            }
        });

        rewindButton.setOnClickListener(v -> {
            int currentPosition = mediaPlayer.getCurrentPosition();
            if ((currentPosition - backwardTime) > 0) {
                mediaPlayer.seekTo(currentPosition - backwardTime);
            } else {
                Toast.makeText(getApplicationContext(), "Cannot jump backward", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Runnable updateSeekBar = new Runnable() {
        public void run() {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
