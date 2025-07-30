package com.openclassrooms.realestatemanager.fragment.list.contentprovider;

import static com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract.CONTENT_TYPE_APARTMENTS;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.fragment.list.ApartmentDao;
import com.openclassrooms.realestatemanager.fragment.list.ApartmentDatabase;
import com.openclassrooms.realestatemanager.fragment.list.Appartment;

public class ApartContentProvider extends ContentProvider {

    private ApartmentDao apartmentDao;

    public static final String AUTHORITY = "com.openclassrooms.realestatemanager.fragment.list.contentprovider";

    public static final String APARTMENT_TABLE_NAME = "apartment_table";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + APARTMENT_TABLE_NAME);

    @Override
    public boolean onCreate(){
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder){
        if (getContext() != null){
            final Cursor cursor = ApartmentDatabase.getInstance(getContext()).apartmentDao().getAllApartmentsCursor();
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        throw new IllegalArgumentException("Failed to query row for uri" + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri){
        return CONTENT_TYPE_APARTMENTS;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues){
        if (getContext() != null){
            final long id = apartmentDao.insertApartment(Appartment.fromContentValues(contentValues));

            if(id != 0){
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri,id);
            }
        }

        throw new IllegalArgumentException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings){
        if(getContext() != null){
            final int count = apartmentDao.deleteFlat(ContentUris.parseId(uri));
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new IllegalArgumentException("Failed to delete row into " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings){
        if(getContext() != null){
            final int count = apartmentDao.updateFlat(Appartment.fromContentValues(contentValues));
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new IllegalArgumentException("Failed to update row into " + uri);
    }


}
