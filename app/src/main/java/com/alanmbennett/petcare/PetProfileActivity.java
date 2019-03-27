package com.alanmbennett.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PetProfileActivity extends AppCompatActivity implements HttpGetCallback {
    Button walkPet;
    Button reminder;
    Button gallery;
    private TextView tvtitle, tvweight, tvage;
    private ImageView img;
    private String Title;
    String petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet__profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });
        tvtitle = (TextView) findViewById(R.id.txttitle);
        tvweight = (TextView) findViewById(R.id.txtweight);
        tvage = (TextView) findViewById(R.id.txtage);
        img = (ImageView) findViewById(R.id.item_thumbnail);
        // Grab data
        Intent intent = getIntent();
        Title = intent.getExtras().getString("Title");
        String Age = intent.getExtras().getString("Age");
        String Weight = intent.getExtras().getString("Weight");
        petId = intent.getExtras().getString("petId");
        final String userID = intent.getExtras().getString("uid");
        int image = intent.getExtras().getInt("Thumbnail");

        // Set values
        tvtitle.setText(Title);
        tvage.setText(Age);
        tvweight.setText(Weight);
        img.setImageResource(image);

        walkPet = (Button) this.findViewById(R.id.btWalkPet);
        walkPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetProfileActivity.this, WalkPetActivity.class));
            }
        });

        reminder = (Button) this.findViewById(R.id.btReminder);
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switchToAddReminder();
            }
        });

        gallery = (Button) this.findViewById(R.id.btGallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetProfileActivity.this, GalleryActivity.class);
                intent.putExtra("petId", petId);
                intent.putExtra("uid", userID);
                startActivity(intent);
            }
        });

        new HttpGetRequestTask(this).execute("https://kennel-server.herokuapp.com/groups/caredby/" + petId);

    }

    void switchToAddReminder(){
        Intent intent = new Intent(this, AddReminderActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("petid", petId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onHttpGetDone(String result) {
        //tvtitle.setText(tvtitle.getText().toString() + "");
        try {
            JSONArray arr = new JSONArray(result);
            JSONObject jsonObj = arr.getJSONObject(0);
            tvtitle.setText(Title +  " (Managed by Group " + jsonObj.get("groupid").toString() + ")");
        }
        catch(Exception e)
        {

        }

        Log.d("Result: ", result);
    }
}
