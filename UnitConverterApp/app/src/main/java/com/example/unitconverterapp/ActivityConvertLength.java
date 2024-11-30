package com.example.unitconverterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import org.w3c.dom.Text;

import java.text.BreakIterator;

public class ActivityConvertLength extends AppCompatActivity {

    static String sourceUnit;
    static String destinationUnit;
    static double convertedValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_length);

        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);

        ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(this, R.array.length_units, android.R.layout.simple_spinner_item);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(fromAdapter);
        spinnerFrom.setOnItemSelectedListener(new FromSpinnerClass());

        ArrayAdapter<CharSequence> toAdapter = ArrayAdapter.createFromResource(this, R.array.length_units, android.R.layout.simple_spinner_item);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(toAdapter);
        spinnerTo.setOnItemSelectedListener(new ToSpinnerClass());

        Button button = (Button) findViewById(R.id.convert);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.d("BUTTONS", "User tapped the Convert Button");
                convertUserInput();
            }
        });

        Intent valueFromIntent = getIntent();
        String value = valueFromIntent.getStringExtra("activity_name");
        Toast.makeText(ActivityConvertLength.this, value, Toast.LENGTH_SHORT).show();
    }

    private void convertUserInput() {

        EditText userInput = (EditText) findViewById(R.id.editTextNumber);

        try {
            int inputValue = Integer.parseInt(userInput.getText().toString());

            if (sourceUnit.equals(destinationUnit)){
                convertedValue = inputValue;
            }

            // converting from inches
            if (sourceUnit.equals("inch") && destinationUnit.equals("foot")){
                convertedValue = (inputValue * 2.54) / 30.48;
            }
            if (sourceUnit.equals("inch") && destinationUnit.equals("yard")){
                convertedValue = (inputValue * 2.54) / 91.44;
            }
            if (sourceUnit.equals("inch") && destinationUnit.equals("mile")){
                convertedValue = (inputValue * 2.54) / (1.60934*100*1000);
            }
            if (sourceUnit.equals("inch") && destinationUnit.equals("centimeters")){
                convertedValue = inputValue * 2.54;
            }
            if (sourceUnit.equals("inch") && destinationUnit.equals("meters")){
                convertedValue = (inputValue * 2.54) / 100;
            }
            if (sourceUnit.equals("inch") && destinationUnit.equals("kilometers")){
                convertedValue = (inputValue * 2.54) / (100*1000);
            }

            // converting from foot
            if (sourceUnit.equals("foot") && destinationUnit.equals("inch")){
                convertedValue = (inputValue * 30.48) / 2.54;
            }
            if (sourceUnit.equals("foot") && destinationUnit.equals("yard")){
                convertedValue = (inputValue * 30.48) / 91.44;
            }
            if (sourceUnit.equals("foot") && destinationUnit.equals("mile")){
                convertedValue = (inputValue * 30.48) / (1.60934*100*1000);
            }
            if (sourceUnit.equals("foot") && destinationUnit.equals("centimeters")){
                convertedValue = inputValue * 30.48;
            }
            if (sourceUnit.equals("foot") && destinationUnit.equals("meters")){
                convertedValue = (inputValue * 30.48) / 100;
            }
            if (sourceUnit.equals("foot") && destinationUnit.equals("kilometers")){
                convertedValue = (inputValue * 30.48) / (100*1000);
            }

            // converting from yard
            if (sourceUnit.equals("yard") && destinationUnit.equals("inch")){
                convertedValue = (inputValue * 91.44) / 2.54;
            }
            if (sourceUnit.equals("yard") && destinationUnit.equals("foot")){
                convertedValue = (inputValue * 91.44) / 30.48;
            }
            if (sourceUnit.equals("yard") && destinationUnit.equals("mile")){
                convertedValue = (inputValue * 91.44) / (1.60934*100*1000);
            }
            if (sourceUnit.equals("yard") && destinationUnit.equals("centimeters")){
                convertedValue = inputValue * 91.44;
            }
            if (sourceUnit.equals("yard") && destinationUnit.equals("meters")){
                convertedValue = (inputValue * 91.44) / 100;
            }
            if (sourceUnit.equals("yard") && destinationUnit.equals("kilometers")){
                convertedValue = (inputValue * 91.44) / (100*1000);
            }

            // converting from mile
            if (sourceUnit.equals("mile") && destinationUnit.equals("inch")){
                convertedValue = (inputValue * (1.60934*100*1000)) / 2.54;
            }
            if (sourceUnit.equals("mile") && destinationUnit.equals("foot")){
                convertedValue = (inputValue * (1.60934*100*1000)) / 30.48;
            }
            if (sourceUnit.equals("mile") && destinationUnit.equals("yard")){
                convertedValue = (inputValue * (1.60934*100*1000)) / 91.44;
            }
            if (sourceUnit.equals("mile") && destinationUnit.equals("centimeters")){
                convertedValue = inputValue * (1.60934*100*1000);
            }
            if (sourceUnit.equals("mile") && destinationUnit.equals("meters")){
                convertedValue = inputValue * (1.60934*100*1000) * 100;
            }
            if (sourceUnit.equals("mile") && destinationUnit.equals("kilometers")){
                convertedValue = inputValue * 1.60934;
            }

            // converting from cm
            if (sourceUnit.equals("centimeters") && destinationUnit.equals("inch")){
                convertedValue = inputValue / 2.54;
            }
            if (sourceUnit.equals("centimeters") && destinationUnit.equals("foot")){
                convertedValue = inputValue / 30.48;
            }
            if (sourceUnit.equals("centimeters") && destinationUnit.equals("yard")){
                convertedValue = inputValue / 91.44;
            }
            if (sourceUnit.equals("centimeters") && destinationUnit.equals("mile")){
                convertedValue = inputValue / (1.60934*100*1000);
            }
            if (sourceUnit.equals("centimeters") && destinationUnit.equals("meters")){
                convertedValue = inputValue / 100;
            }
            if (sourceUnit.equals("centimeters") && destinationUnit.equals("kilometers")){
                convertedValue = inputValue / (100*1000);
            }

            // converting from m
            if (sourceUnit.equals("meters") && destinationUnit.equals("inch")){
                convertedValue = (inputValue * 2.54) / 100;
            }
            if (sourceUnit.equals("meters") && destinationUnit.equals("foot")){
                convertedValue = (inputValue * 30.48) / 100;
            }
            if (sourceUnit.equals("meters") && destinationUnit.equals("yard")){
                convertedValue = (inputValue * 91.44) / 100;
            }
            if (sourceUnit.equals("meters") && destinationUnit.equals("mile")){
                convertedValue = (inputValue * (1.60934*100*1000)) / 100;
            }
            if (sourceUnit.equals("meters") && destinationUnit.equals("centimeters")){
                convertedValue = inputValue * 100;
            }
            if (sourceUnit.equals("meters") && destinationUnit.equals("kilometers")){
                convertedValue = inputValue / 1000;
            }

            // converting from km
            if (sourceUnit.equals("kilometers") && destinationUnit.equals("inch")){
                convertedValue = (inputValue * (1.60934*100*1000)) / 2.54;
            }
            if (sourceUnit.equals("kilometers") && destinationUnit.equals("foot")){
                convertedValue = (inputValue * (1.60934*100*1000)) / 30.48;
            }
            if (sourceUnit.equals("kilometers") && destinationUnit.equals("yard")){
                convertedValue = (inputValue * (1.60934*100*1000)) / 91.44;
            }
            if (sourceUnit.equals("kilometers") && destinationUnit.equals("mile")){
                convertedValue = inputValue / (1.60934*100*1000);
            }
            if (sourceUnit.equals("kilometers") && destinationUnit.equals("centimeters")){
                convertedValue = inputValue * (100 * 1000);
            }
            if (sourceUnit.equals("kilometers") && destinationUnit.equals("meters")){
                convertedValue = inputValue * 1000;
            }

            //Log.d("RESULT", inputValue + sourceUnit + " = " + String.valueOf(convertedValue) + destinationUnit);
            TextView results = (TextView) findViewById(R.id.result);
            results.setText(String.valueOf(convertedValue));
        }
        catch (Exception NumberFormatException){
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "Please enter a inputValue to convert", duration);
            toast.show();
        }

        //Log.d("User Input:", String.valueOf(inputValue));
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