package com.alanmbennett.petcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

public class AddGroupActivity extends AppCompatActivity implements HttpPostCallback {

    private String userID;
    private EditText groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        Bundle bundle = new Bundle();

        bundle.putString("uid", userID);

        groupID = this.findViewById(R.id.groupID_editText);
        Button joinGroup = this.findViewById(R.id.join_button);

        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject groupJSON = new JSONObject();
                    groupJSON.put("uid", userID);
                    groupJSON.put("groupid", groupID);

                    new HttpPostRequestTask(groupJSON.toString(),AddGroupActivity.this).execute("https://kennel-server.herokuapp.com/users/");
                }
                catch(Exception e)
                {
                    Log.d("Error: ", e.getMessage());
                }
            }
        });
    }

    @Override
    public void onHttpPostDone(String result) {
        try {
            // TODO
        }
        catch(Exception e)
        {
            Log.d("Add Group Error: ", e.getMessage());
        }
    }

    void switchToDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("uid", userID);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
