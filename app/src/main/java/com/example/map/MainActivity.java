package com.example.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MapView testmap;
    private final List<Marker> markers = new ArrayList<>();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testmap = findViewById(R.id.mapview);

        // Set up the map
        testmap.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);

        // Request location permission when the button is clicked
        Button openmap = findViewById(R.id.open_map);
        openmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLocationPermission();
            }
        });
        //map shows zoom in and out
        final MapController mapController = (MapController) testmap.getController();
        testmap.setMultiTouchControls(true);

        final int zoomLevel = 14;
        mapController.setZoom(zoomLevel);

        final double latitude = 123.9650;
        final double longitude = 10.3168;
        final GeoPoint startPoint = new GeoPoint(latitude, longitude);
        mapController.setCenter(startPoint);
    }

    //requests turn on location to get current location of the user
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            showCurrentLocation();
        }
    }
    //shows marker of the users current location
    private void showCurrentLocation() {
        double latitude = 37.7749;
        double longitude = -122.4194;

        MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(testmap);
        myLocationOverlay.enableFollowLocation();
        myLocationOverlay.enableMyLocation();

        GeoPoint currentLocation = new GeoPoint(latitude, longitude);
        myLocationOverlay.setDrawAccuracyEnabled(true);

        Marker marker = new Marker(testmap);
        marker.setPosition(currentLocation);
        marker.setAnchor(0.5f, 1.0f); // Set the anchor to the bottom center of the marker
        testmap.getOverlays().add(marker);
        markers.add(marker);

        marker.setTitle("Current Location");
        marker.setSnippet("Marker Snippet");

        Context context = MainActivity.this;
        marker.setIcon(ContextCompat.getDrawable(context, R.drawable.marker_round));

        testmap.invalidate();
    }
}