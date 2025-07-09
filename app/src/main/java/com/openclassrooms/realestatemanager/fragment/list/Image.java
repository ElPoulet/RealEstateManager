package com.openclassrooms.realestatemanager.fragment.list;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "image_table")

public class Image {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "image_id")
    private int id;

    @ColumnInfo(name = "id_apartment", index = true)
    private int idApartment;

    @ColumnInfo(name = "image_uri")
    String imgUrl;

    @Ignore
    public Image(int idApartment,String imgUrl){
        this.idApartment = idApartment;
        this.imgUrl = imgUrl;
    }


    public Image(String imgUrl){
        this.imgUrl = imgUrl;
    }



    public String getImgUrl(){
        return imgUrl;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getIdApartment() {
        return idApartment;
    }

    public void setIdApartment(int idApartment) {
        this.idApartment = idApartment;
    }
}
