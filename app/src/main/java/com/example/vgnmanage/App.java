package com.example.vgnmanage;

import android.app.Application;

import com.example.vgnmanage.Database.AssetsDatabase;

public class App extends Application {
    @Override
    public void onCreate() {
        new AssetsDatabase(this).checkDb();
        super.onCreate();
    }
}
