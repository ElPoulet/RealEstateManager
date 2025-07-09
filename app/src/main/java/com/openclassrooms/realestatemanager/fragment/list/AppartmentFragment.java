package com.openclassrooms.realestatemanager.fragment.list;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AppartmentFragment extends AppCompatActivity{

    private TextView textViewMain;
    private TextView textViewQuantity;

    private Button buttonAddApps;

    private TextView todayDate;

    public RecyclerView aRecyclerView;

    public AppartmentRecyclerAdapter appartmentRecyclerAdapter;

    public List<Appartment> finalListApartments = new ArrayList<>();

    public List<Image> finalListImages;

    private Filter filter = new Filter();

    private NavigationView navigationView;

    private DrawerLayout drawerLayout;

    private Toolbar toolbar;

    private ApartmentViewModel mApartmentViewModel;

    private Handler mainHandler = new Handler(Looper.getMainLooper()); // Updating UI on main thread

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_appartment);
        aRecyclerView = findViewById(R.id.recyclerViewApps);

        try {
            @SuppressLint("PrivateApi")
            Field field =
                    CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        drawerLayout = findViewById(R.id.drawer_layout_list);
        toolbar = findViewById(R.id.toolbar_appartment_fragment);
        navigationView = findViewById(R.id.nav_view_list);

        finalListImages = new ArrayList<>();


        configureViewModel();

        mainHandler.post(new Runnable() {
            @Override
            public void run() {

                //configureViewModel();
                getApartments();
                getImagesWithId();

                appartmentRecyclerAdapter = new AppartmentRecyclerAdapter(getApplicationContext(), finalListApartments, finalListImages);
                aRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                aRecyclerView.setAdapter(appartmentRecyclerAdapter);
                appartmentRecyclerAdapter.notifyDataSetChanged();
            }
        });


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
                    Intent intent = new Intent(AppartmentFragment.this, MainActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.nav_map) {
                    // Show a Toast message for the Account item
                    Intent intent = new Intent(AppartmentFragment.this, MapFragment.class);
                    startActivity(intent);
                }

                if (item.getItemId() == R.id.nav_search) {
                    Intent intent = new Intent(AppartmentFragment.this, FilterMapFragment.class);
                    startActivity(intent);
                }

                if (item.getItemId() == R.id.nav_list) {
                    // Show a Toast message for the Settings item
                    Intent intent = new Intent(AppartmentFragment.this, AppartmentFragment.class);
                    startActivity(intent);
                }

                if (item.getItemId() == R.id.nav_simulator) {
                    // Show a Toast message for the Settings item
                    Intent intent = new Intent(AppartmentFragment.this, Simulator.class);
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



        //this.textViewMain = root.findViewById(R.id.activity_main_activity_text_view_main); Old view
        //this.textViewQuantity = root.findViewById(R.id.activity_main_activity_text_view_quantity); Old View
        this.buttonAddApps = (Button) findViewById(R.id.buttonAddApps);
        //this.todayDate = root.findViewById(R.id.dateView); Old View

        this.openAddApps();

    }

    private void openAddApps(){
        buttonAddApps.setOnClickListener(view -> {
            Intent intent = new Intent(AppartmentFragment.this, AddAppartmentActivity.class);
            startActivity(intent);
        });
    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mApartmentViewModel = ViewModelProviders.of(this,mViewModelFactory).get(ApartmentViewModel.class);
        getApartments();
        getImagesWithId();
    }

    private void configureRecyclerView(){

        appartmentRecyclerAdapter = new AppartmentRecyclerAdapter(getApplicationContext(), finalListApartments, finalListImages);

        aRecyclerView.setHasFixedSize(true);
        aRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        aRecyclerView.setAdapter(appartmentRecyclerAdapter);
        appartmentRecyclerAdapter.notifyDataSetChanged();

        Log.i(TAG, "Data List Apartment RecyclerView: " + finalListApartments.size());


    }

    private void getApartments(){

        finalListApartments = mApartmentViewModel.getListApartment();
        Log.i(TAG, "Data List Apartment getApartment: " + finalListApartments.size());
    }

    private void getImagesWithId(){
        finalListImages = mApartmentViewModel.getListImage();
    }





}
