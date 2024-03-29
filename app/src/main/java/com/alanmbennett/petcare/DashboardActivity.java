package com.alanmbennett.petcare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class DashboardActivity extends AppCompatActivity implements HttpGetCallback {

    //List of pets that should be populated from database?
    ArrayList<Pet> listPet;
    private String userID;
    private int counter;
    private ArrayList<Reminder> reminderArrayList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Bundle bundle = new Bundle();

            bundle.putString("uid", userID);

            switch (item.getItemId()) {
                case R.id.navigation_pet:
                    if (listPet.size() > 1){
                        Intent intent = new Intent(DashboardActivity.this, PetListActivity.class);
                        Bundle petBundle = new Bundle();
                        petBundle.putSerializable("arraylist", (Serializable)listPet);
                        intent.putExtra("uid", userID);
                        intent.putExtra("bundle", petBundle);
                        startActivity(intent);
                        return true;
                    }
                    else {
                        Intent intent = new Intent(DashboardActivity.this, PetProfileActivity.class);

                        //passing data to the item profile
                        intent.putExtra("Title", listPet.get(0).getName());
                        intent.putExtra("Age", listPet.get(0).getBirthdate());
                        intent.putExtra("Weight", listPet.get(0).getWeight());
                        intent.putExtra("Thumbnail", listPet.get(0).getThumbnail());
                        intent.putExtra("petId", listPet.get(0).getPetId());
                        intent.putExtra("uid", userID);
                        startActivity(intent);
                        return true;
                    }
                case R.id.navigation_weather:
                    startActivity(new Intent(DashboardActivity.this, TemperatureActivity.class));
                    return true;
                case R.id.navigation_map:
                    startActivity(new Intent(DashboardActivity.this, locationActivity.class));
                    return true;
                case R.id.navigation_user:
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
        counter = 0;
        reminderArrayList = new ArrayList<Reminder>();

        new HttpGetRequestTask(this).execute("https://kennel-server.herokuapp.com/pets/byuser/" + userID);


    }

    @Override
    public void onHttpGetDone(String result) {
        if(counter == 0 || counter % 2 == 0){
                try {
                listPet = new ArrayList<>();
                JSONArray petJSONArr = new JSONArray(result);

                if (petJSONArr.length() <= 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                    builder.setMessage("It looks like you have no pets to view! Would you like to add one or join a group?").setCancelable(false);
                    ;

                    builder.setPositiveButton("Add a Pet", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            switchToAddPet();
                        }
                    });

                    builder.setNegativeButton("Join a Group", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            switchToAddGroup();
                        }
                    });

                    builder.show();
                } else {
                    for (int i = 0; i < petJSONArr.length(); i++) {
                        JSONObject petJSON = petJSONArr.getJSONObject(i);
                        listPet.add(new Pet(
                                petJSON.get("petid").toString(),
                                petJSON.get("name").toString(),
                                petJSON.get("birthdate").toString(),
                                petJSON.get("weight").toString(),
                                R.drawable.mleh));
                    }

                    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                    navigation.setVisibility(VISIBLE);
                    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                }
            } catch (Exception e) {
                Log.d("Error: ", e.getMessage());
                listPet = new ArrayList<>();
                listPet.add(new Pet("123", "Error Doge", "404", "50", R.drawable.mleh));
            }
            counter++;
            new HttpGetRequestTask(this).execute("http://kennel-server.herokuapp.com/reminders/" + userID);
        } else {
            try{
                JSONArray remindersJSON = new JSONArray(result);
                for (int i = 0; i < remindersJSON.length(); i++) {
                    JSONObject reminder = remindersJSON.getJSONObject(i);
                    String title = reminder.getString("title");
                    String description = reminder.getString("description");
                    String time = reminder.getString("time");
                    String reocurring = reminder.getString("reocurring");
                    String petName = (String) reminder.get("name");
                    Log.d("Description", description);
                    Reminder temp = new Reminder(title, description, time, reocurring, petName);
                    reminderArrayList.add(temp);

                }
                RecyclerView myrv = (RecyclerView) findViewById(R.id.rRecycler);
                ReminderRecyclerViewAdapter myAdapter = new ReminderRecyclerViewAdapter(this, reminderArrayList);
                myrv.setLayoutManager(new GridLayoutManager(this, 1));
                myrv.setAdapter(myAdapter);
                counter++;
            } catch (Exception e){
                Log.d("ReminderError", e.getMessage());
            }

        }
    }

    public void switchToAddPet()
    {
        Intent intent = new Intent(this, AddPetActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("uid", userID);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void switchToAddGroup() {
        Intent intent = new Intent(this, AddGroupActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("uid", userID);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
