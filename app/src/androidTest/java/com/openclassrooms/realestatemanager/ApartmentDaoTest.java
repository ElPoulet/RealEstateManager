package com.openclassrooms.realestatemanager;


import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static junit.framework.TestCase.assertTrue;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.fragment.list.ApartmentDatabase;
import com.openclassrooms.realestatemanager.fragment.list.Appartment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ApartmentDaoTest {


    private ApartmentDatabase apartmentDatabase;

    private static Appartment Apartment1 = new Appartment("Loft", "99 Rue du test", 150000.0, 250, "Description Test",
            "12/12/2025", "Agent 1", 4,"For Sale", 1, 0, 1, "37.5",
            "105.7");

    private static Appartment Apartment2 = new Appartment("Apartment", "110 Rue du test", 100000.0, 110, "Description Test App 2",
            "25/03/2025", "Agent 3", 3,"Sold", 0, 1, 1, "39.5",
            "110.7");

    private static Appartment Apartment3 = new Appartment("Manor", "112 Rue du test", 120000.0, 110, "Description Test App 2",
            "25/03/2025", "Agent 2", 3,"Sold", 0, 1, 1, "39.5",
            "110.7");


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public ApartmentDaoTest() {
    }

    @Before
    public void initDb() {
        this.apartmentDatabase = Room.inMemoryDatabaseBuilder(getApplicationContext(),
                ApartmentDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        apartmentDatabase.close();
    }

    @Test
    public void insertAndVerifApartment(){

        this.apartmentDatabase.apartmentDao().insertAll(Apartment1);
        this.apartmentDatabase.apartmentDao().insertAll(Apartment2);
        this.apartmentDatabase.apartmentDao().insertAll(Apartment3);


        List<Appartment> apartments = new ArrayList<>();
        apartments = apartmentDatabase.apartmentDao().getAll();
        assertTrue(apartments.size() == 3);
    }

    @Test
    public void insertAndDeleteApartment(){

        this.apartmentDatabase.apartmentDao().insertAll(Apartment1);
        this.apartmentDatabase.apartmentDao().insertAll(Apartment2);

        List<Appartment> apartments = new ArrayList<>();
        apartments = apartmentDatabase.apartmentDao().getAll();
        assertTrue(apartments.size() == 2);

        apartmentDatabase.apartmentDao().deleteAllApartment();

        apartments.clear();
        apartments = apartmentDatabase.apartmentDao().getAll();

        assertTrue(apartments.isEmpty());
    }

    @Test
    public void insertAndUpdateApartment(){

        this.apartmentDatabase.apartmentDao().insertAll(Apartment1);

        List<Appartment> apartments = new ArrayList<>();
        apartments = apartmentDatabase.apartmentDao().getAll();
        assertTrue(apartments.size() == 1);

        apartments.get(0).setRealEstateType("Manor");
        apartmentDatabase.apartmentDao().updateApartment(apartments.get(0));

        assertTrue(apartments.get(0).getRealEstateType().toString().equals("Manor"));

    }

    @Test
    public void convert200DollarsToEuroTest(){
        Utils myUtils = new Utils();
        String result = String.valueOf(myUtils.convertDollarToEuro(200));
        assertTrue(result.equals("162"));

    }

    @Test
    public void convert200EurosToDollarTest(){
        Utils myUtils = new Utils();
        String result = String.valueOf(myUtils.convertEuroToDollar(200));
        assertTrue(result.equals("264"));
    }


   /* @Test
    public void testFormatDate(){

        DatePicker datePicker = new DatePicker();
        datePicker.se

    }*/



}
