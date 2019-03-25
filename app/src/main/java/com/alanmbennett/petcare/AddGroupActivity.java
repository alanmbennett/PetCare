package com.alanmbennett.petcare;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;

public class AddGroupActivity extends AppCompatActivity implements HttpPostCallback {

    private String userID;
    private EditText groupID;
    private HttpPostRequestTask postRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        Bundle bundle = getIntent().getExtras();
        userID = bundle.getString("uid");

        groupID = this.findViewById(R.id.groupID_editText);
        Button joinGroup = this.findViewById(R.id.join_button);

        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject groupJSON = new JSONObject();
                    groupJSON.put("uid", userID);
                    groupJSON.put("groupid", Integer.parseInt(groupID.getText().toString()));

                    postRequest = new HttpPostRequestTask(groupJSON.toString(),AddGroupActivity.this);
                    postRequest.execute("https://kennel-server.herokuapp.com/groups/adduser");
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

            Log.d("Response code", "" + postRequest.getResponseCode());

            if(postRequest.getResponseCode() == 403)
            {
                Log.d("Error: ","Doesn't exist");

                AlertDialog.Builder builder = new AlertDialog.Builder(this).setCancelable(true);;
                builder.setMessage("Sorry, but group of groupID " + groupID.getText().toString() + " does not exist. " +
                        "Please enter a valid groupID.");
                builder.show();
            }
            else {
                Log.d("Success: ","Does exist");
                switchToDashboard();
            }
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
