package com.alanmbennett.petcare;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

public class locationActivity extends AppCompatActivity implements HttpGetCallback {

    private RecyclerView recyclerView;
    private EditText zipCodeInput;
    private Button searchBtn;
    private ProgressDialog progress;
    private Button vetsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.Recycler);
        zipCodeInput = findViewById(R.id.zipCode);
        searchBtn = findViewById(R.id.searchButton);
        progress = new ProgressDialog(this);
        vetsBtn = findViewById(R.id.searchTwo);


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zipCode = zipCodeInput.getText().toString();

                //https://api.yelp.com/v3/businesses/search?term=Pet Store&location=33613
                String apiKey = "Bearer Fw6kAxs9ZQopB96cUk7r9Fg0WnAZxIkU08pvzd8IDCAR8NKeHdSjWDEeNJYj_VO2D--GzdiGcFLIxECLWVjfgCkZUV-gg1RPL1_b5knEUyjbSN0MLrHs9_XXMWaaXHYx";
                new HttpGetRequestTask(locationActivity.this, "Authorization", apiKey).execute("https://api.yelp.com/v3/businesses/search?term=Dog Activities&location=" + zipCode);
                progress.setTitle("Searching");
                progress.show();


            }
        });

        vetsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zipCode = zipCodeInput.getText().toString();

                //https://api.yelp.com/v3/businesses/search?term=Pet Store&location=33613
                String apiKey = "Bearer Fw6kAxs9ZQopB96cUk7r9Fg0WnAZxIkU08pvzd8IDCAR8NKeHdSjWDEeNJYj_VO2D--GzdiGcFLIxECLWVjfgCkZUV-gg1RPL1_b5knEUyjbSN0MLrHs9_XXMWaaXHYx";
                new HttpGetRequestTask(locationActivity.this, "Authorization", apiKey).execute("https://api.yelp.com/v3/businesses/search?term=Vet&location=" + zipCode);
                progress.setTitle("Searching");
                progress.show();
            }
        });




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onHttpGetDone(String result){
        progress.dismiss();
        ArrayList<Business> data = new ArrayList<Business>();
        try{
            JSONObject json = new JSONObject(result);
            JSONArray businesses = new JSONArray(json.getJSONArray("businesses").toString());
//            JSONArray businesses = jsonArray.getJSONArray(0);

            for (int i = 0; i < businesses.length(); i++){
                JSONObject jsonObject = businesses.getJSONObject(i);
                String name = jsonObject.getString("name");
                String address = jsonObject.getString("address1") + " " + jsonObject.getString("address2") + " " + jsonObject.getString("address3") + " ";
                String city = jsonObject.getString("city");
                String state = jsonObject.getString("state");
                String zip = jsonObject.getString("zip_code");
                String country = jsonObject.getString("country");

                address += city + ", " + state + " " + zip + " " + country;

                Business temp = new Business(name, address);
                data.add(temp);
            }

        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
    }

}
