package com.example.workpouttimerappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button begin_phase;

    private EditText minutes_workout;

    private EditText seconds_workout;

    private EditText minutes_rest;

    private EditText seconds_rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        begin_phase = (Button) findViewById(R.id.begin_phase);
        minutes_workout = (EditText) findViewById(R.id.editTextTime_Minutes_Workout);
        seconds_workout = (EditText) findViewById(R.id.editTextTime_Seconds_Workout);
        minutes_rest = (EditText) findViewById(R.id.editTextTime_Minutes_Rest);
        seconds_rest = (EditText) findViewById(R.id.editTextTime_Seconds_Rest);

        // open activity TimerActivity on click
        begin_phase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openActivityActivityTimer();
            }
        });
    }

    // start activity TimerActivity and pass values
    private void openActivityActivityTimer() {
        Intent intent = new Intent(MainActivity.this, ActivityTimer.class);

        // input validation
        try{
            long W_Minutes = Long.parseLong(minutes_workout.getText().toString());
            long W_Seconds = Long.parseLong(seconds_workout.getText().toString());

            if (W_Minutes == 0 && W_Seconds == 0){
                Toast.makeText(this, "Value can not be '0'", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                intent.putExtra("workoutMinutes", W_Minutes);
                intent.putExtra("workoutSeconds", W_Seconds);
            }
        }
        catch (NumberFormatException exception){
            Toast.makeText(this, "Field can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // input validation
        try{
            long R_Minutes = Long.parseLong(minutes_rest.getText().toString());
            long R_Seconds = Long.parseLong(seconds_rest.getText().toString());

            if (R_Minutes == 0 && R_Seconds == 0){
                Toast.makeText(this, "Value can not be '0'", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                intent.putExtra("restMinutes", R_Minutes);
                intent.putExtra("restSeconds", R_Seconds);
            }
        }
        catch (NumberFormatException exception){
            long R_Minutes = 0;
            intent.putExtra("restMinutes", R_Minutes);

            long R_Seconds = 0;
            intent.putExtra("restSeconds", R_Seconds);
        }

        startActivity(intent);
    }
}