package com.alanmbennett.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_pet:
                    mTextMessage.setText(R.string.title_home);
                    startActivity(new Intent(DashboardActivity.this, PetProfileActivity.class));
                    return true;
                case R.id.navigation_weather:
                    mTextMessage.setText(R.string.title_notifications);
                    startActivity(new Intent(DashboardActivity.this, WeatherActivity.class));
                    return true;
                case R.id.navigation_reminder:
                    mTextMessage.setText(R.string.title_dashboard);
                    startActivity(new Intent(DashboardActivity.this, AddReminderActivity.class));
                    return true;
                case R.id.navigation_map:
                    mTextMessage.setText(R.string.title_notifications);
                    startActivity(new Intent(DashboardActivity.this, MapSearchActivity.class));
                    return true;
                case R.id.navigation_user:
                    mTextMessage.setText(R.string.title_notifications);
                    startActivity(new Intent(DashboardActivity.this, UserProfileActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
