package com.openclassrooms.realestatemanager.fragment.list;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM image_table")
    List<Image> getImage();

    @Query("DELETE FROM image_table")
    void deleteAllImage();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllImage(Image... images);

    @Delete
    void deleteImage(Image images);

    @Insert
    default int addApartmentForId(Appartment appartment) {
        return 0;
    }

    @Insert
    void addImages(List<Image> images);

    @Insert
    void insertAll(Appartment ... appartments);

    @Query("SELECT * FROM apartment_table")
    List<Appartment> getAll();


    @Transaction
    default void addApartmentWithImages(Appartment appartment, List<Image> images){

        insertAll(appartment);
        List<Appartment> appartmentList = new ArrayList<>();
        appartmentList = getAll();

        int listId = 0;

        for (int y = 0; y<appartmentList.size(); y++){
            listId = appartmentList.get(y).getRealEstateId();
        }

        for (int i = 0; i<images.size(); i++){
            images.get(i).setIdApartment(listId);
        }
        addImages(images);
    }

    @Query("SELECT * FROM image_table JOIN apartment_table ON apartment_id = id_apartment")
    List<ImageWithApartment> getImageWithApartment();

    @Query("SELECT * FROM apartment_table INNER JOIN image_table WHERE " +
            "(apartment_id = id_apartment) AND apartment_id= :id")
    List<Image> returnListImage(int id);


}
