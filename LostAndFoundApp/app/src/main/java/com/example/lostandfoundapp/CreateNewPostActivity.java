package com.example.lostandfoundapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfoundapp.data.DatabaseHelperPost;
import com.example.lostandfoundapp.data.DatabaseHelperUser;
import com.example.lostandfoundapp.model.Post;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CreateNewPostActivity extends AppCompatActivity {
    DatabaseHelperPost db_posts;
    DatabaseHelperUser db_users;

    LocationManager locationManager;

    LocationListener locationListener;

    String locationProvider;

    PlacesClient placesClient;

    String selectedLatLng;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_advert);

        // Initialize views and buttons
        RadioGroup postTypeRadioGroup = findViewById(R.id.radioGroupPostType);

        EditText nameEditText = findViewById(R.id.editTextName);
        EditText phoneEditText = findViewById(R.id.editTextPhone);
        EditText descriptionEditText = findViewById(R.id.editTextDescription);
        EditText dateEditText = findViewById(R.id.editTextDate);
        EditText monthEditText = findViewById(R.id.editTextMonth);
        EditText yearEditText = findViewById(R.id.editTextYear);
        TextView locationTextView = findViewById(R.id.textViewLocation);

        Button postButton = findViewById(R.id.buttonPost);
        Button getCurrentLocationButton = findViewById(R.id.button_GET_CURRENT_LOCATION);

        // Initialize database helpers
        db_posts = new DatabaseHelperPost(this);
        db_users = new DatabaseHelperUser(this);

        // locationTextView.setText("Your Current Location: " + currentLatLng + " || " + "Selected Location: " + (selectedLatLng != null ? selectedLatLng : "none"));

        // ------------------------------------------------------ Google Maps Location API (Select the current location of the device/user) --------------------------------------------------

        // Initialize location manager
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        // Define criteria for location provider
        Criteria criteria = new Criteria();
        locationProvider = locationManager.getBestProvider(criteria, false);

        // Location listener for receiving location updates
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Handle location updates
                String Lat = String.valueOf(location.getLatitude());
                String Lng = String.valueOf(location.getLongitude());
                selectedLatLng = Lat + "," + Lng;
                locationTextView.setText("Current Location: " + selectedLatLng);
            }

            public void onProviderEnabled(String provider) {
                // Handle provider enabled
            }

            public void onProviderDisabled(String provider) {
                // Handle provider disabled
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                // If the status indicates that the location provider is out of service or temporarily unavailable call this function
            }
        };

        // Set up click listener for getting current location
        getCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Request location permission if not granted
                if (ActivityCompat.checkSelfPermission(CreateNewPostActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CreateNewPostActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(CreateNewPostActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    // return;
                }
                // Request single location update
                else
                {
                    locationManager.requestSingleUpdate(locationProvider, locationListener, Looper.myLooper());
                }
            }
        });

        // ------------------------------------------------------------------- Google Maps Places API (Select any location user wants) ------------------------------------------------------------------------------

        // obtain the API Key
        String API_KEY = this.getResources().getString(R.string.API_KEY);

        // Initialize Places API client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), API_KEY);
        }

        placesClient = Places.createClient(this);

        // Set up AutocompleteSupportFragment for place selection
        final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autoCompleteFragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));

        // Set up PlaceSelectionListener
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {

            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng latLng = place.getLatLng();
                String Lat = String.valueOf(latLng.latitude);
                String Lng = String.valueOf(latLng.longitude);
                selectedLatLng = Lat + "," + Lng;
                locationTextView.setText("Selected Location: " + selectedLatLng);
            }
        });

        // Set up click listener for posting
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selected = postTypeRadioGroup.getCheckedRadioButtonId();
                RadioButton radio = findViewById(selected);

                // Get selected post type
                String postType = radio.getText().toString();

                // get current active user
                String userid = String.valueOf(UserManager.getInstance().getCurrentUserId());

                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String month = monthEditText.getText().toString();
                String year = yearEditText.getText().toString();

                // set the location to the selected location or the current location of the device (as latitude and longitude)
                String location = selectedLatLng;

                // create post and insert into database
                long result = db_posts.insertPost(new Post(postType, userid, name, phone, description, date, month, year, location));
                if (result > 0) {
                    // display message if the post was created successfully
                    Toast.makeText(CreateNewPostActivity.this, "Posted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // else display error
                    Toast.makeText(CreateNewPostActivity.this, "Post error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}