package com.openclassrooms.realestatemanager.injections;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.fragment.list.ApartmentDataRepository;
import com.openclassrooms.realestatemanager.fragment.list.ApartmentViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ApartmentDataRepository apartmentDataRepository;
    private final Executor executor;

    public ViewModelFactory(ApartmentDataRepository apartmentDataRepository, Executor executor){
        this.apartmentDataRepository = apartmentDataRepository;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        if (modelClass.isAssignableFrom(ApartmentViewModel.class)){
            return (T) new ApartmentViewModel(apartmentDataRepository, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
