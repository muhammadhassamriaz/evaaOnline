package com.ciesto.evaafashion.Other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSTracker implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, LocationListener {

    double lat, lng;
    Location mLastLocation;
    final static int REQUEST_LOCATION = 199;
    // check GPS enable or not
    private LocationManager locationManager;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Context context;
    GoogleApiClient mGoogleApiClient;
    private GoogleApiClient googleApiClient;
    private static final long MIN_TIME = 10000; //10000 millisecond = 10 second
    private static final float MIN_DISTANCE = 10; //1000 meter = 1km

    public String city, address1, state, pincode, fullAdd,Country;
    public static String Latitude,Longitude;
    ProgressBar my_progressBar;
    EditText edt_building_name,edt_pincode,edt_country,edt_state,edt_city;


    public GPSTracker(Activity activity, ProgressBar my_progressBar, EditText edt_building_name, EditText edt_pincode, EditText edt_country, EditText edt_state, EditText edt_city) {

        this.context = activity;
        this.my_progressBar = my_progressBar;
        this.edt_building_name = edt_building_name;
        this.edt_pincode = edt_pincode;
        this.edt_country=edt_country;
        this.edt_state=edt_state;
        this.edt_city=edt_city;
    }


    @SuppressLint("MissingPermission")
    public void GetLocation() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();


        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!hasGPSDevice(context)) {
            Toast.makeText(context, "Gps not Supported", Toast.LENGTH_SHORT).show();
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(context)) {
            Log.e("keshav", "Gps not enabled");
            enableLoc();
        } else {
            Log.e("keshav", "Gps already enabled");

               my_progressBar.setVisibility(View.VISIBLE);

            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();

            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
            mLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (mLastLocation != null)
            {
                Log.e("latitude==", String.valueOf(mLastLocation.getLatitude()));
                Latitude=String.valueOf(mLastLocation.getLatitude());
                Longitude=String.valueOf(mLastLocation.getLongitude());
                new MyAsyncTask(mLastLocation.getLatitude(), mLastLocation.getLongitude()).execute();
            }
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }


    private void enableLoc() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();

                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location
                            // requests here.
                            Log.e("onResult", "SUCCESS");
                            // flag = 0;
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult((Activity) context, REQUEST_LOCATION);
                                // finish();
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.

                            Log.e("onResult", "UNAVAILABLE");
                            break;

                    }
                }
            });
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        new MyAsyncTask(lat, lng).execute();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
    }


    class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress;
        double lat, lng;

        public MyAsyncTask(double latitude, double longitude) {
            this.lat = latitude;
            this.lng = longitude;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progress = ProgressDialog.show(context, "Loading data", "Please wait...");
            my_progressBar.setVisibility(View.VISIBLE);
        }

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... voids) {
            String fullAdd = null;
            List<Address> addresses = null;
            Log.e("lati && lng==", String.valueOf(lat) + "==" + lng);
            try {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                addresses = geocoder.getFromLocation(lat, lng, 1);
                if (addresses.size() > 0) {
                    //my_progressBar.setVisibility(View.GONE);
                    Address address = addresses.get(0);
                    fullAdd = address.getAddressLine(0);

                    city = address.getSubAdminArea();
                    pincode = address.getPostalCode();
                    address1 = address.getAddressLine(0);
                    state = address.getAdminArea();
                    Country=address.getCountryName();

                    /*Log.e("Address==", fullAdd);
                    Log.e("city==",city);
                    Log.e("state==",state);
                    Log.e("pincode==",pincode);*/

                }


            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //progress.dismiss();
            my_progressBar.setVisibility(View.GONE);

            edt_building_name.setText(address1);
            edt_pincode.setText(pincode);
            edt_country.setText(Country);
            edt_state.setText(state);
            edt_city.setText(city);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
