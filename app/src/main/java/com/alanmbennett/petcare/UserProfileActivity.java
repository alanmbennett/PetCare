package com.alanmbennett.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserProfileActivity extends AppCompatActivity implements AsyncTaskCallback {
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
    public void onPostExecute(String result) {

        try {
            JSONArray userJSONArr = new JSONArray(result);
            JSONObject userJSON = userJSONArr.getJSONObject(0);

            TextView email = (TextView)this.findViewById(R.id.textView3);
            TextView name = (TextView)this.findViewById(R.id.textView4);
            TextView title = (TextView)this.findViewById(R.id.tv_user_name);
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

        }
        catch(Exception e)
        {
            Log.d("BS ERROR: ", e.getMessage());
        }
    }
}
