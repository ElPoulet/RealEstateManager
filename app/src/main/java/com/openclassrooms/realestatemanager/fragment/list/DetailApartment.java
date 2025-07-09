package com.openclassrooms.realestatemanager.fragment.list;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.fragment.list.embedding.MyAdapterSlider;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class DetailApartment extends AppCompatActivity {

    public Appartment resultAppartment = new Appartment();

    public ApartmentViewModel mApartmentViewModel;

    private Button buttonModifApartment;

    private Button buttonBack;

    private ViewPager2 viewPager2;
    public List<Image> dataList;
    MyAdapterSlider myAdapter;

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apartment_detail);

        viewPager2 = findViewById(R.id.detail_apartment_view_pager);

        dataList = new ArrayList<>();

        getIntentReturn(resultAppartment);



        configureViewModel();
        //getImages();

        mainHandler.post(new Runnable() {
            @Override
            public void run() {

                getImages();
                configureViewModel();

                myAdapter = new MyAdapterSlider(dataList, getApplicationContext());
                viewPager2.setAdapter(myAdapter);

            }
        });



        //Image image = new Image(resultAppartment.getRealEstateURLImage());

        Log.i(TAG, "Voici lien Image dans DetailPage: " + resultAppartment.getRealEstateURLImage());


        //dataList.add(image);


        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailApartment.this, AppartmentFragment.class);
                startActivity(intent);
            }
        });


        TextView textViewItemApartmentType = findViewById(R.id.detail_item_apartment_type);
        TextView textViewItemApartmentAddress = findViewById(R.id.detail_item_apartment_address);
        TextView textViewItemApartmentLivingSpace = findViewById(R.id.detail_item_apartment_living_space);
        TextView textViewItemApartmentPrice = findViewById(R.id.detail_item_apartment_price);
        TextView textViewItemApartmentStatus = findViewById(R.id.detail_item_apartment_status);
        TextView textViewItemApartmentDescription = findViewById(R.id.detail_item_apartment_description);
        TextView textViewItemApartmentPutOnSale = findViewById(R.id.detail_item_apartment_date_put_on_sale);
        TextView textViewItemApartmentNameAgent = findViewById(R.id.detail_item_apartment_name_agent);
        TextView textViewItemApartmentNumberPieces = findViewById(R.id.detail_item_apartment_number_pieces);
        TextView textViewItemApartmentPointOfInterest = findViewById(R.id.detail_item_apartment_point_of_interest);
        //ImageView imageViewItemApartmentPicture = findViewById(R.id.detail_apartment_picture);

        textViewItemApartmentType.setText(resultAppartment.getRealEstateType());
        textViewItemApartmentAddress.setText(resultAppartment.getRealEstateAddress());
        textViewItemApartmentLivingSpace.setText(String.valueOf(resultAppartment.getRealEstateLivingSpace()));
        textViewItemApartmentPrice.setText(String.valueOf(resultAppartment.getRealEstatePrice()));
        textViewItemApartmentStatus.setText(resultAppartment.getRealEstateStatus());
        textViewItemApartmentDescription.setText(resultAppartment.getRealEstateDescription());
        textViewItemApartmentPutOnSale.setText(resultAppartment.getRealEstateDateOfPutOnSale());
        textViewItemApartmentNameAgent.setText(resultAppartment.getRealEstateNameAgent());
        textViewItemApartmentNumberPieces.setText(String.valueOf(resultAppartment.getRealEstateNumberOfPiecies()));


        if (resultAppartment.getRealEstateStatusInterestSchool() == 1){
            textViewItemApartmentPointOfInterest.setText("School");
        } else textViewItemApartmentPointOfInterest.setText("None Info");

        if (resultAppartment.getRealEstateStatusInterestMarket() == 1){
            textViewItemApartmentPointOfInterest.setText(textViewItemApartmentPointOfInterest.getText() + " Market ");
        } else textViewItemApartmentPointOfInterest.setText(textViewItemApartmentPointOfInterest.getText() + " No Market");

        buttonModifApartment = findViewById(R.id.button_modif_app);

        buttonModifApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditApartmentActivity.class);
                intent.putExtra("apartmentId", resultAppartment.getRealEstateId());
                intent.putExtra("apartmentType", resultAppartment.getRealEstateType());
                intent.putExtra("apartmentAddress", resultAppartment.getRealEstateAddress());
                intent.putExtra("apartmentLivingSpace", resultAppartment.getRealEstateLivingSpace());
                intent.putExtra("apartmentPrice", resultAppartment.getRealEstatePrice());
                intent.putExtra("apartmentStatus", resultAppartment.getRealEstateStatus());
                intent.putExtra("apartmentDescription", resultAppartment.getRealEstateDescription());
                intent.putExtra("apartmentDatePutOnSale", resultAppartment.getRealEstateDateOfPutOnSale());
                intent.putExtra("apartmentNameAgent", resultAppartment.getRealEstateNameAgent());
                intent.putExtra("apartmentNumberPieces", resultAppartment.getRealEstateNumberOfPiecies());
                intent.putExtra("apartmentStatusInterestSchool", resultAppartment.getRealEstateStatusInterestSchool());
                intent.putExtra("apartmentStatusInterestMarket", resultAppartment.getRealEstateStatusInterestMarket());
                intent.putExtra("apartmentLat", resultAppartment.getRealEstateLat());
                intent.putExtra("apartmentLng", resultAppartment.getRealEstateLng());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });



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
        String apartmentPicture = getIntent().getStringExtra("apartmentPicture");

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
        //apartment.setRealEstateURLImage(apartmentPicture);



    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mApartmentViewModel = ViewModelProviders.of(this,mViewModelFactory).get(ApartmentViewModel.class);
        getImages();
    }

    private void getImages(){
        dataList = mApartmentViewModel.getListImageWithID(resultAppartment.getRealEstateId());
    }



}