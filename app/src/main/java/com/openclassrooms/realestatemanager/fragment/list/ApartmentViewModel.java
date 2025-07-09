package com.openclassrooms.realestatemanager.fragment.list;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ApartmentViewModel extends ViewModel {

    private final ApartmentDataRepository apartmentDataRepository;
    private final Executor executor;

    public List<Appartment> listApartments = new ArrayList<>();

    public List<Image> listImage = new ArrayList<>();


    public ApartmentViewModel(ApartmentDataRepository apartmentDataRepository, Executor executor) {
        this.apartmentDataRepository = apartmentDataRepository;
        this.executor = executor;
    }

    public void createImage(Image image){
        executor.execute(() -> apartmentDataRepository.createImage(image));
    }

    //Apartment
    public void deleteApartment(Appartment apartment){
        executor.execute(() -> apartmentDataRepository.deleteApartment(apartment));
    }

    public void createApartment(Appartment apartment){
        executor.execute(() -> apartmentDataRepository.createApartment(apartment));
    }

    public void createApartmentWithImage(Appartment apartment, List<Image> images){
        executor.execute(() -> apartmentDataRepository.createApartmentWithImage(apartment, images));
    }

    public void updateApartment(Appartment apartment){
        executor.execute(() -> apartmentDataRepository.updateApartment(apartment));
        Log.i(TAG, "Check price in ViewModel: " + apartment.getRealEstatePrice());
    }

    public void updateApartment(Double price, int id){
        executor.execute(() -> apartmentDataRepository.updateApartment(price, id));
        Log.i(TAG, "Check price in ViewModel: " + price + " id: " +  id);
    }

    public List<Appartment> getListApartment(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                listApartments = apartmentDataRepository.getApartments();
                Log.i(TAG, "Data List Apartment in ApartmentViewModel: " + listApartments.size());
            }
        });
       return listApartments;
    }

    public List<Image> getListImage(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                listImage = apartmentDataRepository.getImages();
                Log.i(TAG, "Data List Image in ApartmentViewModel: " + listImage.size());
            }
        });
        return listImage;
    }

    public List<Image> getListImageWithID(int id){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                listImage = apartmentDataRepository.getImageApartment(id);
                Log.i(TAG, "Data List Image in ApartmentViewModel: " + listImage.size());
            }
        });
        return listImage;
    }

    public List<Appartment> getListApartmentWithFilter(){

        return apartmentDataRepository.getApartmentsWithFilter();
    }


}
