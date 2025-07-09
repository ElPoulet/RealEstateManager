package com.openclassrooms.realestatemanager.fragment.list;

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




}
