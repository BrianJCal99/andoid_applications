package com.example.workpouttimerappv2;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityTimer extends AppCompatActivity {

    private ImageButton start_pause_timer;

    private ImageButton reset_timer;

    private TextView current_workout_time_remaining;

    private TextView current_rest_time_remaining;

    private long START_TIME_IN_MILLIS_WORKOUT;

    private long START_TIME_IN_MILLIS_REST;

    private android.os.CountDownTimer CountDownTimer_Workout;

    private android.os.CountDownTimer CountDownTimer_Rest;

    private boolean WorkoutTimerRunning;

    private long TimeLeftInMillis_Workout;

    private long TimeLeftInMillis_Rest;

    private ProgressBar workout_progressbar;

    private ProgressBar rest_progressbar;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        start_pause_timer = (ImageButton) findViewById(R.id.start_pause_timer);
        reset_timer = (ImageButton) findViewById(R.id.reset_timer);

        current_workout_time_remaining = (TextView) findViewById(R.id.current_workout_time_remaining);
        current_rest_time_remaining = (TextView) findViewById(R.id.current_rest_time_remaining);

        workout_progressbar = (ProgressBar) findViewById(R.id.workout_progressbar);
        rest_progressbar = (ProgressBar) findViewById(R.id.rest_progressBar);

        // get passed values from the parent intent
        Intent valueFromIntent = getIntent();

        long W_Minutes = valueFromIntent.getLongExtra("workoutMinutes", 0) * 60 * 1000;
        long W_Seconds = valueFromIntent.getLongExtra("workoutSeconds", 0) * 1000;

        START_TIME_IN_MILLIS_WORKOUT = W_Minutes + W_Seconds;
        TimeLeftInMillis_Workout = START_TIME_IN_MILLIS_WORKOUT;

        long R_Minutes = valueFromIntent.getLongExtra("restMinutes", 10) * 60 * 1000;
        long R_Seconds = valueFromIntent.getLongExtra("restSeconds", 10) * 1000;

        START_TIME_IN_MILLIS_REST = R_Minutes + R_Seconds;
        TimeLeftInMillis_Rest = START_TIME_IN_MILLIS_REST;

        // update UI with the current status of the timer

        // update workout progress bar and text view fields
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateCountDownText_Workout();
                updateWorkoutProgressbar();
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        // update rest progress bar and text view fields
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateCountDownText_Rest();
                updateRestProgressbar();
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        // start timer on click
        start_pause_timer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (WorkoutTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer_Workout();
                }
            }
        });

        // reset timer on click
        reset_timer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
    }

    // start workout timer function
    private void startTimer_Workout() {
        // create a new instance of CountDownTimer for each phase - workout
        CountDownTimer_Workout = new CountDownTimer(TimeLeftInMillis_Workout, 1000) {
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis_Workout = millisUntilFinished;
                updateCountDownText_Workout();
            }

            public void onFinish() {
                WorkoutTimerRunning = false;
                start_pause_timer.setImageResource(R.drawable.play);
                start_pause_timer.setVisibility(View.INVISIBLE);
                // if the phase has a rest start rest timer
                if (START_TIME_IN_MILLIS_REST != 0){
                    startTimer_Rest();
                }
                // else end current workout timer and alert user
                else {
                    workout_progressbar.setVisibility(View.INVISIBLE);
                    current_rest_time_remaining.setText("DONE!");
                    reset_timer.setVisibility(View.VISIBLE);
                    alertUser();
                }
            }
        }.start();

        WorkoutTimerRunning = true;
        start_pause_timer.setImageResource(R.drawable.pause);
        reset_timer.setVisibility(View.INVISIBLE);

    }

    // start rest timer function
    private void startTimer_Rest() {
        // create a new instance of CountDownTimer for each phase - rest
        CountDownTimer_Rest = new CountDownTimer(TimeLeftInMillis_Rest, 1000) {
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis_Rest = millisUntilFinished;
                updateCountDownText_Rest();
            }

            public void onFinish() {
                rest_progressbar.setVisibility(View.INVISIBLE);
                current_rest_time_remaining.setVisibility(View.INVISIBLE);
                workout_progressbar.setVisibility(View.VISIBLE);
                current_workout_time_remaining.setVisibility(View.VISIBLE);
                reset_timer.setVisibility(View.VISIBLE);
                alertUser();
            }
        }.start();
        reset_timer.setVisibility(View.INVISIBLE);
        rest_progressbar.setVisibility(View.VISIBLE);
        current_rest_time_remaining.setVisibility(View.VISIBLE);
        workout_progressbar.setVisibility(View.INVISIBLE);
        current_workout_time_remaining.setVisibility(View.INVISIBLE);
    }

    // pause timer function - pause timer as appropriate on call
    private void pauseTimer() {
        CountDownTimer_Workout.cancel();
        WorkoutTimerRunning = false;
        start_pause_timer.setImageResource(R.drawable.play);
        reset_timer.setVisibility(View.VISIBLE);
    }

    // reset timer function - reset timer as appropriate on call
    private void resetTimer() {
        TimeLeftInMillis_Workout = START_TIME_IN_MILLIS_WORKOUT;
        TimeLeftInMillis_Rest = START_TIME_IN_MILLIS_REST;
        updateCountDownText_Workout();
        updateCountDownText_Rest();
        reset_timer.setVisibility(View.INVISIBLE);
        start_pause_timer.setImageResource(R.drawable.play);
        start_pause_timer.setVisibility(View.VISIBLE);
    }

    // update workout countdown timer text view field as it gets called periodically
    private void updateCountDownText_Workout() {
        int seconds = (int) (TimeLeftInMillis_Workout / 1000) % 60;
        int minutes = (int) ((TimeLeftInMillis_Workout / (1000 * 60)) % 60);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        current_workout_time_remaining.setText(timeLeftFormatted);
    }

    // update rest countdown timer text view field as it gets called periodically
    private void updateCountDownText_Rest() {
        int seconds = (int) (TimeLeftInMillis_Rest / 1000) % 60;
        int minutes = (int) ((TimeLeftInMillis_Rest / (1000 * 60)) % 60);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        current_rest_time_remaining.setText(timeLeftFormatted);
    }

    // update workout progress bar as it gets called periodically
    private void updateWorkoutProgressbar() {
        workout_progressbar.setMax((int) START_TIME_IN_MILLIS_WORKOUT);
        workout_progressbar.setProgress((int) TimeLeftInMillis_Workout);
    }

    // update rest progress bar as it gets called periodically
    private void updateRestProgressbar() {
        rest_progressbar.setMax((int) START_TIME_IN_MILLIS_REST);
        rest_progressbar.setProgress((int) TimeLeftInMillis_Rest);
    }

    // alert use when the current phase finished
    private void alertUser() {
        final MediaPlayer soundAlert = MediaPlayer.create(this, R.raw.magic_marimba);
        soundAlert.start();
    }
}