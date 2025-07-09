package com.openclassrooms.realestatemanager.fragment.list;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "MY_COMPANY.DB";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_TABLE_FILTER = "FILTER";

    static final String FILTER_ID = "_ID";

    static final String FILTER_STATUS_SCHOOL = "filter_status_school";

    static final String FILTER_STATUS_PARK = "filter_status_park";

    static final String FILTER_STATUS_MARKET = "filter_status_market";

    static final String FILTER_TYPE = "filter_type";

    static final String FILTER_PRICE_MIN = "filter_price_min";
    static final String FILTER_PRICE_MAX = "filter_price_max";
    static final String FILTER_NUMBER_PIECES = "filter_number_pieces";

    static final String FILTER_SURFACE_MIN = "filter_surface_min";
    static final String DATABASE_TABLE = "APPARTMENTS";
    static final String APPARTMENT_ID = "_ID";
    static final String APPARTMENT_NAME = "appartment_name";

    static final String APPARTMENT_ADRESS = "appartment_address";
    static final String APPARTMENT_SURFACE = "appartment_surface";

    static final String APPARTMENT_PRICE = "appartment_price";

    static final String APPARTMENT_STATUS = "appartment_status";

    static final String APPARTMENT_DESCRIPTION = "appartment_description";

    static final String APPARTMENT_DATE_ON_MARKET = "appartment_date_on_market";

    static final String APPARTMENT_AGENT = "appartment_agent";

    static final String APPARTMENT_NUMBER_PIECES = "appartment_number_pieces";
    static final String APPARTMENT_INTEREST_SCHOOL = "appartment_interest_school";

    static final String APPARTMENT_INTEREST_MARKET = "appartment_interest_market";

    static final String APPARTMENT_INTEREST_PARK = "appartment_interest_park";

    static final String APPARTMENT_LATITUDE = "appartment_latitude";

    static final String APPARTMENT_LONGITUDE = "appartment_longitude";

    static final String APPARTMENT_PICTURE = "appartment_picture";

    static final String DATABASE_TABLE_PHOTO = "PHOTO";
    static final String PHOTO_ID = "_ID";
    static final String PHOTO_URI = "photo_uri";
    static final String APPARTMENT_ID_PHOTO = "appartment_id_photo";

    private static final String CREATE_DB_QUERY = "CREATE TABLE " + DATABASE_TABLE +
            " ( " + APPARTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + APPARTMENT_NAME + " TEXT NOT NULL, " + APPARTMENT_ADRESS + " TEXT NOT NULL, "
            + APPARTMENT_SURFACE + " INTEGER NOT NULL, " + APPARTMENT_PRICE + " DOUBLE NOT NULL, " + APPARTMENT_STATUS +
            " TEXT NOT NULL, " + APPARTMENT_DESCRIPTION + " TEXT NOT NULL, " + APPARTMENT_DATE_ON_MARKET + " TEXT NOT NULL, " +
            APPARTMENT_AGENT + " TEXT NOT NULL, " + APPARTMENT_NUMBER_PIECES + " INTEGER NOT NULL, " + APPARTMENT_INTEREST_SCHOOL + " INTEGER, " +
            APPARTMENT_INTEREST_MARKET + " INTEGER, " + APPARTMENT_INTEREST_PARK + " INTEGER, " + APPARTMENT_LATITUDE +
            " TEXT NOT NULL, " + APPARTMENT_LONGITUDE + " TEXT NOT NULL, " + APPARTMENT_PICTURE + " BLOB ) ";

    private static final String CREATE_DB_QUERY_FILTER = "CREATE TABLE " + DATABASE_TABLE_FILTER +
            " ( " + FILTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FILTER_STATUS_SCHOOL + " INTEGER, "
            + FILTER_STATUS_PARK + " INTEGER, " + FILTER_TYPE + " TEXT, " + FILTER_STATUS_MARKET + " INTEGER, " +
            FILTER_PRICE_MIN + " INTERGER, " + FILTER_PRICE_MAX + " INTEGER, " + FILTER_NUMBER_PIECES + " INTEGER, " +
            FILTER_SURFACE_MIN + " INTEGER ) ";

    /*private static final String CREATE_DB_QUERY_PHOTO = "CREATE TABLE " + DATABASE_TABLE_PHOTO + " ( " + PHOTO_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + PHOTO_URI + " BLOB, " +
            " INTEGER, FOREIGN KEY("+APPARTMENT_ID_PHOTO+") REFERENCES "+DATABASE_TABLE+"("+APPARTMENT_ID+"));";*/

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_QUERY);
        sqLiteDatabase.execSQL(CREATE_DB_QUERY_FILTER);
        //sqLiteDatabase.execSQL(CREATE_DB_QUERY_PHOTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_FILTER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PHOTO);
    }


}
