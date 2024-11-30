package com.example.lostandfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button createNewAdvertButton = findViewById(R.id.button_CREATE_A_NEW_ADVERT);
        Button showAllItemsButton = findViewById(R.id.button_SHOW_ALL_LOST_AND_FOUND_ITEMS);
        Button showOnMapButton = findViewById(R.id.button_SHOW_ON_MAP);

        TextView usernameTextView = findViewById(R.id.textViewUsername);
        String username = UserManager.getInstance().getCurrentUserName();

        usernameTextView.setText("Welcome " + username.substring(0, 1).toUpperCase() + username.substring(1));

        createNewAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open create new post activity
                Intent createNewPostIntent = new Intent(HomeActivity.this, CreateNewPostActivity.class);
                startActivity(createNewPostIntent);
            }
        });

        showAllItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open show all posts activity
                Intent showAllItemsIntent = new Intent(HomeActivity.this, ShowAllItemsActivity.class);
                showAllItemsIntent.putExtra("Username", username);
                startActivity(showAllItemsIntent);
            }
        });

        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showOnMapIntent = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(showOnMapIntent);
            }
        });
    }
}