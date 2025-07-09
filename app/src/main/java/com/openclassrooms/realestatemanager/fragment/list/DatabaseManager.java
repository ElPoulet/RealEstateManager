package com.openclassrooms.realestatemanager.fragment.list;

import static com.openclassrooms.realestatemanager.fragment.list.DatabaseHelper.DATABASE_TABLE;
import static com.openclassrooms.realestatemanager.fragment.list.DatabaseHelper.DATABASE_TABLE_FILTER;
import static com.openclassrooms.realestatemanager.fragment.list.DatabaseHelper.DATABASE_TABLE_PHOTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLDataException;
import java.util.ArrayList;

public class DatabaseManager {

    private DatabaseHelper dbHelper;
    private final Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context ctx){
        context = ctx;
    }

    public DatabaseManager open() throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    //For Add Data in Database
    public void insert(String name, String address, int surface, Double price, String status, String description, String dateOnMarket,
                       String agent, int numberPieces, int statusInterestSchool, int statusInterestMarket, int statusInterestPark,
                       String lat, String lng, byte[] picture){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.APPARTMENT_NAME, name);
        contentValues.put(DatabaseHelper.APPARTMENT_ADRESS, address);
        contentValues.put(DatabaseHelper.APPARTMENT_SURFACE, surface);
        contentValues.put(DatabaseHelper.APPARTMENT_PRICE, price);
        contentValues.put(DatabaseHelper.APPARTMENT_STATUS, status);
        contentValues.put(DatabaseHelper.APPARTMENT_DESCRIPTION, description);
        contentValues.put(DatabaseHelper.APPARTMENT_DATE_ON_MARKET, dateOnMarket);
        contentValues.put(DatabaseHelper.APPARTMENT_AGENT, agent);
        contentValues.put(DatabaseHelper.APPARTMENT_NUMBER_PIECES, numberPieces);
        contentValues.put(DatabaseHelper.APPARTMENT_INTEREST_SCHOOL, statusInterestSchool);
        contentValues.put(DatabaseHelper.APPARTMENT_INTEREST_MARKET, statusInterestMarket);
        contentValues.put(DatabaseHelper.APPARTMENT_INTEREST_PARK, statusInterestPark);
        contentValues.put(DatabaseHelper.APPARTMENT_LATITUDE, lat);
        contentValues.put(DatabaseHelper.APPARTMENT_LONGITUDE, lng);
        contentValues.put(DatabaseHelper.APPARTMENT_PICTURE, picture);


        database.insert(DATABASE_TABLE, null, contentValues);
    }

    public void insertFilter(int statusSchool, int statusPark, String type, int statusMarket, int priceMin, int priceMax, int numberPieces,
                             int surfaceMin){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.FILTER_STATUS_SCHOOL, statusSchool);
        contentValues.put(DatabaseHelper.FILTER_STATUS_PARK, statusPark);
        contentValues.put(DatabaseHelper.FILTER_TYPE, type);
        contentValues.put(DatabaseHelper.FILTER_STATUS_MARKET, statusMarket);
        contentValues.put(DatabaseHelper.FILTER_PRICE_MIN, priceMin);
        contentValues.put(DatabaseHelper.FILTER_PRICE_MAX, priceMax);
        contentValues.put(DatabaseHelper.FILTER_NUMBER_PIECES, numberPieces);
        contentValues.put(DatabaseHelper.FILTER_SURFACE_MIN, surfaceMin);

        database.insert(DATABASE_TABLE_FILTER, null, contentValues);
    }

    public void insertPhoto(byte[] photoURI){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.PHOTO_URI, photoURI);

        database.insert(DATABASE_TABLE_PHOTO, null, contentValues);
    }

    public Cursor fetch(){
        String [] columns = new String[] { DatabaseHelper.APPARTMENT_ID, DatabaseHelper.APPARTMENT_NAME,
                DatabaseHelper.APPARTMENT_ADRESS, DatabaseHelper.APPARTMENT_SURFACE, DatabaseHelper.APPARTMENT_PRICE,
                DatabaseHelper.APPARTMENT_STATUS, DatabaseHelper.APPARTMENT_DESCRIPTION, DatabaseHelper.APPARTMENT_DATE_ON_MARKET,
                DatabaseHelper.APPARTMENT_AGENT, DatabaseHelper.APPARTMENT_NUMBER_PIECES, DatabaseHelper.APPARTMENT_INTEREST_SCHOOL,
                DatabaseHelper.APPARTMENT_INTEREST_MARKET, DatabaseHelper.APPARTMENT_INTEREST_PARK, DatabaseHelper.APPARTMENT_LATITUDE,
                DatabaseHelper.APPARTMENT_LONGITUDE, DatabaseHelper.APPARTMENT_PICTURE};
        Cursor cursor = database.query(DATABASE_TABLE, columns, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchFilter(){
        String [] columns = new String[] { DatabaseHelper.FILTER_ID, DatabaseHelper.FILTER_STATUS_SCHOOL,
        DatabaseHelper.FILTER_STATUS_PARK, DatabaseHelper.FILTER_TYPE, DatabaseHelper.FILTER_STATUS_MARKET};
        Cursor cursor = database.query(DATABASE_TABLE_FILTER, columns, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchPhoto(){
        String[] columns = new String[] { DatabaseHelper.PHOTO_ID, DatabaseHelper.PHOTO_URI};
        Cursor cursor = database.query(DATABASE_TABLE_PHOTO, columns, null, null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String address, int surface, Double price, String status, String description){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DATABASE_NAME, name);
        contentValues.put(DatabaseHelper.DATABASE_NAME, address);
        contentValues.put(DatabaseHelper.APPARTMENT_SURFACE, surface);
        contentValues.put(DatabaseHelper.APPARTMENT_PRICE, price);
        contentValues.put(DatabaseHelper.APPARTMENT_STATUS, status);
        contentValues.put(DatabaseHelper.APPARTMENT_DESCRIPTION, description);
        int ret = database.update(DATABASE_TABLE, contentValues, DatabaseHelper.APPARTMENT_ID +
                "=" + _id, null);
        return ret;
    }

    public int updateFilter(long _id, int statusSchool, int statusPark, String type, int statusMarket){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.FILTER_STATUS_SCHOOL, statusSchool);
        contentValues.put(DatabaseHelper.FILTER_STATUS_PARK, statusPark);
        contentValues.put(DatabaseHelper.FILTER_TYPE, type);
        contentValues.put(DatabaseHelper.FILTER_STATUS_MARKET, statusMarket);


        int ret = database.update(DATABASE_TABLE_FILTER, contentValues, DatabaseHelper.FILTER_ID + "=" + _id,
                null);
        return ret;
    }

    public void delete(long _id){
        database.delete(DATABASE_TABLE, DatabaseHelper.APPARTMENT_ID + "=" + _id,
                null);
    }

    public void deleteFilter(){
        database.delete(DATABASE_TABLE_FILTER, null,
                null);
    }

    public Cursor getData(){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        return cursor;
    }

    public Cursor getDataFilter(){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE_FILTER, null);
        return cursor;
    }

    public boolean getTableFilter(){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        try{
            database.rawQuery("SELECT * FROM " + DATABASE_TABLE_FILTER,null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public ArrayList<Appartment> getTableAppartment(){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getReadableDatabase();

        Cursor cursorAppartement = database.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);

        // on below line we are creating a new array list.
        ArrayList<Appartment> appartmentModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorAppartement.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                appartmentModalArrayList.add(new Appartment(cursorAppartement.getString(1)));
            } while (cursorAppartement.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorAppartement.close();
        return appartmentModalArrayList;

    }

}
