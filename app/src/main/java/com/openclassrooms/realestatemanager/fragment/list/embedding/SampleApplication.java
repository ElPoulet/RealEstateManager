package com.openclassrooms.realestatemanager.fragment.list.embedding;

import android.app.Application;

/**
 * Initializer for activity embedding split rules.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SplitManager.createSplit(this);
    }
}
