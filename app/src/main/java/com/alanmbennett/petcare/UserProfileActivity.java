package com.alanmbennett.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserProfileActivity extends AppCompatActivity implements HttpGetCallback {
    Button editUser;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        Bundle bundle = getIntent().getExtras();
        userID = bundle.getString("uid");

        new HttpGetRequestTask(this).execute("https://kennel-server.herokuapp.com/users/" + userID);
    }

    @Override
    public void onHttpGetDone(String result) {

        try {
            JSONArray userJSONArr = new JSONArray(result);
            JSONObject userJSON = userJSONArr.getJSONObject(0);

            TextView email = (TextView)this.findViewById(R.id.textView3);
            TextView name = (TextView)this.findViewById(R.id.textView4);
            TextView title = (TextView)this.findViewById(R.id.tv_user_name);
            Button addPetButton = (Button)this.findViewById(R.id.add_more_button);
            email.setText(userJSON.get("email").toString());
            name.setText(userJSON.get("name").toString());
            title.setText(userJSON.get("name").toString());

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // back button pressed
                    onBackPressed();
                }
            });

            editUser = (Button) this.findViewById(R.id.btEditUser);
            editUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserProfileActivity.this, EditUserActivity.class));
                }
            });

            addPetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchToAddPet();
                }
            });

        }
        catch(Exception e)
        {
            Log.d("BS ERROR: ", e.getMessage());
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
}
