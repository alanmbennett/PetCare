package com.alanmbennett.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PetProfileActivity extends AppCompatActivity {
    Button editPet;
    Button walkPet;
    Button reminder;
    Button gallery;

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
                startActivity(new Intent(PetProfileActivity.this, GalleryActivity.class));
            }
        });


    }

}
