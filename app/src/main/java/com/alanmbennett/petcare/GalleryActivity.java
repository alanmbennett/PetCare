package com.alanmbennett.petcare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;

public class GalleryActivity extends AppCompatActivity {

    private Button cameraBtn, galleryBtn, uploadBtn;
    private ImageView targetImage;
    private String imagePath;

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    //private StorageReference storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });
        FirebaseStorage storage = FirebaseStorage.getInstance();

        Intent intent = getIntent();
        final String petid = intent.getExtras().getString("petId");
        StorageReference ref = storage.getReference().child(petid);
        Log.d("Petid: ", petid);

        cameraBtn = (Button) findViewById(R.id.btCamera);
        galleryBtn = (Button) findViewById(R.id.btGallery);
        targetImage = (ImageView) findViewById(R.id.ivTarget);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra("petId", petid);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);


            }
        });
        galleryBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra("petId", petid);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();


            //StorageReference filepath = storage.child();
        }
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            imagePath = uri.toString();
            Log.d("image path: ", imagePath);
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                targetImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
