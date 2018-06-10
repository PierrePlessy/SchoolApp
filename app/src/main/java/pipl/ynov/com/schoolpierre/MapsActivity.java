package pipl.ynov.com.schoolpierre;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;
import java.util.List;

import pipl.ynov.com.schoolpierre.Interfaces.IVolleyCallback;
import pipl.ynov.com.schoolpierre.Models.School;
import pipl.ynov.com.schoolpierre.Services.SchoolService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public ImageButton returnButton;
    public ImageButton listButton;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public SchoolService schoolService;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;
    public static final int REQUEST_LOCATION_CODE = 99 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(genericOnClickListener);
        listButton = findViewById(R.id.list_button);
        listButton.setOnClickListener(genericOnClickListener);

        schoolService = new SchoolService(this);

    }

    View.OnClickListener genericOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.return_button:
                    // Go to Menu
                    Intent intent = new Intent(MapsActivity.this, MenuActivity.class);
                    startActivity(intent);
                    break;
                case R.id.list_button:
                    // Go to list School
                    intent = new Intent(MapsActivity.this, ListSchoolActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        setSchoolMarkerInMap();

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastLocation = location;
        if(currentLocationMarker != null)
        {
            currentLocationMarker.remove();

        }
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.myposicone));
        currentLocationMarker = mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f), 1000, null);

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, (com.google.android.gms.location.LocationListener) this);
        }

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
//            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, (com.google.android.gms.location.LocationListener) this);
        }


    }

    public boolean checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setSchoolMarkerInMap () {

        schoolService.retrieveSchool(MapsActivity.this,new IVolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {

                for (Iterator it = schoolService.SchoolsList.iterator(); it.hasNext();) {
                    School currentSchool = (School) it.next();

                    LatLng currentLatLng = new LatLng(currentSchool.getLatitude() , currentSchool.getLongitude());

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(currentLatLng);
                    markerOptions.title(currentSchool.getName());
                    if (currentSchool.getNb_student() <= 50) {
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.markericone));
                    } else if (currentSchool.getNb_student() > 50 && currentSchool.getNb_student() < 200) {
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.markericoneorange));
                    } else {
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.markericonevert));
                    }
                    currentLocationMarker = mMap.addMarker(markerOptions);
                }

            }

            @Override
            public void onSuccessResponseList(List<School> result) {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

}
