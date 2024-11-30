package com.example.lostandfoundapp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.lostandfoundapp.data.DatabaseHelperPost;
import com.example.lostandfoundapp.model.Post;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.lostandfoundapp.databinding.ActivityMapsBinding;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    DatabaseHelperPost db_post;

    List<Post> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the DatabaseHelperPost
        db_post = new DatabaseHelperPost(this);
        // Retrieve the list of posts from the database
        list = db_post.fetchPosts();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (Post post : list) {
            String latLang = post.getLocation();
            String[] latLngArray = latLang.split(",");
            double latitude = Double.parseDouble(latLngArray[0]);
            double longitude = Double.parseDouble(latLngArray[1]);

            LatLng location = new LatLng(latitude, longitude);

            // Add a marker for each location
            String postType = post.getPost_type();
            // set the color of the marker to red if the post type is lost
            if(postType.equals("Lost")){
                mMap.addMarker(new MarkerOptions().position(location).title(post.getDescription()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
            // set the color of the marker to green if the post type is found
            if(postType.equals("Found")){
                mMap.addMarker(new MarkerOptions().position(location).title(post.getDescription()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
        }

        // Move the camera to the first location in the list
        if (!list.isEmpty()) {
            Post firstPost = list.get(0);
            String firstPostLocation = firstPost.getLocation();
            String[] firstPostLocationArray = firstPostLocation.split(",");
            double firstLatitude = Double.parseDouble(firstPostLocationArray[0]);
            double firstLongitude = Double.parseDouble(firstPostLocationArray[1]);
            LatLng firstLocation = new LatLng(firstLatitude, firstLongitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 15));
        }
    }
}