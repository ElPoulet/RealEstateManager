package com.openclassrooms.realestatemanager.fragment.list;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Appartment.class,Filter.class,Image.class}, version = 1)
public abstract class ApartmentDatabase extends RoomDatabase {

    private static ApartmentDatabase apartmentDatabase;

    private static String DATABASE_NAME = "Apartment_DB";

    public abstract ApartmentDao apartmentDao();

    public abstract FilterDao filterDao();

    public abstract ImageDao imageDao();

    public synchronized static ApartmentDatabase getInstance(Context context){
        if(apartmentDatabase == null){
            apartmentDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    ApartmentDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return apartmentDatabase;
    }


}
