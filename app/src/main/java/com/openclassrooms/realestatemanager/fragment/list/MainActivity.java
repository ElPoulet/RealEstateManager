package com.openclassrooms.realestatemanager.fragment.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.os.Bundle;
import android.view.Menu;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;

import java.sql.SQLDataException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    //private ActivityMainBinding binding;

    private TextView textViewMain;
    private TextView textViewQuantity;

    private Button buttonAddApps;

    private Button buttonListApps;

    private Button buttonFilter;

    private Button buttonSimulator;

    private TextView todayDate;

    //public RecyclerView aRecyclerView;

    public AppartmentRecyclerAdapter appartmentRecyclerAdapter;

    public ArrayList<Appartment> appartments;

    DatabaseManager databaseManager;

    private WifiManager wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseManager = new DatabaseManager(this);
        try {
            databaseManager.open();
            //deleteDatabase(DatabaseHelper.DATABASE_NAME);
        } catch (SQLDataException e) {
            e.printStackTrace();
        }




        drawerLayout = findViewById(R.id.drawer_layout_main);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Called when an item in the NavigationView is selected.
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle the selected item based on its ID
            if (item.getItemId() == R.id.nav_main) {
                // Show a Toast message for the Account item
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
            if (item.getItemId() == R.id.nav_map) {
                // Show a Toast message for the Account item
                Intent intent = new Intent(MainActivity.this, MapFragment.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.nav_search) {
                Intent intent = new Intent(MainActivity.this, FilterMapFragment.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.nav_list) {
                // Show a Toast message for the Settings item
                Intent intent = new Intent(MainActivity.this, AppartmentFragment.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.nav_simulator) {
                // Show a Toast message for the Settings item
                Intent intent = new Intent(MainActivity.this, Simulator.class);
                startActivity(intent);
            }

            // Close the drawer after selection
            drawerLayout.closeDrawers();
            // Indicate that the item selection has been handled
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

        buttonAddApps = findViewById(R.id.button_main_add_app);

        buttonAddApps.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddAppartmentActivity.class);
            startActivity(intent);
        });

        buttonListApps = findViewById(R.id.button_main_list_app);

        buttonListApps.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AppartmentFragment.class);
            startActivity(intent);
        });

        buttonFilter = findViewById(R.id.button_main_search);

        buttonFilter.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FilterMapFragment.class);
            startActivity(intent);
        });

        buttonSimulator = findViewById(R.id.button_main_simulator);

        buttonSimulator.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Simulator.class);
            startActivity(intent);
        });

        //deleteDatabase(DatabaseHelper.DATABASE_NAME);


        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());



        //BottomNavigationView navView = findViewById(R.id.nav_view);




        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragment_list,R.id.fragment_map)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);  //Add in fragment mainActivity
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);*/




        //CheckWifiEnabled(); //Check for WIFI If not OK, this open windows for ask the to turn on

        //databaseManager= new DatabaseManager(this);
        //appartments = new ArrayList<>();


        /*this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main);
        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);
        this.buttonAddApps = (Button) findViewById(R.id.buttonAddApps);
        this.todayDate = findViewById(R.id.dateView);
        this.aRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewApps);*/

        //Appartment appartmentTest = new Appartment("TestDurApps");
        //appartments.add(appartmentTest);

        //aRecyclerView.setHasFixedSize(true);

        //this.configureTextViewMain();
        //this.configureTextViewQuantity();
        //this.configureDate();
        //this.openAddApps();


        /*appartmentRecyclerAdapter = new AppartmentRecyclerAdapter(this, appartments);
        aRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aRecyclerView.setAdapter(appartmentRecyclerAdapter);*/

        //this.getData();
        //appartmentRecyclerAdapter.notifyDataSetChanged();

        /*for (int i = 0; i < appartments.size(); i++){
            Toast.makeText(MainActivity.this, appartments.get(i).getRealEstateType(), Toast.LENGTH_SHORT).show();
            Log.i("LIST_TAG", "READ TYPE: " + appartments.get(i).getRealEstateType());
        }*/
    }



    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void configureTextViewMain(){
        this.textViewMain.setTextSize(25);
        this.textViewMain.setText("Le Prix de l'appartement est");
    }

    private void configureDate(){
        String date = Utils.getTodayDate();
        this.todayDate.setText(date);

    }

    private void configureTextViewQuantity(){
        double quantity = Utils.convertDollarToEuro(100);
        this.textViewQuantity.setTextSize(50);
        this.textViewQuantity.setText(String.valueOf(quantity));
    }

    private void CheckWifiEnabled(){
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(!wifi.isWifiEnabled()){
            Intent turnWifiOn = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(turnWifiOn);
        }
    }

    private void getData(){
        Cursor cursor = databaseManager.getData();
        if (cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "No Data Exist", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while(cursor.moveToNext()){
                String nameAppartment = cursor.getString(1);
                Appartment appartment = new Appartment(nameAppartment);
                appartments.add(appartment);
            }
        }
    }

}
