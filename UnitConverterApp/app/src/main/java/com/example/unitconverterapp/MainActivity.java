/*
Student Name: Brian Caldera
Campus: Burwood
Student ID: 221033693
Unit: SIT305
 */

package com.example.unitconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openActivityConvertLength();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openActivityConvertWeight();
            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openActivityConvertTemperature();
            }
        });
    }

    public void openActivityConvertLength() {
        Intent intent = new Intent(this, ActivityConvertLength.class);
        intent.putExtra("activity_name", "Convert Length");
        startActivity(intent);
    }

    public void openActivityConvertWeight() {
        Intent intent = new Intent(this, ActivityConvertWeight.class);
        intent.putExtra("activity_name", "Convert Weight");
        startActivity(intent);
    }

    private void openActivityConvertTemperature() {
        Intent intent = new Intent(this, ActivityConvertTemperature.class);
        intent.putExtra("activity_name", "Convert Temperature");
        startActivity(intent);
    }
}