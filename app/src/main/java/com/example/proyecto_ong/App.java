package com.example.proyecto_ong;
import com.parse.Parse;
import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("com.example.proyecto_ong")
                // if defined
                //.clientKey("YOUR_CLIENT_KEY")
                .server("http://localhost:1337/parse/")
                .build()
        );
    }
}
