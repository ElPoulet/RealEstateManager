package com.openclassrooms.realestatemanager.fragment.list;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
@Entity(tableName = "apartment_table")
public class Appartment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "apartment_id")
    private int realEstateId;

    @ColumnInfo(name = "apartment_type")
    private String realEstateType;

    @ColumnInfo(name = "apartment_price")
    private Double realEstatePrice;

    @ColumnInfo(name = "apartment_surface")
    private int realEstateLivingSpace;

    @ColumnInfo(name = "apartment_number_pieces")
    private int realEstateNumberOfPiecies;

    @ColumnInfo(name = "apartment_description")
    private String realEstateDescription;

    @ColumnInfo(name = "apartment_img")
    private String realEstateURLImage;

    @ColumnInfo(name = "apartment_address")
    private String realEstateAddress;

    @ColumnInfo(name = "apartment_status")
    private String realEstateStatus;

    @ColumnInfo(name = "apartment_date_on_sale")
    private String realEstateDateOfPutOnSale;
    //private DateFormat realEstateDateOfSale;

    @ColumnInfo(name = "apartment_name_agent")
    private String realEstateNameAgent;

    @ColumnInfo(name = "apartment_interest_school")
    private int realEstateStatusInterestSchool;


    @ColumnInfo(name = "apartment_interest_market")
    private int realEstateStatusInterestMarket;

    @ColumnInfo(name = "apartment_interest_park")
    private int realEstateStatusInterestPark;

    @ColumnInfo(name = "apartment_lng")
    private String realEstateLng;

    @ColumnInfo(name = "apartment_lat")
    private String realEstateLat;

    public Appartment(String realEstateType){
        this.realEstateType = realEstateType;
    }

    public Appartment(){}

    public Appartment(int uId, String realEstateType, String realEstateAddress, int realEstateLivingSpace, Double realEstatePrice, String realEstateStatus,
                      String realEstateDescription, String realEstateDateOfPutOnSale, String realEstateNameAgent, int realEstateNumberOfPiecies,
                      int realEstateStatusInterestSchool, int realEstateStatusInterestMarket, int realEstateStatusInterestPark,
                      String realEstateLat, String realEstateLng, String realEstateURLImage) {
        this.realEstateId = uId;
        this.realEstateType = realEstateType;
        this.realEstateAddress = realEstateAddress;
        this.realEstateLivingSpace = realEstateLivingSpace;
        this.realEstatePrice = realEstatePrice;
        this.realEstateStatus = realEstateStatus;
        this.realEstateDescription = realEstateDescription;
        this.realEstateDateOfPutOnSale = realEstateDateOfPutOnSale;
        this.realEstateNameAgent = realEstateNameAgent;
        this.realEstateNumberOfPiecies = realEstateNumberOfPiecies;
        this.realEstateStatusInterestSchool = realEstateStatusInterestSchool;
        this.realEstateStatusInterestMarket = realEstateStatusInterestMarket;
        this.realEstateStatusInterestPark = realEstateStatusInterestPark;
        this.realEstateLat = realEstateLat;
        this.realEstateLng = realEstateLng;
        this.realEstateURLImage = realEstateURLImage;
    }

    public Appartment(String realEstateType, String realEstateAddress, Double realEstatePrice, int realEstateLivingSpace, String realEstateDescription,
                      String realEstateDateOfPutOnSale, String realEstateNameAgent, int realEstateNumberOfPiecies, String realEstateStatus, int realEstateStatusInterestSchool,
                      int realEstateStatusInterestMarket, int realEstateStatusInterestPark, String realEstateLat, String realEstateLng, String realEstateURLImage){
        this.realEstateType = realEstateType;
        this.realEstateAddress = realEstateAddress;
        this.realEstatePrice = realEstatePrice;
        this.realEstateLivingSpace = realEstateLivingSpace;
        this.realEstateDescription = realEstateDescription;
        this.realEstateDateOfPutOnSale = realEstateDateOfPutOnSale;
        this.realEstateNameAgent = realEstateNameAgent;
        this.realEstateNumberOfPiecies = realEstateNumberOfPiecies;
        this.realEstateStatus = realEstateStatus;
        this.realEstateStatusInterestSchool = realEstateStatusInterestSchool;
        this.realEstateStatusInterestMarket = realEstateStatusInterestMarket;
        this.realEstateStatusInterestPark = realEstateStatusInterestPark;
        this.realEstateLat = realEstateLat;
        this.realEstateLng = realEstateLng;
        this.realEstateURLImage = realEstateURLImage;
    }

    public Appartment(String realEstateType, String realEstateAddress, Double realEstatePrice, int realEstateLivingSpace, String realEstateDescription,
                      String realEstateDateOfPutOnSale, String realEstateNameAgent, int realEstateNumberOfPiecies, String realEstateStatus, int realEstateStatusInterestSchool,
                      int realEstateStatusInterestMarket, int realEstateStatusInterestPark, String realEstateLat, String realEstateLng){
        this.realEstateType = realEstateType;
        this.realEstateAddress = realEstateAddress;
        this.realEstatePrice = realEstatePrice;
        this.realEstateLivingSpace = realEstateLivingSpace;
        this.realEstateDescription = realEstateDescription;
        this.realEstateDateOfPutOnSale = realEstateDateOfPutOnSale;
        this.realEstateNameAgent = realEstateNameAgent;
        this.realEstateNumberOfPiecies = realEstateNumberOfPiecies;
        this.realEstateStatus = realEstateStatus;
        this.realEstateStatusInterestSchool = realEstateStatusInterestSchool;
        this.realEstateStatusInterestMarket = realEstateStatusInterestMarket;
        this.realEstateStatusInterestPark = realEstateStatusInterestPark;
        this.realEstateLat = realEstateLat;
        this.realEstateLng = realEstateLng;
    }

    public Appartment(String realEstateType, String realEstateAddress, int realEstateLivingSpace, Double realEstatePrice, String realEstateStatus,
                      String realEstateDescription, String realEstateDateOfPutOnSale, String realEstateNameAgent, int realEstateNumberOfPiecies,
                      int realEstateStatusInterestSchool, int realEstateStatusInterestMarket, int realEstateStatusInterestPark,
                      String realEstateLat, String realEstateLng) {
        this.realEstateType = realEstateType;
        this.realEstateAddress = realEstateAddress;
        this.realEstateLivingSpace = realEstateLivingSpace;
        this.realEstatePrice = realEstatePrice;
        this.realEstateStatus = realEstateStatus;
        this.realEstateDescription = realEstateDescription;
        this.realEstateDateOfPutOnSale = realEstateDateOfPutOnSale;
        this.realEstateNameAgent = realEstateNameAgent;
        this.realEstateNumberOfPiecies = realEstateNumberOfPiecies;
        this.realEstateStatusInterestSchool = realEstateStatusInterestSchool;
        this.realEstateStatusInterestMarket = realEstateStatusInterestMarket;
        this.realEstateStatusInterestPark = realEstateStatusInterestPark;
        this.realEstateLat = realEstateLat;
        this.realEstateLng = realEstateLng;
        //this.realEstateURLImage = realEstateURLImage;
    }

    public int getRealEstateId(){return realEstateId;}

    public void setRealEstateId(int id){
        this.realEstateId = id;
    }

    public String getRealEstateAddress() {
        return realEstateAddress;
    }

    public void setRealEstateAddress(String realEstateAddress) {
        this.realEstateAddress = realEstateAddress;
    }

    public String getRealEstateType() {
        return realEstateType;
    }

    public void setRealEstateType(String realEstateType) {
        this.realEstateType = realEstateType;
    }

    public int getRealEstateLivingSpace(){
        return realEstateLivingSpace;
    }

    public void setRealEstateLivingSpace(int realEstateLivingSpace){
        this.realEstateLivingSpace = realEstateLivingSpace;
    }

    public Double getRealEstatePrice(){
        return realEstatePrice;
    }

    public void setRealEstatePrice(Double realEstatePrice){
        this.realEstatePrice = realEstatePrice;
    }

    public String getRealEstateStatus(){
        return realEstateStatus;
    }

    public void setRealEstateStatus(String realEstateStatus){
        this.realEstateStatus = realEstateStatus;
    }

    public String getRealEstateDescription(){ return realEstateDescription; }

    public void setRealEstateDescription(String realEstateDescription){
        this.realEstateDescription = realEstateDescription;
    }

    public String getRealEstateDateOfPutOnSale(){ return realEstateDateOfPutOnSale; }

    public void setRealEstateDateOfPutOnSale(String realEstateDateOfPutOnSale){
        this.realEstateDateOfPutOnSale = realEstateDateOfPutOnSale;
    }

    public String getRealEstateNameAgent(){ return realEstateNameAgent; }

    public void setRealEstateNameAgent(String realEstateNameAgent){
        this.realEstateNameAgent = realEstateNameAgent;
    }

    public int getRealEstateNumberOfPiecies(){ return realEstateNumberOfPiecies;}

    public void setRealEstateNumberOfPiecies(int realEstateNumberOfPiecies){
        this.realEstateNumberOfPiecies = realEstateNumberOfPiecies;
    }

    public int getRealEstateStatusInterestSchool(){ return realEstateStatusInterestSchool; }

    public void setRealEstateStatusInterestSchool(int realEstateStatusInterestSchool){
        this.realEstateStatusInterestSchool = realEstateStatusInterestSchool;
    }

    public int getRealEstateStatusInterestMarket(){ return realEstateStatusInterestMarket; }

    public void setRealEstateStatusInterestMarket(int realEstateStatusInterestMarket){
        this.realEstateStatusInterestMarket = realEstateStatusInterestMarket;
    }

    public int getRealEstateStatusInterestPark(){ return realEstateStatusInterestPark;}

    public void setRealEstateStatusInterestPark(int realEstateStatusInterestPark){
        this.realEstateStatusInterestPark = realEstateStatusInterestPark;
    }

    public String getRealEstateLng(){ return realEstateLng; }

    public void setRealEstateLng(String realEstateLng){
        this.realEstateLng = realEstateLng;
    }

    public String getRealEstateLat(){ return realEstateLat;}

    public void setRealEstateLat(String realEstateLat){
        this.realEstateLat = realEstateLat;
    }

    @Nullable
    public String getRealEstateURLImage(){
        return realEstateURLImage;
    }

    public void setRealEstateURLImage(@Nullable String realEstateURLImage){
        this.realEstateURLImage = realEstateURLImage;
    }

}
