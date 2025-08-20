package com.openclassrooms.realestatemanager.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.openclassrooms.realestatemanager.fragment.list.model.Appartment;
import com.openclassrooms.realestatemanager.fragment.list.model.Image;

import java.util.List;

public class ImageWithApartment {

    @Embedded
    public Appartment appartment;
    @Relation(
            parentColumn = "apartment_id",
            entityColumn = "id_apartment"
    )
    public List<Image> imageList;

}
