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

public class ActivityConvertWeight extends AppCompatActivity {

    static String sourceUnit;
    static String destinationUnit;
    static double convertedValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_weight);

        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);

        ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(this, R.array.weight_units, android.R.layout.simple_spinner_item);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(fromAdapter);
        spinnerFrom.setOnItemSelectedListener(new FromSpinnerClass());

        ArrayAdapter<CharSequence> toAdapter = ArrayAdapter.createFromResource(this, R.array.weight_units, android.R.layout.simple_spinner_item);
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
        Toast.makeText(ActivityConvertWeight.this, value, Toast.LENGTH_SHORT).show();
    }

    private void convertUserInput() {

        EditText userInput = (EditText) findViewById(R.id.editTextNumberW);

        try {
            int inputValue = Integer.parseInt(userInput.getText().toString());

            if (sourceUnit.equals(destinationUnit)){
                convertedValue = inputValue;
            }

            // converting from pounds
            if (sourceUnit.equals("pound") && destinationUnit.equals("kilograms")){
                convertedValue = inputValue * 0.453592;
            }
            if (sourceUnit.equals("pound") && destinationUnit.equals("grams")){
                convertedValue = (inputValue * 0.453592) * 1000;
            }
            if (sourceUnit.equals("pound") && destinationUnit.equals("milligrams")){
                convertedValue = (inputValue * 0.453592) * 1000 * 1000;
            }
            if (sourceUnit.equals("pound") && destinationUnit.equals("ounce")){
                convertedValue = ((inputValue * 0.453592) * 1000) / 28.3495;
            }
            if (sourceUnit.equals("pound") && destinationUnit.equals("ton")){
                convertedValue = (inputValue * 0.453592) / 907.185;
            }

            // converting from ounce
            if (sourceUnit.equals("ounce") && destinationUnit.equals("kilograms")){
                convertedValue = (inputValue * 28.3495) / 1000;
            }
            if (sourceUnit.equals("ounce") && destinationUnit.equals("grams")){
                convertedValue = inputValue * 28.3495;
            }
            if (sourceUnit.equals("ounce") && destinationUnit.equals("milligrams")){
                convertedValue = (inputValue * 28.3495) * 1000;
            }
            if (sourceUnit.equals("ounce") && destinationUnit.equals("pound")){
                convertedValue = ((inputValue * 28.3495) / 1000) / 0.453592;
            }
            if (sourceUnit.equals("ounce") && destinationUnit.equals("ton")){
                convertedValue = (inputValue * 28.3495) / 1000 / 907.185;
            }

            // converting from ton
            if (sourceUnit.equals("ton") && destinationUnit.equals("kilograms")){
                convertedValue = inputValue * 907.185;
            }
            if (sourceUnit.equals("ton") && destinationUnit.equals("grams")){
                convertedValue = inputValue * 907.185 * 1000;
            }
            if (sourceUnit.equals("ton") && destinationUnit.equals("milligrams")){
                convertedValue = inputValue * 907.185 * 1000 * 1000;
            }
            if (sourceUnit.equals("ton") && destinationUnit.equals("pound")){
                convertedValue = (inputValue * 907.185) / 0.453592;
            }
            if (sourceUnit.equals("ton") && destinationUnit.equals("ounce")){
                convertedValue = (inputValue * 907.185) * 1000 / 28.3495;
            }

            // converting from grams
            if (sourceUnit.equals("grams") && destinationUnit.equals("kilograms")){
                convertedValue = inputValue / 1000;
            }
            if (sourceUnit.equals("grams") && destinationUnit.equals("ton")){
                convertedValue = (inputValue / 1000) / 907.185;
            }
            if (sourceUnit.equals("grams") && destinationUnit.equals("milligrams")){
                convertedValue = inputValue * 1000;
            }
            if (sourceUnit.equals("grams") && destinationUnit.equals("pound")){
                convertedValue = (inputValue / 1000) / 0.453592;
            }
            if (sourceUnit.equals("grams") && destinationUnit.equals("ounce")){
                convertedValue = inputValue / 28.3495;
            }

            // converting from kilograms
            if (sourceUnit.equals("kilograms") && destinationUnit.equals("ton")){
                convertedValue = inputValue / 907.185;
            }
            if (sourceUnit.equals("kilograms") && destinationUnit.equals("grams")){
                convertedValue = inputValue * 1000;
            }
            if (sourceUnit.equals("kilograms") && destinationUnit.equals("milligrams")){
                convertedValue = inputValue * 1000 * 1000;
            }
            if (sourceUnit.equals("kilograms") && destinationUnit.equals("pound")){
                convertedValue = inputValue / 0.453592;
            }
            if (sourceUnit.equals("kilograms") && destinationUnit.equals("ounce")){
                convertedValue = (inputValue * 1000) / 28.3495;
            }

            // converting from milligrams
            if (sourceUnit.equals("milligrams") && destinationUnit.equals("kilograms")){
                convertedValue = inputValue * 907.185;
            }
            if (sourceUnit.equals("milligrams") && destinationUnit.equals("grams")){
                convertedValue = inputValue / 1000;
            }
            if (sourceUnit.equals("milligrams") && destinationUnit.equals("ton")){
                convertedValue = ((inputValue / 1000) / 1000) / 907.185;
            }
            if (sourceUnit.equals("milligrams") && destinationUnit.equals("pound")){
                convertedValue = ((inputValue / 1000) / 1000) / 0.453592;
            }
            if (sourceUnit.equals("milligrams") && destinationUnit.equals("ounce")){
                convertedValue = (inputValue / 1000) / 28.3495;
            }

            TextView results = (TextView) findViewById(R.id.result);
            results.setText(String.valueOf(convertedValue));
        } catch (Exception NumberFormatException) {
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