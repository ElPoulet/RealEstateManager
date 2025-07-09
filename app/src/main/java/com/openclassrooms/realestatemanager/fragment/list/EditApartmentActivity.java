package com.openclassrooms.realestatemanager.fragment.list;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditApartmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public Appartment returnApartment = new Appartment();

    private EditText editTextAddress;

    private EditText editTextSurface;

    private EditText editTextPrice;

    private EditText editTextDescription;

    private Spinner spinnerStatus;

    private TextView textViewStatus;

    private TextView editTextDate;

    private Spinner spinnerNumberPieces;

    private TextView textViewNumberPieces;

    private Spinner spinnerAgent;

    private TextView textViewAgent;

    private EditText editTextLat;

    private EditText editTextLng;

    private Button buttonModificationApartment;

    private Notifications notifications = new Notifications();

    private Handler mainHandler = new Handler(Looper.getMainLooper()); // Handler for updating UI on main thread
    private ApartmentViewModel mApartmentViewModel;

    private ImageView searchIconAddress;

    private static final String CHANNEL_ID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_apartment);

        configureViewModel();

        getIntentReturn(returnApartment);

        this.searchIconAddress = findViewById(R.id.icon_address);
        searchIconAddress.setOnClickListener(this::geoLocate);

        this.editTextAddress = findViewById(R.id.textPostalAddress);

        this.editTextSurface = findViewById(R.id.textSurface);

        this.editTextPrice = findViewById(R.id.textPrice);

        this.editTextDescription = findViewById(R.id.textDescription);

        this.spinnerStatus = findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapterSpinnerStatus = ArrayAdapter.createFromResource(
                this,
                R.array.status_array,
                android.R.layout.simple_spinner_item
        );
        adapterSpinnerStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterSpinnerStatus);

        this.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textViewStatus.setText(spinnerStatus.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.textViewNumberPieces = findViewById(R.id.textView_number_pieces);

        this.spinnerNumberPieces = findViewById(R.id.spinner_number_pieces);
        ArrayAdapter<CharSequence> adapterSpinnerNumberPieces = ArrayAdapter.createFromResource(
                this,
                R.array.number_pieces_array,
                android.R.layout.simple_spinner_item
        );
        adapterSpinnerNumberPieces.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNumberPieces.setAdapter(adapterSpinnerNumberPieces);


        this.spinnerNumberPieces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textViewNumberPieces.setText(spinnerNumberPieces.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textViewNumberPieces.setText(returnApartment.getRealEstateNumberOfPiecies());
            }
        });

        this.spinnerNumberPieces.setSelection(returnApartment.getRealEstateNumberOfPiecies() -1);

        this.textViewStatus = findViewById(R.id.textView_status);

        this.editTextDate = findViewById(R.id.textDateAddOnMarket);

        this.editTextDate.setOnClickListener(view -> {
            DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
        });

        this.textViewAgent = findViewById(R.id.textView_agent);

        this.spinnerAgent = findViewById(R.id.spinner_agent);
        ArrayAdapter<CharSequence> adapterSpinnerAgent = ArrayAdapter.createFromResource(
                this,
                R.array.agent_array,
                android.R.layout.simple_spinner_item
        );
        adapterSpinnerAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgent.setAdapter(adapterSpinnerAgent);

        this.spinnerAgent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textViewAgent.setText(spinnerAgent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textViewAgent.setText(returnApartment.getRealEstateNumberOfPiecies());
            }
        });

        this.editTextLat = findViewById(R.id.textLat);

        this.editTextLng = findViewById(R.id.textLng);

        this.buttonModificationApartment = findViewById(R.id.button_modifcation_apartment);

        this.buttonModificationApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificationDataApartment();
            }
        });

        setInformationAp();

    }




    public void getIntentReturn(Appartment apartment){
        int apartmentId = getIntent().getIntExtra("apartmentId",0);
        String apartmentType = getIntent().getStringExtra("apartmentType");
        String apartmentAddress = getIntent().getStringExtra("apartmentAddress");
        int apartmentLivingSpace = getIntent().getIntExtra("apartmentLivingSpace",0);
        Double apartmentPrice = getIntent().getDoubleExtra("apartmentPrice", 0);
        String apartmentStatus = getIntent().getStringExtra("apartmentStatus");
        String apartmentDescription = getIntent().getStringExtra("apartmentDescription");
        String apartmentDateOnMarket = getIntent().getStringExtra("apartmentDatePutOnSale");
        String apartmentNameAgent = getIntent().getStringExtra("apartmentNameAgent");
        int apartmentNumberPieces = getIntent().getIntExtra("apartmentNumberPieces",0);
        int apartmentPointOfInterestSchool = getIntent().getIntExtra("apartmentStatusInterestSchool", 0);
        int apartmentPointOfInterestMarket = getIntent().getIntExtra("apartmentStatusInterestMarket", 0);
        String apartmentLat = getIntent().getStringExtra("apartmentLat");
        String apartmentLng = getIntent().getStringExtra("apartmentLng");
        //String apartmentPicture = getIntent().getStringExtra("apartmentPicture");

        apartment.setRealEstateId(apartmentId);
        apartment.setRealEstateType(apartmentType);
        apartment.setRealEstateAddress(apartmentAddress);
        apartment.setRealEstateLivingSpace((apartmentLivingSpace));
        apartment.setRealEstatePrice(apartmentPrice);
        apartment.setRealEstateStatus(apartmentStatus);
        apartment.setRealEstateDescription(apartmentDescription);
        apartment.setRealEstateDateOfPutOnSale(apartmentDateOnMarket);
        apartment.setRealEstateNameAgent(apartmentNameAgent);
        apartment.setRealEstateNumberOfPiecies(apartmentNumberPieces);
        apartment.setRealEstateStatusInterestSchool(apartmentPointOfInterestSchool);
        apartment.setRealEstateStatusInterestMarket(apartmentPointOfInterestMarket);
        apartment.setRealEstateLat(apartmentLat);
        apartment.setRealEstateLng(apartmentLng);

        //byte[] returnBytes = apartmentPicture.getBytes(StandardCharsets.UTF_8);
        //apartment.setRealEstateURLImage(returnBytes);

    }

    private void setInformationAp(){

        if(!returnApartment.getRealEstateAddress().equals("")){
            this.editTextAddress.setText(returnApartment.getRealEstateAddress());
        }

        if(returnApartment.getRealEstateLivingSpace() != 0){
            this.editTextSurface.setText(String.valueOf(returnApartment.getRealEstateLivingSpace()));
        }

        if(returnApartment.getRealEstatePrice() != 0){
            this.editTextPrice.setText(String.valueOf(returnApartment.getRealEstatePrice()));
        }

        if(!returnApartment.getRealEstateDescription().equals("")){
            this.editTextDescription.setText(returnApartment.getRealEstateDescription());
        }

        if(!returnApartment.getRealEstateStatus().equals("")){
            this.textViewStatus.setText(returnApartment.getRealEstateStatus());
            if(returnApartment.getRealEstateStatus().equals("Sold")){
                spinnerStatus.setSelection(0);
            }else spinnerStatus.setSelection(1);
        }

        if(!returnApartment.getRealEstateDateOfPutOnSale().equals("")){
            this.editTextDate.setText(returnApartment.getRealEstateDateOfPutOnSale());
        }

        if(returnApartment.getRealEstateNumberOfPiecies() != 0){
            this.textViewNumberPieces.setText(String.valueOf(returnApartment.getRealEstateNumberOfPiecies()));
        }

        if(!returnApartment.getRealEstateNameAgent().equals("")){
            if(returnApartment.getRealEstateNameAgent().equals("Agent 1")){
                spinnerAgent.setSelection(0);
            }
            if(returnApartment.getRealEstateNameAgent().equals("Agent 2")){
                spinnerAgent.setSelection(1);
            }
            if(returnApartment.getRealEstateNameAgent().equals("Agent 3")){
                spinnerAgent.setSelection(2);
            }
        }

        if(!returnApartment.getRealEstateLat().equals("")){
            editTextLat.setText(returnApartment.getRealEstateLat());
        }
        if(!returnApartment.getRealEstateLng().equals("")){
            editTextLng.setText(returnApartment.getRealEstateLng());
        }

    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateFormat.setCalendar(mCalendar);
        String selectedDate = dateFormat.format(mCalendar.getTime());
        editTextDate.setText(selectedDate);
    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mApartmentViewModel = ViewModelProviders.of(this,mViewModelFactory).get(ApartmentViewModel.class);
    }

    public void modificationDataApartment(){

        returnApartment.setRealEstateAddress(editTextAddress.getText().toString());
        returnApartment.setRealEstateLivingSpace(Integer.parseInt(editTextSurface.getText().toString()));
        returnApartment.setRealEstatePrice(Double.parseDouble(editTextPrice.getText().toString()));
        returnApartment.setRealEstateDescription(editTextDescription.getText().toString());
        returnApartment.setRealEstateDateOfPutOnSale(editTextDate.getText().toString());
        returnApartment.setRealEstateStatus(textViewStatus.getText().toString());
        returnApartment.setRealEstateNumberOfPiecies(Integer.parseInt(textViewNumberPieces.getText().toString()));
        returnApartment.setRealEstateNameAgent(textViewAgent.getText().toString());
        returnApartment.setRealEstateLat(editTextLat.getText().toString());
        returnApartment.setRealEstateLng(editTextLng.getText().toString());


        Log.i(TAG, "Return Apartment Price after modif: " + returnApartment.getRealEstatePrice());


        this.mApartmentViewModel.updateApartment(returnApartment);

        notifications.createNotification(getApplicationContext(), returnApartment.getRealEstateId(), "Modification Apartment",
                "The apartment data has been modified");

        mainHandler.post(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(EditApartmentActivity.this, AppartmentFragment.class);
                startActivity(intent);
            }
        });
    }

    private void geoLocate(View view){

        String locationName = editTextAddress.getText().toString();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName,1);

            if(addressList.size() > 0){
                Address address = addressList.get(0);

                editTextAddress.setText(address.getAddressLine(0));

                editTextLat.setText(String.valueOf(address.getLatitude()));
                editTextLng.setText(String.valueOf(address.getLongitude()));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
