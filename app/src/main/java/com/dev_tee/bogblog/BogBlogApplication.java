package com.dev_tee.bogblog;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Dev_Tee on 1/7/17.
 */

public class BogBlogApplication extends Application {

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
