package com.openclassrooms.realestatemanager.fragment.list;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.CursorWindow;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.chip.Chip;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddAppartmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public EditText textviewName;

    public EditText  textViewAddress;

    public EditText textViewSurface;

    public EditText textViewPrice;

    public Spinner spinnerType;

    public Spinner spinnerStatus;

    public EditText textViewDescription;

    public TextView textViewDateOnMarket;

    public Spinner spinnerAgent;

    public Spinner spinnerNumberPieces;

    public Chip chipSchool;

    public Chip chipMarket;

    public Chip chipPark;

    private Button buttonUpdateData;

    public EditText textViewlatitude;

    public EditText textViewLongitude;


    public int statusChipSchool;

    public int statusChipMarket;

    public int statusChipPark;

    private Button buttonAddPicture;

    private ImageView testImageView;

    private String imgPath;

    private byte[] uriPicture;

    private Notifications notifications = new Notifications();

    private Handler mainHandler = new Handler(Looper.getMainLooper()); // Handler for updating UI on main thread
    private ApartmentViewModel mApartmentViewModel;

    private ImageView searchIcon;

    private ActivityResultLauncher<Intent> launchGallery;

    private ArrayList<Uri> uriArrayList = new ArrayList<>();

    private  static final int Read_Permission = 101;

    private Button buttonBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appartment);


        configureViewModel();

        try {
            @SuppressLint("PrivateApi")
            Field field =
                    CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.searchIcon = findViewById(R.id.icon_address);
        searchIcon.setOnClickListener(this::geoLocate);

        //this.textviewName = findViewById(R.id.textName);
        this.textViewAddress = findViewById(R.id.textPostalAddress);
        this.textViewSurface = findViewById(R.id.textSurface);
        this.textViewPrice = findViewById(R.id.textPrice);

        this.spinnerType = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.types_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        this.spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapterSpinnerStatus = ArrayAdapter.createFromResource(
                this,
                R.array.status_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterSpinnerStatus);

        this.textViewDescription = findViewById(R.id.textDescription);

        this.textViewDateOnMarket = findViewById(R.id.textAddressAddOnMarket);

        textViewDateOnMarket.setOnClickListener(view -> {
            DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
        });

        this.spinnerAgent = (Spinner) findViewById(R.id.spinnerAgent);
        ArrayAdapter<CharSequence> adapterSpinnerAgent = ArrayAdapter.createFromResource(
                this,
                R.array.agent_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgent.setAdapter(adapterSpinnerAgent);

        this.spinnerNumberPieces = (Spinner) findViewById(R.id.spinnerNumberPieces);
        ArrayAdapter<CharSequence> adapterSpinnerNumberPieces = ArrayAdapter.createFromResource(
                this,
                R.array.number_pieces_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNumberPieces.setAdapter(adapterSpinnerNumberPieces);

        this.chipSchool = findViewById(R.id.chipSchool);
        chipSchool.setOnClickListener(view -> {
            if(chipSchool.isChecked()){
                statusChipSchool = 1;
            }else statusChipSchool = 0;
        });

        this.chipMarket = findViewById(R.id.chipMarket);

        chipMarket.setOnClickListener(view -> {
            if (chipMarket.isChecked()){
                statusChipMarket = 1;
            }else statusChipMarket = 0;
        });

        this.chipPark = findViewById(R.id.chipPark);

        chipPark.setOnClickListener(view -> {
            if (chipPark.isChecked()){
                statusChipPark = 1;
            }else statusChipPark = 0;
        });

        this.buttonUpdateData = findViewById(R.id.buttonUpdateData);

        buttonUpdateData.setOnClickListener(view -> UpdateData()); //Add apartment in database when you click on the button

        this.textViewlatitude = findViewById(R.id.textLatitude);

        this.textViewLongitude = findViewById(R.id.textLongitude);

        this.testImageView = findViewById(R.id.icon_image_app);

        /*this.launchGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
            if (result.getResultCode() == 1 && result.getResultCode() ==  Activity.RESULT_OK){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PackageManager.PERMISSION_GRANTED);
                        Intent data = result.getData();

                        Uri imageUri = data.getData();

                    try {
                        Uri TempUri = copyToLocalStorage(this, imageUri);
                        imgPath = TempUri.toString();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    testImageView.setImageURI(imageUri);

                    //imgPath = TempUri.toString();

                }else {

                }


            }
        });*/

        this.buttonAddPicture = findViewById(R.id.button_add_photo); //Open Storage picture
        this.buttonAddPicture.setOnClickListener(view -> {

            Intent gallery = new Intent();
            gallery.setType("image/*");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery,"Choose your pictures"),1);

            //Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            //launchGallery.launch(gallery);


        });

        this.buttonBack = findViewById(R.id.button_back);
        this.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReturn = new Intent(AddAppartmentActivity.this, MainActivity.class);
                startActivity(intentReturn);
            }
        });


    }

    public Uri copyToLocalStorage(Context context, Uri sourceUri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(sourceUri);

        File localFile = new File(context.getCacheDir(), "image_" + System.currentTimeMillis() + ".jpg");
        OutputStream outputStream = new FileOutputStream(localFile);

        byte[] buffer = new byte[4096];
        int read;
        while ((read = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, read);
        }

        inputStream.close();
        outputStream.close();

        return Uri.fromFile(localFile);
    }


    public void UpdateData(){

        int valueIntSurface = Integer.parseInt(textViewSurface.getText().toString());
        Double valueIntPrice = Double.parseDouble(textViewPrice.getText().toString());
        int valueIntNumberPieces = Integer.parseInt(spinnerNumberPieces.getSelectedItem().toString());

        Appartment apartmentToDatabase = new Appartment(spinnerType.getSelectedItem().toString(), textViewAddress.getText().toString(), valueIntPrice,
                valueIntSurface, textViewDescription.getText().toString(), textViewDateOnMarket.getText().toString(),
                spinnerAgent.getSelectedItem().toString(), valueIntNumberPieces,spinnerStatus.getSelectedItem().toString(), statusChipSchool, statusChipMarket,
                statusChipPark, textViewlatitude.getText().toString(), textViewLongitude.getText().toString());

        List<Image> imagesList = new ArrayList<>();

        for(int i =0;i<uriArrayList.size();i++){
            Image image = new Image(String.valueOf(uriArrayList.get(i)));
            imagesList.add(image);
        }

        //this.mApartmentViewModel.createApartment(apartmentToDatabase);
        this.mApartmentViewModel.createApartmentWithImage(apartmentToDatabase, imagesList);

        /*for(int i =0;i<uriArrayList.size();i++){
            Image image = new Image(String.valueOf(uriArrayList.get(i)));
            this.mApartmentViewModel.createImage(image);
        }*/

        notifications.createNotification(getApplicationContext(), apartmentToDatabase.getRealEstateId(), "Creation Apartment",
                "Creation of the apartment is complete");

        mainHandler.post(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(AddAppartmentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


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
        textViewDateOnMarket.setText(selectedDate);
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            //Curseur accès image
            Cursor cursor = this.getContentResolver().query(selectedImage,filePathColumn,
                    null, null, null);
            //Position sur le premiere ligne
            cursor.moveToFirst();
            //Récupération du chemin précis de l'image
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgPath = cursor.getString(columnIndex);
            cursor.close();

            Log.i(TAG, "Image Path after recup: " + imgPath);


            //RécupérationImage
            try {
                Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                uriPicture = byteArray.toByteArray();

                testImageView.setImageBitmap(image);

                Log.i(TAG, "Image Path after recup in try: " + uriPicture);

                //Toast.makeText(this, uriPicture, Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
                /*ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] img = byteArray.toByteArray();
                uriPicture = img.toString();*/

            //image = BitmapFactory.decodeFile((imgPath));
            //testImageView.setImageBitmap(image);

            //Toast.makeText(this, uriPicture, Toast.LENGTH_LONG).show();


            //Bitmap image = BitmapFactory.decodeFile((imgPath));
            //testImageView.setImageBitmap(image);


       /* }else{
            Toast.makeText(this, "No Image", Toast.LENGTH_LONG).show();
        }


    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            if(data.getClipData() != null){
                int x = data.getClipData().getItemCount();

                uriArrayList.clear();

                for (int i =0;i<x;i++){
                    Uri sourceUri = data.getClipData().getItemAt(i).getUri();
                    try {
                        Uri localUri = copyToLocalStorage(this, sourceUri);
                        uriArrayList.add(localUri);
                        Log.i("Data List in for: ", localUri.toString());
                    }
                    catch (IOException e) {
                        Log.e("AddApartmentActivity", "Error copying image: " + sourceUri, e);
                        // Handle error (e.g., show a toast to the user)
                    }

                    Log.i("Data List in for: ", uriArrayList.get(i).toString());
                }
                //adapter.notifyDataSetChanged();
                //textView.setText("Photos ("+uri.size()+")");
            }else if(data.getData() != null){
                String imageURL = data.getData().getPath();
                uriArrayList.add(Uri.parse(imageURL));
                Log.i("Data List external for: ", uriArrayList.toString());
            }
        }

    }

    private void geoLocate(View view){

        String locationName = textViewAddress.getText().toString();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName,1);

            if(addressList.size() > 0){
                Address address = addressList.get(0);

                textViewAddress.setText(address.getAddressLine(0));

                textViewlatitude.setText(String.valueOf(address.getLatitude()));
                textViewLongitude.setText(String.valueOf(address.getLongitude()));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mApartmentViewModel = ViewModelProviders.of(this,mViewModelFactory).get(ApartmentViewModel.class);
    }

}
