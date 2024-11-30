package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lostandfoundapp.data.DatabaseHelperUser;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelperUser db_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText usernameEditText = findViewById(R.id.editTextUsername);
        EditText passwordEdittext = findViewById(R.id.editTextPassword);

        Button loginButton = findViewById(R.id.buttonLogin);
        Button signupButton = findViewById(R.id.buttonSignup);

        db_user = new DatabaseHelperUser(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password =  passwordEdittext.getText().toString();
                boolean result = db_user.fetchUser(username,password);
                if (result == true){
                    Toast.makeText(LoginActivity.this, "Successfully Logged in.", Toast.LENGTH_SHORT).show();
                    // start main activity if the user logged in successfully
                    Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(mainIntent);
                }
                else{
                    // else display error message
                    Toast.makeText(LoginActivity.this, "The user does not exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // open signup activity
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signupIntent);
            }
        });
    }
}