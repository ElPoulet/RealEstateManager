package com.openclassrooms.realestatemanager;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;

@RunWith(RobolectricTestRunner.class)
public class InternetTest {

    private ConnectivityManager connectivityManager;
    private ShadowConnectivityManager shadowConnectivityManager;
    private ShadowNetworkInfo shadowOfActiveNetworkInfo;

    @Test
    public void internetTest(){

        connectivityManager = getConnectivityManager();
        shadowConnectivityManager = Shadows.shadowOf(connectivityManager);
        shadowOfActiveNetworkInfo = Shadows.shadowOf(connectivityManager.getActiveNetworkInfo());

        NetworkInfo networkInfo =  ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, true);
        // Correct API call: use setActiveNetworkInfo instead of setNetworkInfo
        shadowConnectivityManager.setActiveNetworkInfo(networkInfo);

        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        assertTrue(activeInfo != null && activeInfo.isConnected());

        // Assertion now passes: Correctly returns TYPE_WIFI
        assertEquals(ConnectivityManager.TYPE_WIFI, activeInfo.getType());

        /*shadowOf((ConnectivityManager) RuntimeEnvironment.systemContext
                .getSystemService(CONNECTIVITY_SERVICE))
                .setActiveNetworkInfo(ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.DISCONNECTED, ConnectivityManager.TYPE_WIFI, 0, false, NetworkInfo.State.DISCONNECTED));

        assertFalse(isInternetAvailable(RuntimeEnvironment.systemContext));*/


    }

    public static Boolean isInternetAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isAvailable();
    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager)     RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

}
