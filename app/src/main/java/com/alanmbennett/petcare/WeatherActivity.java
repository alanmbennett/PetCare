package com.alanmbennett.petcare;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public class WeatherActivity extends AppCompatActivity implements AsyncTaskCallback{
    private LocationTracker locTracker;
    private static String darkSkyKey = "33492075741daec503dbab41cd294cc6";
    private static String darkSkyAPI = "https://api.darksky.net/forecast/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locTracker = new LocationTracker(this);

        new HttpGetRequestTask(this).execute(darkSkyAPI + darkSkyKey + "/" + locTracker.getLatitude() + "," + locTracker.getLongitude());

        setContentView(R.layout.activity_weather);
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
    public void onPostExecute(String result) {
        Log.d("JSON get: ", result);

        try {
            JSONObject weatherJSON = new JSONObject(result);
            JSONObject currentWeather = (JSONObject)weatherJSON.get("currently");
            TextView degrees = (TextView)this.findViewById(R.id.degrees);
            TextView weather = (TextView)this.findViewById(R.id.weather);
            degrees.setText(currentWeather.get("temperature").toString());
            weather.setText(currentWeather.get("summary").toString());

        }
        catch(Exception e)
        {

        }
    }
}
