package com.openclassrooms.realestatemanager.fragment.list.contentprovider;

import android.net.Uri;
import android.content.ContentResolver;

public final class ApartmentProviderContract {

    // Autorité doit correspondre à celle déclarée dans AndroidManifest.xml
    public static final String AUTHORITY = "com.openclassrooms.realestatemanager.fragment.list.contentprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // --- TABLE APPARTMENTS ---
    public static final String PATH_APARTMENTS = "apartments";
    public static final Uri CONTENT_URI_APARTMENTS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_APARTMENTS).build();
    public static final String CONTENT_TYPE_APARTMENTS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_APARTMENTS;
    public static final String CONTENT_ITEM_TYPE_APARTMENT = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_APARTMENTS;

    // Colonnes Apartments (basé sur Appartment Entity et ApartmentDao)
    public static final String APARTMENT_TABLE_NAME = "apartment_table";
    public static final String APARTMENT_COLUMN_ID = "apartment_id";
    public static final String APARTMENT_COLUMN_PRICE = "apartment_price";
    // Ajoutez ici les autres colonnes de ton Appartment (par exemple : apartment_type, apartment_surface, etc.)

    // --- TABLE FILTERS ---
    public static final String PATH_FILTERS = "filters";
    public static final Uri CONTENT_URI_FILTERS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FILTERS).build();
    public static final String CONTENT_TYPE_FILTERS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_FILTERS;

    // Colonnes Filters (basé sur filter_table)
    public static final String FILTER_TABLE_NAME = "filter_table";

    // --- TABLE IMAGES ---
    public static final String PATH_IMAGES = "images";
    public static final Uri CONTENT_URI_IMAGES = BASE_CONTENT_URI.buildUpon().appendPath(PATH_IMAGES).build();
    public static final String CONTENT_TYPE_IMAGES = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_IMAGES;
    public static final String CONTENT_ITEM_TYPE_IMAGE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + PATH_IMAGES;

    // Colonnes Images (basé sur Image Entity)
    public static final String IMAGE_TABLE_NAME = "image_table";
    public static final String IMAGE_COLUMN_ID = "image_id";
    public static final String IMAGE_COLUMN_URI = "image_uri";
    public static final String IMAGE_COLUMN_APARTMENT_ID = "id_apartment";

    private ApartmentProviderContract() {}
}