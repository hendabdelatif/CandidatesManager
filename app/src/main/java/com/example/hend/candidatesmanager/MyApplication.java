package com.example.hend.candidatesmanager;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Hend on 4/22/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initRealmConfiguration();
    }

    /**
     * Initialise Realm Configurations
     */
    private void initRealmConfiguration() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

    }

}
