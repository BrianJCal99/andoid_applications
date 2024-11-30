package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lostandfoundapp.data.DatabaseHelperUser;
import com.example.lostandfoundapp.model.User;

public class SignupActivity extends AppCompatActivity {
    DatabaseHelperUser db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText usernameEditText = findViewById(R.id.editTextUsername);
        EditText passwordEdittext = findViewById(R.id.editTextPassword);
        EditText confirmPasswordEdittext = findViewById(R.id.editTextConfirmPassword);

        Button signupButton = findViewById(R.id.buttonSignup);

        db = new DatabaseHelperUser(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEdittext.getText().toString();
                String confirmPassword = confirmPasswordEdittext.getText().toString();

                // create new user account
                if (password.equals(confirmPassword)){
                    long result = db.insertUser(new User(username,password));
                    if(result>0){
                        // display message if user created their account successfully
                        Toast.makeText(SignupActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        // else display error message
                        Toast.makeText(SignupActivity.this, "Signup error", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    // let user know if the passwords don't match
                    Toast.makeText(SignupActivity.this, "Two passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}