package com.alanmbennett.petcare;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class WalkPetActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv_steps, tv_miles;
    private float steps = 0;
    SensorManager sManager;
    Sensor stepSensor;
    int flag = 0;


    boolean walking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_pet);
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
        tv_steps = (TextView) findViewById(R.id.steps);
        tv_miles = (TextView) findViewById(R.id.miles);
        sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (walking) {
            tv_steps.setText(String.valueOf(event.values[0]*4));
            float milesWalked = getDistanceRun((long)(event.values[0]));
            tv_miles.setText(String.valueOf(milesWalked));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float getDistanceRun(long steps) {
        float distance = (float)(steps*78)/(float)100000;
        return distance;
    }

    @Override
    protected void onResume() {
        super.onResume();
        walking = true;
        Sensor countSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sManager.registerListener(this, countSensor, sManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        walking = false;
        //sManager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sManager.unregisterListener(this, stepSensor);
    }
}
