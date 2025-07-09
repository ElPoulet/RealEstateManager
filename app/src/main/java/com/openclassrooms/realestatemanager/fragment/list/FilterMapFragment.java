package com.openclassrooms.realestatemanager.fragment.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.chip.Chip;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.R;

import java.sql.SQLDataException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilterMapFragment extends AppCompatActivity {

    public Chip chipSchoolFilter;

    public Chip chipMarketFilter;

    public Chip chipParkFilter;

    public Button buttonSendFilter;

    public int chipSchoolFilterStatus;

    public int chipMarketFilterStatus;

    public int chipParkFilterStatus;

    public TextInputEditText editTextPriceMin;

    public TextInputEditText editTextPriceMax;

    public Spinner spinnerFilterType;

    private NavigationView navigationView;

    private DrawerLayout drawerLayout;

    private Toolbar toolbar;

    private Spinner spinnerFilterNumberPieces;

    private EditText editTextSurfaceFilter;

    ApartmentDatabase apartmentDatabase;

    private Handler mainHandler = new Handler(Looper.getMainLooper()); // Updating UI on main thread
    private ExecutorService executor = Executors.newSingleThreadExecutor(); // Executor for background tasks


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment_filter);

        drawerLayout = findViewById(R.id.drawer_layout_filter);
        toolbar = findViewById(R.id.toolbar_appartment_fragment);
        navigationView = findViewById(R.id.nav_view_filter);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // Called when an item in the NavigationView is selected.
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle the selected item based on its ID
            if (item.getItemId() == R.id.nav_main) {
                Intent intent = new Intent(FilterMapFragment.this, MainActivity.class);
                startActivity(intent);
            }
            if (item.getItemId() == R.id.nav_map) {
                Intent intent = new Intent(FilterMapFragment.this, MapFragment.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.nav_search) {
                Intent intent = new Intent(FilterMapFragment.this, FilterMapFragment.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.nav_list) {
                Intent intent = new Intent(FilterMapFragment.this, AppartmentFragment.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.nav_simulator) {
                Intent intent = new Intent(FilterMapFragment.this, Simulator.class);
                startActivity(intent);
            }

            drawerLayout.closeDrawers();

            return true;
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


        this.chipSchoolFilter = findViewById(R.id.chipSchoolFilter);

        chipSchoolFilter.setOnClickListener(view -> {
            if (chipSchoolFilter.isChecked()){
                chipSchoolFilterStatus = 1;
            }else chipSchoolFilterStatus = 0;
        });

        this.chipMarketFilter = findViewById(R.id.chipMarketFilter);

        chipMarketFilter.setOnClickListener(view -> {
            if (chipMarketFilter.isChecked()){
                chipMarketFilterStatus = 1;
            }else chipMarketFilterStatus = 0;
        });

        this.chipParkFilter = findViewById(R.id.chipParkFilter);
        chipParkFilter.setOnClickListener(view -> {
            if (chipParkFilter.isChecked()){
                chipParkFilterStatus = 1;
            }else chipParkFilterStatus = 0;
        });

        this.spinnerFilterType = findViewById(R.id.spinner_filter_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.types_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilterType.setAdapter(adapter);

        this.editTextPriceMin = findViewById(R.id.price_min);

        this.editTextPriceMax = findViewById(R.id.price_max);

        this.buttonSendFilter = findViewById(R.id.buttonSendFilter);

        this.editTextSurfaceFilter = findViewById(R.id.surface_filter);

        this.spinnerFilterNumberPieces = findViewById(R.id.spinner_filter_number_pieces);
        ArrayAdapter<CharSequence> adapterSpinnerNumberPieces = ArrayAdapter.createFromResource(
                this,
                R.array.number_pieces_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilterNumberPieces.setAdapter(adapterSpinnerNumberPieces);

        buttonSendFilter.setOnClickListener(view -> {


            executor.submit(new Runnable() {
                @Override
                public void run() {
                    apartmentDatabase = ApartmentDatabase.getInstance(getApplicationContext());
                    apartmentDatabase.filterDao().deleteAll();

                    int valuePriceMin;
                    int valuePriceMax;

                    if(editTextPriceMin.getText().toString().matches("")){
                       Toast.makeText(getApplicationContext(), "Vous devez mettre un prix",Toast.LENGTH_SHORT).show();
                       return;
                    }else{
                       valuePriceMin = Integer.parseInt(editTextPriceMin.getText().toString());
                    }

                    if(editTextPriceMax.getText().toString().matches("")){
                        valuePriceMax = Integer.parseInt(null);
                    }else{
                        valuePriceMax = Integer.parseInt(editTextPriceMax.getText().toString());
                    }

                    int valueNumberPieces = Integer.parseInt(spinnerFilterNumberPieces.getSelectedItem().toString());
                    int valueSurfaceMin = Integer.parseInt(editTextSurfaceFilter.getText().toString());

                    Filter filter = new Filter(chipMarketFilterStatus, chipSchoolFilterStatus, chipParkFilterStatus, spinnerFilterType.getSelectedItem().toString(),
                            valuePriceMin, valuePriceMax, valueNumberPieces, valueSurfaceMin);
                    apartmentDatabase.filterDao().insertFilter(filter);

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(FilterMapFragment.this,AppartmentFragment.class);
                            startActivity(intent);

                        }
                    });

                }
            });

        });


    }

}
