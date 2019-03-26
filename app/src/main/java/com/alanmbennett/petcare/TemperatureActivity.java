package com.alanmbennett.petcare;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

public class TemperatureActivity extends AppCompatActivity implements HttpGetCallback {

    private LocationTracker locTracker;
    private static String darkSkyKey = "33492075741daec503dbab41cd294cc6";
    private static String darkSkyAPI = "https://api.darksky.net/forecast/";
    private TextView petText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locTracker = new LocationTracker(this);
        setContentView(R.layout.activity_temperature);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new HttpGetRequestTask(this).execute(darkSkyAPI + darkSkyKey + "/" + locTracker.getLatitude() + "," + locTracker.getLongitude());


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (locTracker.getLocManager().isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                    locTracker.getLocManager().requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 0, locTracker.getLocListener());
                else
                    locTracker.getLocManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locTracker.getLocListener());
            }
        }
    }

    @Override
    public void onHttpGetDone(String result) {
//        Log.d("JSON get: ", result);
        double temp;
        petText = findViewById(R.id.PetText);

        try {
            JSONObject weatherJSON = new JSONObject(result);
            JSONObject currentWeather = (JSONObject)weatherJSON.get("currently");
            TextView degrees = (TextView)this.findViewById(R.id.degrees);
            TextView weather = (TextView)this.findViewById(R.id.weather);
            degrees.setText(currentWeather.get("temperature").toString());

            temp = Double.parseDouble(currentWeather.get("temperature").toString());


            weather.setText(currentWeather.get("summary").toString());



            if(temp > 93){
                weather.setText("It's too hot for your pooch, make sure they're hydrated!");
            } else if(temp < 20){
                weather.setText("Brrr....Too Cold for good old doggo, keep them warm!");
            } else {
                petText.setText("Weather's okay enough for a walk!");
            }

        }
        catch(Exception e)
        {
            Log.d("Exception", e.getMessage());
        }
    }

}
