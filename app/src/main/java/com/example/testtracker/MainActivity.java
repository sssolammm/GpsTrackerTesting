package com.example.testtracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int INITIAL_REQUEST = 1337;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
            Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
            mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500,
                    1, mLocationListener);
            turnGPSOn();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500,
                            1, mLocationListener);
                }
            }
        } else {
            // Permission was denied. Display an error message.
        }
    }

    public void turnGPSOn() {
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "gps enabled", Toast.LENGTH_SHORT).show();
//            if (onGpsListener != null) {
//                onGpsListener.gpsStatus(true);
//            }
        } else {
            Toast.makeText(this, "gps disabled", Toast.LENGTH_SHORT).show();
//            mSettingsClient
//                    .checkLocationSettings(mLocationSettingsRequest)
//                    .addOnSuccessListener((Activity) context, new OnSuccessListener<LocationSettingsResponse>() {
//                        @SuppressLint("MissingPermission")
//                        @Override
//                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
////  GPS is already enable, callback GPS status through listener
//                            if (onGpsListener != null) {
//                                onGpsListener.gpsStatus(true);
//                            }
//                        }
//                    })
//                    .addOnFailureListener((Activity) context, new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            int statusCode = ((ApiException) e).getStatusCode();
//                            switch (statusCode) {
//                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                                    try {
//                                        // Show the dialog by calling startResolutionForResult(), and check the
//                                        // result in onActivityResult().
//                                        ResolvableApiException rae = (ResolvableApiException) e;
//                                        rae.startResolutionForResult((Activity) context, AppConstants.GPS_REQUEST);
//                                    } catch (IntentSender.SendIntentException sie) {
//                                        Log.i(TAG, "PendingIntent unable to execute request.");
//                                    }
//                                    break;
//                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                                    String errorMessage = "Location settings are inadequate, and cannot be " +
//                                            "fixed here. Fix in Settings.";
//                                    Log.e(TAG, errorMessage);
//                                    Toast.makeText((Activity) context, errorMessage, Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
        }
    }
    public interface onGpsListener {
        void gpsStatus(boolean isGPSEnable);
    }


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Toast.makeText(MainActivity.this, String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
