package com.openclassrooms.realestatemanager;


import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.fragment.list.ApartmentDatabase;
import com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentContentProvider;
import com.openclassrooms.realestatemanager.fragment.list.contentprovider.ApartmentProviderContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ApartmentContentProviderTest {

    private ContentResolver mContentResolver;

    private ApartmentDatabase apartmentDatabase;

    private static long AGENT_ID = 0;

    @Before
    public void initDb() {
        this.apartmentDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(),
                        ApartmentDatabase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = getApplicationContext().getContentResolver();
    }

    @After
    public void closeDb() {
        apartmentDatabase.close();
    }

    /*@Test
    public void getFlatsWhenNoFlatInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(ApartmentProviderContract.CONTENT_URI_APARTMENTS, AGENT_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        cursor.close();
    }*/

    @Test
    public void insertAndGetFlat() {
        // BEFORE : Adding demo flat
        mContentResolver.insert(ApartmentProviderContract.CONTENT_URI_APARTMENTS, generateApartment());
        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(ApartmentProviderContract.CONTENT_URI_APARTMENTS, AGENT_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("apartment_type")), is("Loft"));
    }


    private ContentValues generateApartment(){
        final ContentValues values = new ContentValues();
        values.put("apartment_type", "Loft");
        values.put("apartment_price", "150000");
        values.put("apartment_surface", "150");
        values.put("apartment_description", "This is loft");
        values.put("apartment_address", "Lille");

        return values;
    }



}
