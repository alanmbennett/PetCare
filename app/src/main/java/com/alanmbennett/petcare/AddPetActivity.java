package com.alanmbennett.petcare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import org.json.JSONObject;

public class AddPetActivity extends AppCompatActivity  implements HttpPostCallback{

    private String userID;
    private String petID;
    private EditText name;
    private EditText birthdate;
    private EditText weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        Bundle bundle = getIntent().getExtras();
        userID = bundle.getString("uid");

        Button add  = (Button) this.findViewById(R.id.add_button);
        name = (EditText) this.findViewById(R.id.name_editText);
        birthdate = (EditText) this.findViewById(R.id.birthdate_editText);
        weight = (EditText) this.findViewById(R.id.weight_editText);

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    JSONObject petJSON = new JSONObject();
                    petJSON.put("uid", userID);
                    petJSON.put("gname", "Unnamed group");
                    petJSON.put("pname", name.getText().toString());
                    petJSON.put("birthdate", birthdate.getText().toString());
                    petJSON.put("weight", weight.getText().toString());

                    new HttpPostRequestTask(petJSON.toString(),AddPetActivity.this).execute("https://kennel-server.herokuapp.com/newuser");
                }
                catch(Exception e)
                {
                    Log.d("Error from Add Pet: ", e.getMessage());
                }

                switchToDashboard();
            }
        });

    }

    void switchToDashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("uid", userID);
        //bundle.putString("add_pet_id", petID);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onHttpPostDone(String result) {
        //petID = result;
        Log.d("Added pet", result);
    }
}
