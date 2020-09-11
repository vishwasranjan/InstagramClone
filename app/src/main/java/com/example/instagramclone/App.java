package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("dAX6ErnwbGIw2xSdLe8ej70hySAaBRslMRNhwOyi")
                // if defined
                .clientKey("T1PQwLOj6i4TjSDhqvGOIOgYCNrqpDQjOA8wyTrP")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
