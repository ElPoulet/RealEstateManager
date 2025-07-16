package com.openclassrooms.realestatemanager.fragment.list;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.util.List;

public class ApartmentDataRepository {

    private final ApartmentDao mApartmentDao;
    private final FilterDao mFilterDao;
    private final ImageDao mImageDao;

    public ApartmentDataRepository(ApartmentDao apartmentDao,FilterDao filterDao, ImageDao imageDao){
        this.mApartmentDao = apartmentDao;
        this.mFilterDao = filterDao;
        this.mImageDao = imageDao;
    }

    //GET
    public List<Appartment> getApartments(){
        return this.mApartmentDao.getAll();
    }
    public List<Image> getImages(){
        return this.mImageDao.getImage();
    }

    public List<Appartment> getApartmentsWithFilter(){ return this.mFilterDao.applyFilter();}

    public List<Image> getImageApartment(int id){ return this.mImageDao.returnListImage(id);}

    //CREATE
    public void createApartment(Appartment appartment){
        mApartmentDao.insertAll(appartment);
    }

    public void createImage(Image image){ mImageDao.insertAllImage(image);}

    public void createFilter(Filter filter){ mFilterDao.insertFilter(filter);}

    ///UPDATE
    public void updateApartment(Appartment appartment){ mApartmentDao.updateApartment(appartment);
        Log.i(TAG, "Check price in DataRepository: " + appartment.getRealEstatePrice());}

    public void updateApartment(Double price, int id){ mApartmentDao.updateApartment(price, id);
        Log.i(TAG, "Check price in DataRepository: " + price + " id: " +  id);}

    //DELETE
    public void deleteApartment(Appartment appartment){
        mApartmentDao.delete(appartment);
    }

    public void deleteFilter(){ mFilterDao.deleteAll();}

    public void createApartmentWithImage(Appartment appartment, List<Image> images){
        mImageDao.addApartmentWithImages(appartment, images);
    }


}
