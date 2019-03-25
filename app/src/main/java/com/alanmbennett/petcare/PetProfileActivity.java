package com.alanmbennett.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PetProfileActivity extends AppCompatActivity {
    Button editPet;
    Button walkPet;
    Button reminder;
    Button gallery;
    private TextView tvtitle, tvweight, tvage;
    private ImageView img;


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
        String Title = intent.getExtras().getString("Title");
        String Age = intent.getExtras().getString("Age");
        String Weight = intent.getExtras().getString("Weight");
        final String petId = intent.getExtras().getString("petId");
        final String userID = intent.getExtras().getString("uid");
        int image = intent.getExtras().getInt("Thumbnail");

        // Set values
        tvtitle.setText(Title);
        tvage.setText(Age);
        tvweight.setText(Weight);
        img.setImageResource(image);

        editPet = (Button) this.findViewById(R.id.btEditPet);
        editPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetProfileActivity.this, EditPetActivity.class));
            }
        });

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
                startActivity(new Intent(PetProfileActivity.this, ReminderActivity.class));
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


    }

}
