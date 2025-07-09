package com.openclassrooms.realestatemanager.fragment.list;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "filter_table")
public class Filter {

    @PrimaryKey(autoGenerate = true)
    private int idFilter;

    @ColumnInfo(name = "filter_status_market")
    public int statusMarket;

    @ColumnInfo(name = "filter_status_school")
    public int statusSchool;

    @ColumnInfo(name = "filter_status_park")
    public int statusPark;

    @ColumnInfo(name = "filter_price_min")
    public double priceMin;

    @ColumnInfo(name = "filter_price_max")
    public double priceMax;

    @ColumnInfo(name = "filter_type")
    public String type;

    @ColumnInfo(name = "filter_number_pieces")
    public int numberPieces;

    @ColumnInfo(name = "filter_surface_min")
    public int surfaceMin;

    public Filter(){

    }

    public Filter(int statusMarket, int statusSchool, int statusPark, double priceMin, double priceMax){
        this.statusMarket = statusMarket;
        this.statusSchool = statusSchool;
        this.statusPark = statusPark;
        this.priceMin = priceMin;
        this.priceMax = priceMax;

    }

    public Filter(int statusMarket, int statusSchool, int statusPark, String type, int priceMin, int priceMax, int numberPieces,
                  int surfaceMin){
        this.statusMarket = statusMarket;
        this.statusSchool = statusSchool;
        this.statusPark = statusPark;
        this.type = type;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.numberPieces = numberPieces;
        this.surfaceMin = surfaceMin;
    }

    public int getIdFilter(){ return idFilter;}

    public void setIdFilter(int idFilter){
        this.idFilter = idFilter;
    }

    public int getStatusMarket(){
        return statusMarket;
    }

    public void setStatusMarket(int statusMarket){
        this.statusMarket = statusMarket;
    }

    public int getStatusSchool(){
        return statusSchool;
    }

    public void setStatusSchool(int statusSchool){
        this.statusSchool = statusSchool;
    }

    public int getStatusPark(){
        return statusPark;
    }

    public void setStatusPark(int statusPark){
        this.statusPark = statusPark;
    }

    public double getPriceMin(){
        return priceMin;
    }

    public void setPriceMin(double priceMin){
        this.priceMin = priceMin;
    }

    public double getPriceMax(){
        return priceMax;
    }

    public void setPriceMax(double priceMax){
        this.priceMax = priceMax;
    }

    public String getType(){ return type;}

    public void setType(String type){this.type = type;}

    public int getNumberPieces(){
        return numberPieces;
    }

    public void setNumberPieces(int numberPieces){
        this.numberPieces = numberPieces;
    }

    public int getSurfaceMin(){
        return surfaceMin;
    }

    public void setSurfaceMin(int surfaceMin){
        this.surfaceMin = surfaceMin;
    }


}
