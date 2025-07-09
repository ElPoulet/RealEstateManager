package com.openclassrooms.realestatemanager.fragment.list;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FilterDao {

    @Query("SELECT * FROM filter_table")
    Filter getFilter();

    @Query("DELETE FROM filter_table")
    void deleteAll();

    @Query("SELECT * FROM apartment_table INNER JOIN filter_table WHERE " +
            "(filter_type IS NULL OR apartment_type = filter_type)" +
            " AND (filter_price_min IS NULL OR filter_price_max IS NULL OR apartment_price BETWEEN filter_price_min AND filter_price_max)" +
            " AND (filter_number_pieces IS NULL OR apartment_number_pieces = filter_number_pieces OR apartment_number_pieces > filter_number_pieces)" +
            " AND (filter_surface_min IS NULL OR apartment_surface = filter_surface_min OR apartment_surface > filter_surface_min)" +
            " AND (filter_status_market = 0 OR apartment_interest_market = filter_status_market AND apartment_interest_market = 1)" +
            " AND (filter_status_school = 0 OR apartment_interest_school = filter_status_school AND apartment_interest_school = 1)" +
            " AND (filter_status_park = 0 OR apartment_interest_park = filter_status_park AND apartment_interest_park = 1)")
    List<Appartment> applyFilter();

    @Query("SELECT * FROM image_table JOIN apartment_table ON apartment_id = id_apartment")
    List<ImageWithApartment> getImageWithApartment();

    /*@RawQuery
    List<Appartment> applyFilterTwo();*/

    /*List<Appartment> applyFilterTwoMultipleQueries(){
        
    }*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFilter(Filter ... filters);

    @Delete
    void delete(Filter filter);

}
