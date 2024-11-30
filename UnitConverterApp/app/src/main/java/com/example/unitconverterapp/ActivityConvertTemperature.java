package com.example.unitconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityConvertTemperature extends AppCompatActivity {

    static String sourceUnit;
    static String destinationUnit;
    static double convertedValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_temperature);

        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);

        ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(this, R.array.temperature_units, android.R.layout.simple_spinner_item);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(fromAdapter);
        spinnerFrom.setOnItemSelectedListener(new ActivityConvertTemperature.FromSpinnerClass());

        ArrayAdapter<CharSequence> toAdapter = ArrayAdapter.createFromResource(this, R.array.temperature_units, android.R.layout.simple_spinner_item);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(toAdapter);
        spinnerTo.setOnItemSelectedListener(new ActivityConvertTemperature.ToSpinnerClass());

        Button button = (Button) findViewById(R.id.convert);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("BUTTONS", "User tapped the Convert Button");
                convertUserInput();
            }
        });

        Intent valueFromIntent = getIntent();
        String value = valueFromIntent.getStringExtra("activity_name");
        Toast.makeText(ActivityConvertTemperature.this, value, Toast.LENGTH_SHORT).show();
    }

    private void convertUserInput() {

        EditText userInput = (EditText) findViewById(R.id.editTextNumberW);

        try {
            int inputValue = Integer.parseInt(userInput.getText().toString());

            if (sourceUnit.equals(destinationUnit)){
                convertedValue = inputValue;
            }

            // converting from C
            if (sourceUnit.equals("Celsius") && destinationUnit.equals("Fahrenheit")){
                convertedValue = (inputValue * 1.8) + 32;
            }
            if (sourceUnit.equals("Celsius") && destinationUnit.equals("Kelvin")){
                convertedValue = inputValue + 273.15;
            }

            // converting from F
            if (sourceUnit.equals("Fahrenheit") && destinationUnit.equals("Celsius")){
                convertedValue = (inputValue - 32) / 1.8;
            }
            if (sourceUnit.equals("Fahrenheit") && destinationUnit.equals("Kelvin")){
                convertedValue = ((inputValue - 32) / 1.8) + 273.15;
            }

            // converting from K
            if (sourceUnit.equals("Kelvin") && destinationUnit.equals("Fahrenheit")){
                convertedValue = ((inputValue - 273.15) * 18) + 32;
            }
            if (sourceUnit.equals("Kelvin") && destinationUnit.equals("Celsius")){
                convertedValue = inputValue - 273.15;
            }

            TextView results = (TextView) findViewById(R.id.result);
            results.setText(String.valueOf(convertedValue));
        }
        catch (Exception NumberFormatException) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "Please enter a inputValue to convert", duration);
            toast.show();
        }
    }

    private class FromSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            sourceUnit = adapterView.getItemAtPosition(i).toString();
            //Toast.makeText(adapterView.getContext(), "From: " + sourceUnit,Toast.LENGTH_SHORT).show();
            //Log.d("SPINNERS", "User selected " + sourceUnit + " from the spinner");
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class ToSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            destinationUnit = adapterView.getItemAtPosition(i).toString();
            //Toast.makeText(adapterView.getContext(), "To: " + destinationUnit,Toast.LENGTH_SHORT).show();
            //Log.d("SPINNERS", "User selected " + destinationUnit + " from the spinner");
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}