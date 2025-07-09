package com.openclassrooms.realestatemanager.fragment.list;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.R;

public class Simulator extends AppCompatActivity {

    private TextInputEditText edittextPrice;

    private EditText editTextRate;

    private EditText editTextDuration;

    private TextView textViewReturnValueSimulator;

    private Button buttonCalculate;

    private double returnValueSimulator;

    private NavigationView navigationView;

    private DrawerLayout drawerLayout;

    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simulator);

        drawerLayout = findViewById(R.id.drawer_layout_simulator);
        toolbar = findViewById(R.id.toolbar_appartment_fragment);
        navigationView = findViewById(R.id.nav_view_simulator);

        edittextPrice = findViewById(R.id.edittext_price);

        editTextRate = findViewById(R.id.edittext_rate);

        editTextDuration = findViewById(R.id.edittext_duration);

        textViewReturnValueSimulator = findViewById(R.id.textviewReturnSimulator);

        buttonCalculate = findViewById(R.id.button_send_simulator);
        buttonCalculate.setOnClickListener(view -> calculateSimulator());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // Called when an item in the NavigationView is selected.
        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle the selected item based on its ID
            if (item.getItemId() == R.id.nav_main) {
                // Show a Toast message for the Account item
                Intent intent = new Intent(Simulator.this, MainActivity.class);
                startActivity(intent);
            }
            if (item.getItemId() == R.id.nav_map) {
                // Show a Toast message for the Account item
                Intent intent = new Intent(Simulator.this, MapFragment.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.nav_search) {
                Intent intent = new Intent(Simulator.this, FilterMapFragment.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.nav_list) {
                // Show a Toast message for the Settings item
                Intent intent = new Intent(Simulator.this, AppartmentFragment.class);
                startActivity(intent);
            }

            if (item.getItemId() == R.id.nav_simulator) {
                // Show a Toast message for the Settings item
                Intent intent = new Intent(Simulator.this, Simulator.class);
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


    }


    private void calculateSimulator(){
        if (edittextPrice.getText().toString().matches("") || editTextRate.getText().toString().matches("") ||
                editTextDuration.getText().toString().matches("")){

            Toast.makeText(getApplicationContext(), "No Data Dispo !",
                    Toast.LENGTH_LONG).show();

        }else{

            double valuePrice = Double.parseDouble(edittextPrice.getText().toString());
            double valueRate = Double.parseDouble(editTextRate.getText().toString());
            int valueDuration = Integer.parseInt(editTextDuration.getText().toString());

            double valueRateTwo = valueRate/12;
            returnValueSimulator = (valuePrice*valueRateTwo)/(1-(1+valueRateTwo-(12+valueDuration)));

            textViewReturnValueSimulator.setText(String.valueOf(returnValueSimulator));
        }




    }




}
