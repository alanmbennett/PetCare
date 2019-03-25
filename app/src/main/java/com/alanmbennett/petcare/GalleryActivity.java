package com.alanmbennett.petcare;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class GalleryActivity extends AppCompatActivity implements HttpPostCallback, HttpGetCallback {

    private Button cameraBtn, galleryBtn, uploadBtn;
    private ImageView targetImage;
    private String imagePath;
    private Uri imageUri;

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    private FirebaseStorage storage;
    private String petid;
    private String uid;
    private ProgressDialog progress;

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
        storage = FirebaseStorage.getInstance();

        Intent intent = getIntent();
        petid = intent.getExtras().getString("petId");
        uid = intent.getExtras().getString("uid");
        progress = new ProgressDialog(this);

        Log.d("Petid: ", petid);

        cameraBtn = (Button) findViewById(R.id.btCamera);
        galleryBtn = (Button) findViewById(R.id.btGallery);
        targetImage = (ImageView) findViewById(R.id.ivTarget);


        //Make Get Request
        new HttpGetRequestTask(GalleryActivity.this).execute("https://kennel-server.herokuapp.com/getphotos/" + petid);


        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(GalleryActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(GalleryActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                1);


                    }
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                }



            }
        });
        galleryBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }});
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        StorageReference storageReference = storage.getReference().child(petid).child(uid + "" + dtf.format(now));
        final String storagePath = petid + "/" + uid + "" + dtf.format(now);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            targetImage.setImageBitmap(imageBitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bitmapData = baos.toByteArray();



            progress.setMessage("Uploading Image...");
            progress.show();
            storageReference.putBytes(bitmapData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progress.dismiss();

                    try{

                        JSONObject photoJSON = new JSONObject();
                        photoJSON.put("petid", petid);
                        photoJSON.put("photopath", storagePath);
                        new HttpPostRequestTask(photoJSON.toString(),GalleryActivity.this).execute("https://kennel-server.herokuapp.com/addphoto/");

                    } catch (Exception e){
                        Log.d("Error ", e.getMessage());
                    }


                }
            });
            Log.d("Log ", "Finished Upload");
        }
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            imagePath = uri.toString();
            Log.d("image path: ", imagePath);
            Bitmap bitmap;
            try {

                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                targetImage.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bitmapData = baos.toByteArray();


                progress.setMessage("Uploading Image...");
                progress.show();
                storageReference.putBytes(bitmapData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progress.dismiss();

                        try{

                            JSONObject photoJSON = new JSONObject();
                            photoJSON.put("petid", petid);
                            photoJSON.put("photopath", storagePath);
                            new HttpPostRequestTask(photoJSON.toString(),GalleryActivity.this).execute("https://kennel-server.herokuapp.com/addphoto/");

                        } catch (Exception e){
                            Log.d("Error ", e.getMessage());
                        }

                    }
                });
                Log.d("Log ", "Finished Upload");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onHttpPostDone(String result) {
        Log.d("Result", result);
    }

    @Override
    public void onHttpGetDone(String result){
        ArrayList<String> photoPaths = new ArrayList<String>();
        try{
            JSONArray photoPathArray = new JSONArray(result);
            for(int i = 0; i < photoPathArray.length(); i++)
            {
                JSONObject photoPathJSON = photoPathArray.getJSONObject(i);
                Log.d("Path", photoPathJSON.getString("photopath"));
                photoPaths.add(photoPathJSON.getString("photopath"));
            }
        } catch (Exception e) {
            Log.d("Error" , e.getMessage());
        }

        //PhotoPaths contains all of the image paths for the current pet (stored within FireStore)

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }



}


//        try {
//            JSONObject userJSON = new JSONObject();
//            userJSON.put("email", email.getText().toString());
//            userJSON.put("name", name.getText().toString());
//            userJSON.put("uid", userID);
//
//            new HttpPostRequestTask(userJSON.toString(),SignupActivity.this).execute("https://kennel-server.herokuapp.com/users/");
//            switchToAddGroup();
//        }
//        catch(Exception e)
//        {
//             Log.d("Error: ", e.getMessage());
//        }