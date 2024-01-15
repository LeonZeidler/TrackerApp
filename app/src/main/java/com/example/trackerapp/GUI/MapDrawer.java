package com.example.trackerapp.GUI;

import static com.example.trackerapp.Statistics.Storage.distance;
import static com.example.trackerapp.Statistics.Storage.locations;

import android.Manifest;


import android.content.IntentSender;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.os.Looper;

import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.trackerapp.R;

import com.example.trackerapp.Statistics.StatisticCollector;
import com.example.trackerapp.Statistics.Statistics;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;

import java.util.Timer;
import java.util.TimerTask;

public class MapDrawer extends FragmentActivity implements OnMapReadyCallback {

    Button statsBtn;
    private GoogleMap map;
    Location currentLocation= new Location("Default") ;
    FusedLocationProviderClient fusedLocationProviderClient;
    Statistics statistics= new StatisticCollector();
    private final static int ReQUEST_CODE = 1001;
    Polyline polyline = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.MapID);
        assert mapFragment != null;
        mapFragment.getMapAsync(MapDrawer.this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                getLocation();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 5000);

        // Setup für den Seitenwechsel Knopf
        statsBtn = findViewById(R.id.ResetButtonId);
        statsBtn.setOnClickListener(v -> {
                statistics.reset();
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

       startGetLocation();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            Toast.makeText(this, "Permission is required", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapDrawer.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {


                double lat = location.getLatitude();
                double lng = location.getLongitude();
                if(currentLocation.distanceTo(location)<200){
                distance=distance+ currentLocation.distanceTo(location);
                }
                LatLng latLng = new LatLng(lat, lng);
                locations.add(latLng);
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.animateCamera(CameraUpdateFactory.zoomTo(17));
                map.clear();

                map.addMarker(new MarkerOptions().position(latLng).title("you"));
                if (polyline != null) polyline.remove();
                PolylineOptions polylineOptions = new PolylineOptions().addAll(locations).color(0xffff0000).clickable(false);
                polyline = map.addPolyline(polylineOptions);
                currentLocation=location;
            }
        });
    }

  public void startGetLocation() {
        LocationRequest locationRequest = new LocationRequest.Builder(3000).setGranularity(Granularity.GRANULARITY_FINE).setPriority(Priority.PRIORITY_HIGH_ACCURACY).setMinUpdateDistanceMeters(75).build();
        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest).addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {

            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                if (task.isSuccessful()) {
                    if (ActivityCompat.checkSelfPermission(MapDrawer.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapDrawer.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MapDrawer.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        return;
                    }
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                } else {
                    if (task.getException() instanceof ResolvableApiException) {

                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) task.getException();
                            resolvableApiException.startResolutionForResult(MapDrawer.this, ReQUEST_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            throw new RuntimeException(e);
                        }

                    } else {

                    }
                }
            }

        });
    }


    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            currentLocation = locationResult.getLastLocation();
            LatLng currlatlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            locations.add(currlatlng);

        }

    };


}
