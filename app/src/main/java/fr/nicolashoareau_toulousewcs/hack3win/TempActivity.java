package fr.nicolashoareau_toulousewcs.hack3win;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TempActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1023;
    //btn title article :
    FloatingActionButton mBtnTitleArticle;
    FloatingActionButton mBtnVideoArticle;
    FloatingActionButton mBtnMapsArticle;
    ConstraintLayout consLayoutMaps;
    ConstraintLayout consLayoutVideo;
    ConstraintLayout consLayoutArticle;
    Button btnValidate;
    ImageView searchImage;
    VideoView mVrecord;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private TextView mCoordonateText;
    private GoogleApiClient mGoogleApiClient;
    private TextView tvTitleArticle;
    private EditText etTitleArticle;
    private EditText etResumeArticle;
    private EditText etLink;
    private Boolean isClick = false;

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    String mUid;
    FirebaseAuth mAuth;
    private String mCurrentPhotoPath;
    private String mGetVideoUrl = "";
    private Uri mVideoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        setTitle("Créer une news");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();



        mCoordonateText = findViewById(R.id.input_search);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        askLocationPermission();

        tvTitleArticle = findViewById(R.id.tv_title_article);
        etTitleArticle = findViewById(R.id.et_title_article);
        etLink = findViewById(R.id.et_link);
        etResumeArticle = findViewById(R.id.et_resume);
        consLayoutArticle = findViewById(R.id.cnsLayoutArticle);
        mBtnTitleArticle = findViewById(R.id.fab_title_article);
        mBtnTitleArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvInfo = findViewById(R.id.title_btn_article);
                if (!isClick) {
                    consLayoutArticle.setVisibility(View.VISIBLE);
                    tvInfo.setVisibility(View.VISIBLE);
                    isClick = true;
                } else {
                    consLayoutArticle.setVisibility(View.GONE);
                    tvInfo.setVisibility(View.GONE);
                    isClick = false;
                }


            }
        });

        consLayoutMaps = findViewById(R.id.consLayout);
        mBtnMapsArticle = findViewById(R.id.fab_maps_article);
        mBtnMapsArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvMaps = findViewById(R.id.title_btn_maps);
                if (!isClick) {
                    consLayoutMaps.setVisibility(View.VISIBLE);
                    tvMaps.setVisibility(View.VISIBLE);
                    isClick = true;
                } else {
                    consLayoutMaps.setVisibility(View.GONE);
                    tvMaps.setVisibility(View.GONE);
                    isClick = false;
                }
            }
        });

        consLayoutVideo = findViewById(R.id.cnsLayoutVideo);
        mBtnVideoArticle = findViewById(R.id.fab_video_article);
        mBtnVideoArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvVideo = findViewById(R.id.title_btn_video);
                if (!isClick) {
                    consLayoutVideo.setVisibility(View.VISIBLE);
                    tvVideo.setVisibility(View.VISIBLE);
                    FloatingActionButton record = findViewById(R.id.fab_create_video);
                    mVrecord = findViewById(R.id.vv_record);
                    record.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                                File photoFile = null;
                                try {
                                    photoFile = createVideoFile();
                                } catch (IOException ex) {
                                }
                                // Continue only if the File was successfully created
                                if (photoFile != null) {
                                    mVideoUri = FileProvider.getUriForFile(getApplicationContext(),
                                            "fr.nicolashoareau_toulousewcs.hack3win.fileprovider",
                                            photoFile);
                                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                            }

                            }
                        }
                    });
                    isClick = true;
                } else {
                    consLayoutVideo.setVisibility(View.GONE);
                    tvVideo.setVisibility(View.GONE);
                    isClick = false;
                }
            }
        });

        final Spinner spinnerTag1 = findViewById(R.id.spin_tag1);
        //Utiliser un Adapter pour rentrer les données du spinner_array
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_tag1, android.R.layout.simple_spinner_item);
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




        btnValidate = findViewById(R.id.btn_validate);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titleArticle = etTitleArticle.getText().toString();
                final String resumeArticle = etResumeArticle.getText().toString();
                final String linkArticle = etLink.getText().toString();
                final String coordonate = mCoordonateText.getText().toString();

                /*mRef = mDatabase.getReference("Users").child("news");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

                if (!mGetVideoUrl.equals("") && mGetVideoUrl != null) {
                    StorageReference avatarRef = FirebaseStorage.getInstance().getReference("newsRef");
                    avatarRef.putFile(mVideoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            final String avatarUrl = downloadUri.toString();
                            mDatabase = FirebaseDatabase.getInstance();
                            ArticleModel articleModel = new ArticleModel(titleArticle, resumeArticle, avatarUrl, linkArticle, coordonate);
                            mDatabase.getReference("Users").child("news").push().setValue(articleModel);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("aa", "onFailure: " + e.getMessage());                        }
                    });
                    Intent intent = new Intent(TempActivity.this, ContributeurActivity.class);
                    startActivity(intent);
                    Toast.makeText(TempActivity.this, "Article enregistré", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(TempActivity.this, "no_image", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private File createVideoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MP4_" + timeStamp + "_";
        File storageDir = TempActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            mVideoUri = data.getData();
            mGetVideoUrl = mVideoUri.getPath();
            mVrecord.setVideoURI(mVideoUri);
            mVrecord.start();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void geoLocate(Location location) {
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            String locality = addresses.get(0).getLocality();
            mCoordonateText.setText(locality);
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
                //mise à jour la position de l'utilisateur
                moveCameraOnUser(location);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
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
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
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

    private void moveCameraOnUser(final Location location) {
        final LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        //ajoute un marker :
        mMap.addMarker(new MarkerOptions().position(userLocation));

        final CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(userLocation, 12);
        mMap.moveCamera(yourLocation);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

        }

        //click image loupe :
        searchImage = findViewById(R.id.search_image);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocate(location);
            }
        });
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
    }


}
