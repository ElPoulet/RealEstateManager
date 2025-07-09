package com.openclassrooms.realestatemanager.fragment.list;

import androidx.room.Embedded;
import androidx.room.Relation;

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
