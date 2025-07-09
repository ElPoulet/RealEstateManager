package com.openclassrooms.realestatemanager.injections;

import android.content.Context;

import com.openclassrooms.realestatemanager.fragment.list.ApartmentDataRepository;
import com.openclassrooms.realestatemanager.fragment.list.ApartmentDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {
    public static ApartmentDataRepository provideApartmentDataSource(Context context){
        ApartmentDatabase apartmentDatabase = ApartmentDatabase.getInstance(context);
        return new ApartmentDataRepository(apartmentDatabase.apartmentDao(),apartmentDatabase.filterDao(),apartmentDatabase.imageDao());
    }

    public static Executor provideExecutor(){return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        ApartmentDataRepository apartmentDataRepository = provideApartmentDataSource(context);
        Executor executor = provideExecutor();

        return new ViewModelFactory(apartmentDataRepository, executor);
    }



}
