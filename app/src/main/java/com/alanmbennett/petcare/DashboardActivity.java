package com.alanmbennett.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements AsyncTaskCallback{

    private TextView mTextMessage;
    //List of pets that should be populated from database?
    ArrayList<Pet> listPet;
    private String userID;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();

            bundle.putString("uid", userID);

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
                    Intent intent = new Intent(DashboardActivity.this, UserProfileActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Bundle bundle = getIntent().getExtras();
        userID = bundle.getString("uid");

        new HttpGetRequestTask(this).execute("https://kennel-server.herokuapp.com/pets/byuser/" + userID);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onPostExecute(String result) {
        try {

            listPet = new ArrayList<>();

            JSONArray petJSONArr = new JSONArray(result);

            for(int i = 0; i < petJSONArr.length(); i++)
            {
                JSONObject petJSON = petJSONArr.getJSONObject(i);
                listPet.add(new Pet(
                            petJSON.get("petid").toString(),
                            petJSON.get("name").toString(),
                            petJSON.get("birthdate").toString(),
                            petJSON.get("weight").toString(),
                            R.drawable.mleh));
            }

        }
        catch(Exception e)
        {
            Log.d("Error: ", e.getMessage());

            listPet = new ArrayList<>();
            listPet.add(new Pet("123", "Error Doge", "404", "50", R.drawable.mleh));
        }

        //initializing pet lis
        //With database think we can loop through and add each pet to the list

        //intializing recyclerview and adapter
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, listPet);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }
}
