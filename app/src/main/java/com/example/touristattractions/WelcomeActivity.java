package com.example.touristattractions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class WelcomeActivity extends AppCompatActivity
{
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getDeviceLocation();
            }
        });
    }
    private void getDeviceLocation() {
        // Create FusedLocationProviderClient instance
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Check if location services are enabled
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                                       .addOnSuccessListener(location -> {
                                           if (location != null) {
                                               double latitude = location.getLatitude();
                                               double longitude = location.getLongitude();

                                               Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                               intent.putExtra("lat", latitude);
                                               intent.putExtra("long", longitude);
                                               startActivity(intent);
                                           } else {
                                               Toast.makeText(WelcomeActivity.this, "Location is null", Toast.LENGTH_SHORT).show();
                                               getDeviceCurrentLocation();
                                           }
                                       })
                                       .addOnFailureListener(e -> {
                                           // Failed to get location, handle accordingly
                                       });
        } else {
            requestLocationPermission();
        }
    }
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private void requestLocationPermission() {
        {
            // Request the location permission
            ActivityCompat.requestPermissions(this,
                                              new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                              REQUEST_LOCATION_PERMISSION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                if (isLocationServicesEnabled())
                {
                    getDeviceLocation();
                }
                else {
                    showLocationServicesDialog();
                }
            } else {
                Toast.makeText(WelcomeActivity.this, "Location permission not granted", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    private boolean isLocationServicesEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    private void showLocationServicesDialog() {
        // Show a dialog to prompt the user to enable location services
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Location services are disabled. Do you want to enable them?")
               .setPositiveButton("Yes", (dialog, which) -> {
                   // Redirect to the device's location settings page
                   Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                   startActivity(intent);
               })
               .setNegativeButton("No", (dialog, which) -> {
                   // Handle accordingly if the user chooses not to enable location services
               })
               .show();
    }

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    private void getDeviceCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Check if location services are enabled
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isLocationEnabled
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000) // Update interval in milliseconds
                    .setFastestInterval(2000); // Fastest update interval in milliseconds

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        // Stop location updates
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        intent.putExtra("lat", latitude);
                        intent.putExtra("long", longitude);
                        startActivity(intent);
                        finish(); // Optional: Finish the WelcomeActivity to prevent going back to it
                    } else {
                        Toast.makeText(WelcomeActivity.this, "Location is null", Toast.LENGTH_SHORT).show();
                        showLocationServicesDialog();
                    }
                }
            };

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            requestLocationPermission();
        }
    }

    private void stopLocationUpdates() {
        if (fusedLocationProviderClient != null && locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }
}