package fr.nicolashoareau_toulousewcs.hack3win;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TempActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1023;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    private AutoCompleteTextView mSearchText;
    //private PlaceArrayAdapter mPlaceArrayAdapter;
    private GoogleApiClient mGoogleApiClient;

    //btn title article :
    FloatingActionButton mBtnTitleArticle;
    FloatingActionButton mBtnVideoArticle;
    FloatingActionButton mBtnMapsArticle;
    private TextView tvTitleArticle;
    private EditText etTitleArticle;
    private Boolean isClick = false;
    ConstraintLayout consLayoutMaps;
    ConstraintLayout consLayoutVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        askLocationPermission();

        init();

        tvTitleArticle = findViewById(R.id.tv_title_article);
        etTitleArticle = findViewById(R.id.et_title_article);
        mBtnTitleArticle = findViewById(R.id.fab_title_article);
        mBtnTitleArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClick) {
                    tvTitleArticle.setVisibility(View.VISIBLE);
                    etTitleArticle.setVisibility(View.VISIBLE);
                    isClick = true;
                }
                else {
                    tvTitleArticle.setVisibility(View.GONE);
                    etTitleArticle.setVisibility(View.GONE);
                    isClick = false;
                }


            }
        });

        consLayoutMaps = findViewById(R.id.consLayout);
        mBtnMapsArticle = findViewById(R.id.fab_maps_article);
        mBtnMapsArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClick) {
                    consLayoutMaps.setVisibility(View.VISIBLE);
                    isClick = true;
                }
                else {
                    consLayoutMaps.setVisibility(View.GONE);
                    isClick = false;
                }
            }
        });

        consLayoutVideo = findViewById(R.id.cnsLayoutVideo);
        mBtnVideoArticle = findViewById(R.id.fab_video_article);
        mBtnVideoArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClick) {
                    consLayoutVideo.setVisibility(View.VISIBLE);
                    isClick = true;
                }
                else {
                    consLayoutVideo.setVisibility(View.GONE);
                    isClick = false;
                }
            }
        });

        final Spinner spinnerTag1 = findViewById(R.id.spin_tag1);
        //Utiliser un Adapter pour rentrer les données du spinner_array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinner_tag1, android.R.layout.simple_spinner_item);
        //Spécifier le layout à utiliser pour afficher les données
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Appliquer l'adapter au spinner
        spinnerTag1.setAdapter(adapter);
        spinnerTag1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = spinnerTag1.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void init() {

        //mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                    //TODO: execute our method for searching
                    geoLocate();
                }
                return false;
            }
        });
    }

    private void geoLocate() {
        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(TempActivity.this);
        List<Address> list = new ArrayList();
        try {
            list = geocoder.getFromLocationName(searchString, 1);

        } catch (IOException e){

        }
        if (list.size() > 0) {
            Address address = list.get(0);
            Toast.makeText(this, address.toString(), Toast.LENGTH_LONG).show();

        }


    }


    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

              //la personne a déja refusée

            } else {
                //on ne lui a pas encore posée la question
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                //demande les droits à l'utilisateur
            }
        } else {
            // TODO : on a déja le droit :
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //TODO : charger la position de la personne
                    getLocation();

                } else {

                    //TODO: afficher un message
                    askLocationPermission();

                }
                return;
            }


        }
    }

    private void getLocation() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //mise à jour la position de l'utilisateur :
                moveCameraOnUser(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };
        //Si l'utilisateur a permis l'utilisation de la localisation
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                moveCameraOnUser(location);
                            }
                        }
                    });

            //Si l'utilisateur n'a pas désactivée la localisation du téléphone
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                //Demande la position de l'utilisateur
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                        0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                        0, locationListener);
            } else {
                Toast.makeText(this, "Géolocalisation désactivée", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void moveCameraOnUser(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        //ajoute un marker :
        mMap.addMarker(new MarkerOptions().position(userLocation));
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(userLocation, 12);
        mMap.setBuildingsEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

        }

       // mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

        //Marker marker = mMap.addMarker(new MarkerOptions().position(userLocation));
        //marker.showInfoWindow();


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
        mMap.setBuildingsEnabled(true);

        init();

    }


}
