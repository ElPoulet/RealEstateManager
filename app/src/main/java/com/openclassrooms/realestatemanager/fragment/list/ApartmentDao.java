package com.openclassrooms.realestatemanager.fragment.list;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ApartmentDao {

    @Query("SELECT * FROM apartment_table")
    List<Appartment> getAll();

    @Insert
    void insertAll(Appartment ... appartments);

    @Query("UPDATE apartment_table SET apartment_price=:price WHERE apartment_id= :id")
    void updateApartment(Double price, int id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateApartment(Appartment apartment);

    @Delete
    void delete(Appartment appartment);

    @Query("DELETE FROM apartment_table")
    void deleteAllApartment();

    //Add fonction for the content provider
    // Méthodes pour le Content Provider
    @Query("SELECT * FROM apartment_table")
    Cursor getAllApartmentsCursor();

    @Query("SELECT * FROM apartment_table WHERE apartment_id = :id")
    Cursor getApartmentByIdCursor(long id);

    // Ajout d'une méthode pour récupérer un appartement par ID (nécessaire pour l'update/delete dans le ContentProvider)
    @Query("SELECT * FROM apartment_table WHERE apartment_id = :id")
    Appartment getApartmentById(long id);




}
