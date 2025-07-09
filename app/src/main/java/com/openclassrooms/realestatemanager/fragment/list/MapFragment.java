package com.openclassrooms.realestatemanager.fragment.list;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.CursorWindow;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapFragment extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean permissionDenied = false;

    public static LatLng myPosition;

    private GoogleMap map;

    public List<Appartment> appartments;

    DatabaseManager databaseManager;

    public Button addFilterButton;

    public Chip chipMarketFilter;

    public Chip chipParkFilter;

    public Chip chipSchoolFilter;

    public int chipFilterMarketStatus;

    public int chipFilterSchoolStatus;

    public int chipFilterParkStatus;

    private NavigationView navigationView;

    private DrawerLayout drawerLayout;

    private Toolbar toolbar;

    ApartmentDatabase apartmentDatabase;

    private ApartmentViewModel mApartmentViewModel;

    private Handler mainHandler = new Handler(Looper.getMainLooper()); // Handler for updating UI on main thread
    private ExecutorService executor = Executors.newSingleThreadExecutor(); // Executor for background tasks



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        try {
            @SuppressLint("PrivateApi")
            Field field =
                    CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        drawerLayout = findViewById(R.id.drawer_layout_map);
        toolbar = findViewById(R.id.toolbar_appartment_fragment);
        navigationView = findViewById(R.id.nav_view_map);

        databaseManager= new DatabaseManager(getApplicationContext());
        appartments = new ArrayList<>();

        apartmentDatabase = ApartmentDatabase.getInstance(this);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // Called when an item in the NavigationView is selected.
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle the selected item based on its ID
                if (item.getItemId() == R.id.nav_main) {
                    // Show a Toast message for the Account item
                    Intent intent = new Intent(MapFragment.this, MainActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.nav_map) {
                    // Show a Toast message for the Account item
                    Intent intent = new Intent(MapFragment.this, MapFragment.class);
                    startActivity(intent);
                }

                if (item.getItemId() == R.id.nav_search) {
                    Intent intent = new Intent(MapFragment.this, FilterMapFragment.class);
                    startActivity(intent);
                }

                if (item.getItemId() == R.id.nav_list) {
                    // Show a Toast message for the Settings item
                    Intent intent = new Intent(MapFragment.this, AppartmentFragment.class);
                    startActivity(intent);
                }

                if (item.getItemId() == R.id.nav_simulator) {
                    // Show a Toast message for the Settings item
                    Intent intent = new Intent(MapFragment.this, Simulator.class);
                    startActivity(intent);
                }

                // Close the drawer after selection
                drawerLayout.closeDrawers();
                // Indicate that the item selection has been handled
                return true;
            }
        });

        // Add a callback to handle the back button press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            // Called when the back button is pressed.
            @Override
            public void handleOnBackPressed() {
                // Check if the drawer is open
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    // Close the drawer if it's open
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    // Finish the activity if the drawer is closed
                    finish();
                }
            }
        });



        this.chipMarketFilter = findViewById(R.id.chipFilterMarket);

        chipMarketFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chipMarketFilter.isChecked()){
                    chipFilterMarketStatus = 1;
                }else chipFilterMarketStatus = 0;

                onResume();
            }
        });

        this.chipParkFilter = findViewById(R.id.chipFilterPark);

        chipParkFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chipParkFilter.isChecked()){
                    chipFilterParkStatus = 1;
                }else chipFilterParkStatus = 0;

                onResume();
            }
        });

        this.chipSchoolFilter = findViewById(R.id.chipFilterSchool);

        chipSchoolFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chipSchoolFilter.isChecked()){
                    chipFilterSchoolStatus = 1;
                }else chipFilterSchoolStatus = 0;

                onResume();
            }
        });

    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
        addPointFilterOnMap();

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                Appartment returnApartment = (Appartment) marker.getTag();

                Intent intent = new Intent(MapFragment.this, DetailApartment.class);
                for (int i = 0; i < appartments.size(); i++){
                    if(returnApartment.getRealEstateId() == appartments.get(i).getRealEstateId()){
                        intent.putExtra("apartmentId", appartments.get(i).getRealEstateId());
                        intent.putExtra("apartmentType", appartments.get(i).getRealEstateType());
                        intent.putExtra("apartmentAddress", appartments.get(i).getRealEstateAddress());
                        intent.putExtra("apartmentLivingSpace", appartments.get(i).getRealEstateLivingSpace());
                        intent.putExtra("apartmentPrice", appartments.get(i).getRealEstatePrice());
                        intent.putExtra("apartmentStatus", appartments.get(i).getRealEstateStatus());
                        intent.putExtra("apartmentDescription", appartments.get(i).getRealEstateDescription());
                        intent.putExtra("apartmentDatePutOnSale", appartments.get(i).getRealEstateDateOfPutOnSale());
                        intent.putExtra("apartmentNameAgent", appartments.get(i).getRealEstateNameAgent());
                        intent.putExtra("apartmentNumberPieces", appartments.get(i).getRealEstateNumberOfPiecies());
                        intent.putExtra("apartmentStatusInterestSchool", appartments.get(i).getRealEstateStatusInterestSchool());
                        intent.putExtra("apartmentStatusInterestMarket", appartments.get(i).getRealEstateStatusInterestMarket());
                        intent.putExtra("apartmentLat", appartments.get(i).getRealEstateLat());
                        intent.putExtra("apartmentLng", appartments.get(i).getRealEstateLng());
                    }
                }

                startActivity(intent);

                return false;
            }
        });

    }

    private void addPointFilterOnMap(){
        map.clear();

        configureViewModel();

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        getApartments();

                        for (int i = 0; i < appartments.size(); i++){
                            Double latitude = Double.parseDouble(appartments.get(i).getRealEstateLat());
                            Double longitude = Double.parseDouble(appartments.get(i).getRealEstateLng());
                            LatLng positionApartment = new LatLng(latitude, longitude);
                            displayPoint(positionApartment, appartments.get(i).getRealEstateType(), appartments.get(i).getRealEstatePrice(), appartments.get(i));
                        }
                    }
                });


    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mApartmentViewModel = ViewModelProviders.of(this,mViewModelFactory).get(ApartmentViewModel.class);
        getApartments();
    }

    private void getApartments(){

        appartments = mApartmentViewModel.getListApartment();
        Log.i(TAG, "Data List Apartment getApartment: " + appartments.size());
    }



    private void displayPoint(LatLng latLng, String type, double price, Appartment apartment){
        Marker marker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(type)
                .snippet(String.valueOf(price)));

        marker.setTag(apartment);

    }

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            return;
        }
        // 2. Otherwise, request location permissions from the user.
        askForPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }

    private void askForPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            permissionDenied = false;
        }
        //addPointFilterOnMap();
        if(map != null){
            map.clear();
            addPointFilterOnMap();
        }
    }


}
